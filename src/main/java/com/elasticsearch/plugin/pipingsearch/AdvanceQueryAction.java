package com.elasticsearch.plugin.pipingsearch;

import com.elasticsearch.plugin.pipingsearch.query.QueryActionParser;
import com.elasticsearch.plugin.pipingsearch.query.ResultBuilder;
import com.elasticsearch.plugin.pipingsearch.query.action.IAction;
import org.elasticsearch.client.node.NodeClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.rest.*;
import org.joda.time.DateTime;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.elasticsearch.rest.RestRequest.Method.GET;
import static org.elasticsearch.rest.RestRequest.Method.POST;

public class AdvanceQueryAction extends BaseRestHandler {

    public AdvanceQueryAction(final Settings settings, final RestController controller) {
        super(settings);
        controller.registerHandler(GET, "/_piping_search", this);
        controller.registerHandler(POST, "/_piping_search", this);
    }

    @Override
    public String getName() {
        return "piping_search_action";
    }

    @Override
    protected RestChannelConsumer prepareRequest(RestRequest request, NodeClient client) throws IOException {
        try {
            Map<String, Object> requestMap = request.contentOrSourceParamParser().map();
            String query = requestMap.get("query").toString();
            Object rangeMap = requestMap.get("date_range");
            String timeField = "@timestamp";
            DateTime fromDateTime = DateTime.now().plusHours(-12);
            DateTime toDateTime = DateTime.now();
            if(rangeMap instanceof Map){
                timeField = ((Map) rangeMap).get("field").toString();
                String from = ((Map) rangeMap).get("from").toString();
                String to = ((Map) rangeMap).get("to").toString();

                System.out.println("from : " + from);
                System.out.println("to : " + to);
                fromDateTime = DateTime.parse(from);
                toDateTime = DateTime.parse(to);
            }

            Map<String, String> globalParams = new HashMap<>();
            globalParams.put("_@timestamp", timeField);
            globalParams.put("_from", fromDateTime.toString("yyyy-MM-dd'T'HH:mm:ss'Z'"));
            globalParams.put("_to", toDateTime.toString("yyyy-MM-dd'T'HH:mm:ss'Z'"));

            return channel -> {
                //query to list of action
                QueryActionParser parser = new QueryActionParser(client, globalParams);
                List<IAction> actionList = parser.getActionList(query);

                //run list of action
                Iterator<Map<String, Object>> output = null;
                for(IAction action : actionList){
                    output = action.getIterator(output);
                }

                //build the return result
                ResultBuilder builder = new ResultBuilder();
                channel.sendResponse(builder.buildResponse(query, output));
            };
        }catch (Exception ex){
            System.out.println(ex);
            return channel -> {
                channel.sendResponse(new BytesRestResponse(channel, ex));
            };
        }
    }
}