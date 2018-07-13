// Generated from E:/KeithTest/piping-search/src/main/java/com/elasticsearch/plugin/pipingsearch/query/grammar\QueryExpr.g4 by ANTLR 4.7
package com.elasticsearch.plugin.pipingsearch.query.grammar;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class QueryExprParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, STRING=6, NOQUOTA=7, DQUOTA=8, 
		SQUOTA=9;
	public static final int
		RULE_expr = 0, RULE_actions = 1, RULE_params = 2, RULE_param = 3, RULE_kv = 4;
	public static final String[] ruleNames = {
		"expr", "actions", "params", "param", "kv"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "' '", "'|'", "'['", "']'", "'='"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, "STRING", "NOQUOTA", "DQUOTA", "SQUOTA"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "QueryExpr.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public QueryExprParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ExprContext extends ParserRuleContext {
		public List<ActionsContext> actions() {
			return getRuleContexts(ActionsContext.class);
		}
		public ActionsContext actions(int i) {
			return getRuleContext(ActionsContext.class,i);
		}
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryExprListener ) ((QueryExprListener)listener).enterExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryExprListener ) ((QueryExprListener)listener).exitExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QueryExprVisitor ) return ((QueryExprVisitor<? extends T>)visitor).visitExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		ExprContext _localctx = new ExprContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_expr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(10);
			actions();
			setState(27);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0 || _la==T__1) {
				{
				{
				setState(14);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__0) {
					{
					{
					setState(11);
					match(T__0);
					}
					}
					setState(16);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(17);
				match(T__1);
				setState(21);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__0) {
					{
					{
					setState(18);
					match(T__0);
					}
					}
					setState(23);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(24);
				actions();
				}
				}
				setState(29);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ActionsContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(QueryExprParser.STRING, 0); }
		public ParamsContext params() {
			return getRuleContext(ParamsContext.class,0);
		}
		public ActionsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_actions; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryExprListener ) ((QueryExprListener)listener).enterActions(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryExprListener ) ((QueryExprListener)listener).exitActions(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QueryExprVisitor ) return ((QueryExprVisitor<? extends T>)visitor).visitActions(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ActionsContext actions() throws RecognitionException {
		ActionsContext _localctx = new ActionsContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_actions);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(30);
			match(STRING);
			setState(37);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				{
				setState(32); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(31);
					match(T__0);
					}
					}
					setState(34); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__0 );
				setState(36);
				params();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParamsContext extends ParserRuleContext {
		public List<ParamContext> param() {
			return getRuleContexts(ParamContext.class);
		}
		public ParamContext param(int i) {
			return getRuleContext(ParamContext.class,i);
		}
		public ParamsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_params; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryExprListener ) ((QueryExprListener)listener).enterParams(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryExprListener ) ((QueryExprListener)listener).exitParams(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QueryExprVisitor ) return ((QueryExprVisitor<? extends T>)visitor).visitParams(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParamsContext params() throws RecognitionException {
		ParamsContext _localctx = new ParamsContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_params);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(39);
			param();
			setState(48);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(41); 
					_errHandler.sync(this);
					_la = _input.LA(1);
					do {
						{
						{
						setState(40);
						match(T__0);
						}
						}
						setState(43); 
						_errHandler.sync(this);
						_la = _input.LA(1);
					} while ( _la==T__0 );
					setState(45);
					param();
					}
					} 
				}
				setState(50);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParamContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public KvContext kv() {
			return getRuleContext(KvContext.class,0);
		}
		public TerminalNode STRING() { return getToken(QueryExprParser.STRING, 0); }
		public ParamContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_param; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryExprListener ) ((QueryExprListener)listener).enterParam(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryExprListener ) ((QueryExprListener)listener).exitParam(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QueryExprVisitor ) return ((QueryExprVisitor<? extends T>)visitor).visitParam(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParamContext param() throws RecognitionException {
		ParamContext _localctx = new ParamContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_param);
		try {
			setState(57);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(51);
				match(T__2);
				setState(52);
				expr();
				setState(53);
				match(T__3);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(55);
				kv();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(56);
				match(STRING);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class KvContext extends ParserRuleContext {
		public List<TerminalNode> STRING() { return getTokens(QueryExprParser.STRING); }
		public TerminalNode STRING(int i) {
			return getToken(QueryExprParser.STRING, i);
		}
		public KvContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_kv; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryExprListener ) ((QueryExprListener)listener).enterKv(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryExprListener ) ((QueryExprListener)listener).exitKv(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QueryExprVisitor ) return ((QueryExprVisitor<? extends T>)visitor).visitKv(this);
			else return visitor.visitChildren(this);
		}
	}

	public final KvContext kv() throws RecognitionException {
		KvContext _localctx = new KvContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_kv);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(59);
			match(STRING);
			setState(63);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(60);
				match(T__0);
				}
				}
				setState(65);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(66);
			match(T__4);
			setState(70);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(67);
				match(T__0);
				}
				}
				setState(72);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(73);
			match(STRING);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\13N\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\3\2\3\2\7\2\17\n\2\f\2\16\2\22\13\2\3\2\3\2"+
		"\7\2\26\n\2\f\2\16\2\31\13\2\3\2\7\2\34\n\2\f\2\16\2\37\13\2\3\3\3\3\6"+
		"\3#\n\3\r\3\16\3$\3\3\5\3(\n\3\3\4\3\4\6\4,\n\4\r\4\16\4-\3\4\7\4\61\n"+
		"\4\f\4\16\4\64\13\4\3\5\3\5\3\5\3\5\3\5\3\5\5\5<\n\5\3\6\3\6\7\6@\n\6"+
		"\f\6\16\6C\13\6\3\6\3\6\7\6G\n\6\f\6\16\6J\13\6\3\6\3\6\3\6\2\2\7\2\4"+
		"\6\b\n\2\2\2S\2\f\3\2\2\2\4 \3\2\2\2\6)\3\2\2\2\b;\3\2\2\2\n=\3\2\2\2"+
		"\f\35\5\4\3\2\r\17\7\3\2\2\16\r\3\2\2\2\17\22\3\2\2\2\20\16\3\2\2\2\20"+
		"\21\3\2\2\2\21\23\3\2\2\2\22\20\3\2\2\2\23\27\7\4\2\2\24\26\7\3\2\2\25"+
		"\24\3\2\2\2\26\31\3\2\2\2\27\25\3\2\2\2\27\30\3\2\2\2\30\32\3\2\2\2\31"+
		"\27\3\2\2\2\32\34\5\4\3\2\33\20\3\2\2\2\34\37\3\2\2\2\35\33\3\2\2\2\35"+
		"\36\3\2\2\2\36\3\3\2\2\2\37\35\3\2\2\2 \'\7\b\2\2!#\7\3\2\2\"!\3\2\2\2"+
		"#$\3\2\2\2$\"\3\2\2\2$%\3\2\2\2%&\3\2\2\2&(\5\6\4\2\'\"\3\2\2\2\'(\3\2"+
		"\2\2(\5\3\2\2\2)\62\5\b\5\2*,\7\3\2\2+*\3\2\2\2,-\3\2\2\2-+\3\2\2\2-."+
		"\3\2\2\2./\3\2\2\2/\61\5\b\5\2\60+\3\2\2\2\61\64\3\2\2\2\62\60\3\2\2\2"+
		"\62\63\3\2\2\2\63\7\3\2\2\2\64\62\3\2\2\2\65\66\7\5\2\2\66\67\5\2\2\2"+
		"\678\7\6\2\28<\3\2\2\29<\5\n\6\2:<\7\b\2\2;\65\3\2\2\2;9\3\2\2\2;:\3\2"+
		"\2\2<\t\3\2\2\2=A\7\b\2\2>@\7\3\2\2?>\3\2\2\2@C\3\2\2\2A?\3\2\2\2AB\3"+
		"\2\2\2BD\3\2\2\2CA\3\2\2\2DH\7\7\2\2EG\7\3\2\2FE\3\2\2\2GJ\3\2\2\2HF\3"+
		"\2\2\2HI\3\2\2\2IK\3\2\2\2JH\3\2\2\2KL\7\b\2\2L\13\3\2\2\2\f\20\27\35"+
		"$\'-\62;AH";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}