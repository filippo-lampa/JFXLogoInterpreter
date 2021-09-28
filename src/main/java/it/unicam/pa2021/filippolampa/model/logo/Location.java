package it.unicam.pa2021.filippolampa.model.logo;

import java.util.Objects;

/**
 * Descrive una posizione all'interno dell'area di disegno
 **/

public class Location {

    private boolean isWritten;

    private final double xAxis;

    private final double yAxis;

    public Location(double xAxis, double yAxis){
        this.xAxis = xAxis;
        this.yAxis = yAxis;
        isWritten = false;
    }

    /**
     * Restituisce la coordinata della location sull'asse x
     */
    public double getXaxis() {
        return xAxis;
    }

    /**
     * Restituisce la coordinata della location sull'asse y
     */
    public double getYaxis() {
        return yAxis;
    }

    public boolean isWritten(){
        return isWritten;
    }

    public void setLocationWritten(){
        isWritten = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return xAxis == location.xAxis && yAxis == location.yAxis;
    }

    @Override
    public int hashCode() {
        return Objects.hash(xAxis, yAxis);
    }
}
