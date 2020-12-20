package my.plaground;

public abstract class Character {

    protected double hp;
    protected int power;
    protected int resistance;
    protected RandomDataGeneratorInterface randomDataGenerator;

    public int getPower() {
        return power;
    }

    protected void setPower(int power){
        if(power <= 0)
            throw new RuntimeException("Invalid power");

        this.power = power;
    }

    public double getHp() {
        return hp;
    }

    public int getResistance() {
        return resistance;
    }


    protected abstract double getSpecialDamage(Character enemy);

    public abstract int attackDamage();

    public int empoweredDamage() {
        return this.attackDamage() * this.power;
    }


    public boolean isDead() {
        return this.hp == 0;
    }

    public boolean isAlive() {
        return this.hp > 0;
    }

    public void attack(Character enemy) {
        if(this.isDead())
            return;

        double totalDamage = this.calculateTotalDamage(enemy);
        enemy.defend(totalDamage);
    }

    public double calculateTotalDamage(Character enemy) {
        return (this.empoweredDamage() * this.getSpecialDamage(enemy)) / enemy.getResistance();
    }

    public void defend(double damage) {
        if(damage <= 0)
            return;

        this.hp -= this.calculateReceivedDamage(damage);
        if (this.hp <= 0)
            this.hp = 0;
    }

    protected double calculateReceivedDamage(double damage) {
        return damage;
    }
}
