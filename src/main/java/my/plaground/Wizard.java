package my.plaground;

public class Wizard extends Character {

    public Wizard() {
        this(new RandomDataGenerator());
    }

    public Wizard(RandomDataGeneratorInterface proc){
        this.randomDataGenerator = proc;
        this.hp = 100;
        this.resistance = 2;
        this.setPower(1);
    }


    @Override
    public int attackDamage() {
        return randomDataGenerator.getRandomValueRange(13,16);
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
        return randomDataGenerator.getRandomPercentage() <= 20;
    }

}
