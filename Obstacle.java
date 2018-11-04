package genetic_learning;

import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Obstacle extends Rectangle {
    
    public static ArrayList<Obstacle> obstacles = new ArrayList(100);
    
    public Obstacle(double x, double y, double width, double height) {
        super(x, y, width, height);
        setFill(Color.RED);
        setStroke(Color.BLACK);
        obstacles.add(this);
    }
    
    public static ArrayList<Obstacle> getObstacles() {
        return obstacles;
    }
}
