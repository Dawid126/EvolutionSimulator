package main.java.map;

import main.java.jungle_elements.Animal;
import main.java.jungle_elements.Grass;
import main.java.utils.RandomInt;
import main.java.utils.Vector2D;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class WorldMap{

    private int startEnergy;
    private int moveEnergy;
    private int plantEnergy;
    private int jungleRatio;
    private int numOfStartingAnimals;
    private int numOfGrassPerDayPerRegion;


    private int numOfStartingGrass;
    private int mapWidth;
    private int mapHeight;
    private int jungleWidth;
    private int jungleHeight;
    private Vector2D mapRightUpperCorner;
    private Vector2D jungleRightUpperCorner;
    private Vector2D jungleLeftBottomCorner;
    private Vector2D mapLowerBottomCorner = new Vector2D(0,0);
    private MapDirection[] mapDirections = new MapDirection[8];

    private HashMap<Vector2D, Grass> grasses = new HashMap<>();
    private HashMap<Vector2D,ArrayList<Animal>> animals = new HashMap<>();
    private ArrayList<Animal> animalList = new ArrayList();


    public WorldMap(int parameters[])
    {
        this.mapWidth = parameters[0];
        this.mapHeight = parameters[1];
        this.startEnergy = parameters[2];
        this.moveEnergy = parameters[3];
        this.plantEnergy = parameters[4];
        this.jungleRatio = parameters[5];
        this.numOfStartingAnimals = parameters[6];
        this.numOfGrassPerDayPerRegion = parameters[7];
        this.numOfStartingGrass = parameters[8];

        mapRightUpperCorner = new Vector2D(mapWidth -1, mapHeight -1);
        mapDirections[0]=MapDirection.NORTH;
        for(int i = 1; i < 8; i++)
        {
            mapDirections[i] = mapDirections[i-1].next();
        }

        jungleWidth = mapWidth * jungleRatio / 100;
        jungleHeight = mapHeight * jungleRatio / 100;
        jungleLeftBottomCorner = new Vector2D(mapWidth/2 - jungleWidth/2, mapHeight/2 - jungleHeight/2);
        jungleRightUpperCorner = new Vector2D( mapWidth/2 + jungleWidth/2 -1, mapHeight/2 + jungleHeight/2 -1);

        for(int i=0; i<numOfStartingGrass/2; i++)
        {
            this.spawnGrass();
        }
        for(int i=0; i<numOfStartingAnimals; i++)
        {
            this.createAnimal();
        }

    }



    public boolean isOccupied(Vector2D position)
    {
        if(animals.get(position) != null || grasses.get(position) != null) return true;
        else return false;

    }

    public boolean canMoveTo(Vector2D position)
    {
        if(animals.get(position) != null)
        {
            if(animals.get(position).size() == 2) return false;
        }
        return true;
    }

    public void spawnGrass()
    {
        int counter = 0;
        Vector2D grassPos;
        RandomInt randomInt = new RandomInt();
        while(counter < 2 * jungleWidth * jungleHeight)
        {
            grassPos = new Vector2D(randomInt.generate(jungleWidth - 1) + jungleLeftBottomCorner.x , randomInt.generate(jungleHeight -1) + jungleLeftBottomCorner.y );

            if(!isOccupied(grassPos))
            {
                grasses.put(grassPos, new Grass(grassPos));
                break;
            }
            else counter++;
        }
        counter = 0;
        while(counter < 3 * mapHeight*mapWidth-jungleHeight*jungleWidth)
        {
            grassPos = new Vector2D(randomInt.generate(mapWidth-1), randomInt.generate(mapHeight-1));
            if(!isOccupied(grassPos) && (!grassPos.precedes(jungleRightUpperCorner) || !grassPos.follows(jungleLeftBottomCorner)))
            {
                grasses.put(grassPos, new Grass(grassPos));
                return;
            }
            else counter++;
        }

    }

    public void createAnimal()
    {
        int counter = 0;
        while(counter < 2 * mapWidth * mapHeight)
        {
            RandomInt randomInt = new RandomInt();
            Vector2D animalPos = new Vector2D(randomInt.generate(mapWidth-1), randomInt.generate(mapHeight-1));
            if(!isOccupied(animalPos))
            {
                Animal animal = new Animal(this, animalPos, mapDirections[randomInt.generate(7)]);
                ArrayList<Animal> animalsOnSquare = new ArrayList<Animal>();
                animalsOnSquare.add(animal);
                animals.put(animalPos, animalsOnSquare);
                animalList.add(animal);
                return;
            }
            else counter++;
        }
    }

    public void animalBorn(ArrayList<Animal> animalsOnSquare, Vector2D pos)
    {

        animals.put(pos, animalsOnSquare);
        animalList.add(animalsOnSquare.get(0));
    }


    public void moveAnimals()
    {
        Collections.sort(animalList, Collections.reverseOrder());
        CopyOnWriteArrayList<Animal> animalListIterator = new CopyOnWriteArrayList<Animal>(animalList);
        for(Animal animal: animalListIterator)
        {
            animal.move();
        }
    }

    public void eating()
    {
        CopyOnWriteArrayList<Grass> grassesList = new CopyOnWriteArrayList<Grass>(grasses.values());
        for(Grass grass: grassesList)
        {
            ArrayList<Animal> animalsOnGrass = animals.get(grass.getPosition());

            if(animalsOnGrass != null)
            {
                if(animalsOnGrass.size() == 2)
                {
                    if(animalsOnGrass.get(0).getEnergy() > animalsOnGrass.get(1).getEnergy())
                    {
                        animalsOnGrass.get(0).changeEnergy(plantEnergy);
                    }
                    else if(animalsOnGrass.get(0).getEnergy() < animalsOnGrass.get(1).getEnergy())
                    {
                        animalsOnGrass.get(1).changeEnergy(plantEnergy);
                    }
                    else
                    {
                        animalsOnGrass.get(0).changeEnergy(plantEnergy/2);
                        animalsOnGrass.get(1).changeEnergy(plantEnergy/2);
                    }
                }

                animalsOnGrass.get(0).changeEnergy(plantEnergy);
                grasses.remove(grass.getPosition());

            }
        }
    }

    public void nextDay()
    {
        CopyOnWriteArrayList<Animal> animalListIterator = new CopyOnWriteArrayList<Animal>(animalList);
        for(Animal animal: animalListIterator)
        {
            MapDirection newOrientation = animal.getOrientation();
            RandomInt randomInt = new RandomInt();
            int rotation = randomInt.generate(7);
            for(int i = 0; i < rotation; i++)
            {
                newOrientation = newOrientation.next();
            }
            animal.changeOrientation(newOrientation);
        }
    }

    public void removeDead()
    {
        CopyOnWriteArrayList<Animal> animalListIterator = new CopyOnWriteArrayList<Animal>(animalList);
        for(Animal animal: animalListIterator)
        {

            if(animal.getEnergy() <= 0)
            {
                if(animals.get(animal.getPosition()) == null)
                {
                    animals.remove(animal.getPosition());
                }
                else
                {
                    animals.get(animal.getPosition()).remove(animal);
                }
                animalList.remove(animal);

            }
        }
    }

    public Vector2D toOppositeSide(Vector2D pos)
    {
        int newX = pos.x;
        int newY = pos.y;
        if(pos.x > mapRightUpperCorner.x)
        {
            newX = 0;
        }
        else if(pos.x < 0)
        {
            newX = mapRightUpperCorner.x;
        }

        if(pos.y > mapRightUpperCorner.y)
        {
            newY = 0;
        }
        else if(pos.y < 0)
        {
            newY = mapRightUpperCorner.y;
        }

        return new Vector2D(newX, newY);
    }

    public void updatePos(Vector2D newPos, Vector2D oldPos, Animal animal)
    {
        ArrayList<Animal> animalsOnNewPos = animals.get(newPos);
        ArrayList<Animal> animalsOnOldPos = animals.get(oldPos);
        if(animalsOnOldPos.size() == 1)
        {
            animals.remove(oldPos);
        }
        else
        {
            animalsOnOldPos.remove(animal);
        }

        if(animalsOnNewPos!= null)
        {
            animalsOnNewPos.add(animal);
        }
        else
        {
            ArrayList<Animal> animalsOnSquare = new ArrayList<Animal>();
            animalsOnSquare.add(animal);
            animals.put(newPos, animalsOnSquare);
        }

    }



    public void copulation()
    {
        CopyOnWriteArrayList<Animal> animalListIterator = new CopyOnWriteArrayList<Animal>(animalList);
        for(Animal animal: animalListIterator)
        {
            ArrayList<Animal> animalsOnSquare = animals.get(animal.getPosition());
            if(animalsOnSquare.size() == 2)
            {
                Animal father = animalsOnSquare.get(0);
                Animal mother = animalsOnSquare.get(1);
                if(father.getEnergy() > 0.5 * startEnergy && mother.getEnergy() > 0.5 * startEnergy)
                {
                    Vector2D freeAdjacentPos = this.freeAdjacentPos(father.getPosition());
                    if(freeAdjacentPos != null)
                    {
                        RandomInt randomInt = new RandomInt();
                        father.breed(freeAdjacentPos, mother, mapDirections[randomInt.generate(7)]);
                    }
                }
            }
        }
    }

    public Vector2D freeAdjacentPos(Vector2D parentPos)
    {
        ArrayList<Vector2D> freeAdjacentPositions = new ArrayList<Vector2D>();
        for(int i = 0; i < 8; i++)
        {
            Vector2D checkedPos = parentPos.add(mapDirections[i].toUnitVector());
            if(!isOccupied(checkedPos)) freeAdjacentPositions.add(checkedPos);
        }
        if(freeAdjacentPositions.size() == 0) return null;
        else
        {
            RandomInt randomInt = new RandomInt();
            return freeAdjacentPositions.get(randomInt.generate(freeAdjacentPositions.size() - 1) );
        }
    }

    public Vector2D getMapRightUpperCorner() {
        return mapRightUpperCorner;
    }

    public Vector2D getJungleRightUpperCorner() {
        return jungleRightUpperCorner;
    }

    public Vector2D getJungleLeftBottomCorner() {
        return jungleLeftBottomCorner;
    }

    public Vector2D getMapLowerBottomCorner() {
        return mapLowerBottomCorner;
    }

    public ArrayList<Grass> getGrasses()
    {
        return new ArrayList<>(grasses.values());
    }

    public ArrayList<Animal> getAnimals() { return animalList;}

    public int getStartEnergy() {
        return startEnergy;
    }

    public int getJungleWidth() {
        return jungleWidth;
    }

    public int getJungleHeight() {
        return jungleHeight;
    }

    public int getMoveEnergy() {
        return moveEnergy;
    }

    public int getNumOfGrassPerDayPerRegion() {
        return numOfGrassPerDayPerRegion;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }
}
