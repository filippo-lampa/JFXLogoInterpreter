package it.unicam.pa2021.filippolampa.model.logo;

public class DefaultDrawingArea extends AbstractDrawingArea {

    public DefaultDrawingArea(int width, int height){
            super(DrawingArea.getDefaultBackgroundColor(),width,height,new Location(width/2,height/2));
    }

}
