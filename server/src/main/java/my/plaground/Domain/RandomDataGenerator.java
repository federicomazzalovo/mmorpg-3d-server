package my.plaground.Domain;

import java.util.concurrent.ThreadLocalRandom;

public class RandomDataGenerator implements RandomDataGeneratorInterface {

    @Override
    public int getRandomPercentage() {
        return ThreadLocalRandom.current().nextInt(1, 101);
    }

    @Override
    public int getRandomValueRange(int minInclude, int maxInclude) {
       return ThreadLocalRandom.current().nextInt(minInclude, maxInclude + 1);
    }


}
