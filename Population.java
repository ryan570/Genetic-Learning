package genetic_learning;

import javafx.geometry.Point2D;

import java.util.Random;

public class Population {

    public Individual[] individuals;
    public float overallFitness;
    private Random random;
    public int maxStep = 150;
    public int generation;

    public Population(int size) {
        generation = 0;
        random = new Random();

        individuals = new Individual[size];
        for (int i = 0; i < individuals.length; i++) {
            individuals[i] = new Individual();
        }

    }

    public void display() {
        for (int i = 0; i < individuals.length; i++) {
            individuals[i].display();
        }
    }

    public void update() {
        for (int i = 0; i < individuals.length; i++) {
            individuals[i].update(maxStep);
        }
    }

    public boolean allDead() {
        for (int i = 0; i < individuals.length; i++) {
            if (!individuals[i].isDead) {
                return false;
            }
        }
        return true;
    }

    public void calculateFitness() {
        for (int i = 0; i < individuals.length; i++) {
            individuals[i].calculateFitness();
        }
    }

    public void calculateSum() {
        overallFitness = 0;
        for (int i = 0; i < individuals.length; i++) {
            overallFitness += individuals[i].fitness;
        }
    }

    public Individual naturalSelection() {
        float sum = 0;
        float rand = random.nextFloat();
        float test = rand * overallFitness;

        for (int i = 0; i < individuals.length; i++) {
            sum += individuals[i].calculateFitness();
            if (sum > test) {
                return individuals[i];
            }
        }
        System.out.println("fail");
        return null;
    }

    public void center() {
        for (int i = 0; i < individuals.length; i++) {
            individuals[i].center();
        }
    }

    public void evolve() {
        Brain[] newPop = new Brain[individuals.length];
        calculateFitness();
        calculateSum();

        for (int i = 0; i < newPop.length; i++) {
            Individual parentA = naturalSelection();
            Individual parentB = naturalSelection();

            newPop[i] = crossover(parentA, parentB).brain;
        }

        for (int i = 0; i < individuals.length; i++) {
            individuals[i].brain = newPop[i];
        }
        center();
        generation++;
    }

    public Individual crossover(Individual parentA, Individual parentB) {
        Individual offspring = new Individual();

        Brain a = parentA.fitness > parentB.fitness ? parentA.brain : parentB.brain;
        Brain b = parentA.fitness > parentB.fitness ? parentB.brain : parentA.brain;

        double alpha = 0.75, beta = 0.25;

        for (int i = 0; i < a.instructions.length; i++) {
            double aX = a.instructions[i].getX(), aY = a.instructions[i].getY();
            double bX = b.instructions[i].getX(), bY = b.instructions[i].getY();

            double x, y;

            double dX = Math.abs(aX - bX);
            if(aX <= bX) {
                x = Math.random() * (dX * (alpha + beta + 1.0D)) + aX - alpha * dX;
            }else {
                x = Math.random() * (dX * (alpha + beta + 1.0D)) + bX - beta * dX;
            }

            double dY = Math.abs(aY - bY);
            if(aY <= bY) {
                y = Math.random() * (dY * (alpha + beta + 1.0D)) + aY - alpha * dY;
            }else {
                y = Math.random() * (dY * (alpha + beta + 1.0D)) + bY - beta * dY;
            }

            x = Math.max(-1.0D, Math.min(1.0D, x));
            y = Math.max(-1.0D, Math.min(1.0D, y));

            offspring.brain.instructions[i] = new Point2D(x, y);
        }

        return offspring;
    }

    public void mutate() {
        for (int i = 0; i < individuals.length; i++) {
            individuals[i].brain.mutate();
        }
    }

    public Individual findVictor() {
        float max = 0;
        Individual victor = null;
        for (Individual individual : individuals) {
            if (individual.fitness > max) {
                max = individual.fitness;
                victor = individual;
            }
        }
        return victor;
    }

    public void setMaxStep() {
        Individual victor = findVictor();
        if (victor != null) {
            if (victor.reachedGoal && victor.brain.currentStep < maxStep) {
                maxStep = victor.brain.currentStep;
            }
        }
    }
}