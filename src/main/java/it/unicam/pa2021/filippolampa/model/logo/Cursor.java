package it.unicam.pa2021.filippolampa.model.logo;

/**Descrive un generico cursore**/

public interface Cursor {

    /**
     * Resitituisce la posizione corrente del cursore
     */
    Location getCurrentLocation();

    /**
     * Restituisce la direzione corrente del cursore
     */
    Direction getCurrentDirection();

    /**
     * Restituisce il colore della linea descritta dal cursore
     */
    Color getCurrentLineColor();

    /**
     * Restituisce il colore dell'area prodotta dall'insieme di segmenti chiusi
     */
    Color getCurrentAreaColor();

    /**
     * Determina se durante lo spostamento il cursore genera o meno un tracciato
     * @return true se il cursore genera un tracciato
     *          false altrimenti
     */
    boolean isPlot();

    /**
     * Imposta il colore della linea descritta dal cursore
     * @param newCurrentLineColor nuovo colore della linea
     */
    void setCurrentLineColor(Color newCurrentLineColor);

    /**
     * Imposta il colore dell'area prodotta dall'insieme di segmenti chiusi
     * @param newAreaColor nuovo colore dell'area
     */
    void setCurrentAreaColor(Color newAreaColor);

    /**
     * Imposta la dimensione del tratto del cursore
     * @param newPenDimension nuova dimensione
     */
    void setCurrentPenDimension(int newPenDimension);

    /**
     * Stacca il cursore dall'area di disegno
     */
    void setPenUp();

    /**
     * Attacca il cursore all'area di disegno
     */
    void setPenDown();

    /**
     * Rstituisce la dimensione del tratto del cursore
     * @return dimensione del tratto
     */
    int getCurrentPenDimension();

    /**
     * Modifica la direzione del cursore
     * @param newDirection nuova direzione del cursore
     */
    void updateCursorDirection(Direction newDirection);

    /**
     * Modifica la posizione del cursore
     * @param newLocation nuova posizione del cursore
     * @throws ClosedAreaException se il cursore è attaccato all'area di disegno e durante il movimento genera un segmento
     *         appartenente a due aree chiuse differenti
     */
    void updateCursorLocation(Location newLocation) throws ClosedAreaException;

    void setCursorUpdateListener(CursorUpdateListener listener);

    static Direction getDefaultDirection(){
        return new Direction(0);
    }

    static Color getDefaultLineColor(){
        return new Color(0,0,0);
    }

    static Color getDefaultDrawingAreaColor(){
        return new Color(255,255,255);
    }

    static int getDefaultPenSize(){
        return 1;
    }
}
