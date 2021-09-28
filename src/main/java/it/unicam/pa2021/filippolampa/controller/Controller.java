package it.unicam.pa2021.filippolampa.controller;

import it.unicam.pa2021.filippolampa.model.logo.ClosedAreaException;
import it.unicam.pa2021.filippolampa.model.logo.CursorUpdateListener;
import it.unicam.pa2021.filippolampa.model.logo.DrawingAreaUpdateListener;
import it.unicam.pa2021.filippolampa.model.parser.IllegalPathException;
import it.unicam.pa2021.filippolampa.model.parser.UnknownCommandException;

public interface Controller {

    /**
     * Inizializza un ide istanziando cursore, area di disegno e set di istruzioni
     * @param width larghezza drawing area
     * @param height altezza drawing area
     */
    void newLogoIde(int width, int height);

    /**
     * Attiva la modalità step by step se questa è disattivata e la disattiva nel caso contrario
     */
    void switchStepByStep();

    /**
     * Fornisce un programma logo in pasto all'interprete dei comandi
     * @param programPath path del programma da eseguire
     * @throws IllegalPathException se il path specificato non viene riconosciuto
     * @throws UnknownCommandException se nel programma da eseguire viene trovato un comando sconosciuto
     * @throws ClosedAreaException se durante l'esecuzione del programma il cursore disegna un segmento appartenente a due
     *          aree chiuse differenti
     */
    void submitProgram(String programPath) throws IllegalPathException, UnknownCommandException, ClosedAreaException;

    /**
     * Esegue il prossimo step del programma in modalità step by step
     * @throws UnknownCommandException se nel programma da eseguire viene trovato un comando sconosciuto
     */
    void nextStep() throws UnknownCommandException;

    void setCursorListener(CursorUpdateListener listener);

    void setAreaListener(DrawingAreaUpdateListener listener);

}
