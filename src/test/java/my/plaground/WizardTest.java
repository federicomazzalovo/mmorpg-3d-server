package my.plaground;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
class WizardTest {

    public static class WizardRandomDataMocked implements RandomDataGeneratorInterface {

        @Override
        public int getRandomPercentage() {
            return 20;
        }

        @Override
        public int getRandomValueRange(int minInclude, int maxInclude) {
            return 13;
        }
    }

    private Wizard wizard;

    @BeforeEach
    public void init() {
        wizard = new Wizard(new WizardRandomDataMocked());
    }


    @Test
    public void is_new_wizard_successfully_initialized() {
        assertEquals(100, wizard.getHp());
        assertEquals(1, wizard.getPower());
        assertEquals(2, wizard.getResistance());
    }

    @Test
    public void ensure_that_wizard_damage_is_valid() {
        int damage = wizard.attackDamage();
        assertTrue( damage >= 13 && damage <= 16, "not in range");
    }

    @ParameterizedTest
    @ValueSource(ints = { 1, 2 })
    public void ensure_that_wizard_empowered_damage_is_valid(int power){
        wizard.setPower(power);
        int empoweredDamage = wizard.empoweredDamage();

        assertTrue(empoweredDamage >= (13 * wizard.getPower()));
        assertTrue(empoweredDamage <= (16 * wizard.getPower()));
    }

    @ParameterizedTest
    @ValueSource(ints = { -1, 0 })
    public void ensure_that_wizard_with_invalid_power_throws_exception(int power){
        assertThrows(
                RuntimeException.class,
                () -> wizard.setPower(power)
        );
    }

    @Test
    public void increase_hp_by_percentage() {
        wizard.increaseHpByPercentage(20);

        assertEquals(120, wizard.getHp());
    }

    @Test
    public void ensure_increase_hp_by_invalid_percent_is_ignored() {
        double initialHps = wizard.getHp();
        wizard.increaseHpByPercentage(-10);

        assertEquals(initialHps, wizard.getHp());
    }

    @Test
    public void ensure_hps_increase_by_correct_percentage_on_attack(){
         Paladin paladin = new Paladin();
        wizard.attack(paladin);

        assertEquals(110, wizard.getHp());
    }

    @Test
    public void ensure_hps_remain_same_when_attack_rogue(){
        Rogue rogue = new Rogue();
        assertEquals(1, wizard.getSpecialDamage(rogue));
    }
}