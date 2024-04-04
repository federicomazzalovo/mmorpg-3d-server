package my.plaground;

import my.plaground.Domain.Character;
import my.plaground.Domain.Faction;
import my.plaground.Domain.Paladin;
import my.plaground.Domain.Rogue;
import my.plaground.Domain.Wizard;
import org.junit.jupiter.api.Test;

import static my.plaground.Domain.Position.*;
import static org.junit.jupiter.api.Assertions.*;

class CharacterTest {

    @Test public void
    ensure_that_hps_not_negative(){
        my.plaground.Domain.Character character = new Paladin();
        character.defend(character.getInitHp() + 1000);
        assertTrue(character.getHp() >= 0);
    }

    @Test public void
    ensure_char_dead_if_hps_zero(){
        my.plaground.Domain.Character character = new Paladin();
        character.defend(character.getInitHp() + 1000);
        assertTrue(character.isDead());
    }

    @Test public void
    ensure_char_alive_if_hps_greater_than_zero(){
        my.plaground.Domain.Character character = new Paladin();
        character.defend(character.getInitHp() - 1);
        assertTrue(character.isAlive());
    }

    @Test public void
    ensure_negative_damage_is_ignored(){
        my.plaground.Domain.Character character = new Paladin();

        double initialHp = character.getHp();
        character.defend(-10);

        assertEquals(initialHp, character.getHp());
    }

    @Test public void
    ensure_that_dead_char_not_inflict_damage() {
        my.plaground.Domain.Character paladin = new Paladin();
        my.plaground.Domain.Character rogue = new Rogue();
        double initRougeHp = rogue.getHp();
        // Kill paladin
        paladin.defend(paladin.getInitHp() + 1000);

        paladin.attack(rogue);

        assertEquals(initRougeHp, rogue.getHp());
    }

    @Test public void
    ensure_that_hps_increase_when_healed(){
        my.plaground.Domain.Character paladin = new Paladin(new my.plaground.PaladinTest.PaladinRandomDataMocked());
        my.plaground.Domain.Character rogue = new Rogue(new my.plaground.RogueTest.RogueRandomDataMocked());
        Faction faction = new Faction();
        paladin.joinFaction(faction);
        rogue.joinFaction(faction);
        paladin.attack(rogue);
        double remainingHps = rogue.getHp();
        paladin.heal(rogue);
        double expected = Math.min(remainingHps + 5, rogue.getInitHp());
        assertEquals(expected, rogue.getHp());
    }


    @Test public void
    ensure_that_hps_do_not_exceed_init_hp_when_healed(){
        my.plaground.Domain.Character paladin = new Paladin(new my.plaground.PaladinTest.PaladinRandomDataMocked());
        my.plaground.Domain.Character rogue = new Rogue(new my.plaground.RogueTest.RogueRandomDataMocked());
        double initHp = rogue.getHp();
        paladin.heal(rogue);
        assertEquals(initHp, rogue.getHp());
    } 

    @Test public void
    ensure_that_dead_characters_cannot_be_healed() {
        my.plaground.Domain.Character paladin = new Paladin(new my.plaground.PaladinTest.PaladinRandomDataMocked());
        my.plaground.Domain.Character rogue = new Rogue(new my.plaground.RogueTest.RogueRandomDataMocked());

        rogue.defend(rogue.getInitHp() + 1000);
        paladin.heal(rogue);

        assertTrue(rogue.isDead());
    }

    @Test public void
    ensure_that_dead_character_cannot_heal(){
        my.plaground.Domain.Character paladin = new Paladin(new my.plaground.PaladinTest.PaladinRandomDataMocked());
        my.plaground.Domain.Character rogue = new Rogue(new my.plaground.RogueTest.RogueRandomDataMocked());

        paladin.defend(paladin.getInitHp() + 1000);
        rogue.defend(10);
        double remainingHps = rogue.getHp();
        paladin.heal(rogue);

        assertEquals(remainingHps, rogue.getHp());
    }

    @Test public void
    ensure_that_character_cannot_deal_dmg_to_itself(){
        my.plaground.Domain.Character paladin = new Paladin(new my.plaground.PaladinTest.PaladinRandomDataMocked());
        paladin.attack(paladin);

        assertEquals(paladin.getInitHp(), paladin.getHp());
    }



    @Test public void
    ensure_that_dmg_is_reduce_by_50_percent_if_enemy_too_many_level_above() {
        my.plaground.Domain.Character paladin = new Paladin(new my.plaground.PaladinTest.PaladinRandomDataMocked());

        my.plaground.Domain.Character rogue = new Rogue(new my.plaground.RogueTest.RogueRandomDataMocked());

        double sameLevelDamage = paladin.calculateTotalDamage(rogue);

        rogue.setLevel(6);
        double diffLevelDamage = paladin.calculateTotalDamage(rogue);

        assertEquals(sameLevelDamage  * 0.5, diffLevelDamage);
    }

