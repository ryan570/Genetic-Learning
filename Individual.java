package genetic_learning;

import javafx.geometry.Point2D;
import javafx.scene.shape.Circle;

public class Individual {

    public Point2D pos, vel, acc;
    public Circle circle;
    public Brain brain;
    public boolean isDead = false, reachedGoal = false;
    public float fitness;
    
    public Individual() {
        fitness = 0;
        brain = new Brain(100);
        
        pos = new Point2D(50, 200);
        vel = new Point2D(0, 0);
        acc = new Point2D(0, 0);
        
        circle = new Circle(pos.getX(), pos.getY(), 3);
    }
    
    public void display() {
        GeneticLearning.root.getChildren().add(circle);
    }
    
    public void center() {
        isDead = false;
        reachedGoal = false;
        
        pos = new Point2D(50, 200);
        vel = new Point2D(0, 0);
        acc = new Point2D(0, 0);
        
        circle.setCenterX(pos.getX());
        circle.setCenterY(pos.getY());
    }
    
    public void move() {
        if (brain.currentStep < brain.instructions.length) {
            acc = brain.instructions[brain.currentStep];
            brain.currentStep++;
        }
        else {
            isDead = true;
        }
        
        vel = vel.add(acc);
        pos = pos.add(vel);
        
        circle.setCenterX(pos.getX());
        circle.setCenterY(pos.getY());
    }
    
    public void update(int maxStep) {
        if (!isDead && !reachedGoal) {
            move();
        }
        
        if (pos.getX() <= 0 || pos.getX() >= 800 || pos.getY() <= 0 || pos.getY() >= 400) {
            isDead = true;
        }
        
        if (brain.currentStep - maxStep > 5) {
            isDead = true;
        }
        
        if ((pos.getX() > 385 && pos.getX() < 415) && (pos.getY() > 100 && pos.getY() < 300)) {
            isDead = true;
        }
        
        double distanceToGoal = pos.distance(GeneticLearning.goal);
        if (distanceToGoal < 7.5) {
            reachedGoal = true;
            isDead = true;
        }
    }
    
    public float calculateFitness() {
        float distanceToGoal = (float) pos.distance(GeneticLearning.goal);
        if (reachedGoal) {
            fitness = 1000 * 1/(brain.currentStep);
        }
        else {
            fitness = 1 / (distanceToGoal * distanceToGoal);
        }
        return fitness;
    }
    
    public Individual clone() {
        Individual clone = new Individual();
        clone.brain = brain;
        clone.brain.currentStep = 0;
        
        return clone;
    }
}
