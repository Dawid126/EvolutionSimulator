package main.java;


import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import main.java.gui.MapSimulation;
import main.java.map.WorldMap;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Main {

    public static void main(String[] args) {

        JSONParser jsonParser = new JSONParser();
        int[] parameters = new int[9];
        String parametersPath = new File("").getAbsolutePath();
        parametersPath = parametersPath.concat("\\src\\main\\resources\\parameters.json");

        try {
            FileReader fileReader = new FileReader(parametersPath);
            JSONObject jsonObject = (JSONObject) jsonParser.parse(fileReader);
            parameters[0] = (int) (long) jsonObject.get("width");
            parameters[1] = (int) (long) jsonObject.get("height");
            parameters[2] = (int) (long) jsonObject.get("startEnergy");
            parameters[3] = (int) (long) jsonObject.get("moveEnergy");
            parameters[4] = (int) (long) jsonObject.get("plantEnergy");
            parameters[5] = (int) (long) jsonObject.get("jungleRatio");
            parameters[6] = (int) (long) jsonObject.get("numOfStartingAnimals");
            parameters[7] = (int) (long) jsonObject.get("numOfGrassPerDayPerRegion");
            parameters[8] = (int) (long) jsonObject.get("numOfStartingGrass");
            
        } catch (IOException | ParseException e) {
            System.out.println("Error when loading parameters from json file");
            System.exit(0);
        }
        WorldMap worldMap = new WorldMap(parameters);
        MapSimulation simulation = new MapSimulation(worldMap);
        simulation.startSimulation();

    }
}
