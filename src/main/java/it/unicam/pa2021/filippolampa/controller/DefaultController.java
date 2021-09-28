package it.unicam.pa2021.filippolampa.controller;

import it.unicam.pa2021.filippolampa.model.logo.*;
import it.unicam.pa2021.filippolampa.model.parser.FileRdr;
import it.unicam.pa2021.filippolampa.model.parser.IllegalPathException;
import it.unicam.pa2021.filippolampa.model.parser.Interpreter;
import it.unicam.pa2021.filippolampa.model.parser.UnknownCommandException;

public class DefaultController implements Controller{

    private DefaultDrawingArea drawingArea;
    private DefaultCursorLineSegment<DefaultDrawingArea> cursor;
    private Instructions<DefaultCursorLineSegment<DefaultDrawingArea>, DefaultDrawingArea>instructions;
    private Interpreter interpreter;
    private boolean stepByStep = false;
    protected CursorUpdateListener cursorListener;
    protected DrawingAreaUpdateListener areaListener;

    @Override
    public void newLogoIde(int width, int height) {
        drawingArea = new DefaultDrawingArea(width,height);
        cursor = new DefaultCursorLineSegment<>(drawingArea);
        setModelCursorUpdateListener();
        setDrawingAreaUpdateListener();
        instructions = new Instructions<>(cursor, drawingArea);
    }

    protected void setModelCursorUpdateListener(){
        cursor.setCursorUpdateListener(cursorListener);
    }

    protected void setDrawingAreaUpdateListener(){
        drawingArea.setDrawingAreaUpdateListener(areaListener);
    }

    @Override
    public void switchStepByStep() {
        stepByStep = !stepByStep;
    }

    public boolean isStepByStep(){
        return stepByStep;
    }

    @Override
    public void submitProgram(String programPath) throws IllegalPathException,UnknownCommandException {
        String program = FileRdr.readFile(programPath);
        if(program.equals("-1"))
            throw new IllegalPathException("Illegal path");
        try {
            if (!stepByStep) {
                interpreter = new Interpreter(program, instructions, false);
            } else {
                interpreter = new Interpreter(program, instructions, true);
            }
            interpreter.parseAndRun();
        }catch(ClosedAreaException e){
            System.out.println("A segment is within two closed areas, exiting program execution");
        }
    }

    @Override
    public void nextStep() throws UnknownCommandException{
        try {
            interpreter.getNextCommand();
            interpreter.parseAndRun();
        }catch (ClosedAreaException e){
            System.out.println("A segment is within two closed areas, exiting program execution");
        }
    }

    @Override
    public synchronized void setCursorListener(CursorUpdateListener listener) {
        this.cursorListener = listener;
    }

    @Override
    public synchronized void setAreaListener(DrawingAreaUpdateListener listener) {
        this.areaListener = listener;
    }

}
