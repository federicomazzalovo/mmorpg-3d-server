package my.plaground;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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

    @ParameterizedTest
    @ValueSource(ints = { 1, 2 })
    public void ensure_that_paladin_empowered_damage_is_valid(int power){
        Character character = new Character(Character.CharacterType.Paladin, power);
        int empoweredDamage = character.empoweredDamage();

        assertTrue(empoweredDamage >= (5 * character.getPower()));
        assertTrue(empoweredDamage <= (8 * character.getPower()));
    }

    @ParameterizedTest
    @ValueSource(ints = {  1, 2 })
    public void ensure_that_rogue_empowered_damage_is_valid(int power){
        Character character = new Character(Character.CharacterType.Rogue, power);
        int empoweredDamage = character.empoweredDamage();

        assertTrue(empoweredDamage >= (9 * character.getPower()));
        assertTrue(empoweredDamage <= (12 * character.getPower()));
    }

    @ParameterizedTest
    @ValueSource(ints = { 1, 2 })
    public void ensure_that_wizard_empowered_damage_is_valid(int power){
        Character character = new Character(Character.CharacterType.Wizard, power);
        int empoweredDamage = character.empoweredDamage();

        assertTrue(empoweredDamage >= (13 * character.getPower()));
        assertTrue(empoweredDamage <= (16 * character.getPower()));
    }


    @ParameterizedTest
    @ValueSource(ints = { -1, 0 })
    public void ensure_that_paladin_with_invalid_power_throws_exception(int power){
        assertThrows(
            RuntimeException.class,
            () -> {
                Character character = new Character(Character.CharacterType.Paladin, power);
                int empoweredDamage = character.empoweredDamage();
            });
    }

    @ParameterizedTest
    @ValueSource(ints = { -1, 0 })
    public void ensure_that_rogue_with_invalid_power_throws_exception(int power){
        assertThrows(
                RuntimeException.class,
                () -> {
                    Character character = new Character(Character.CharacterType.Rogue, power);
                    int empoweredDamage = character.empoweredDamage();
                });
    }

    @ParameterizedTest
    @ValueSource(ints = { -1, 0 })
    public void ensure_that_wizard_with_invalid_power_throws_exception(int power){
        assertThrows(
                RuntimeException.class,
                () -> {
                    Character character = new Character(Character.CharacterType.Wizard, power);
                    int empoweredDamage = character.empoweredDamage();
                });
    }
}