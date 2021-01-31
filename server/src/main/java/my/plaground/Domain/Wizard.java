package my.plaground.Domain;

public class Wizard extends Character {

    public Wizard() {
        this(new RandomDataGenerator());
    }

    public Wizard(RandomDataGeneratorInterface proc){
        this.characterClass = CharacterClass.Wizard;
        this.randomDataGenerator = proc;
        this.hp = 100;
        this.initHp = hp;
        this.resistance = 2;
        this.maxRange = 1000;
        this.position = Position.at(0,0);
        this.setLevel(1);
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
    public double calculateTotalDamage(Character target) {
        double totalDamage = super.calculateTotalDamage(target);
        if(canProc())
            this.increaseHpByPercentage(10.0);

        return totalDamage;
    }

    @Override
    protected double calculateHealingHps() {
        return randomDataGenerator.getRandomValueRange(1,4);
    }

    public void increaseHpByPercentage(double percentage) {
        if(percentage < 0)
            return;

        this.getHealed(this.hp * (percentage / 100.0));
    }

    private boolean canProc() {
        return randomDataGenerator.getRandomPercentage() <= 20;
    }

}
