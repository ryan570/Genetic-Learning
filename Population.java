package genetic_learning;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Random;

public class Population {

    public Individual[] individuals;
    public float overallFitness;
    private Random random;
    public int maxStep = 200;
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
            Brain parent = naturalSelection().brain;
            newPop[i] = parent.clone();
        }

        for (int i = 0; i < individuals.length; i++) {
            individuals[i].brain = newPop[i];
        }
        center();
        generation++;
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
            if (victor.reachedGoal) {
                maxStep = victor.brain.currentStep;
            }
        }
    }
}
