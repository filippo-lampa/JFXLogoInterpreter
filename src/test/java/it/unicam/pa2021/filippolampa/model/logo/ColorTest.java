package it.unicam.pa2021.filippolampa.model.logo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ColorTest {

    @Test
    void ColorConstructorTest(){
        assertThrows(IllegalArgumentException.class, ()-> new Color(256,255,255));
        assertThrows(IllegalArgumentException.class, ()-> new Color(255,256,255));
        assertThrows(IllegalArgumentException.class, ()-> new Color(255,255,256));
        assertThrows(IllegalArgumentException.class, ()-> new Color(-1,0,0));
        assertThrows(IllegalArgumentException.class, ()-> new Color(0,-1,0));
        assertThrows(IllegalArgumentException.class, ()-> new Color(0,0,-1));
    }

    @Test
    void getColorRGBValuesTest() {
        Color color = new Color(80,200,50);
        int [] colorValuesExample = {80,200,50};
        for(int i = 0; i<3; i++)
            assertEquals(color.getColorRGBValues()[i], colorValuesExample[i]);
    }

}