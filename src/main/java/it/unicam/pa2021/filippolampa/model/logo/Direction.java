package it.unicam.pa2021.filippolampa.model.logo;

import java.util.Objects;

/**
 * Definisce una direzione del cursore compresa tra 0 e 360, in cui 0 indica la posizione
 * orizzontale verso destra
 */
public class Direction {

    private final int directionAngle;

    public Direction(int angle){
        this.directionAngle = angle%360;
    }

    int getDirectionAngle(){
        return directionAngle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Direction direction = (Direction) o;
        return directionAngle == direction.directionAngle;
    }

    @Override
    public int hashCode() {
        return Objects.hash(directionAngle);
    }
}
