package genetic_learning;

import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class GeneticLearning extends Application {

    public static Pane root;
    public static Population pop;
    public static Point2D goal;
    public static Rectangle box;

    @Override
    public void start(Stage primaryStage) {
        root = new Pane();

        Scene scene = new Scene(root, 800, 400);

        primaryStage.setTitle("Genetic Learning");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        pop = new Population(100);
        pop.display();

        box = new Rectangle(385, 100, 30, 200);
        box.setFill(Color.RED);
        goal = new Point2D(750, 200);
        Circle circle = new Circle(goal.getX(), goal.getY(), 5);
        circle.setFill(Color.BLUE);
        root.getChildren().addAll(circle, box);

        new Timer().schedule(new FrameUpdate(), 40, 40);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
