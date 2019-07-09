package com.github.monkeyj;

import com.github.monkeyj.ast.LetStatement;
import com.github.monkeyj.ast.Program;
import com.github.monkeyj.ast.Statement;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

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

        Assert.assertEquals(3, program.getStatements().size());
        List<Statement> statements = program.getStatements();
        Assert.assertEquals(statements.get(0).getClass(), LetStatement.class);
        LetStatement stmt = (LetStatement)statements.get(0);
        Assert.assertEquals("x", stmt.getName().tokenLiteral());
        stmt = (LetStatement)statements.get(1);
        Assert.assertEquals("y", stmt.getName().tokenLiteral());
        stmt = (LetStatement)statements.get(2);
        Assert.assertEquals("foobar", stmt.getName().tokenLiteral());
    }

    @Test
    public void testReturnStatement() {
        String input = "return 5;" +
                "return 10;" +
                "return 993322;";

        Program program = parse(input);


    }
}
