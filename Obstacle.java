package genetic_learning;

import java.util.ArrayList;

import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static javafx.scene.input.KeyCode.DELETE;

public class Obstacle extends Rectangle {
    
    public static ArrayList<Obstacle> obstacles = new ArrayList(100);
    
    public Obstacle(double x, double y, double width, double height) {
        super(x, y, width, height);
        if (!(height < 5 || width < 5)) {
            setFill(Color.RED);
            setStroke(Color.BLACK);
            obstacles.add(this);

            setOnMousePressed(event -> {
                GeneticLearning.root.getChildren().remove(this);
                obstacles.remove(this);
            });
        }
        else {
            GeneticLearning.root.getChildren().remove(this);
        }
    }
    
    public static ArrayList<Obstacle> getObstacles() {
        return obstacles;
    }
}
