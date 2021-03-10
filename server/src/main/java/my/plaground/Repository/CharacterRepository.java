package my.plaground.Repository;

import my.plaground.Domain.Entity.CharacterEntity;
import my.plaground.Domain.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharacterRepository extends  JpaRepository<CharacterEntity, Integer> {
    List<CharacterEntity> findByUser(UserEntity user);
}
