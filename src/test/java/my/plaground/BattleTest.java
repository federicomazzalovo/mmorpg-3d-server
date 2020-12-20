package my.plaground;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BattleTest {


    @Test public void
    ensure_paladin_inflicts_50_percent_more_dmg_to_rogue(){
        Character paladin = new Paladin();
        Character rogue = new Rogue();

        double rogueInitialHp = rogue.getHp();

        Battle battle = new Battle(paladin, rogue);
        battle.fight();

        assertTrue(rogue.getHp() >= (rogueInitialHp - ((8 * 1.5) / 3)) && rogue.getHp() <= (rogueInitialHp - ((5 * 1.5) / 3)));
    }

    @Test public void
    ensure_rogue_inflicts_50_percent_more_dmg_to_wizard(){
        Character rogue = new Rogue(new my.plaground.RogueTest.RogueRandomDataMocked());
        Character wizard = new Wizard(new my.plaground.WizardTest.WizardRandomDataMocked());

        double wizardInitialHp = wizard.getHp();

        rogue.attack(wizard);

        assertTrue(wizard.getHp() <= (wizardInitialHp - ((9 * 1.5) / 2) * 2));
    }

    @Test public void
    ensure_wizard_inflicts_50_percent_more_dmg_to_paladin(){
        Character wizard = new Wizard();
        Character paladin = new Paladin();

        double paladinInitialHp = paladin.getHp();

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