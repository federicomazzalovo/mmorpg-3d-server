package my.plaground;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharacterTest {

    @Test
    public void is_new_paladin_successfully_initialized() {
        Character character = new Character(Character.CharacterType.Paladin);

        assertEquals(150, character.getHp());
        assertEquals(Character.CharacterType.Paladin, character.getType());
    }

}