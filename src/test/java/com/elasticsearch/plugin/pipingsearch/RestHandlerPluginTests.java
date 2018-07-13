package com.elasticsearch.plugin.pipingsearch;

import com.elasticsearch.plugin.pipingsearch.query.grammar.QueryExprLexer;
import com.elasticsearch.plugin.pipingsearch.query.grammar.QueryExprParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.elasticsearch.test.ESTestCase;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class RestHandlerPluginTests extends ESTestCase {
    public void testThatWotker() throws Exception{

    }

    public void testAntlr() throws Exception{
        QueryExprLexer lexer = new QueryExprLexer(CharStreams.fromString(""));
        QueryExprParser parser = new QueryExprParser(new CommonTokenStream(lexer));

        lexer.reset();
        parser.reset();

        InputStream stream = new ByteArrayInputStream(("search aaa index=filebeat-* kk=\"value_value\" k=\"aa\" | " +
                "join [search index=aaa]").getBytes(StandardCharsets.UTF_8));
        lexer.setInputStream(CharStreams.fromStream(stream, StandardCharsets.UTF_8));
        parser.setInputStream(new CommonTokenStream(lexer));


        QueryExprParser.ExprContext context = parser.expr();
        assertEquals(0, parser.getNumberOfSyntaxErrors());
        assertEquals("([] ([10] search   ([36 10] ([39 36 10] aaa)   " +
                "([45 36 10] ([55 45 36 10] index = filebeat-*))   " +
                "([45 36 10] ([55 45 36 10] kk = \"value_value\"))   " +
                "([45 36 10] ([55 45 36 10] k = \"aa\"))))   |   " +
                "([24] join   ([36 24] ([39 36 24] [ ([52 39 36 24] ([10 52 39 36 24] " +
                "search   ([36 10 52 39 36 24] ([39 36 10 52 39 36 24] ([55 39 36 10 52 39 36 24] " +
                "index = aaa))))) ]))))", context.toStringTree());

    }

    public void testAntlr2() throws Exception{
    }
}
