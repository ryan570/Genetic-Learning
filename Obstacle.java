package genetic_learning;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Obstacle extends Rectangle {

    public static ArrayList<Obstacle> obstacles = new ArrayList();

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
        } else {
            GeneticLearning.root.getChildren().remove(this);
        }
    }

    public static ArrayList<Obstacle> getObstacles() {
        return obstacles;
    }
}
