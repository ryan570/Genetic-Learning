package genetic_learning;

import java.util.Random;
import javafx.geometry.Point2D;

public class Brain {

    public Point2D[] instructions;
    public int currentStep;
    private Random random;

    public Brain(int size) {
        random = new Random();

        currentStep = 0;
        instructions = new Point2D[size];
        randomize();
    }

    private void randomize() {
        for (int i = 0; i < instructions.length; i++) {
            float x = (float) (random.nextFloat() * 3 - 1.5);
            float y = (float) (random.nextFloat() * 3 - 1.5);
            instructions[i] = new Point2D(x, y);
        }
    }

    public Brain clone() {
        Brain clone = new Brain(instructions.length);
        for (int i = 0; i < clone.instructions.length; i++) {
            clone.instructions[i] = instructions[i];
        }
        return clone;
    }

    public void mutate() {
        double mutationRate = 0.05;

        for (int i = 0; i < instructions.length; i++) {
            float rand = random.nextFloat();
            if (rand < mutationRate) {
                double x = random.nextDouble() - .5;
                double y = random.nextDouble() - .5;
                instructions[i] = instructions[i].add(x, y);
            }
        }
    }
}
