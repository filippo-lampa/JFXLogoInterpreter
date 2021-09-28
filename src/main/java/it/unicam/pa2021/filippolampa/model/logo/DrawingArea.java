package it.unicam.pa2021.filippolampa.model.logo;

/**
 * Descrive una generica area di disegno
 */
public interface DrawingArea {

    /**
     * Restituisce l'altezza dell'area di disegno
     */
    int getHeight();

    /**
     * Restituisce la larghezza dell'area di disegno
     */
    int getWidth();

    /**
     * Restituisce le coordinate del centro dell'area di disegno (home)
     */
    Location getHomeLocation();

    /**
     * Restituisce il colore di sfondo dell'area di disegno
     */
    Color getBackgroundColor();

    /**
     * Imposta il colore di sfondo dell'area di disegno
     * @param newBackgroundColor nuovo colore dell'area di disegno
     */
    void setCurrentBackgroundColor(Color newBackgroundColor);

    /**
     * Restituisce le locazioni dell'area di disegno
     * @return Locazioni dell'area di disegno
     */
    Location[][] getDrawingAreaLocations();

    /**
     * Cancella il contenuto dell'area
     */
    void overrideWithEmptyArea();

    /**
     * Determina se l'area di disegno è vuota o no
     * @return true se nell'area è stato disegnato almeno un segmento, false altrimenti
     */
    boolean areaIsEmpty();

    /**
     * Determina se una location rientra nei limiti dell'area
     * @param location location da controllare
     * @return true se la location rientra nei limiti dell'area, false altrimenti
     */
    boolean isWithinBoundaries(Location location);

    static Color getDefaultBackgroundColor(){
        return new Color(255,255,255);
    }

    void setDrawingAreaUpdateListener(DrawingAreaUpdateListener listener);
}
