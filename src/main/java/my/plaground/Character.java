package my.plaground;

import static java.lang.Math.*;

public abstract class Character {

    protected double initHp;
    protected double hp;
    protected int level;
    protected int resistance;
    protected RandomDataGeneratorInterface randomDataGenerator;

    public int getLevel() {
        return level;
    }

    protected void setLevel(int level) {
        if (level <= 0)
            throw new RuntimeException("Invalid power");

        this.level = level;
    }

    public double getHp() {
        return hp;
    }


    public double getInitHp() {
        return initHp;
    }

    public int getResistance() {
        return resistance;
    }

    protected abstract double getSpecialDamage(Character enemy);

    public abstract int attackDamage();

    public int empoweredDamage() {
        return this.attackDamage() * this.level;
    }

    public boolean isDead() {
        return this.hp == 0;
    }

    public boolean isAlive() {
        return this.hp > 0;
    }

    public void attack(Character enemy) {
        if (this.isDead() || this.equals(enemy))
            return;

        double totalDamage = this.calculateTotalDamage(enemy);
        enemy.defend(totalDamage);
    }

    public double calculateTotalDamage(Character enemy) {
        return (this.empoweredDamage() * this.getSpecialDamage(enemy)) / enemy.getResistance();
    }

    public void defend(double damage) {
        if (damage <= 0)
            return;

        this.hp -= this.calculateReceivedDamage(damage);
        if (this.hp <= 0)
            this.hp = 0;
    }

    protected double calculateReceivedDamage(double damage) {
        return damage;
    }

    public void heal(Character target) {
        if(this.isDead() || target.isDead())
            return;

        double hps = this.calculateHealingHps();
        target.getHealed(hps);
    }

    protected abstract double calculateHealingHps();

    protected void getHealed(double hps) {
        this.hp = min(this.hp + hps, this.initHp);
    }
}
