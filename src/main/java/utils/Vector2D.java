package main.java.utils;

import java.util.*;
public class Vector2D extends LinkedHashMap {
    public final int x;
    public final int y;

    public Vector2D(int x,int y)
    {
        this.x=x;
        this.y=y;
    }

    public String toString()
    {
        return ("(" + x + "," + y + ")");
    }

    @Override
    public int hashCode()
    {
        int hash = 13;
        hash += this.x * 31;
        hash += this.y * 17;
        return hash;
    }


    public Vector2D upperRight(Vector2D other)
    {
        Vector2D tmp = new Vector2D(Math.max(this.x,other.x), Math.max(this.y,other.y));
        return tmp;

    }

    public Vector2D lowerLeft(Vector2D other)
    {
        Vector2D tmp = new Vector2D(Math.min(this.x,other.x), Math.min(this.y,other.y));
        return tmp;

    }

    public Vector2D add(Vector2D other)
    {
        Vector2D tmp = new Vector2D(other.x + this.x, other.y + this.y);
        return tmp;
    }

    public Vector2D subtract(Vector2D other)
    {
        Vector2D tmp = new Vector2D(this.x - other.x, this.y - other.y);
        return tmp;
    }

    public boolean equals(Object other)
    {
        if(this == other) return true;
        if(!(other instanceof Vector2D)) return false;
        Vector2D tmp = (Vector2D) other;
        if(this.x == tmp.x && this.y == tmp.y) return true;
        else return false;
    }

    public Vector2D opposite()
    {
        Vector2D tmp = new Vector2D(-this.x, -this.y);
        return tmp;
    }

    public boolean follows(Vector2D other)
    {
        if(this.x>=other.x && this.y>=other.y) return true;
        return false;
    }

    public boolean precedes(Vector2D other)
    {
        if(this.x<=other.x && this.y<=other.y) return true;
        return false;
    }

    public static int compareX(Vector2D a, Vector2D b)
    {
        if(a.x > b.x) return 1;
        else if (a.x < b.x) return -1;
        else return 0;

    }

    public static int compareY(Vector2D a, Vector2D b)
    {
        if (a.y > b.y) return 1;
        else if (a.y < b.y) return -1;
        else return 0;
    }
}
