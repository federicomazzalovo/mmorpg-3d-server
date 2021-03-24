package my.plaground.Domain;

public class Battle {

    private final Character characterOne;
    private final Character characterTwo;

    public Battle(Character c1, Character c2) {
        this.characterOne = c1;
        this.characterTwo = c2;
    }

    public void fight() {
        characterOne.attack(characterTwo);
        characterTwo.attack(characterOne);
    }

}
