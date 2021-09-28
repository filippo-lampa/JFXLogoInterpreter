package it.unicam.pa2021.filippolampa.model.logo;

import java.util.ArrayList;

public class CursorUpdateSupport {

    private CursorUpdateListener listener;

    public synchronized void setListener(CursorUpdateListener listener) {
        if (listener == null) return ;
        this.listener = listener;
    }

    public synchronized void fireCursorRotated(int degrees) {
        listener.fireCursorRotated(degrees);
    }

    public synchronized void fireCursorMoved(Location newLocation) {
        listener.fireCursorMoved(newLocation);
    }

    public void fireSegmentDrawn(Segment segment) {
        listener.fireSegmentDrawn(segment);
    }

    public void fireCursorLineColorChanged(Color newCurrentLineColor) {
        listener.fireCursorLineColorChanged(newCurrentLineColor);
    }

    public void firePenDimensionChanged(int newPenDimension) {
        listener.firePenDimensionChanged(newPenDimension);
    }

    public <S extends Segment> void fireClosedArea(ArrayList<S> closedArea, Color areaColor) {
        listener.fireClosedArea(closedArea, areaColor);
    }

    public void fireTwoClosedAreasAlert() {
        listener.fireTwoClosedAreasAlert();
    }
}
