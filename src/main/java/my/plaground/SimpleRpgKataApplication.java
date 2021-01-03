package my.plaground;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class SimpleRpgKataApplication {


    public static void main(String[] args) {
        SpringApplication.run(SimpleRpgKataApplication.class, args);
    }


    public static List<Character> getCharacterList() {
        Character paladin = new Paladin();
        paladin.id = 1;
        paladin.setPosition(new Position(1, 2));
        Character wizardOne = new Wizard();
        wizardOne.id = 2;
        Character wizardTwo = new Wizard();
        wizardTwo.id = 3;
        Character rogue = new Rogue();
        rogue.id = 4;

        return List.of(paladin, wizardOne, wizardTwo, rogue);
    }
}
