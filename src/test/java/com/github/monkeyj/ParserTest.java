package com.github.monkeyj;

import com.github.monkeyj.ast.*;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class ParserTest {
    private Parser parse(String input) {
        Lexer lexer = new Lexer(input);
        return new Parser(lexer);

    }
    @Test
    public void testLetStatement() {
        String input = "let x = 5;" +
                "let y = 10;" +
                "let foobar = 838383;";


        Program program = parse(input).parseProgram();

        Assert.assertNotNull(program);

        assertEquals(3, program.getStatements().size());
        List<Statement> statements = program.getStatements();
        assertEquals(statements.get(0).getClass(), LetStatement.class);
        LetStatement stmt = (LetStatement)statements.get(0);
        System.out.println(stmt.toString());
        assertEquals("x", stmt.getName().tokenLiteral());
        stmt = (LetStatement)statements.get(1);
        System.out.println(stmt.toString());
        assertEquals("y", stmt.getName().tokenLiteral());
        stmt = (LetStatement)statements.get(2);
        System.out.println(stmt.toString());
        assertEquals("foobar", stmt.getName().tokenLiteral());
    }

    @Test
    public void testReturnStatement() {
        String input = "return 5;" +
                "return 10;" +
                "return 993322;";

        Parser parser = parse(input);
        checkParserErrors(parser);

        Program program = parser.parseProgram();

        for(Statement stmt : program.getStatements()) {
            assertEquals(ReturnStatement.class, stmt.getClass());

        }

    }

    @Test
    public void testIdentifierExpression() {
        Program program = parse("foobar;").parseProgram();
        assertEquals(1, program.getStatements().size());
        ExpressionStatement stmt = (ExpressionStatement)program.getStatements().get(0);
        Identifier identifier = (Identifier)stmt.getExpression();
    }
    @Test
    public void testIntegerLiteralExpression() {
        Program program = parse("5;").parseProgram();

        assertEquals(1, program.getStatements().size());
        ExpressionStatement expr = (ExpressionStatement)program.getStatements().get(0);
        IntegerLiteral literal = (IntegerLiteral)expr.getExpression();
        assertSame(5, literal.getValue());
        assertEquals("5", literal.tokenLiteral());
    }

    @Test
    public void testParsePrefixExpression() {
        Parser parser = parse("!5;-15;");


        Program program = parser.parseProgram();
        checkParserErrors(parser);
        List<Statement> statements = program.getStatements();
        assertSame(2, statements.size());

    }

    @Test
    public void testInfixExpression() {
        Parser parser = parse("5 - 5;5 + 5;5 != 5;");
        Program program = parser.parseProgram();
        checkParserErrors(parser);
        List<Statement> statements = program.getStatements();
        assertSame(3, statements.size());
        ExpressionStatement  es = (ExpressionStatement)statements.get(0);
        InfixExpression ex = (InfixExpression)es.getExpression();
        assertIntegerLiteral(5, ex.getLeft());
        assertEquals("-", ex.getOperator());
        assertIntegerLiteral(5, ex.getRight());

        es = (ExpressionStatement)statements.get(1);
        ex = (InfixExpression)es.getExpression();
        assertIntegerLiteral(5, ex.getLeft());
        assertEquals("+", ex.getOperator());
        assertIntegerLiteral(5, ex.getRight());

        es = (ExpressionStatement)statements.get(2);
        ex = (InfixExpression)es.getExpression();
        assertIntegerLiteral(5, ex.getLeft());
        assertEquals("!=", ex.getOperator());
        assertIntegerLiteral(5, ex.getRight());
    }

    @Test
    public void testOperatorPrecedenceParsing() {
        String[][] tests = new String[][]{
                {"-a * b", "((-a) * b)"},
                {"a + b + c","((a + b) + c)"},
                {"a * b * c","((a * b) * c)"},
                {"a + b / c","(a + (b / c))"}
        };

        for(String[] items : tests) {
            Program p = parse(items[0]).parseProgram();
            assertEquals(items[1], p.toString());
        }
    }
    private void assertIntegerLiteral(int value,Expression expression) {
        assertEquals(IntegerLiteral.class, expression.getClass());
        IntegerLiteral literal = (IntegerLiteral)expression;
        assertSame(value, literal.getValue());
    }
    private void checkParserErrors(Parser parser) {
        List<String> errors = parser.getErrors();
        if(errors.size() < 1) return;

        StringBuilder msg = new StringBuilder();
        msg.append(String.format("parser has %d errors\n", errors.size()));
        for(String error : errors) {
            msg.append(String.format("parser error: %s\n", error));
        }
        Assert.fail(msg.toString());
    }
}
