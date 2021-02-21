package my.plaground.Service;

import my.plaground.Domain.*;
import my.plaground.Domain.Character;
import my.plaground.Repository.CharacterEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CharacterFactory {

    @Transactional
    public Character getCharacter(CharacterEntity entity) {
        Character instance;

        switch (entity.getClassId()) {
            case Paladin:
                instance = new Paladin();
                break;
            case Wizard:
                instance = new Wizard();
                break;
            case Rogue:
                instance = new Rogue();
                break;
            default:
                instance = null;
        }

        instance.setId(entity.getId());
        instance.setLevel(entity.getLevelValue());
        instance.setIsPlayer(entity.isPlayer());
        instance.setPosition(Position.at(entity.getPositionx(), entity.getPositiony()));
        instance.setHp(entity.getHp());

        return instance;
    }
}
