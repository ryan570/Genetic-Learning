package genetic_learning;

import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.util.Duration;

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

        Label generationLabel = new Label();
        Label stepLabel = new Label();
        stepLabel.setLayoutY(15);

        root.getChildren().addAll(circle, box, generationLabel, stepLabel);

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), e -> {
            if (!pop.allDead()) {
                pop.update();
            }
            else {
                pop.setMaxStep(); 
                
                pop.evolve();
                pop.mutate();
            }
            generationLabel.setText(String.format("Generation: %d", pop.generation));
            stepLabel.setText(String.format("Step Count: %d", pop.maxStep));
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
