package my.plaground;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static my.plaground.Character.CharacterType.*;

public class  Character {

    private final Map<CharacterType, CharacterType> typeVersusWeak = Map.of(
            Paladin, Rogue,
            Rogue, Wizard,
            Wizard, Paladin
    );

    public boolean isDead() {
        return this.hp == 0;
    }

    public boolean isAlive() {
        return this.hp > 0;
    }

    public enum CharacterType {
        Paladin,
        Rogue,
        Wizard
    }

    private int hp;
    private int power;
    private final int resistance;
    private final CharacterType type;

    public Character(CharacterType type) {

        switch (type) {
            case Paladin:
                this.hp = 150;
                this.power = 1;
                this.resistance = 4;
                break;

            case Rogue:
                this.hp = 120;
                this.power = 1;
                this.resistance = 3;
                break;

            case Wizard:
                this.hp = 100;
                this.power = 1;
                this.resistance = 2;
                break;

            default:
                throw new RuntimeException();
        }

        this.type = type;
    }

    public Character(CharacterType type, int power) {
        this(type);
       this.setPower(power);
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power){
        if(power <= 0)
            throw new RuntimeException("Invalid power");

        this.power = power;
    }

    public int getHp() {
        return hp;
    }

    public int getResistance() {
        return resistance;
    }

    public CharacterType getType() {
        return type;
    }

    public int attackDamage() {
        Random random = new Random();
        switch (this.type){
            case Paladin:
                return random.nextInt(8 - 5) + 5;
            case Rogue:
                return random.nextInt(12 - 9) + 9;
            case Wizard:
                return random.nextInt(16 - 13) + 13;
            default:
                throw new RuntimeException();
        }
    }

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

    public void attack(Character enemy) {
        if(this.isDead())
            return;

        double totalDamage = (this.empoweredDamage() * this.getSpecialDamage(enemy.getType())) / enemy.getResistance();
        enemy.receiveDamage(totalDamage);
    }

    private double getSpecialDamage(CharacterType enemyType) {
       return this.typeVersusWeak.get(this.type) == enemyType
               ? 1.5
               : 1;
    }


}
