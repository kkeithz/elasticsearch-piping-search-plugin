// Generated from E:/KeithTest/piping-search/src/main/java/com/elasticsearch/plugin/pipingsearch/query/grammar\QueryExpr.g4 by ANTLR 4.7
package com.elasticsearch.plugin.pipingsearch.query.grammar;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link QueryExprParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface QueryExprVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link QueryExprParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr(QueryExprParser.ExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link QueryExprParser#actions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitActions(QueryExprParser.ActionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link QueryExprParser#params}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParams(QueryExprParser.ParamsContext ctx);
	/**
	 * Visit a parse tree produced by {@link QueryExprParser#param}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParam(QueryExprParser.ParamContext ctx);
	/**
	 * Visit a parse tree produced by {@link QueryExprParser#kv}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitKv(QueryExprParser.KvContext ctx);
}