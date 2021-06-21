package ex42;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TokenTest {

    @Test
    void typeToString() {
        Token a = new Token(TokenType.FNAME, "Bob");
        Token b = new Token(TokenType.LNAME, "Bob2");
        Token c = new Token(TokenType.SALARY, "Bob3");
        Token d = new Token(TokenType.EOF, "Bob4");
        Token e = new Token(TokenType.UNKNOWN, "Jerry");
        assertAll(() -> Token.typeToString(a.type).equals("First Name"),
                () -> Token.typeToString(b.type).equals("Last Name"),
                () -> Token.typeToString(c.type).equals("Salary"),
                () -> Token.typeToString(d.type).equals("EOF"),
                () -> Token.typeToString(e.type).equals("Unknown"));
    }

    @Test
    void testToString() {
        Token t = new Token(TokenType.EOF, "test");
        assert(t.toString().equals("{<EOF> `test`}"));
    }
}