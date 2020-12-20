package my.plaground;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class PaladinTest {
    @Test
    public void is_new_paladin_successfully_initialized() {
        Character character = new Paladin();

        assertEquals(150, character.getHp());
        assertEquals(1, character.getPower());
        assertEquals(4, character.getResistance());
    }

    @Test
    public void ensure_that_paladins_damage_is_valid() {
        Character character = new Paladin();
        int damage = character.attackDamage();
        assertTrue( damage >= 5 && damage <= 8, "not in range");
    }

    @ParameterizedTest
    @ValueSource(ints = { 1, 2 })
    public void ensure_that_paladin_empowered_damage_is_valid(int power){
        Character character = new Paladin(power);
        int empoweredDamage = character.empoweredDamage();

        assertTrue(empoweredDamage >= (5 * character.getPower()));
        assertTrue(empoweredDamage <= (8 * character.getPower()));
    }

    @ParameterizedTest
    @ValueSource(ints = { -1, 0 })
    public void ensure_that_paladin_with_invalid_power_throws_exception(int power){
        assertThrows(
                RuntimeException.class,
                () -> {
                    Character character = new Paladin(power);
                });
    }
}