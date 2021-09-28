package it.unicam.pa2021.filippolampa.model.logo;

import java.util.Objects;

public class Color {

    private final int r;

    private final int g;

    private final int b;

    public Color(int r, int g, int b){
        if(isValueInvalid(r) || isValueInvalid(g) || isValueInvalid(b))
            throw new IllegalArgumentException("I valori RGB devono essere compresi tra 0 e 255");
        this.r = r;
        this.g = g;
        this.b = b;
    }

    private boolean isValueInvalid(int colorValue){
        return colorValue < 0 || colorValue > 255;
    }

    public int[] getColorRGBValues(){
        return new int[]{r,g,b};
    }

    public int getR(){ return r;}

    public int getG(){ return g;}

    public int getB(){ return b;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Color color = (Color) o;
        return r == color.r && g == color.g && b == color.b;
    }

    @Override
    public int hashCode() {
        return Objects.hash(r, g, b);
    }
}
