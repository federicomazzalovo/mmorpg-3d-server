package my.plaground;

import java.util.Random;

public class Wizard extends Character {

    public Wizard() {
        this(new ProgrammedRandomOccurrence());
    }

    public Wizard(ProgrammedRandomOccurrenceInterface proc){
        this.proc = proc;
        this.hp = 100;
        this.resistance = 2;
        this.setPower(1);
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


    @Override
    public double calculateTotalDamage(Character enemy) {
        double totalDamage = super.calculateTotalDamage(enemy);
        if(canProc())
            this.increaseHpByPercentage(10.0);

        return totalDamage;
    }

    public void increaseHpByPercentage(double percentage) {
        if(percentage < 0)
            return;

        this.hp += this.hp * (percentage / 100.0);
    }

    private boolean canProc() {
        return proc.getRandomPercentage() <= 20;
    }

}
