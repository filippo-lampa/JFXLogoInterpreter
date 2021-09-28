package it.unicam.pa2021.filippolampa.model.logo;

import java.util.Objects;

public abstract class AbstractDrawingArea implements DrawingArea{

    private Color currentBackgroundColor;

    protected Location[][] drawingAreaLocations;

    private final int width;

    private final int height;

    private final Location homeLocation;

    protected int writtenLocationsNumber;

    DrawingAreaUpdateSupport updateSupport;

    public AbstractDrawingArea(Color startBackgroundColor, int width, int height, Location homeLocation){
        if(width <=0 || height <=0)
            throw new IllegalArgumentException("Le dimensioni devono essere maggiori di 0");
        this.width = width;
        this.height = height;
        currentBackgroundColor = startBackgroundColor;
        drawingAreaLocations = new Location [width][height];
        this.homeLocation = homeLocation;
        writtenLocationsNumber = 0;
        initializeDrawingArea();
        updateSupport = new DrawingAreaUpdateSupport();
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public Location getHomeLocation() {
        return homeLocation;
    }

    @Override
    public Color getBackgroundColor() {
        return currentBackgroundColor;
    }

    /**
     * Restituisce il numero di locazioni scritte, ovvero il numero di locazioni da cui inizia o finisce un segmento
     * @return numero di locazioni scritte
     */
    public int getWrittenLocationsNumber() {
        if (areaIsEmpty())
            return writtenLocationsNumber;
        return writtenLocationsNumber+1;
    }

    @Override
    public void setCurrentBackgroundColor(Color newBackgroundColor) {
        this.currentBackgroundColor = newBackgroundColor;
        updateSupport.fireBackgroundColorChanged(getBackgroundColor());
    }

    @Override
    public Location[][] getDrawingAreaLocations() {
        return drawingAreaLocations;
    }

    /**
     * Sovrascrive il piano dell'area da disegno con un nuovo piano vuoto
     */
    @Override
    public void overrideWithEmptyArea() {
        drawingAreaLocations = new Location[width][height];
        writtenLocationsNumber = 0;
        initializeDrawingArea();
        updateSupport.fireEmptyArea();
    }

    @Override
    public boolean areaIsEmpty(){
        return writtenLocationsNumber == 0;
    }

    /**
     * Controlla che una Location rientri nei bordi dell'area di disegno
     * @return true se la location rientra nei bordi
     *          false altrimenti
     */
    @Override
    public boolean isWithinBoundaries(Location location) {
        return location.getXaxis()<=width && location.getYaxis()<=height && location.getXaxis()>=0 && location.getYaxis() >=0;
    }

    public Location getLocation (int xAxis, int yAxis){
        if(!isWithinBoundaries(new Location(xAxis,yAxis)))
            throw new IllegalArgumentException("Impossibile ottenere una locazione esterna all'area da disegno");
        cartesianPlaneCoordinates translatedCoordinates = translateLocationCoordinatesToMatrixCoordinates(xAxis,yAxis);
        return getDrawingAreaLocations()[translatedCoordinates.getYAxis()][translatedCoordinates.getXAxis()];
    }

    @Override
    public synchronized void setDrawingAreaUpdateListener(DrawingAreaUpdateListener listener) {
        this.updateSupport.setListener(listener);
    }

    protected void initializeDrawingArea(){
        for(int i=0; i<width; i++)
            for (int j=0; j<height; j++)
                getDrawingAreaLocations()[i][j] = new Location(j,width-i-1);
    }

    private cartesianPlaneCoordinates translateLocationCoordinatesToMatrixCoordinates(int xAxisLoactionCoordinate, int yAxisLocationCoordinate){
        return new cartesianPlaneCoordinates(xAxisLoactionCoordinate,height-yAxisLocationCoordinate-1);
    }


    private static class cartesianPlaneCoordinates{

        private final int xAxis;

        private final int yAxis;

        public cartesianPlaneCoordinates(int xAxis, int yAxis){
            this.xAxis = xAxis;
            this.yAxis = yAxis;
        }

        public int getXAxis(){
            return xAxis;
        }

        public int getYAxis(){
            return yAxis;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            cartesianPlaneCoordinates that = (cartesianPlaneCoordinates) o;
            return xAxis == that.xAxis && yAxis == that.yAxis;
        }

        @Override
        public int hashCode() {
            return Objects.hash(xAxis, yAxis);
        }
    }

}
