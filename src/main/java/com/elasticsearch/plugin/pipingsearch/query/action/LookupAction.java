package com.elasticsearch.plugin.pipingsearch.query.action;

import com.elasticsearch.plugin.pipingsearch.query.QueryActionParser;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.node.NodeClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class LookupAction extends JoinAction {
    public LookupAction(NodeClient client, List<String> params, Map<String, String> keyValueParams, List<IAction> subActions) {
        super(client, params, keyValueParams, subActions);
    }

    @Override
    public String getKey() {
        return "lookup";
    }

    @Override
    protected Iterator<Map<String, Object>> getSubSearch() throws Exception {
        String lookupTable = this.getKeyParam("table", "Please provide table field");

        SearchRequestBuilder builder = this._client.prepareSearch(".piping-search");
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.matchQuery("_sourcetype", "_lookup_data"));
        boolQueryBuilder.must(QueryBuilders.matchQuery("_name", lookupTable));
        builder.setQuery(boolQueryBuilder);
        builder.setSize(10000);
        System.out.println("Request : " + builder.toString());

        SearchResponse searchResponse = builder.get(TimeValue.timeValueSeconds(15));
        if(searchResponse.isTimedOut()){
            throw new Exception("Query is more than 15 second, please modify the search query");
        }

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
}
