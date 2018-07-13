package com.elasticsearch.plugin.pipingsearch.query.action;

import org.elasticsearch.client.node.NodeClient;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AppendAction extends BaseAction {
    public AppendAction(NodeClient client, List<String> params, Map<String, String> keyValueParams, List<IAction> subActions) {
        super(client, params, keyValueParams, subActions);
    }

    @Override
    public String getKey() {
        return "append";
    }

    @Override
    public Iterator<Map<String, Object>> getIterator(Iterator<Map<String, Object>> source) throws Exception {
        Iterator<Map<String, Object>> output = null;
        if(this._subActions != null){
            //run list of action
            for(IAction action : this._subActions){
                output = action.getIterator(output);
            }
        }
        Iterator<Map<String, Object>> appendSource = output;
        return new Iterator<Map<String, Object>>() {
            @Override
            public boolean hasNext() {
                return source.hasNext() || (appendSource != null && appendSource.hasNext());
            }

            @Override
            public Map<String, Object> next() {
                if(source.hasNext()){
                    return source.next();
                }
                if(appendSource != null && appendSource.hasNext()){
                    return appendSource.next();
                }
                return null;
            }
        };
    }
}
