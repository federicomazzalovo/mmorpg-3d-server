package my.plaground;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PropTest {

    @Test public void
    ensure_that_when_reduced_to_0_health_things_are_destroyed() {
        Prop prop = new TreeProp();
        prop.defend(prop.hp);
        assertTrue(prop.isDestroyed());
    }

    @Test public void
    ensure_that_prop_can_be_attack() {
        Prop prop = new TreeProp();
        Character paladin = new Paladin();

        paladin.attack(prop);

        assertTrue(prop.hp < prop.initHp);
    }


}