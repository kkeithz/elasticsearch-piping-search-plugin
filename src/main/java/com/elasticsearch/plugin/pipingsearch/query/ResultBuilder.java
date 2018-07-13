package com.elasticsearch.plugin.pipingsearch.query;

import org.elasticsearch.common.document.DocumentField;
import org.elasticsearch.common.xcontent.ToXContent;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.rest.BytesRestResponse;
import org.elasticsearch.rest.RestResponse;
import org.elasticsearch.rest.RestStatus;

import java.util.Iterator;
import java.util.Map;

public class ResultBuilder {
    public RestResponse buildResponse(String query, Iterator<Map<String, Object>> output) throws Exception {
        XContentBuilder builder = org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder();

        builder.startObject();
        builder.field("query", query);
        builder.startArray("results");
        //result
        if(output != null) {
            while (output.hasNext()) {
                Map<String, Object> item = output.next();
                builder.startObject();

                item.forEach((k, v) -> {
                    try {
                        builder.field(k, v);
                    } catch (Exception ex) {
                    }
                });

                builder.endObject();
            }
        }

        builder.endArray();
        builder.endObject();

        return new BytesRestResponse(RestStatus.OK, builder);
    }
}
