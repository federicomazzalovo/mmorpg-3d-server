package my.plaground;

public class Character {

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
                this.power = 2;
                this.resistance = 4;
                break;

            case Rogue:
                this.hp = 120;
                this.power = 4;
                this.resistance = 3;
                break;

            case Wizard:
                this.hp = 100;
                this.power = 4;
                this.resistance = 2;
                break;

            default:
                throw new RuntimeException();
        }

        this.type = type;
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
}
