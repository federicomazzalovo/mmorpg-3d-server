package my.plaground;

import java.util.Random;

public class Paladin extends Character {

    public Paladin() {
        this(new ProgrammedRandomOccurrence());
    }

    public Paladin(ProgrammedRandomOccurrenceInterface proc) {
        this.proc = proc;
        this.hp = 150;
        this.resistance = 4;
        this.setPower(1);
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


    @Override
    protected void procDefendAbility() {
        if(!canProc())
            return;

        this.doubleResistance();
    }

    private void doubleResistance() {
        this.resistance = this.resistance * 2;
    }

    private boolean canProc() {
        return proc.getRandomPercentage() <= 20;
    }
}
