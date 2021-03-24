package my.plaground.Domain;

public abstract class Target {
    protected double initHp = 0;
    protected double hp = 0;

    public void setHp(double hp) {
        this.hp = hp;
    }
    public double getHp() {
        return hp;
    }
    public double getInitHp() {
        return initHp;
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
}
