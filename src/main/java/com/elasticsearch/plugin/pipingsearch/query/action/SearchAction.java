package com.elasticsearch.plugin.pipingsearch.query.action;

import org.apache.lucene.util.FilterIterator;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.node.NodeClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.MultiBucketsAggregation;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.NumericMetricsAggregation;
import org.elasticsearch.search.aggregations.metrics.tophits.TopHits;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.joda.time.DateTime;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchAction extends BaseAction{
    private Pattern AggsFieldPattern = Pattern.compile("(\\w+)\\(([^\\s]+)\\)");

    public SearchAction(NodeClient client, List<String> params, Map<String, String> keyValueParams, List<IAction> subActions) {
        super(client, params, keyValueParams, subActions);
    }


    public String getKey(){
        return "search";
    }

    @Override
    public Iterator<Map<String, Object>> getIterator(Iterator<Map<String, Object>> source) throws Exception {
        if(source == null){
            return this.performQuery();
        }else{
            return this.performFilter(source);
        }
    }

    private Iterator<Map<String, Object>> performQuery() throws Exception{
        String[] indexes = this.getKeyParamInArray("index", "Please provide index");
        String[] fields = this.getKeyParamInArray("field", null);
        String[] groups = this.getKeyParamInArray("group", null);
        Map<String, String> matches = this.getKeyParamsExcludeDefault("index", "field", "group");

        SearchRequestBuilder builder = this._client.prepareSearch(indexes);
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        matches.forEach((k,v) -> {
            boolQueryBuilder.must(QueryBuilders.matchQuery(k, v));
        });

        String timeField = this.getKeyParam("_@timestamp", "Please provide date range field");
        boolQueryBuilder.must(QueryBuilders.rangeQuery(timeField)
                .from(DateTime.parse(this.getKeyParam("_from", "Please provide date range")))
                .to(DateTime.parse(this.getKeyParam("_to", "Please provide date range"))));

        builder.setQuery(boolQueryBuilder);

        builder.addSort(SortBuilders.fieldSort(timeField).order(SortOrder.DESC));

        if(groups != null){
            builder.setSize(0);
            AggregationBuilder aggsBuilder = null;
            for (String group : groups) {
                AggregationBuilder newAggsBuilder = null;
                if(group.startsWith("datehistogram")){
                    Matcher matcher = AggsFieldPattern.matcher(group);
                    if(matcher.find()){
                        String paramStr = matcher.group(2);
                        String[] params = paramStr.split(",");
                        DateHistogramAggregationBuilder dateHistogramBuilder = AggregationBuilders
                                .dateHistogram(params[0])
                                .field(params[0]);
                        String intervalType = params[1].substring(params[1].length()-1).toLowerCase();
                        int interval = Integer.parseInt(params[1].substring(0, params[1].length()-1));

                        switch(intervalType){
                            case "s":
                                dateHistogramBuilder = dateHistogramBuilder.dateHistogramInterval(DateHistogramInterval.seconds(interval));
                                break;
                            case "m":
                                dateHistogramBuilder = dateHistogramBuilder.dateHistogramInterval(DateHistogramInterval.minutes(interval));
                                break;
                            case "h":
                                dateHistogramBuilder = dateHistogramBuilder.dateHistogramInterval(DateHistogramInterval.hours(interval));
                                break;
                            case "d":
                                dateHistogramBuilder = dateHistogramBuilder.dateHistogramInterval(DateHistogramInterval.days(interval));
                                break;
                            case "w":
                                dateHistogramBuilder = dateHistogramBuilder.dateHistogramInterval(DateHistogramInterval.weeks(interval));
                                break;
                            default:
                                throw new Exception("Please provide valid interval");
                        }

                        newAggsBuilder = dateHistogramBuilder;
                    }else{
                        throw new Exception("datehistogram params not correct");
                    }
                }else {
                    newAggsBuilder = AggregationBuilders.terms(group).field(group).size(10000);
                }

                if(aggsBuilder == null){
                    builder.addAggregation(newAggsBuilder);
                }else{
                    aggsBuilder.subAggregation(newAggsBuilder);
                }
                aggsBuilder = newAggsBuilder;
            }

            if(fields == null){
                throw new Exception("Please set field in grouping");
            }else{
                for(String field : fields){
                    Matcher matcher = AggsFieldPattern.matcher(field);
                    if(matcher.find()){
                        String aggs = matcher.group(1);
                        String fieldName = matcher.group(2);
                        switch(aggs){
                            case "sum":
                                aggsBuilder.subAggregation(AggregationBuilders.sum("sum_"+fieldName).field(fieldName));
                                break;
                            case "avg":
                                aggsBuilder.subAggregation(AggregationBuilders.avg("avg_"+fieldName).field(fieldName));
                                break;
                            case "count":
                                aggsBuilder.subAggregation(AggregationBuilders.count("count_"+fieldName).field(fieldName));
                                break;
                            case "max":
                                aggsBuilder.subAggregation(AggregationBuilders.max("max_"+fieldName).field(fieldName));
                                break;
                            case "min":
                                aggsBuilder.subAggregation(AggregationBuilders.min("min_"+fieldName).field(fieldName));
                                break;
                            case "latest":
                                aggsBuilder.subAggregation(AggregationBuilders.topHits("latest_"+fieldName)
                                        .fetchSource(fieldName, null)
                                        .sort("@timestamp", SortOrder.DESC)
                                        .size(1)
                                );
                                break;
                            default:
                                throw new Exception("Aggs " + aggs + " is not support");
                        }
                    }
                }
            }
        }else{
            builder.setSize(1000);
        }

        System.out.println("Request : " + builder.toString());

        SearchResponse searchResponse = builder.get(TimeValue.timeValueSeconds(15));
        if(searchResponse.isTimedOut()){
            throw new Exception("Query is more than 15 second, please modify the search query");
        }

        /*
        String query = this._params.get("q");
        System.out.println("Query : " + query);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        SearchModule searchModule = new SearchModule(Settings.EMPTY, false, Collections.emptyList());
        XContentParser parser = XContentFactory.xContent(XContentType.JSON).createParser(new NamedXContentRegistry(searchModule.getNamedXContents()), query);
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.source(new SearchSourceBuilder());
        searchRequest.source().parseXContent(parser);
        SearchResponse searchResponse = this._client.search(searchRequest).actionGet();
        */

        if(searchResponse.getAggregations() != null){
            //handle aggregation
            List<Map<String, Object>> results = flattenAggregation(searchResponse.getAggregations().iterator().next());

            return new Iterator<Map<String, Object>>() {
                private int _index = 0;

                @Override
                public boolean hasNext() {
                    return results.size() > this._index;
                }

                @Override
                public Map<String, Object> next() {
                    Map<String, Object> output = results.get(this._index);
                    this._index++;
                    return output;
                }
            };
        }else {
            return new Iterator<Map<String, Object>>() {
                private int _index = 0;

                @Override
                public boolean hasNext() {
                    return searchResponse.getHits().getHits().length > this._index;
                }

                @Override
                public Map<String, Object> next() {
                    Map<String, Object> output = new HashMap<>();
                    try {
                        SearchHit hit = searchResponse.getHits().getAt(this._index);
                        this._index++;
                        Map<String, Object> sourceMap = hit.getSourceAsMap();
                        flatten(sourceMap, output, null);
                    } catch (Exception ex) {
                        System.out.println(ex.toString());
                    }
                    return output;
                }
            };
        }
    }

    private Iterator<Map<String, Object>> performFilter(Iterator<Map<String, Object>> source) throws Exception{
        Map<String, String> matchingFieldMap = this.getKeyParamsExcludeDefault();
        return new FilterIterator<Map<String, Object>, Map<String, Object>>(source) {
            @Override
            protected boolean predicateFunction(Map<String, Object> object) {
                boolean isMatch = true;
                for(String key : matchingFieldMap.keySet()){
                    String value = matchingFieldMap.get(key);
                    if(object.get(key) != null && object.get(key).toString().equals(value)){
                        //match
                    }else{
                        //not match
                        isMatch = false;
                        break;
                    }
                }
                return isMatch;
            }
        };
    }

    @SuppressWarnings("unchecked")
    private void flatten(Map<String, Object> map, Map<String, Object> output, String key) throws Exception {
        String prefix = "";
        if (key != null) {
            prefix = key + ".";
        }
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String currentKey = prefix + entry.getKey();
            if (entry.getValue() instanceof Map) {
                flatten((Map<String, Object>) entry.getValue(), output, prefix + entry.getKey());
            } else if (entry.getValue() instanceof List) {
                output.put(currentKey, entry.getValue());
            } else {
                output.put(currentKey, entry.getValue());
            }
        }
    }

    private List<Map<String, Object>> flattenAggregation(Aggregation aggregation) throws Exception{
        List<Map<String, Object>> resultList = new ArrayList<>();
        Map<String, Object> groupResult = new HashMap<>();
        Map<String, Object> valueResult = new HashMap<>();
        this.loopAggregation(aggregation, resultList, groupResult, valueResult);
        return resultList;
    }

    private void loopAggregation(Aggregation aggregation, List<Map<String, Object>> resultList, Map<String, Object> groupResult, Map<String, Object> valueResult) throws Exception{
        if(aggregation instanceof MultiBucketsAggregation){
            MultiBucketsAggregation multiBucketsAggregation = (MultiBucketsAggregation)aggregation;
            String key = multiBucketsAggregation.getName();
            for(MultiBucketsAggregation.Bucket item : multiBucketsAggregation.getBuckets()){
                Object value = item.getKey();
                groupResult.put(key, value);

                valueResult.clear();
                boolean hasValue = false;
                for(Aggregation aggs : item.getAggregations()){
                    if(aggs instanceof Terms){
                        this.loopAggregation((Terms)aggs, resultList, groupResult, valueResult);
                    }
                    if(aggs instanceof NumericMetricsAggregation.SingleValue){
                        String k = ((NumericMetricsAggregation.SingleValue) aggs).getName();
                        Object v = ((NumericMetricsAggregation.SingleValue) aggs).value();
                        valueResult.put(k, v);
                        hasValue = true;
                    }
                    if(aggs instanceof TopHits){
                        Map<String, Object> output = new HashMap<>();
                        Map<String, Object> sourceMap = ((TopHits) aggs).getHits().getAt(0).getSourceAsMap();
                        flatten(sourceMap, output, null);
                        valueResult.putAll(output);
                        hasValue = true;
                    }
                }

                if(hasValue){
                    HashMap<String, Object> result = new HashMap<String, Object>();
                    result.putAll(groupResult);
                    result.putAll(valueResult);
                    resultList.add(result);
                }
            }
        }
    }
}
