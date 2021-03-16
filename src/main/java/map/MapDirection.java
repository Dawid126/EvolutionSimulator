package main.java.map;

import main.java.utils.Vector2D;

public enum MapDirection
{
    NORTH,
    NORTH_EAST,
    EAST,
    SOUTH_EAST,
    SOUTH,
    SOUTH_WEST,
    WEST,
    NORTH_WEST;

    public MapDirection next()
    {
        switch(this)
        {
            case NORTH: return NORTH_EAST;
            case NORTH_EAST: return EAST;
            case EAST: return SOUTH_EAST;
            case SOUTH_EAST: return SOUTH;
            case SOUTH: return SOUTH_WEST;
            case SOUTH_WEST: return WEST;
            case WEST: return NORTH_WEST;
            case NORTH_WEST: return NORTH;
        }
        return null;
    }

    public Vector2D toUnitVector()
    {

        switch(this)
        {
            case NORTH:
            {
                return (new Vector2D(0,1));
            }
            case NORTH_EAST:
            {
                return (new Vector2D(1,1));
            }
            case EAST:
            {
                return (new Vector2D(1,0));
            }
            case SOUTH_EAST:
            {
                return (new Vector2D(1,-1));
            }
            case SOUTH:
            {
                return (new Vector2D(0,-1));
            }
            case SOUTH_WEST:
            {
                return (new Vector2D(-1,-1));
            }
            case WEST:
            {
                return (new Vector2D(-1,0));
            }
            case NORTH_WEST:
            {
                return (new Vector2D(-1,1));
            }
        }
        return null;
    }


}

