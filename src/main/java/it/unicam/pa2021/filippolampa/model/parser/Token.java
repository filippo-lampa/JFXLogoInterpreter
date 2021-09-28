package it.unicam.pa2021.filippolampa.model.parser;

/**
 * Definisce un Token associando ad ogni simbolo un nome identificativo
 */
public class Token {

    public static final Token TOKEN_BRACKET_LEFT = new Token(TokenType.BRACKET_LEFT, "[");

    public static final Token TOKEN_BRACKET_RIGHT = new Token(TokenType.BRACKET_RIGHT, "]");

    public enum TokenType {

        CHARACTERS("CHARACTERS"),
        NUMBER("NUMBER"),
        BRACKET_LEFT("BRACKET_LEFT"),
        BRACKET_RIGHT("BRACKET_RIGHT");

        private final String tokenName;

        TokenType(final String tokenName) {
            this.tokenName = tokenName;
        }

        @Override
        public String toString() {
            return this.tokenName;
        }
    }

    private final String value;

    private final TokenType type;

    private Token(final TokenType type, final String value) {
        this.type = type;
        this.value = value;
    }

    public String value() {
        return this.value;
    }

    public TokenType type() {
        return this.type;
    }

    public boolean matchesType(final TokenType otherTokenType) {
        return this.type.equals(otherTokenType);
    }

    public boolean matchesValue(final String otherTokenValue) {
        return this.value.equalsIgnoreCase(otherTokenValue);
    }

    public boolean matches(final TokenType otherTokenType, final String otherTokenValue) {
        return matchesType(otherTokenType) && matchesValue(otherTokenValue);
    }

    @Override
    public String toString() {
        return this.type + "[value=\"" + this.value + "\"]";
    }

    public static Token number(final String number) {
        return new Token(TokenType.NUMBER, number);
    }

    public static Token characters(final String characters) {
        return new Token(TokenType.CHARACTERS, characters);
    }
}
