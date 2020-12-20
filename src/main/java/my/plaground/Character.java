package my.plaground;

public abstract class Character {

    protected double hp;
    protected int power;
    protected int resistance;
    protected ProgrammedRandomOccurrenceInterface proc;

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

    public void receiveDamage(double damage) {
        if(damage <= 0)
            return;

        this.hp -= damage;
        if (this.hp <= 0)
            this.hp = 0;
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

        procAbility();

        double totalDamage = (this.empoweredDamage() * this.getSpecialDamage(enemy)) / enemy.getResistance();
        enemy.receiveDamage(totalDamage);
    }

    protected abstract void procAbility();
}
