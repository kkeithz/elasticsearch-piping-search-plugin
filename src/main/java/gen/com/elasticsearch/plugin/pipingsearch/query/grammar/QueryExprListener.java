// Generated from E:/KeithTest/piping-search/src/main/java/com/elasticsearch/plugin/pipingsearch/query/grammar\QueryExpr.g4 by ANTLR 4.7
package com.elasticsearch.plugin.pipingsearch.query.grammar;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link QueryExprParser}.
 */
public interface QueryExprListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link QueryExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(QueryExprParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(QueryExprParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryExprParser#actions}.
	 * @param ctx the parse tree
	 */
	void enterActions(QueryExprParser.ActionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryExprParser#actions}.
	 * @param ctx the parse tree
	 */
	void exitActions(QueryExprParser.ActionsContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryExprParser#params}.
	 * @param ctx the parse tree
	 */
	void enterParams(QueryExprParser.ParamsContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryExprParser#params}.
	 * @param ctx the parse tree
	 */
	void exitParams(QueryExprParser.ParamsContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryExprParser#param}.
	 * @param ctx the parse tree
	 */
	void enterParam(QueryExprParser.ParamContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryExprParser#param}.
	 * @param ctx the parse tree
	 */
	void exitParam(QueryExprParser.ParamContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryExprParser#kv}.
	 * @param ctx the parse tree
	 */
	void enterKv(QueryExprParser.KvContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryExprParser#kv}.
	 * @param ctx the parse tree
	 */
	void exitKv(QueryExprParser.KvContext ctx);
}