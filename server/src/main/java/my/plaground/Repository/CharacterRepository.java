package my.plaground.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRepository extends  JpaRepository<CharacterEntity, Integer> { }
