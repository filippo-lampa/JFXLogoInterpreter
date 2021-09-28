package it.unicam.pa2021.filippolampa.model.parser;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

class LexerTest {

    @Test
    void tokenizeTest() {
        final String program = "forward[ 100 [fd] 200 pd penup ]pendown";
        ArrayList<Token>tokenTestList = new ArrayList<>();
        Token tokenOne = Token.characters("forward");
        Token tokenTwo = Token.TOKEN_BRACKET_LEFT;
        Token tokenThree = Token.number("100");
        Token tokenFour = Token.TOKEN_BRACKET_LEFT;
        Token tokenFive = Token.characters("fd");
        Token tokenSix = Token.TOKEN_BRACKET_RIGHT;
        Token tokenSeven = Token.number("200");
        Token tokenEight = Token.characters("pd");
        Token tokenNine = Token.characters("penup");
        Token tokenTen = Token.TOKEN_BRACKET_RIGHT;
        Token tokenEleven = Token.characters("pendown");
        tokenTestList.add(tokenOne);
        tokenTestList.add(tokenTwo);
        tokenTestList.add(tokenThree);
        tokenTestList.add(tokenFour);
        tokenTestList.add(tokenFive);
        tokenTestList.add(tokenSix);
        tokenTestList.add(tokenSeven);
        tokenTestList.add(tokenEight);
        tokenTestList.add(tokenNine);
        tokenTestList.add(tokenTen);
        tokenTestList.add(tokenEleven);
        final Lexer lexer = new Lexer(program);
        ArrayList<Token> tokenizingResult = lexer.tokenize();
        for(int i=0; i<tokenizingResult.size(); i++){
            assertEquals(tokenizingResult.get(i).value(), tokenTestList.get(i).value());
            assertTrue(tokenizingResult.get(i).matchesType(tokenTestList.get(i).type()));
        }
    }
}