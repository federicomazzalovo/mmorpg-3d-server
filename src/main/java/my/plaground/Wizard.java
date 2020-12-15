package my.plaground;

import java.util.Random;

public class Wizard extends Character {

    public Wizard() {
        this(1);
    }

    public Wizard( int power) {
        this.hp = 100;
        this.setPower(power);
        this.resistance = 2;
    }

    @Override
    public int attackDamage() {
        Random random = new Random();
        return random.nextInt(16 - 13) + 13;
    }

    @Override
    public double getSpecialDamage(Character enemy) {
        if(enemy instanceof Paladin)
            return 1.5;

        return 1;
    }

    public void increaseHpByPercentage(int percentage) {
        if(percentage < 0)
            return;

        this.hp += this.hp * ((double)percentage / 100);
    }
}
