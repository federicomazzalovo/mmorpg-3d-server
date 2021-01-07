DROP TABLE IF EXISTS Characters;

CREATE TABLE Characters(
    id INT AUTO_INCREMENT  PRIMARY KEY,
    levelValue INT,
    isPlayer BIT,
    positionX DOUBLE,
    positionY DOUBLE,
    hp DOUBLE,
    classId INT
);

INSERT INTO Characters (levelValue, isPlayer, positionX, positionY, hp, classId) VALUES
  (1, 1, 5, 3, 150, 1),
  (2, 0, 10, 8, 100, 2),
  (3, 0, 12, 12, 120, 3);
