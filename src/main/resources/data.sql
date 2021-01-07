DROP TABLE IF EXISTS Characters;

CREATE TABLE Characters(
    id INT AUTO_INCREMENT  PRIMARY KEY,
    level_value INT,
    is_player BIT,
    positionx DOUBLE,
    positiony DOUBLE,
    hp DOUBLE,
    class_id INT
);

INSERT INTO Characters (level_value, is_player, positionx, positiony, hp, class_id) VALUES
  (1, 1, 5, 3, 150, 1),
  (2, 0, 10, 8, 100, 2),
  (3, 0, 12, 12, 120, 3);
