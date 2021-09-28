package it.unicam.pa2021.filippolampa.model.logo;

public class DrawingAreaUpdateSupport {

    private DrawingAreaUpdateListener listener;

    public synchronized void setListener(DrawingAreaUpdateListener listener) {
        if (listener == null) return ;
        this.listener = listener;
    }


    public void fireEmptyArea() {
        listener.fireEmptyArea();
    }

    public void fireBackgroundColorChanged(Color newBackgroundColor) {
        listener.fireBackgroundColorChanged(newBackgroundColor);
    }
}
