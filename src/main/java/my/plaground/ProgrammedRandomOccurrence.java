package my.plaground;

import java.util.Random;

public class ProgrammedRandomOccurrence implements  ProgrammedRandomOccurrenceInterface {

    @Override
    public int getRandomPercentage() {
        Random random = new Random();
        return random.nextInt(101) + 1;
    }
}
