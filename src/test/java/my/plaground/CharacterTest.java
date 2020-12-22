package my.plaground;


import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharacterTest {

    @Test public void
    ensure_that_hps_not_negative(){
        Character character = new Paladin();
        character.defend(character.getInitHp() + 1000);
        assertTrue(character.getHp() >= 0);
    }

    @Test public void
    ensure_char_dead_if_hps_zero(){
        Character character = new Paladin();
        character.defend(character.getInitHp() + 1000);
        assertTrue(character.isDead());
    }

    @Test public void
    ensure_char_alive_if_hps_greater_than_zero(){
        Character character = new Paladin();
        character.defend(character.getInitHp() - 1);
        assertTrue(character.isAlive());
    }

    @Test public void
    ensure_negative_damage_is_ignored(){
        Character character = new Paladin();

        double initialHp = character.getHp();
        character.defend(-10);

        assertEquals(initialHp, character.getHp());
    }

    @Test public void
    ensure_that_dead_char_not_inflict_damage() {
        Character paladin = new Paladin();
        Character rogue = new Rogue();
        double initRougeHp = rogue.getHp();
        // Kill paladin
        paladin.defend(paladin.getInitHp() + 1000);

        paladin.attack(rogue);

        assertEquals(initRougeHp, rogue.getHp());
    }

    @Test public void
    ensure_that_hps_increase_when_healed(){
        Character paladin = new Paladin(new my.plaground.PaladinTest.PaladinRandomDataMocked());
        Character rogue = new Rogue(new my.plaground.RogueTest.RogueRandomDataMocked());

        paladin.attack(rogue);
        double remainingHps = rogue.getHp();
        paladin.heal(rogue);
        double expected = Math.min(remainingHps + 5, rogue.getInitHp());
        assertEquals(expected, rogue.getHp());
    }


    @Test public void
    ensure_that_hps_do_not_exceed_init_hp_when_healed(){
        Character paladin = new Paladin(new my.plaground.PaladinTest.PaladinRandomDataMocked());
        Character rogue = new Rogue(new my.plaground.RogueTest.RogueRandomDataMocked());
        double initHp = rogue.getHp();
        paladin.heal(rogue);
        assertEquals(initHp, rogue.getHp());
    } 

    @Test public void
    ensure_that_dead_characters_cannot_be_healed() {
        Character paladin = new Paladin(new my.plaground.PaladinTest.PaladinRandomDataMocked());
        Character rogue = new Rogue(new my.plaground.RogueTest.RogueRandomDataMocked());

        rogue.defend(rogue.getInitHp() + 1000);
        paladin.heal(rogue);

        assertTrue(rogue.isDead());
    }

    @Test public void
    ensure_that_dead_character_cannot_heal(){
        Character paladin = new Paladin(new my.plaground.PaladinTest.PaladinRandomDataMocked());
        Character rogue = new Rogue(new my.plaground.RogueTest.RogueRandomDataMocked());

        paladin.defend(paladin.getInitHp() + 1000);
        rogue.defend(10);
        double remainingHps = rogue.getHp();
        paladin.heal(rogue);

        assertEquals(remainingHps, rogue.getHp());
    }

    @Test public void
    ensure_that_character_cannot_deal_dmg_to_itself(){
        Character paladin = new Paladin(new my.plaground.PaladinTest.PaladinRandomDataMocked());
        paladin.attack(paladin);

        assertEquals(paladin.getInitHp(), paladin.getHp());
    }



}