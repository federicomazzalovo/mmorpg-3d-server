package my.plaground;

public class Paladin extends Character {

    public Paladin() {
        this(new RandomDataGenerator());
    }

    public Paladin(RandomDataGeneratorInterface proc) {
        this.randomDataGenerator = proc;
        this.hp = 150;
        this.initHp = hp;
        this.resistance = 4;
        this.setLevel(1);
    }

    @Override
    public int attackDamage() {
        return randomDataGenerator.getRandomValueRange(5, 8);
    }

    @Override
    public double getSpecialDamage(Character enemy) {
        if (enemy instanceof Rogue)
            return 1.5;

        return 1;
    }

    @Override
    protected double calculateReceivedDamage(double damage) {
        if (canProc())
            this.doubleResistance();

        return super.calculateReceivedDamage(damage);
    }

    @Override
    protected double calculateHealingHps() {
        return randomDataGenerator.getRandomValueRange(3, 6);
    }

    private void doubleResistance() {
        this.resistance = this.resistance * 2;
    }

    private boolean canProc() {
        return randomDataGenerator.getRandomPercentage() <= 20;
    }
}
