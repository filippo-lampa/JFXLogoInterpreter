package it.unicam.pa2021.filippolampa.model.logo;

public  class Instructions <C extends AbstractCursor, D extends AbstractDrawingArea> {

    private final C associatedCursor;

    private final D associatedDrawingArea;

    protected ExecutionResultPrinter resultPrinter;

    public Instructions(C associatedCursor, D associatedDrawingArea){
        this.associatedCursor = associatedCursor;
        this.associatedDrawingArea = associatedDrawingArea;
        resultPrinter = new ExecutionResultPrinter();
        resultPrinter.printResult("A new drawing area of W "+associatedDrawingArea.getWidth()+" and H "+associatedDrawingArea.getHeight()+" has been created. "+"The cursor is now at home location x:"+associatedCursor.getCurrentLocation().getXaxis()+" y:"+associatedCursor.getCurrentLocation().getYaxis());
    }

    public D getAssociatedDrawingArea(){ return associatedDrawingArea;}

    public C getAssociatedCursor(){ return associatedCursor;}

    /**
     * Sposta il cursore in avanti della distanza specificata rispetto alla sua posizione corrente
     * @param distance distanza di spostamento
     */
    public void moveForward (double distance) throws ClosedAreaException{
        double posX = getAssociatedCursor().getCurrentLocation().getXaxis() + (Math.cos(Math.toRadians(getAssociatedCursor().getCurrentDirection().getDirectionAngle())) * distance);
        double posY = getAssociatedCursor().getCurrentLocation().getYaxis() - (Math.sin(Math.toRadians(getAssociatedCursor().getCurrentDirection().getDirectionAngle())) * distance);
        Location newLocation = new Location(Math.round(posX),Math.round(posY));
        getAssociatedCursor().updateCursorLocation(newLocation);
        if(distance>=0)
            resultPrinter.printResult("Cursor moved "+distance+" forward. The location is now: x "+newLocation.getXaxis()+" y "+newLocation.getYaxis());
    }

    /**
     * Sposta il cursore indietro della distanza specificata rispetto alla sua posizione corrente
     * @param distance distanza di spostamento
     */
    public void moveBackwards(double distance) throws ClosedAreaException{
        System.out.println("cursor moved backwards");
        moveForward(-distance);
        resultPrinter.printResult("Cursor moved "+distance+" backwards. The location is now: x "+ associatedCursor.getCurrentLocation().getXaxis()+" y "+associatedCursor.getCurrentLocation().getYaxis());
    }

    /**
     * Ruota il cursore in senso antiorario dei gradi specificati
     * @param degrees angolo di rotazione
     */
    public void turnLeft (int degrees){
        turnRight(360-degrees);
     }

    /**
     * Ruota il cursore in senso orario dei gradi specificati
     * @param degrees angolo di rotazione
     */
    public void turnRight (int degrees){
        getAssociatedCursor().updateCursorDirection(new Direction(getAssociatedCursor().getCurrentDirection().getDirectionAngle()+ degrees));
        resultPrinter.printResult("Cursor turned, the new angle is: "+getAssociatedCursor().getCurrentDirection().getDirectionAngle());
    }

    /**
     * Cancella il contenuto dell'area di disegno
     */
    public void clearScreen(){
        getAssociatedDrawingArea().overrideWithEmptyArea();
        resultPrinter.printResult("Screen cleared");
    }

    /**
     * Posiziona il cursore nella home dell'area di disegno
     */
    public void home() {
        try{
        getAssociatedCursor().setPenUp();
        getAssociatedCursor().updateCursorLocation(getAssociatedDrawingArea().getHomeLocation());
        getAssociatedCursor().setPenDown();
        }catch (ClosedAreaException e){
            e.getStackTrace();
        }
        resultPrinter.printResult("Cursor is now home");
    }

    /**
     * Stacca la penna dal foglio per poter muovere il cursore senza tracciare una linea
     */
    public void penUp(){
        getAssociatedCursor().setPenUp();
        resultPrinter.printResult("Pen is now up");
    }

    /**
     * Attacca la penna al foglio per tracciare una linea quando si muove il cursore
     */
    public void penDown(){
        getAssociatedCursor().setPenDown();
        resultPrinter.printResult("Pen is now down");
    }

    /**
     * Imposta il colore della penna
     * @param r valore di rosso
     * @param g valore di verde
     * @param b valore di blu
     */
    public void setPenColor (int r, int g, int b){
        Color newColor = new Color(r,g,b);
        getAssociatedCursor().setCurrentLineColor(newColor);
        resultPrinter.printResult("Pen color set. The new RGB color is: "+r+" "+g+" "+b);
    }

    /**
     * Imposta il colore del riempimento di un'area chiusa
     * @param r valore di rosso
     * @param g valore di verde
     * @param b valore di blu
     */
    public void setFillColor (int r, int g, int b){
        Color newColor = new Color(r,g,b);
        getAssociatedCursor().setCurrentAreaColor(newColor);
        resultPrinter.printResult("Fill color set. The new RGB color is: "+r+" "+g+" "+b);
    }

    /**
     * Imposta il colore di sfondo dell'area di disegno
     * @param r valore di rosso
     * @param g valore di verde
     * @param b valore di blu
     */
    public void setScreenColor (int r, int g, int b){
        Color newColor = new Color(r,g,b);
        getAssociatedDrawingArea().setCurrentBackgroundColor(newColor);
        resultPrinter.printResult("Screen color set. The new RGB color is: "+r+" "+g+" "+b);
    }

    /**
     * Imposta la dimensione del tratto della penna
     * @param size nuova dimensione del tratto
     */
    public void setPenSize (int size){
        getAssociatedCursor().setCurrentPenDimension(size);
        resultPrinter.printResult("Pen size set. The new size is: "+size);
    }

}
