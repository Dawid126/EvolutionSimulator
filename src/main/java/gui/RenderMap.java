package main.java.gui;
import main.java.jungle_elements.Animal;
import main.java.jungle_elements.Grass;
import main.java.map.WorldMap;

import javax.swing.*;
import java.awt.*;

public class RenderMap extends JPanel {

    private WorldMap map;
    private MapSimulation simulation;

    public RenderMap(WorldMap map, MapSimulation simulation) {
        this.map = map;
        this.simulation = simulation;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setSize((int) (simulation.frame.getWidth()), simulation.frame.getHeight() - 38);
        this.setLocation( 0, 0);
        int width = this.getWidth();
        int height = this.getHeight(); //38 is toolbar size
        int widthScale = width / map.getMapWidth();
        int heightScale = height / map.getMapHeight();

        //draw Steppe
        g.setColor(new Color(170, 224, 103));
        g.fillRect(0, 0, width, height);

        //draw Jungle
        g.setColor(new Color(0, 160, 7));
        g.fillRect(map.getJungleLeftBottomCorner().x * widthScale,
                map.getJungleLeftBottomCorner().y * heightScale,
                map.getJungleWidth() * widthScale,
                map.getJungleHeight() * heightScale);

        //draw Grass
        for (Grass grass : map.getGrasses()) {
            g.setColor(new Color(0, 102, 0));
            int y = grass.getPosition().y * heightScale;
            int x = grass.getPosition().x * widthScale;
            g.fillRect(x, y, widthScale, heightScale);
        }
        //draw Animals
        for (Animal animal : map.getAnimals()) {
            g.setColor(animal.getColor());
            int y = animal.getPosition().y * heightScale;
            int x = animal.getPosition().x * widthScale;
            g.fillOval(x, y, widthScale, heightScale);
        }
    }
}
