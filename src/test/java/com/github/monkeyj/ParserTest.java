package com.github.monkeyj;

import com.github.monkeyj.ast.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class ParserTest {
    private Program parse(String input) {
        Lexer lexer = new Lexer(input);
        Parser parser = new Parser(lexer);
        return parser.parseProgram();
    }
    @Test
    public void testLetStatement() {
        String input = "let x = 5;" +
                "let y = 10;" +
                "let foobar = 838383;";


        Program program = parse(input);

        Assert.assertNotNull(program);

        assertEquals(3, program.getStatements().size());
        List<Statement> statements = program.getStatements();
        assertEquals(statements.get(0).getClass(), LetStatement.class);
        LetStatement stmt = (LetStatement)statements.get(0);
        assertEquals("x", stmt.getName().tokenLiteral());
        stmt = (LetStatement)statements.get(1);
        assertEquals("y", stmt.getName().tokenLiteral());
        stmt = (LetStatement)statements.get(2);
        assertEquals("foobar", stmt.getName().tokenLiteral());
    }

    @Test
    public void testReturnStatement() {
        String input = "return 5;" +
                "return 10;" +
                "return 993322;";

        Program program = parse(input);
        checkParserErrors(program);

        for(Statement stmt : program.getStatements()) {
            assertEquals(ReturnStatement.class, stmt.getClass());

        }

    }

    @Test
    public void testIdentifierExpression() {
        Program program = parse("foobar;");
        assertEquals(1, program.getStatements().size());
        ExpressionStatement stmt = (ExpressionStatement)program.getStatements().get(0);
        Identifier identifier = (Identifier)stmt.getExpression();
    }
    @Test
    public void testIntegerLiteralExpression() {
        Program program = parse("5;");

        assertEquals(1, program.getStatements().size());
        ExpressionStatement expr = (ExpressionStatement)program.getStatements().get(0);
        IntegerLiteral literal = (IntegerLiteral)expr.getExpression();
        assertSame(5, literal.getValue());
    }

    private void checkParserErrors(Program program) {
        List<String> errors = program.getErrors();
        if(errors.size() < 1) return;

        System.err.printf("parser has %d errors", errors.size());
        for(String error : errors) {
            System.err.printf("parser error: %s", error);
        }

        throw new AssertionError();
    }
}
