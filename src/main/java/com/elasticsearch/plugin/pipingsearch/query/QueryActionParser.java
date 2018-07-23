package com.elasticsearch.plugin.pipingsearch.query;

import com.elasticsearch.plugin.pipingsearch.query.action.*;
import com.elasticsearch.plugin.pipingsearch.query.grammar.QueryExprLexer;
import com.elasticsearch.plugin.pipingsearch.query.grammar.QueryExprParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.elasticsearch.client.node.NodeClient;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class QueryActionParser {
    private NodeClient _client;
    private QueryExprLexer _lexer;
    private QueryExprParser _parser;
    private Map<String, String> _globalParams;

    public QueryActionParser(NodeClient client, Map<String, String> globalParams){
        this._client = client;
        this._lexer = new QueryExprLexer(CharStreams.fromString(""));
        this._parser = new QueryExprParser(new CommonTokenStream(this._lexer));
        this._globalParams = globalParams;
    }

    public List<IAction> getActionList(String query) throws Exception{
        this._lexer.reset();
        this._parser.reset();

        InputStream stream = new ByteArrayInputStream(query.getBytes(StandardCharsets.UTF_8));
        this._lexer.setInputStream(CharStreams.fromStream(stream, StandardCharsets.UTF_8));
        this._parser.setInputStream(new CommonTokenStream(this._lexer));

        if(this._parser.getNumberOfSyntaxErrors() > 0){
            throw new Exception("Syntax error");
        }
        QueryExprParser.ExprContext expr = this._parser.expr();
        return this.getActionList(expr);
    }

    private  List<IAction> getActionList(QueryExprParser.ExprContext expr){
        ArrayList<IAction> actionList = new ArrayList<IAction>();

        expr.actions().forEach(action -> {
            String actionName = action.STRING().getText();
            List<String> params = new ArrayList<>();
            Map<String, String> keyValueParams = new LinkedHashMap<>();
            List<IAction> subActions = new ArrayList<>();

            if(action.params() != null && action.params().param() != null) {
                action.params().param().forEach(param -> {
                    if (param.STRING() != null) {
                        params.add(trimString(param.STRING().getText()));
                    }
                    if (param.kv() != null) {
                        String key = trimString(param.kv().STRING(0).getText());
                        String value = trimString(param.kv().STRING(1).getText());
                        keyValueParams.put(key, value);
                    }
                    if (param.expr() != null) {
                        subActions.addAll(getActionList(param.expr()));
                    }
                });
            }
            IAction a = createAction(actionName, params, keyValueParams, subActions);
            if(a != null){
                actionList.add(a);
            }
        });

        return actionList;
    }

    private String trimString(String str){
        str = str.trim();
        if(str.startsWith("\"") && str.endsWith("\"")){
            str = str.substring(1, str.length()-1);
        }
        if(str.startsWith("'") && str.endsWith("'")){
            str = str.substring(1, str.length()-1);
        }
        return str;
    }

    private IAction createAction(String actionName, List<String> params, Map<String, String> keyValueParams, List<IAction> subActions){
        //assign global param
        if(this._globalParams != null) {
            this._globalParams.forEach((k, v) -> {
                keyValueParams.putIfAbsent(k, v);
            });
        }

        //TODO: change to load from IAction getKey
        switch(actionName.toLowerCase(Locale.getDefault())){
            case "search":
                return new SearchAction(this._client, params, keyValueParams, subActions);
            case "table":
                return new TableAction(this._client, params, keyValueParams, subActions);
            case "rename":
                return new RenameAction(this._client, params, keyValueParams, subActions);
            case "eval":
                return new EvalAction(this._client, params, keyValueParams, subActions);
            case "append":
                return new AppendAction(this._client, params, keyValueParams, subActions);
            case "sort":
                return new SortAction(this._client, params, keyValueParams, subActions);
            case "join":
                return new JoinAction(this._client, params, keyValueParams, subActions);
            case "lookup":
                return new LookupAction(this._client, params, keyValueParams, subActions);
            default:
                return null;
        }
    }
}
