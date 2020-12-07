package my.plaground;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharacterTest {

    @Test
    public void is_new_paladin_successfully_initialized() {
        Character character = new Character(Character.CharacterType.Paladin);

        assertEquals(150, character.getHp());
        assertEquals(Character.CharacterType.Paladin, character.getType());
        assertEquals(1, character.getPower());
        assertEquals(4, character.getResistance());
    }

    @Test
    public void is_new_rogue_successfully_initialized() {
        Character character = new Character(Character.CharacterType.Rogue);

        assertEquals(120, character.getHp());
        assertEquals(Character.CharacterType.Rogue, character.getType());
        assertEquals(1, character.getPower());
        assertEquals(3, character.getResistance());
    }

    @Test
    public void is_new_wizard_successfully_initialized() {
        Character character = new Character(Character.CharacterType.Wizard);

        assertEquals(100, character.getHp());
        assertEquals(Character.CharacterType.Wizard, character.getType());
        assertEquals(1, character.getPower());
        assertEquals(2, character.getResistance());
    }


    @Test
    public void ensure_that_paladins_damage_is_valid() {
        Character character = new Character(Character.CharacterType.Paladin);
        int damage = character.attackDamage();
        assertTrue( damage >= 5 && damage <= 8, "not in range");
    }


    @Test
    public void ensure_that_rogue_damage_is_valid() {
        Character character = new Character(Character.CharacterType.Rogue);
        int damage = character.attackDamage();
        assertTrue( damage >= 9 && damage <= 12, "not in range");
    }


    @Test
    public void ensure_that_wizard_damage_is_valid() {
        Character character = new Character(Character.CharacterType.Wizard);
        int damage = character.attackDamage();
        assertTrue( damage >= 13 && damage <= 16, "not in range");
    }

}