package com.elasticsearch.plugin.pipingsearch.query.action;

import org.elasticsearch.client.node.NodeClient;

import java.util.*;

public class TableAction extends BaseAction{
    public TableAction(NodeClient client, List<String> params, Map<String, String> keyValueParams, List<IAction> subActions) {
        super(client, params, keyValueParams, subActions);
    }

    public String getKey(){
        return "table";
    }

    @Override
    public Iterator<Map<String, Object>> getIterator(Iterator<Map<String, Object>> source) throws Exception {
        String[] keys = this.getKeyParam("field", "Please provide field").split(",");
        //filter current source
        return new Iterator<Map<String, Object>>() {
            @Override
            public boolean hasNext() {
                return source.hasNext();
            }

            @Override
            public Map<String, Object> next() {
                Map<String, Object> row = source.next();
                LinkedHashMap<String, Object> newRow = new LinkedHashMap<>();
                for(String key : keys){
                    newRow.put(key, row.get(key));
                }
                return newRow;
            }
        };
    }
}
