package com.elasticsearch.plugin.pipingsearch.query.action;

import org.elasticsearch.client.node.NodeClient;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class EvalAction extends BaseAction{
    public EvalAction(NodeClient client, List<String> params, Map<String, String> keyValueParams, List<IAction> subActions) {
        super(client, params, keyValueParams, subActions);
    }

    public String getKey(){
        return "eval";
    }

    @Override
    public Iterator<Map<String, Object>> getIterator(Iterator<Map<String, Object>> source) throws Exception {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");

        //filter current source
        return new Iterator<Map<String, Object>>() {
            @Override
            public boolean hasNext() {
                return source.hasNext();
            }

            @Override
            public Map<String, Object> next() {
                Map<String, Object> row = source.next();
                row.forEach((k,v)->{
                    engine.put(k, v);
                });
                getKeyParamsExcludeDefault().forEach((k,v)->{
                    Object value = null;
                    try {
                        value = engine.eval(v);
                    }catch(Exception ex){
                        value = ex.getMessage();
                    }
                    row.put(k, value);
                });
                return row;
            }
        };
    }
}