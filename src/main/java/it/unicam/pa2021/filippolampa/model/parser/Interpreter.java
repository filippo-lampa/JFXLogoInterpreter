package it.unicam.pa2021.filippolampa.model.parser;

import it.unicam.pa2021.filippolampa.model.logo.ClosedAreaException;
import it.unicam.pa2021.filippolampa.model.logo.Instructions;

import java.util.ArrayList;

public class Interpreter extends AbstractInterpreter<Instructions>{

    public Interpreter(String stringToParse, Instructions instructions, boolean isStepByStep){
        super(stringToParse, instructions, isStepByStep);
    }

    protected Interpreter(ArrayList<Token>tokenList, Instructions instructions, boolean isStepByStep){
        super(tokenList, instructions, isStepByStep);
    }

    private void parseZeroArgumentsInstruction(Token instructionToken){
        switch (instructionToken.value()) {

            case "clean": case "cs": instructions.clearScreen(); break;

            case "home": instructions.home(); break;

            case "penup": case "pu": instructions.penUp(); break;

            case "pendown": case "pd": instructions.penDown(); break;

            default:
                throw new IllegalArgumentException("Comando non riconosciuto");
        }
    }

    private void parseOneArgumentInstruction(Token instructionToken, int argument) throws ClosedAreaException {
        switch (instructionToken.value()) {

            case "forward": case "fd": instructions.moveForward(argument); break;

            case "back": case "bk": instructions.moveBackwards(argument); break;

            case "left": case "lt": instructions.turnLeft(argument); break;

            case "right": case "rt": instructions.turnRight(argument); break;

            case "setps": case "setpensize": instructions.setPenSize(argument); break;

            default:
                throw new IllegalArgumentException("Comando non riconosciuto");
        }
    }

    private void parseThreeArgumentsInstruction(Token instructionToken, int firstArgument, int secondArgument, int thirdArgument){
        switch (instructionToken.value()) {

            case "setpc": case "setpencolor": instructions.setPenColor(firstArgument,secondArgument,thirdArgument); break;

            case "setfc": case "setfillcolor": instructions.setFillColor(firstArgument,secondArgument,thirdArgument); break;

            case "setsc": case "setscreencolor": instructions.setScreenColor(firstArgument,secondArgument,thirdArgument); break;

            default:
                throw new IllegalArgumentException("Comando non riconosciuto");
        }
    }

    private boolean isAlpha(Token token){
        return token.matchesType(Token.TokenType.CHARACTERS);
    }

    private void parseRepeat() throws UnknownCommandException, ClosedAreaException {
        consume();
        int numberOfLoops = Integer.parseInt(getCurrentTokenAndConsume().value());
        //Ignora la parentesi quadra di sinistra
        consume();
        ArrayList<Token> commandsTokens = createRepeatCommandsList();
        Interpreter nestedInterpreter;
        for(int i=0; i<numberOfLoops; i++) {
            nestedInterpreter = createNestedInterpreter(commandsTokens);
            nestedInterpreter.parseAndRun();
        }
        //Ignora la parentesi quadra di destra
        consume();
    }

    private Interpreter createNestedInterpreter(ArrayList<Token> commandsTokens){
     //   if(!stepByStepMode) {
            return new Interpreter(commandsTokens, instructions, false);
     //   else
     //     return new Interpreter(commandsTokens, instructions, true);
    }

    private ArrayList<Token> createRepeatCommandsList(){
        ArrayList<Token> commandsTokens = new ArrayList<>();
        while (tokenIndex + 1 < tokenList.size()) {
            if(!getCurrentToken().value().equals("]"))
                commandsTokens.add(getCurrentTokenAndConsume());
            else if (!checkIfIsLastRightBracket())
                commandsTokens.add(getCurrentTokenAndConsume());
            else break;
        }
        return commandsTokens;
    }

    private boolean checkIfIsLastRightBracket(){
        for(int i=tokenIndex+1; i<tokenList.size(); i++)
            if(tokenList.get(i).matches(Token.TokenType.BRACKET_RIGHT,"]"))
                return false;
        return true;
    }

    private boolean isRepeat(Token token){
        return token.value().equals("repeat");
    }

    private boolean hasZeroArguments(Token token) throws UnknownCommandException{
        return Builtins.arity(token.value()) == 0;
    }

    private boolean hasOneArgument(Token token) throws UnknownCommandException{
        return Builtins.arity(token.value()) == 1;
    }

    private boolean hasThreeArguments(Token token) throws UnknownCommandException{
        return Builtins.arity(token.value()) == 3;
    }


    private int getIntegerArgument(Token token){
        return Integer.parseInt(token.value());
    }

    public void parseAndRun() throws UnknownCommandException, ClosedAreaException{
        while(tokensLeft>0) {
                if (tokenList.size() == 0)
                    throw new IllegalStateException("Lista comandi vuota");
                if (isAlpha(getCurrentToken()) && hasZeroArguments(getCurrentToken())) {
                    parseZeroArgumentsInstruction(getCurrentTokenAndConsume());
                }
                else if (isAlpha(getCurrentToken()) && hasOneArgument(getCurrentToken()) && !isRepeat(getCurrentToken())) {
                    parseOneArgumentInstruction(getCurrentTokenAndConsume(), Integer.parseInt(getCurrentTokenAndConsume().value()));
                }
                else if (isAlpha(getCurrentToken()) && hasOneArgument(getCurrentToken()) && isRepeat(getCurrentToken())) {
                    parseRepeat();
                }
                else if (isAlpha(getCurrentToken()) && hasThreeArguments(getCurrentToken())) {
                    Token instruction = getCurrentTokenAndConsume();
                    int firstArgument = getIntegerArgument(getCurrentTokenAndConsume());
                    int secondArgument = getIntegerArgument(getCurrentTokenAndConsume());
                    int thirdArgument = getIntegerArgument(getCurrentTokenAndConsume());
                    parseThreeArgumentsInstruction(instruction, firstArgument, secondArgument, thirdArgument);
                }
                if(stepByStepMode) {
                    return;
                }
        }
    }
}

