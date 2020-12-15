package my.plaground;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class CharacterTest {

    @Test public void
    ensure_that_hps_not_negative(){
        Character character = new Paladin(1);
        character.receiveDamage(1000);
        assertTrue(character.getHp() >= 0);
    }

    @Test public void
    ensure_char_dead_if_hps_zero(){
        Character character = new Paladin(1);
        character.receiveDamage(1000);
        assertTrue(character.isDead());
    }

    @Test public void
    ensure_char_alive_if_hps_greater_than_zero(){
        Character character = new Paladin(1);
        character.receiveDamage(149);
        assertTrue(character.isAlive());
    }

    @Test public void
    ensure_negative_damage_is_ignored(){
        Character character = new Paladin(1);

        int initialHp = character.getHp();
        character.receiveDamage(-10);

        assertEquals(initialHp, character.getHp());
    }

    @Test public void
    ensure_that_dead_char_not_inflict_damage() {
        Character paladin = new Paladin();
        Character rogue = new Rogue();
        int initRougeHp = rogue.getHp();
        // Kill paladin
        paladin.receiveDamage(1000);

        paladin.attack(rogue);

        assertEquals(initRougeHp, rogue.getHp());
    }
}