package it.unicam.pa2021.filippolampa.model.parser;

import java.util.ArrayList;

/**
 * Trasforma una stringa in input in una sequenza di Token
 */
public class Lexer {

    private static final char EOF = (char) -1;

    private final String stringToTokenize;

    private int currentIndex;

    private char currentChar;

    public Lexer(final String inputString){
        currentIndex = 0;
        currentChar = inputString.charAt(currentIndex);
        stringToTokenize = inputString;
    }

    public ArrayList<Token> tokenize(){
        ArrayList<Token>tokenList = new ArrayList<>();
        while(!isEOF()) {
            if(isNotWhitespace())
                tokenList.add(analyzeCurrentChar());
            updateCurrentChar();
        }
        return tokenList;
    }

    private boolean noMoreCharacters() {
        return this.currentIndex >= this.stringToTokenize.length();
    }

    private char getNextChar(){
        if(currentIndex+1 < stringToTokenize.length())
            return this.stringToTokenize.charAt(currentIndex+1);
        return (char)-1;
    }

    private void updateCurrentChar() {
        this.currentIndex++;
        if (!noMoreCharacters()) {
            this.currentChar = this.stringToTokenize.charAt(currentIndex);
        } else {
            this.currentChar = EOF;
        }
    }

    private Token analyzeCurrentChar() {

        if (isBracketLeft()) {
            return Token.TOKEN_BRACKET_LEFT;
        }

        if (isBracketRight()) {
            return Token.TOKEN_BRACKET_RIGHT;
        }

        if (isNumeric()) {
            return Token.number(readNumber());
        }

        if (isAlpha()) {
            return Token.characters(readCharacters());
        }

        throw new IllegalArgumentException("Impossibile trasformare in token a causa di un simbolo non riconosciuto");

    }

    private boolean matchesCharacterToken(final Token token) {
        return token.value().toCharArray()[0] == this.currentChar;
    }

    private boolean isEOF() {
        return currentChar == EOF;
    }

    private boolean isNotWhitespace() {
        return currentChar != ' ';
    }

    private boolean isNotWhitespace(char character) {
        return character != ' ';
    }

    private boolean isBracketLeft() {
        return matchesCharacterToken(Token.TOKEN_BRACKET_LEFT);
    }

    private boolean isBracketRight() {
        return matchesCharacterToken(Token.TOKEN_BRACKET_RIGHT);
    }

    private boolean isNumeric() {
        return currentChar >= '0' && currentChar <= '9';
    }

    private boolean isNumeric(char character) {
        return character >= '0' && character <= '9';
    }

    private String readNumber() {
        final StringBuilder buffer = new StringBuilder();
        buffer.append(this.currentChar);
        while (currentIndex+1 < stringToTokenize.length() && isNotWhitespace(getNextChar()) && isNumeric(getNextChar())) {
            updateCurrentChar();
            buffer.append(this.currentChar);
        }
        return buffer.toString();
    }

    private boolean isAlpha() {
        return currentChar >= 'A' && currentChar <= 'Z' || currentChar >= 'a' && currentChar <= 'z';
    }

    private boolean isAlpha(char character) {
        return character >= 'A' && character <= 'Z' || character >= 'a' && character <= 'z';
    }

    private boolean isAlphanumeric(char character) {
        return isAlpha(character) || isNumeric(character);
    }

    private String readCharacters() {
        final StringBuilder buffer = new StringBuilder();
        buffer.append(this.currentChar);
        while (currentIndex+1 < stringToTokenize.length() && isNotWhitespace(getNextChar()) && isAlphanumeric(getNextChar())) {
            updateCurrentChar();
            buffer.append(this.currentChar);
        }
        return buffer.toString();
    }

}