    @Test public void
    ensure_that_dmg_is_increase_by_50_percent_if_enemy_too_many_level_below() {
        my.plaground.Domain.Character paladin = new Paladin(new my.plaground.PaladinTest.PaladinRandomDataMocked());

        my.plaground.Domain.Character rogue = new Rogue(new my.plaground.RogueTest.RogueRandomDataMocked());

        double sameLevelDamage = paladin.calculateTotalDamage(rogue);

        paladin.setLevel(6);
        double diffLevelDamage = paladin.calculateTotalDamage(rogue);

        assertEquals(sameLevelDamage  + (sameLevelDamage * 0.5), diffLevelDamage / paladin.getLevel() );
    }

    @Test public void
    ensure_character_can_attack_if_enemy_in_range(){
        my.plaground.Domain.Character paladin = new Paladin(new my.plaground.PaladinTest.PaladinRandomDataMocked());
        my.plaground.Domain.Character wizard = new Wizard(new my.plaground.WizardTest.WizardRandomDataMocked());

        paladin.setPosition(at(10,0, 0));
        wizard.setPosition(at(5,0, 0));


        wizard.attack(paladin);

        assertTrue(paladin.getHp() < paladin.getInitHp());
    }

    @Test public void
    ensure_character_cannot_attack_if_enemy_not_in_range(){
        my.plaground.Domain.Character paladin = new Paladin(new my.plaground.PaladinTest.PaladinRandomDataMocked());
        my.plaground.Domain.Character wizard = new Wizard(new my.plaground.WizardTest.WizardRandomDataMocked());

        paladin.setPosition(at(1500,0, 0));
        wizard.setPosition(at(5,0, 0));

        wizard.attack(paladin);

        assertEquals(paladin.getInitHp(), paladin.getHp());
    }

    @Test public void
    ensure_new_character_can_join_faction(){
        my.plaground.Domain.Character paladin = new Paladin(new my.plaground.PaladinTest.PaladinRandomDataMocked());

        Faction faction = new Faction();
        paladin.joinFaction(faction);

        assertEquals(faction, paladin.getFaction());
    }

    @Test public void
    ensure_character_cannot_join_faction_if_already_belongs_to_faction(){
        my.plaground.Domain.Character paladin = new Paladin(new my.plaground.PaladinTest.PaladinRandomDataMocked());

        Faction faction1 = new Faction();
        paladin.joinFaction(faction1);

        Faction faction2 = new Faction();
        paladin.joinFaction(faction2);

        assertEquals(faction1, paladin.getFaction());
    }

    @Test public void
    ensure_character_leaves_faction_when_requested(){
        my.plaground.Domain.Character paladin = new Paladin(new my.plaground.PaladinTest.PaladinRandomDataMocked());

        Faction faction = new Faction();
        paladin.joinFaction(faction);

        paladin.leaveFaction();

        assertNull(paladin.getFaction());
    }

    @Test public void
    ensure_same_faction_characters_are_allies(){
        my.plaground.Domain.Character paladin1 = new Paladin(new my.plaground.PaladinTest.PaladinRandomDataMocked());
        my.plaground.Domain.Character paladin2 = new Paladin(new my.plaground.PaladinTest.PaladinRandomDataMocked());

        Faction faction = new Faction();
        paladin1.joinFaction(faction);
        paladin2.joinFaction(faction);

        assertTrue(paladin1.isAlly(paladin2));
    }

    @Test public void
    ensure_different_faction_characters_are_not_allies(){
        my.plaground.Domain.Character paladin1 = new Paladin(new my.plaground.PaladinTest.PaladinRandomDataMocked());
        my.plaground.Domain.Character paladin2 = new Paladin(new my.plaground.PaladinTest.PaladinRandomDataMocked());

        Faction faction1 = new Faction();
        paladin1.joinFaction(faction1);

        assertFalse(paladin1.isAlly(paladin2));
    }

    @Test public void
    ensure_that_allies_cannot_deal_damage_to_one_another() {
        my.plaground.Domain.Character paladin1 = new Paladin(new my.plaground.PaladinTest.PaladinRandomDataMocked());
        my.plaground.Domain.Character paladin2 = new Paladin(new my.plaground.PaladinTest.PaladinRandomDataMocked());

        Faction faction = new Faction();
        paladin1.joinFaction(faction);
        paladin2.joinFaction(faction);

        paladin1.attack(paladin2);

        assertEquals(paladin2.getHp(), paladin2.getInitHp());
    }

    @Test public void
    ensure_that_char_only_heal_himself_if_no_faction() {
        my.plaground.Domain.Character paladin1 = new Paladin(new my.plaground.PaladinTest.PaladinRandomDataMocked());
        Character paladin2 = new Paladin(new my.plaground.PaladinTest.PaladinRandomDataMocked());

        paladin2.defend(10);
        double remainingHpPaladin2 = paladin2.getHp();
        paladin1.heal(paladin2);


        paladin1.defend(10);
        double remainingHpPaladin1 = paladin1.getHp();
        paladin1.heal(paladin1);


        assertTrue(paladin1.getHp() > remainingHpPaladin1);
        assertEquals(paladin2.getHp(), remainingHpPaladin2);
    }

}