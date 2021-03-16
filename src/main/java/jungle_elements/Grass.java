package main.java.jungle_elements;

import main.java.utils.Vector2D;

public class Grass extends WorldMapElement{
    public Grass(Vector2D position)
    {
        this.position=position;
    }
    public String toString()
    {
        return "*";
    }

}
