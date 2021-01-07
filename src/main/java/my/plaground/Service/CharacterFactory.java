package my.plaground.Service;

import my.plaground.*;
import my.plaground.Character;
import my.plaground.Repository.CharacterEntity;
import org.springframework.stereotype.Service;

@Service
public class CharacterFactory {

    public Character getCharacter(CharacterEntity entity) {
        Character instance;

        switch(entity.getClassId())
        {
            case 1:
                instance = new Paladin();
                break;
            case 2:
                instance = new Wizard();
                break;
            case 3:
                instance = new Rogue();
                break;
            default:
                instance = null;
        }

        instance.setId(entity.getId());
        instance.setLevel(entity.getLevelValue());
        instance.setIsPlayer(entity.isPlayer());
        instance.setPosition(Position.at(entity.getPositionX(),entity.getPositionY()));
        instance.setHp(entity.getHp());

        return instance;
    }
}
