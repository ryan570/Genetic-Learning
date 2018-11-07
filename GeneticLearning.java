package genetic_learning;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    private Button begin;
    private Label generationLabel, stepLabel;
    private Timeline timeline;
    
    @Override
    public void start(Stage primaryStage) {
        root = new Pane();

        pop = new Population(100);

        goal = new Point2D(750, 200);
        Circle goalCircle = new Circle(goal.getX(), goal.getY(), 5);
        goalCircle.setFill(Color.BLUE);

        start = new Point2D(50, 200);
        Circle startCircle = new Circle(start.getX(), start.getY(), 5);
        startCircle.setFill(Color.BLACK);

        begin = new Button("Start Evolution");
        begin.setLayoutX(355);
        begin.setOnAction(this::beginEvolution);

        generationLabel = new Label();
        stepLabel = new Label();
        stepLabel.setLayoutY(15);

        root.getChildren().addAll(selection, begin, goalCircle, startCircle);

        timeline = new Timeline(new KeyFrame(Duration.millis(10), e -> {
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

        Scene scene = new Scene(root, 800, 400);

        scene.setOnMousePressed(this::onMousePress);
        scene.setOnMouseDragged(this::onDrag);
        scene.setOnMouseReleased(this::onRelease);

        primaryStage.setTitle("Genetic Learning");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void beginEvolution(ActionEvent event) {
        root.getChildren().addAll(generationLabel, stepLabel);
        root.getChildren().remove(begin);

        pop.display();
        timeline.play();
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