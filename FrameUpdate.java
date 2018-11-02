package genetic_learning;

import java.util.TimerTask;

public class FrameUpdate extends TimerTask {

    Population pop = GeneticLearning.pop;

    public void run() {
        if (!pop.allDead()) {
            pop.update();
        }
        else {
            //pop.setMaxStep(); breaks the algorithm in later generations
            pop.evolve();
            pop.mutate();
        }
    }
}
