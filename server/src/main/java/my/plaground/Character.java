package my.plaground;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import static java.lang.Math.*;

public abstract class Character extends Target {

    protected int id;
    protected int level;
    protected int resistance;
    protected double maxRange;
    @JsonProperty("isPlayer")
    protected boolean isPlayer;
    protected Faction faction;
    protected Position position;
    @JsonIgnore
    protected RandomDataGeneratorInterface randomDataGenerator;

    public void setIsPlayer(boolean player) {
        isPlayer = player;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        if (level <= 0)
            throw new RuntimeException("Invalid power");

        this.level = level;
    }

    public int getResistance() {
        return resistance;
    }

    public Position getPosition() {
        return position;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
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

    public void attack(Character target) {
        if (!canAttack(target))
            return;

        double totalDamage =  this.calculateTotalDamage(target);
        target.defend(totalDamage);
    }

    public void attack(Prop target) {
        double totalDamage = this.calculateTotalDamage(target);
        target.defend(totalDamage);
    }

    private boolean canAttack(Character character) {
        double enemyDistance = this.position.distanceFrom(character.getPosition());
        return this.isAlive()
                && enemyDistance <= maxRange
                && !character.isAlly(this)
                && !character.isMe(this);
    }

    public double calculateTotalDamage(Character target) {
        double totalDamage = (this.empoweredDamage() * this.getSpecialDamage(target)) / target.getResistance();
        if(target.level - this.level >= 5 )
            totalDamage *= 0.5;
        else if(this.level - target.level >= 5)
            totalDamage += totalDamage *  0.5;

        return totalDamage;
    }

    public double calculateTotalDamage(Prop target) {
        return this.empoweredDamage();
    }

    public void heal(Character target) {
        if (!canHeal(target))
            return;

        double hps = this.calculateHealingHps();
        target.getHealed(hps);
    }

    private boolean canHeal(Character target) {
        return      (this.isAlive() && target.isAlive())
                &&  (this.isMe(target) || target.isAlly(this));
    }

    protected abstract double calculateHealingHps();

    protected void getHealed(double hps) {
        this.hp = min(this.hp + hps, this.initHp);
    }


    public void setPosition(Position position) {
        this.position = position;
    }

    public void joinFaction(Faction faction){
        if(this.faction != null)
            return;

        this.faction = faction;
    }

    public void leaveFaction(){
        this.faction = null;
    }

    public boolean isAlly(Character character){
        return this.faction != null && this.faction.equals(character.faction);
    }


    private boolean isMe(Character character){
      return this.equals(character);
    }
}
