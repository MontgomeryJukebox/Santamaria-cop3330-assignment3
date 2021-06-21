package ex42;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LexerTest {

    @Test
    void nextToken() {
        Lexer lex = new Lexer("EXERCISE_43_TEST_INPUT.txt");
        Token t = lex.nextToken();
        assert(t.type == TokenType.FNAME);
        assert(t.lexeme.equals("Hello"));
    }

    @Test
    void hasNextToken() {
        Lexer lex = new Lexer("EXERCISE_43_TEST_INPUT.txt");
        assert(lex.hasNextToken());
    }
}