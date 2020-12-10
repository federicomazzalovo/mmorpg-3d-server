package my.plaground;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BattleTest {

    @Test public void
    when_fight_happens_life_points_decrease() {
        Character wizard = new Paladin();
        Character paladin = new Wizard();
        int wizardInitHp = wizard.getHp();
        int paladinInitHp = paladin.getHp();

        Battle battle = new Battle(paladin, wizard);
        battle.fight();

        assertTrue( wizard.getHp() < wizardInitHp);
        assertTrue( paladin.getHp() < paladinInitHp);
    }

    @Test public void
    ensure_paladin_inflicts_50_percent_more_dmg_to_rogue(){
        Character paladin = new Paladin();
        Character rogue = new Rogue();

        int rogueInitialHp = rogue.getHp();

        Battle battle = new Battle(paladin, rogue);
        battle.fight();

        assertTrue(rogue.getHp() >= (rogueInitialHp - ((8 * 1.5) / 3)) && rogue.getHp() <= (rogueInitialHp - ((5 * 1.5) / 3)));
    }

    @Test public void
    ensure_rogue_inflicts_50_percent_more_dmg_to_wizard(){
        Character rogue = new Rogue();
        Character wizard = new Wizard();

        int wizardInitialHp = wizard.getHp();

        Battle battle = new Battle(rogue, wizard);
        battle.fight();

        assertTrue(wizard.getHp() >= (wizardInitialHp - ((12 * 1.5) / 2)) && wizard.getHp() <= (wizardInitialHp - ((9 * 1.5) / 2)));
    }

    @Test public void
    ensure_wizard_inflicts_50_percent_more_dmg_to_paladin(){
        Character wizard = new Wizard();
        Character paladin = new Paladin();

        int paladinInitialHp = paladin.getHp();

        Battle battle = new Battle(wizard, paladin);
        battle.fight();

        assertTrue(paladin.getHp() >= (paladinInitialHp - ((16 * 1.5) / 4)) && paladin.getHp() <= (paladinInitialHp - ((13 * 1.5) / 4)));
    }


    @Test public void
    ensure_that_at_least_one_char_dies_after_long_fight() {
        Character paladin = new Paladin();
        Character rogue = new Rogue();

        Battle battle = new Battle(rogue, paladin);
        int counter = 0;
        double maxCounter = rogue.getHp() / ((5 * 1.5) / rogue.getResistance());
        do {
            battle.fight();
            counter++;
        }while ( paladin.isAlive() && rogue.isAlive() && counter < maxCounter );

        assertTrue(paladin.isDead() || rogue.isDead());
    }

}