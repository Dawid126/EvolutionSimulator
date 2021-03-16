package main.java.jungle_elements;

import main.java.map.MapDirection;
import main.java.map.WorldMap;
import main.java.utils.Vector2D;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class Animal extends WorldMapElement implements Comparable<Animal>{

    protected int energy;
    protected int startEnergy;
    protected Genes genotype;
    protected MapDirection orientation;
    protected WorldMap map;

    public Animal(WorldMap map, Vector2D initialPosition, MapDirection orientation)
    {
        this.position = initialPosition;
        this.map = map;
        this.genotype = new Genes();
        this.startEnergy = map.getStartEnergy();
        this.energy = startEnergy;
        this.orientation = orientation;

    }
    public Animal(WorldMap map, Vector2D initialPosition, Genes genotype, MapDirection orientation, int energy)
    {
        this.position = initialPosition;
        this.map = map;
        this.genotype = genotype;
        this.orientation = orientation;
        this.energy = energy;
        this.startEnergy = map.getStartEnergy();
    }

    public void move()
    {

        Vector2D newPos = position.add(orientation.toUnitVector());
        if(!newPos.follows(map.getMapLowerBottomCorner()) || !newPos.precedes(map.getMapRightUpperCorner()))
        {
            newPos = map.toOppositeSide(newPos);
        }
        if(map.canMoveTo(newPos))
        {
            map.updatePos(newPos, this.position, this);
            position = newPos;
            this.changeEnergy( - map.getMoveEnergy());
        }
    }

    public int transferEnergyForChild(Animal partner)
    {
        int childEnergy = this.getEnergy()/4 + partner.getEnergy()/4;
        this.changeEnergy(-this.getEnergy()/4);
        partner.changeEnergy(-partner.getEnergy()/4);
        return childEnergy;
    }

    public void changeEnergy(int amountOfEnergy)
    {
        energy = energy + amountOfEnergy;
    }

    public void breed(Vector2D pos, Animal mother, MapDirection newDirection)
    {

        Genes childGenotype = new Genes(this, mother);
        int childEnergy = this.transferEnergyForChild(mother);
        Animal animal = new Animal(map, pos, childGenotype, newDirection, childEnergy);
        ArrayList<Animal> animalsOnSquare = new ArrayList<Animal>();
        animalsOnSquare.add(animal);
        map.animalBorn(animalsOnSquare, pos);
    }

    public int getEnergy()
    {
        return energy;
    }
    public MapDirection  getOrientation() { return orientation; }
    public Color getColor() { return new Color( Math.min( 255 * energy / startEnergy , 255 ),0,0); }
    public Genes getGenes() { return genotype; }

    public void changeOrientation(MapDirection newOrientation) { this.orientation = newOrientation; }


    @Override
    public int compareTo(Animal animal)
    {
        return(Integer.compare(this.getEnergy(), animal.getEnergy()));
    }

}