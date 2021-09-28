package it.unicam.pa2021.filippolampa.model.logo;

import java.util.ArrayList;

public interface CursorUpdateListener {

    void fireCursorMoved(Location newLocation);

    void fireCursorRotated(int degrees);

    void fireSegmentDrawn(Segment segment);

    void fireCursorLineColorChanged(Color newCurrentLineColor);

    void firePenDimensionChanged(int newPenDimension);

    <S extends Segment> void fireClosedArea(ArrayList<S> closedArea, Color areaColor);

    void fireTwoClosedAreasAlert();
}
