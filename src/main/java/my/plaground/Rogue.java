package my.plaground;

import java.util.Random;

public class Rogue extends Character {

    public Rogue() {
        this(1);
    }

    public Rogue(int power){
        this.hp = 120;
        this.setPower(power);
        this.resistance = 3;
    }

    @Override
    public int attackDamage() {
        Random random = new Random();
        return random.nextInt(12 - 9) + 9;
    }

    @Override
    public double getSpecialDamage(Character enemy) {
        if(enemy instanceof Wizard)
            return 1.5;

        return 1;
    }

    @Override
    protected void procAbility() {
    }

}
