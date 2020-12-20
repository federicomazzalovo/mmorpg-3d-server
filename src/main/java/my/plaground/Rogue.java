package my.plaground;

import java.util.Random;

public class Rogue extends Character {

    public Rogue() {
        this(new RandomDataGenerator());
    }

    public Rogue(RandomDataGeneratorInterface randomDataGenerator) {
        this.randomDataGenerator = randomDataGenerator;
        this.hp = 120;
        this.setPower(1);
        this.resistance = 3;
    }

    @Override
    public int attackDamage() {
        return randomDataGenerator.getRandomValueRange(9,12);
    }

    @Override
    public double getSpecialDamage(Character enemy) {
        if (enemy instanceof Wizard)
            return 1.5;

        return 1;
    }

    @Override
    public double calculateTotalDamage(Character enemy) {
        double damage = super.calculateTotalDamage(enemy);

        if(this.canProc())
            damage = damage * 2;

        return damage;
    }

    private boolean canProc() {
        return randomDataGenerator.getRandomPercentage() <= 20;
    }
}
