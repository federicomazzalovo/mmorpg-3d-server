



INSERT INTO characters (id,level_value, is_connected, positionx, positiony, positionz, rotationx, rotationy, rotationz, rotation_amount, hp, class_id) VALUES
  (100,1, 1, 8, 2, 1, 0, 0, 0, 0, 150, 1),
  (200,2, 0, 7, 3, 1, 0, 0, 0, 0, 100, 2),
  (300,2, 0, 11, 11, 1, 0, 0, 0, 0, 120, 3),
  --- To update position
  (999,2, 0, 0, 0, 1, 0, 0, 0, 0, 120, 3),
  --- To attack in range
  (1001,2, 0, 5, 5, 1, 0, 0, 0, 0, 150, 1),
  (1002,2, 0, 6, 6, 1, 0, 0, 0, 0, 120, 2),
  --- To attack not in range
  (1003,2, 0, 5, 5, 1, 0, 0, 0, 0, 150, 1),
  (1004,2, 0, 300, 300, 1, 0, 0, 0, 0, 120, 2),
  -- Only for test alive dont modify
  (2001,2, 0, 300, 300, 1, 0, 0, 0, 0, 120, 2),
  -- Only for test dead dont modify
  (2002,2, 0, 300, 300, 1, 0, 0, 0, 0, 0, 2);

 -- Only for test alive dont modify
INSERT INTO users (id,username) VALUES
    (1,'PippoFranco');

INSERT INTO characters (id,level_value, is_connected, positionx, positiony, positionz, rotationx, rotationy, rotationz, rotation_amount, hp, class_id, user_id) VALUES
  (3001,2, 0, 300, 300, 1, 0, 0, 0, 0, 120, 2,1);