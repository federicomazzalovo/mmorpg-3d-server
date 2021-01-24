package my.plaground;

public class Rogue extends Character {

    public Rogue() {
        this(new RandomDataGenerator());
    }

    public Rogue(RandomDataGeneratorInterface randomDataGenerator) {
        this.randomDataGenerator = randomDataGenerator;
        this.hp = 120;
        this.initHp = hp;
        this.resistance = 3;
        this.maxRange = 100;
        this.position = Position.at(0,0);
        this.setLevel(1);
    }

    @Override
    public int attackDamage() {
        return randomDataGenerator.getRandomValueRange(9, 12);
    }

    @Override
    public double getSpecialDamage(Character enemy) {
        if (enemy instanceof Wizard)
            return 1.5;

        return 1;
    }

    @Override
    public double calculateTotalDamage(Character target) {
        double damage = super.calculateTotalDamage(target);
        if (this.canProc())
            damage = damage * 2;

        return damage;
    }

    @Override
    protected double calculateHealingHps() {
        return 0;
    }

    private boolean canProc() {
        return randomDataGenerator.getRandomPercentage() <= 20;
    }
}
