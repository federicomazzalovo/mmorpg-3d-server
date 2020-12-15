package my.plaground;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
class WizardTest {

    @Test
    public void is_new_wizard_successfully_initialized() {
        Character character = new Wizard();

        assertEquals(100, character.getHp());
        assertEquals(1, character.getPower());
        assertEquals(2, character.getResistance());
    }

    @Test
    public void ensure_that_wizard_damage_is_valid() {
        Character character = new Wizard();
        int damage = character.attackDamage();
        assertTrue( damage >= 13 && damage <= 16, "not in range");
    }

    @ParameterizedTest
    @ValueSource(ints = { 1, 2 })
    public void ensure_that_wizard_empowered_damage_is_valid(int power){
        Character character = new Wizard(power);
        int empoweredDamage = character.empoweredDamage();

        assertTrue(empoweredDamage >= (13 * character.getPower()));
        assertTrue(empoweredDamage <= (16 * character.getPower()));
    }

    @ParameterizedTest
    @ValueSource(ints = { -1, 0 })
    public void ensure_that_wizard_with_invalid_power_throws_exception(int power){
        assertThrows(
                RuntimeException.class,
                () -> {
                    Character character = new Wizard(power);
                });
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, 10, 20 })
    public void increase_hp_by_percentage(int percentage) {
        Wizard wizard = new Wizard();
        int initialHps = wizard.getHp();
        wizard.increaseHpByPercentage(percentage);

        assertEquals(initialHps + (initialHps * (percentage / 100)), wizard.getHp());
    }

    @Disabled
    @Test
    public void ensure_increase_hp_by_invalid_percent_is_ignored()
    {
        Wizard wizard = new Wizard();
        int initialHps = wizard.getHp();
        wizard.increaseHpByPercentage(-10);

        assertEquals(initialHps, wizard.getHp());
    }
}