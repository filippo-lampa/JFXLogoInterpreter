package it.unicam.pa2021.filippolampa.model.logo;

public abstract class DefaultCursor<D extends AbstractDrawingArea, S extends Segment> extends AbstractCursor <D,S> {

    public DefaultCursor(D associatedDrawingArea) {
        super(associatedDrawingArea.getHomeLocation(), Cursor.getDefaultDirection(), Cursor.getDefaultLineColor(), Cursor.getDefaultDrawingAreaColor(),
                Cursor.getDefaultPenSize(), associatedDrawingArea);
    }

}
