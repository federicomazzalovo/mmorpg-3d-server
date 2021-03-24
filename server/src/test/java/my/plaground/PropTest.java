package my.plaground;

import my.plaground.Domain.Character;
import my.plaground.Domain.Paladin;
import my.plaground.Domain.Prop;
import my.plaground.Domain.TreeProp;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PropTest {

    @Test public void
    ensure_that_when_reduced_to_0_health_things_are_destroyed() {
        Prop prop = new TreeProp();
        prop.defend(prop.getHp());
        assertTrue(prop.isDestroyed());
    }

    @Test public void
    ensure_that_prop_can_be_attack() {
        Prop prop = new TreeProp();
        Character paladin = new Paladin();

        paladin.attack(prop);

        assertTrue(prop.getHp() < prop.getInitHp());
    }


}