package it.unicam.pa2021.filippolampa.model.parser;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;

/**
 * Definisce le procedure che questa implementazione del linguaggio logo è in grado di comprendere
 **/

public enum Builtins {

    FORWARD(Arrays.asList("forward", "fd"), 1),

    BACK(Arrays.asList("back", "bk"), 1),

    LEFT(Arrays.asList("left", "lt"), 1),

    RIGHT(Arrays.asList("right", "rt"), 1),

    CLEARSCREEN(Arrays.asList("clean", "cs"), 0),

    HOME(List.of("home"), 0),

    PENUP(Arrays.asList("penup", "pu"), 0),

    PENDOWN(Arrays.asList("pendown", "pd"), 0),

    SETPENCOLOR(Arrays.asList("setpc", "setpencolor"), 3),

    SETFILLCOLOR(Arrays.asList("setfc", "setfillcolor"), 3),

    SETSCREENCOLOR(Arrays.asList("setsc", "setscreencolor"), 3),

    SETPENSIZE(Arrays.asList("setps", "setpensize"), 1),

    REPEAT(List.of("repeat"), 1);



    private final List<String> aliases;

        private final int numberOfArguments;

        Builtins(final List<String> aliases, final int numberOfArguments) {
            this.aliases = aliases;
            this.numberOfArguments = numberOfArguments;
        }

        private boolean knownAs(final String functionName) {
            return this.aliases.contains(functionName);
        }

        public static int arity(final String functionName) throws UnknownCommandException {
            final OptionalInt arity = Arrays
                    .stream(Builtins.values())
                    .filter(builtin -> builtin.knownAs(functionName))
                    .mapToInt(builtin -> builtin.numberOfArguments)
                    .findFirst();
            if (arity.isEmpty()) {
                throw new UnknownCommandException("Unable to identify arity of built-in function " + functionName + ".");
            }
            return arity.getAsInt();
        }

}
