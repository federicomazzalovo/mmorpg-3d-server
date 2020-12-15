package my.plaground;

import java.util.Random;

public class Paladin extends Character {

    public Paladin() {
      this(1);
    }

    public Paladin(int power) {
        this.hp = 150;
        this.setPower(power);
        this.resistance = 4;
    }

    @Override
    public int attackDamage() {
        Random random = new Random();
        return random.nextInt(8 - 5) + 5;
    }

    @Override
    public double getSpecialDamage(Character enemy) {
        if(enemy instanceof Rogue)
            return 1.5;

        return 1;
    }

}
