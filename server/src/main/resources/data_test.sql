



INSERT INTO characters (id,level_value, is_player, positionx, positiony, hp, class_id) VALUES
  (100,1, 1, 8, 2, 150, 1),
  (200,2, 0, 7, 3, 100, 2),
  (300,2, 0, 11, 11, 120, 3),
  --- To update position
  (999,2, 0, 0, 0, 120, 3),
  --- To attack in range
  (1001,2, 0, 5, 5, 150, 1),
  (1002,2, 0, 6, 6, 120, 2),
  --- To attack not in range
  (1003,2, 0, 5, 5, 150, 1),
  (1004,2, 0, 300, 300, 120, 2),
  -- Only for test alive dont modify
  (2001,2, 0, 300, 300, 120, 2);

