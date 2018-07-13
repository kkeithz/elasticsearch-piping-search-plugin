package com.elasticsearch.plugin.pipingsearch.query.action;

import org.elasticsearch.client.node.NodeClient;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class RenameAction extends BaseAction{

    public RenameAction(NodeClient client, List<String> params, Map<String, String> keyValueParams, List<IAction> subActions) {
        super(client, params, keyValueParams, subActions);
    }

    public String getKey(){
        return "rename";
    }

    @Override
    public Iterator<Map<String, Object>> getIterator(Iterator<Map<String, Object>> source) throws Exception {
        //filter current source
        return new Iterator<Map<String, Object>>() {
            @Override
            public boolean hasNext() {
                return source.hasNext();
            }

            @Override
            public Map<String, Object> next() {
                Map<String, Object> row = source.next();
                getKeyParamsExcludeDefault().forEach((k,v)->{
                    row.put(k, row.get(v));
                });
                return row;
            }
        };
    }
}