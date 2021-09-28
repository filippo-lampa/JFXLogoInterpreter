package it.unicam.pa2021.filippolampa.model.logo;

public interface DrawingAreaUpdateListener {
    void fireEmptyArea();

    void fireBackgroundColorChanged(Color newBackgroundColor);
}
