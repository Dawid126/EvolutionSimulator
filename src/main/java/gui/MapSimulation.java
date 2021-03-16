package main.java.gui;

import main.java.map.WorldMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MapSimulation implements ActionListener{


    public WorldMap map;
    public JFrame frame;
    public RenderMap renderMap;
    public Timer timer;


    public MapSimulation(WorldMap map) {

        this.map = map;
        timer = new Timer(50, this);
        frame = new JFrame("Evolution Simulator");
        int frameScale = 700/Math.max(map.getMapHeight(), map.getMapWidth());
        frame.setSize(map.getMapWidth()*frameScale, map.getMapHeight()*frameScale);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setBackground(Color.white);
        renderMap = new RenderMap(map, this);
        renderMap.setSize(new Dimension(1, 1));
        frame.add(renderMap);
    }

    public void startSimulation() {
        timer.start();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        renderMap.repaint();

        map.nextDay();
        map.moveAnimals();
        map.eating();
        map.copulation();
        map.removeDead();
        for (int i = 0; i < map.getNumOfGrassPerDayPerRegion(); i++) {
            map.spawnGrass();

        }
    }
}
