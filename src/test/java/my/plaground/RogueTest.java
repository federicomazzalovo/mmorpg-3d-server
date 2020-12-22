package my.plaground;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class RogueTest {

    public static class RogueRandomDataMocked implements RandomDataGeneratorInterface {

        @Override
        public int getRandomPercentage() {
            return 20;
        }

        @Override
        public int getRandomValueRange(int minInclude, int maxInclude) {
            return 9;
        }
    }

    private Rogue character;

    @BeforeEach
    public void init() {
        character = new Rogue(new RogueRandomDataMocked());
    }

    @Test
    public void is_new_rogue_successfully_initialized() {
        assertEquals(120, character.getHp());
        assertEquals(1, character.getLevel());
        assertEquals(3, character.getResistance());
    }

    @Test
    public void ensure_that_rogue_damage_is_valid() {
        int damage = character.attackDamage();
        assertTrue(damage >= 9 && damage <= 12, "not in range");
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    public void ensure_that_rogue_empowered_damage_is_valid(int power) {
        character.setLevel(power);
        int empoweredDamage = character.empoweredDamage();

        assertTrue(empoweredDamage >= (9 * character.getLevel()));
        assertTrue(empoweredDamage <= (12 * character.getLevel()));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0})
    public void ensure_that_rogue_with_invalid_power_throws_exception(int power) {
        assertThrows(
                RuntimeException.class,
                () -> {
                    character.setLevel(power);
                });
    }


    @Test
    public void ensure_that_have_20_perc_chance_double_damages_each_attack() {
        Character paladin = new Paladin();

        double normalDamage = (character.empoweredDamage() * character.getSpecialDamage(paladin)) / paladin.getResistance();
        double doubledDamage = character.calculateTotalDamage(paladin);

        assertEquals(doubledDamage, normalDamage * 2);
    }
}