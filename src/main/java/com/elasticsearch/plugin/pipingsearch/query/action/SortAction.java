package com.elasticsearch.plugin.pipingsearch.query.action;

import org.elasticsearch.client.node.NodeClient;

import java.math.BigDecimal;
import java.util.*;

public class SortAction extends BaseAction {
    public SortAction(NodeClient client, List<String> params, Map<String, String> keyValueParams, List<IAction> subActions) {
        super(client, params, keyValueParams, subActions);
    }

    @Override
    public String getKey() {
        return "sort";
    }

    @Override
    public Iterator<Map<String, Object>> getIterator(Iterator<Map<String, Object>> source) throws Exception {
        Map<String, String> sortFields = this.getKeyParamsExcludeDefault();

        List<Map<String, Object>> list = new ArrayList<>();
        while (source.hasNext()) {
            list.add(source.next());
        }

        Collections.sort(list, (o1, o2) -> {
            for(String key : sortFields.keySet()){
                int orderDirection = 1;
                String order = sortFields.get(key);
                if(order.toLowerCase().equals("desc")){
                    orderDirection = -1;
                }

                Object o1Value = o1.get(key);
                Object o2Value = o2.get(key);

                if(o1Value == null && o2Value == null){
                }else if(o1Value == null && o2Value != null){
                    return orderDirection;
                }else if(o1Value != null && o2Value == null){
                    return -orderDirection;
                }else{
                    if(o1Value instanceof Number && o2Value instanceof Number){
                        int o = new BigDecimal(o1Value.toString()).compareTo(new BigDecimal(o2Value.toString()));
                        if(o != 0){
                            return o * orderDirection;
                        }
                    }else{
                        int o = o1Value.toString().compareTo(o2Value.toString());
                        if(o != 0){
                            return o * orderDirection;
                        }
                    }
                }
            }
            return 0;
        });
        return list.iterator();
    }
}
