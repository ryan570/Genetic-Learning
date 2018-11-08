package genetic_learning;

import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew on 11/7/2018.
 */
public class VisualGraph<T extends Number> extends Pane {

    private List<T> data;
    private int cycleCount;

    public VisualGraph(int cycleCount) {
        data = new ArrayList<>();
        this.cycleCount = cycleCount;

        Group permanent = new Group(new Line(18, 18, 18, 192), new Line(18, 192, 192, 192));
        for (int i = 1; i <= 10; i++) {
            double y = calculateY(2 * i * 0.1D);
            permanent.getChildren().add(new Line(16, y, 20, y));

            Text text = new Text(4, y + 3, String.format("%.1f", i * 0.1f));
            text.setFont(Font.font(8));
            permanent.getChildren().add(text);
        }
        for (int i = 1; i < cycleCount; i++) {
            double x = calculateX(i);
            permanent.getChildren().add(new Line(x, 190, x, 194));
        }

        Text title = new Text(70, 15, "Average Fitness");
        permanent.getChildren().add(title);

        getChildren().add(permanent);
    }

    public void insertData(T value) {
        if (data.size() < cycleCount) {
            data.add(value);
        } else {
            data.remove(0);
            data.add(value);
        }
    }

    public void draw() {
        getChildren().removeIf(n -> n instanceof Circle);
        getChildren().removeIf(n -> n instanceof Line);
        Circle lastCircle = null;
        for (int i = 0; i < data.size(); i++) {
            Circle circle = new Circle(calculateX(i), calculateY(data.get(i).doubleValue()), 2);
            getChildren().add(circle);
            if (i > 0) {
                getChildren().add(new Line(lastCircle.getCenterX(), lastCircle.getCenterY(), circle.getCenterX(), circle.getCenterY()));
            }
            lastCircle = circle;
        }
    }

    public double calculateX(double input) {
        return 18 + input * (160 / cycleCount + 1);
    }

    public double calculateY(double input) {
        return 192 - input * 80;
    }
}
