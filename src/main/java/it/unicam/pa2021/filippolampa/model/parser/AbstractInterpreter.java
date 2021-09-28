package it.unicam.pa2021.filippolampa.model.parser;

import it.unicam.pa2021.filippolampa.model.logo.ClosedAreaException;
import it.unicam.pa2021.filippolampa.model.logo.Instructions;

import java.util.ArrayList;

/**
 *  Definisce un generico interprete che presa una stringa in input ed un set di istruzioni effettua le fasi di
 *  Lexing, Parsing ed esecuzione delle istruzioni
 * @param <I> set di istruzioni comprensibili dall'interprete
 */
public abstract class AbstractInterpreter<I extends Instructions> {

    protected final ArrayList<Token> tokenList;

    protected int tokenIndex;

    private Token currentToken;

    protected I instructions;

    protected int tokensLeft;

    protected final boolean stepByStepMode;

    protected boolean waitingForNextStepCommand = false;

    public AbstractInterpreter(String stringToParse, I instructions, boolean isStepByStep) {
        stepByStepMode = isStepByStep;
        this.instructions = instructions;
        this.tokenList = new Lexer(stringToParse).tokenize();
        tokensLeft = tokenList.size();
        tokenIndex = 0;
        currentToken = tokenList.get(0);
    }

    protected AbstractInterpreter(ArrayList<Token>tokenList, I instructions, boolean isStepByStep){
        stepByStepMode = isStepByStep;
        this.instructions = instructions;
        this.tokenList = tokenList;
        tokensLeft = tokenList.size();
        tokenIndex = 0;
        currentToken = tokenList.get(0);
    }

    public void getNextCommand() {
        if(tokensLeft>0)
            stopWaitingForNextStepCommand();
        else System.out.println("Non ci sono più comandi da eseguire");
    }

    protected void stopWaitingForNextStepCommand(){
        waitingForNextStepCommand = false;
    }

    public abstract void parseAndRun() throws UnknownCommandException, ClosedAreaException;

    protected void consume(){
        if(tokenIndex+1<tokenList.size()) {
            this.tokenIndex++;
            this.currentToken = tokenList.get(tokenIndex);
        }
        if(tokensLeft>0)
            this.tokensLeft--;
    }

    protected Token getCurrentToken(){
        return this.currentToken;
    }

    protected Token getCurrentTokenAndConsume(){
        Token current = currentToken;
        consume();
        return current;
    }
}
