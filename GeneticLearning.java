package genetic_learning;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.Light.Point;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GeneticLearning extends Application {

    public static Pane root;
    public static Population pop;
    public static Point2D goal, start;
    private final Rectangle selection = new Rectangle();
    private final Point anchor = new Point();
    
    @Override
    public void start(Stage primaryStage) {
        root = new Pane();

        Scene scene = new Scene(root, 800, 400);

        primaryStage.setTitle("Genetic Learning");
        primaryStage.setScene(scene);
        primaryStage.show();

        pop = new Population(100);
        pop.display();

        goal = new Point2D(750, 200);
        Circle goalCircle = new Circle(goal.getX(), goal.getY(), 5);
        goalCircle.setFill(Color.BLUE);

        start = new Point2D(50, 200);
        Circle startCircle = new Circle(start.getX(), start.getY(), 5);
        startCircle.setFill(Color.BLACK);

        Label generationLabel = new Label();
        Label stepLabel = new Label();
        stepLabel.setLayoutY(15);

        root.getChildren().addAll(selection, goalCircle, startCircle, generationLabel, stepLabel);

        scene.setOnMousePressed(this::onMousePress);
        scene.setOnMouseDragged(this::onDrag);
        scene.setOnMouseReleased(this::onRelease);

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), e -> {
            if (!pop.allDead()) {
                pop.update();
            } else {
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

    private void onMousePress(MouseEvent event) {
        anchor.setX(event.getX());
        anchor.setY(event.getY());
        selection.setX(anchor.getX());
        selection.setY(anchor.getY());
        selection.setFill(null);
        selection.setStroke(Color.BLACK);
        selection.getStrokeDashArray().add(10.0);
    }

    private void onDrag(MouseEvent event) {
        selection.setWidth(Math.abs(event.getX() - anchor.getX()));
        selection.setHeight(Math.abs(event.getY() - anchor.getY()));
        selection.setX(Math.min(anchor.getX(), event.getX()));
        selection.setY(Math.min(anchor.getY(), event.getY()));
    }

    private void onRelease(MouseEvent event) {
        selection.setStroke(null);
        root.getChildren().add(new Obstacle(selection.getX(), selection.getY(), selection.getWidth(), selection.getHeight()));
        selection.setWidth(0);
        selection.setHeight(0);
    }
}