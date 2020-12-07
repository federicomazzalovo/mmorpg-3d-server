package my.plaground;

public class Battle {

    private final Character characterOne;
    private final Character characterTwo;

    Battle(Character c1, Character c2) {
        this.characterOne = c1;
        this.characterTwo = c2;
    }

    public void fight() {
        characterOne.receiveDamage(characterTwo.totalDamage());
        characterTwo.receiveDamage(characterOne.totalDamage());
    }

}
