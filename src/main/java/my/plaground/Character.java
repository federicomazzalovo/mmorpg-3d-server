package my.plaground;

import java.util.Random;

public class  Character {

    public enum CharacterType {
        Paladin,
        Rogue,
        Wizard
    }

    private int hp;
    private int power;
    private int resistance;
    private CharacterType type;

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
        this.power = power;
    }

    public int getPower() {
        return power;
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
}
