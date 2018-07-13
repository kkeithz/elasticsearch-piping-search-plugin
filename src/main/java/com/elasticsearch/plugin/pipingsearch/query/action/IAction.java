package com.elasticsearch.plugin.pipingsearch.query.action;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public interface IAction {
    String getKey();
    List<String> getParams();
    Map<String, String> getKeyValueParams();
    List<IAction> getSubActions();
    Iterator<Map<String,Object>> getIterator(Iterator<Map<String,Object>> source) throws Exception;
}
