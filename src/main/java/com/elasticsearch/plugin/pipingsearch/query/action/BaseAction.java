package com.elasticsearch.plugin.pipingsearch.query.action;

import org.elasticsearch.client.node.NodeClient;

import java.util.*;

public abstract class BaseAction implements IAction {
    protected NodeClient _client;
    protected List<String> _params;
    protected Map<String, String> _keyValueParams;
    protected List<IAction> _subActions;

    public BaseAction(NodeClient client, List<String> params, Map<String, String> keyValueParams, List<IAction> subActions){
        this._client = client;
        this._params = params;
        this._keyValueParams = keyValueParams;
        this._subActions = subActions;
    }

    public List<String> getParams(){ return this._params; }

    public Map<String, String> getKeyValueParams(){
        return this._keyValueParams;
    }

    public List<IAction> getSubActions(){
        return this._subActions;
    }

    protected String getKeyParam(String key, String errorMessage) throws Exception{
        String param = this._keyValueParams.get(key);
        if(errorMessage != null && param == null){
            throw new Exception(errorMessage);
        }
        return param;
    }

    protected String[] getKeyParamInArray(String key, String errorMessage) throws Exception{
        String param = this.getKeyParam(key, errorMessage);
        if(param != null) {
            return param.split(",(?![^()]*+\\))");
        }
        return null;
    }

    protected Map<String, String> getKeyParamsExcludeDefault(String... keys){
        Map<String, String> newParams = new LinkedHashMap<>(this._keyValueParams);
        for (String key : keys) {
            newParams.remove(key);
        }
        for(String key : newParams.keySet().toArray(new String[0])){
            if(key.startsWith("_")){
                newParams.remove(key);
            }
        }
        return newParams;
    }
}
