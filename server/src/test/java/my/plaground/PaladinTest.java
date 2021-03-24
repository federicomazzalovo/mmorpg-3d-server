package my.plaground;

 import my.plaground.Domain.Paladin;
 import my.plaground.Domain.RandomDataGeneratorInterface;
 import my.plaground.Domain.Wizard;
 import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class PaladinTest {

    public static class PaladinRandomDataMocked implements RandomDataGeneratorInterface {

        @Override
        public int getRandomPercentage() {
            return 20;
        }

        @Override
        public int getRandomValueRange(int minInclude, int maxInclude) {
            return 5;
        }
    }

     private Paladin character;

     @BeforeEach
     public void init() {
         character = new Paladin(new PaladinRandomDataMocked());
     }

    @Test
    public void is_new_paladin_successfully_initialized() {
        assertEquals(150, this.character.getHp());
        assertEquals(1, character.getLevel());
        assertEquals(4, character.getResistance());
    }

    @Test
    public void ensure_that_paladins_damage_is_valid() {
        int damage = character.attackDamage();
        assertTrue( damage >= 5 && damage <= 8, "not in range");
    }

    @ParameterizedTest
    @ValueSource(ints = { 1, 2 })
    public void ensure_that_paladin_empowered_damage_is_valid(int power){
        character.setLevel(power);
        int empoweredDamage = character.empoweredDamage();

        assertTrue(empoweredDamage >= (5 * character.getLevel()));
        assertTrue(empoweredDamage <= (8 * character.getLevel()));
    }

    @ParameterizedTest
    @ValueSource(ints = { -1, 0 })
    public void ensure_that_paladin_with_invalid_power_throws_exception(int power){
        assertThrows(
                RuntimeException.class,
                () -> {
                    character.setLevel(power);
                });
    }

    @Test
    public void ensure_resistance_doubles_when_defends(){
        Wizard wizard = new Wizard();

        double initialResistance = character.getResistance();
        wizard.attack(character);

        assertEquals(initialResistance * 2, character.getResistance());
    }
}