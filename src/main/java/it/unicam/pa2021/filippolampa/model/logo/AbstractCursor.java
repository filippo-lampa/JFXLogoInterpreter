package it.unicam.pa2021.filippolampa.model.logo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractCursor <D extends AbstractDrawingArea,S extends Segment> implements Cursor{

    private Location currentLocation;

    private Direction currentDirection;

    protected Color currentLineColor, currentAreaColor;

    protected int currentPenDimension;

    private boolean penIsUp;

    private final D associatedDrawingArea;

    protected ArrayList<S> currentClosedArea, drawnSegments;

    protected Set<ArrayList<S>>allClosedAreas;

    protected CursorUpdateSupport updateSupport = new CursorUpdateSupport();

    public AbstractCursor(Location startLocation, Direction startDirection, Color startLineColor,
                            Color startAreaColor, int startPenDimension, D associatedDrawingArea){

        this.currentDirection = startDirection;

        this.currentLocation = startLocation;

        this.currentAreaColor = startAreaColor;

        this.currentLineColor = startLineColor;

        this.currentPenDimension = startPenDimension;

        this.associatedDrawingArea = associatedDrawingArea;

        penIsUp = false;

        drawnSegments = new ArrayList<>();

        allClosedAreas = new HashSet<>();

        currentClosedArea = new ArrayList<>();

    }

    @Override
    public void setCurrentLineColor(Color newCurrentLineColor) {
        this.currentLineColor = newCurrentLineColor;
        updateSupport.fireCursorLineColorChanged(newCurrentLineColor);
    }

    @Override
    public void setCurrentAreaColor(Color newAreaColor) {
        this.currentAreaColor = newAreaColor;
    }

    @Override
    public void setCurrentPenDimension(int newPenDimension) {
        if(newPenDimension<1)
            throw new IllegalArgumentException("La dimensione del tratto della penna deve essere maggiore o uguale ad uno");
        this.currentPenDimension = newPenDimension;
        updateSupport.firePenDimensionChanged(getCurrentPenDimension());
    }

    private void setCurrentDirection(Direction newDirection){
        currentDirection = newDirection;
        updateSupport.fireCursorRotated(newDirection.getDirectionAngle());
    }

    private void setCurrentLocation(Location newLocation){
        currentLocation = newLocation;
        updateSupport.fireCursorMoved(newLocation);
    }

    @Override
    public void setPenUp(){
        penIsUp = true;
    }

    @Override
    public void setPenDown(){
        penIsUp = false;
    }

    public int getCurrentPenDimension(){ return currentPenDimension;}

    @Override
    public Location getCurrentLocation(){ return currentLocation;}

    @Override
    public Direction getCurrentDirection(){ return currentDirection;}

    @Override
    public Color getCurrentLineColor(){ return currentLineColor;}

    @Override
    public Color getCurrentAreaColor(){ return currentAreaColor;}

    public D getAssociatedDrawingArea(){ return associatedDrawingArea;}

    @Override
    public boolean isPlot(){
        return !penIsUp;
    }

    public boolean penIsUp(){ return penIsUp; }

    @Override
    public void updateCursorDirection(Direction newDirection){
        setCurrentDirection(newDirection);
    }

    @Override
    public void updateCursorLocation(Location newLocation)throws ClosedAreaException{
        if(!isPlot()) {
            moveWithoutDrawing(newLocation);
        }
        else {
            moveAndDraw(newLocation);
        }
    }

    private void moveWithoutDrawing(Location newLocation){
        newLocation = checkBoundariesAndCorrectCoordinates(newLocation);
        setCurrentLocation(newLocation);
    }

    private void moveAndDraw(Location newLocation) throws ClosedAreaException{
        newLocation = checkBoundariesAndCorrectCoordinates(newLocation);
        if(!newLocation.isWritten()) {
            newLocation.setLocationWritten();
            associatedDrawingArea.writtenLocationsNumber++;
        }
        drawSegment(instantiateNewSegment(currentLocation,newLocation));
        setCurrentLocation(newLocation);
    }

    private Location checkBoundariesAndCorrectCoordinates(Location newLocation) {
        double correctXaxis = newLocation.getXaxis(), correctYaxis = newLocation.getYaxis();
        if(newLocation.getXaxis()<0)
            correctXaxis = 0;
        if(newLocation.getXaxis()>=getAssociatedDrawingArea().getWidth())
            correctXaxis = getAssociatedDrawingArea().getWidth()-1;
        if(newLocation.getYaxis()<0)
            correctYaxis = 0;
        if(newLocation.getYaxis()>=getAssociatedDrawingArea().getHeight())
            correctYaxis = getAssociatedDrawingArea().getHeight()-1;
        Location correctLocation = new Location(correctXaxis,correctYaxis);
        if(newLocation.isWritten())
            correctLocation.setLocationWritten();
        return correctLocation;
    }

    protected void drawSegment(S segmentToDraw) throws ClosedAreaException {
        if(makesClosedArea(segmentToDraw)) {
            manageMakesClosedArea(segmentToDraw);
        }
        showLine(segmentToDraw.getStartingPoint().getXaxis(),segmentToDraw.getStartingPoint().getYaxis(),
                segmentToDraw.getDestinationPoint().getXaxis(),segmentToDraw.getDestinationPoint().getYaxis());
        currentClosedArea.add(segmentToDraw);
        drawnSegments.add(segmentToDraw);
    }

    private void manageMakesClosedArea(S segmentToDraw) throws  ClosedAreaException{
        if (!checkIfAlreadyInClosedArea(segmentToDraw)) {
            currentClosedArea.add(segmentToDraw);
            updateSupport.fireClosedArea(currentClosedArea, getCurrentAreaColor());
            drawnSegments.add(segmentToDraw);
            closeAndOpenNewClosedArea();
        } else {
            updateSupport.fireTwoClosedAreasAlert();
            throw new ClosedAreaException();
        }
    }

    private void closeAndOpenNewClosedArea(){
        allClosedAreas.add(currentClosedArea);
        currentClosedArea = new ArrayList<>();
    }

    /**Determina se un segmento fa già parte di un'area chisua
     * @param segmentToCheck segmento
     * @return true se fa già parte di un'area chiusa
     *          false altrimenti
     */
    protected boolean checkIfAlreadyInClosedArea(S segmentToCheck){
        for(ArrayList<S> closedArea : allClosedAreas) {
            for(Segment segment : closedArea)
                if(segmentToCheck.endsWhereTheOtherStarts(segment))
                    return true;
        }
        return false;
    }

    protected abstract S instantiateNewSegment(Location startLocation, Location destinationLocation);

    /**
     * Determina se un segmento, se disegnato, creerebbe un'area chiusa
     * @param segmentToCheck segmento
     * @return true se il segmento formerebbe un'area chiusa,
     *          false altrimenti
     */
    protected boolean makesClosedArea(S segmentToCheck){
        for(S currentSegment : drawnSegments)
            if(segmentToCheck.endsWhereTheOtherStarts(currentSegment))
                return true;
        return false;
    }

    @Override
    public synchronized void setCursorUpdateListener(CursorUpdateListener listener) {
        this.updateSupport.setListener(listener);
    }

    protected void showLine(double oldX, double oldY, double newX, double newY )
    {
        Location startLocation = new Location (oldX,oldY);
        Location destinationLocation = new Location (newX, newY);
        Segment segmentToDraw = new LineSegment(startLocation,destinationLocation);
        updateSupport.fireSegmentDrawn(segmentToDraw);
    }
}
