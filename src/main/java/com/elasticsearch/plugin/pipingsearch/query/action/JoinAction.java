package com.elasticsearch.plugin.pipingsearch.query.action;

import org.elasticsearch.client.node.NodeClient;

import java.util.*;

public class JoinAction extends BaseAction {
    public JoinAction(NodeClient client, List<String> params, Map<String, String> keyValueParams, List<IAction> subActions) {
        super(client, params, keyValueParams, subActions);
    }

    @Override
    public String getKey() {
        return "join";
    }

    @Override
    public Iterator<Map<String, Object>> getIterator(Iterator<Map<String, Object>> source) throws Exception {
        String[] joinFields = this.getKeyParamInArray("on", "Please provide on field list");

        Iterator<Map<String, Object>> joinOutput = this.getSubSearch();

        List<Map<String, Object>> joinList = new ArrayList<>();
        while (joinOutput.hasNext()) {
            joinList.add(joinOutput.next());
        }

        List<Map<String, Object>> list = new ArrayList<>();
        while (source.hasNext()) {
            list.add(source.next());
        }

        List<Map<String, Object>> outputList = new ArrayList<>();

        for(Map<String, Object> item : list){
            for(Map<String, Object> joinItem : joinList){
                boolean isMatch = true;
                for(String joinField : joinFields){
                    Object value1 = item.get(joinField);
                    Object value2 = joinItem.get(joinField);

                    if(value1 == null || !value1.equals(value2)){
                        isMatch = false;
                        break;
                    }
                }
                if(isMatch){
                    Map<String, Object> newItem = new HashMap<>();
                    newItem.putAll(item);
                    newItem.putAll(joinItem);
                    outputList.add(newItem);
                }
            }
        }

        return outputList.iterator();
    }

    protected Iterator<Map<String, Object>> getSubSearch() throws Exception{
        Iterator<Map<String, Object>> joinOutput = null;
        if(this._subActions != null){
            //run list of action
            for(IAction action : this._subActions){
                joinOutput = action.getIterator(joinOutput);
            }
        }
        return joinOutput;
    }
}
