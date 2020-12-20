package my.plaground;

import java.util.Random;

public class Rogue extends Character {

    public Rogue() {
        this(new ProgrammedRandomOccurrence());
    }

    public Rogue(ProgrammedRandomOccurrenceInterface proc) {
        this.proc = proc;
        this.hp = 120;
        this.setPower(1);
        this.resistance = 3;
    }

    @Override
    public int attackDamage() {
        Random random = new Random();
        return random.nextInt(12 - 9) + 9;
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
        return proc.getRandomPercentage() <= 20;
    }
}
