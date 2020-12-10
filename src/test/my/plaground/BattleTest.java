package my.plaground;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BattleTest {

    @Test public void
    when_fight_happens_life_points_decrease() {
        Character wizard = new Character(Character.CharacterType.Paladin);
        Character paladin = new Character(Character.CharacterType.Wizard);
        int wizardInitHp = wizard.getHp();
        int paladinInitHp = paladin.getHp();

        Battle battle = new Battle(paladin, wizard);
        battle.fight();

        assertTrue( wizard.getHp() < wizardInitHp);
        assertTrue( paladin.getHp() < paladinInitHp);
    }

    @Test public void
    ensure_paladin_inflicts_50_percent_more_dmg_to_rogue(){
        Character paladin = new Character(Character.CharacterType.Paladin);
        Character rogue = new Character(Character.CharacterType.Rogue);

        Battle battle = new Battle(paladin, rogue);
        battle.fight();

        assertTrue(rogue.getHp() >= 108 && rogue.getHp() <= 112.5);
    }
}