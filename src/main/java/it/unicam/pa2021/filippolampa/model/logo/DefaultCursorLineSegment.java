package it.unicam.pa2021.filippolampa.model.logo;

public class DefaultCursorLineSegment<D extends AbstractDrawingArea> extends DefaultCursor<D,LineSegment> {

    public DefaultCursorLineSegment(D associatedDrawingArea) {
        super(associatedDrawingArea);
    }

    @Override
    protected LineSegment instantiateNewSegment(Location startLocation, Location destinationLocation) {
        return new LineSegment(startLocation, destinationLocation);
    }
}
