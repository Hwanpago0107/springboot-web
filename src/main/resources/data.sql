---Cartegory
--depth 1
INSERT INTO Category (category_id, name)
values  (0, 'ALL CATEGORIES');
INSERT INTO Category (category_id, name, parent_id)
values  (1, '노트북', 0);
INSERT INTO Category (category_id, name, parent_id)
values  (2, '스마트폰', 0);
INSERT INTO Category (category_id, name, parent_id)
values  (3, '태블릿', 0);
INSERT INTO Category (category_id, name, parent_id)
values  (4, '카메라', 0);
INSERT INTO Category (category_id, name, parent_id)
values  (5, '악세서리', 0);
INSERT INTO Category (category_id, name, parent_id)
values  (6, '기타', 0);
--depth 2
INSERT INTO Category (category_id, name, parent_id)
values  (7, '일반 노트북', 1);
INSERT INTO Category (category_id, name, parent_id)
values  (8, '랩탑', 1);
INSERT INTO Category (category_id, name, parent_id)
values  (9, 'GELEXY', 2);
INSERT INTO Category (category_id, name, parent_id)
values  (10, 'GELEXY', 3);
INSERT INTO Category (category_id, name, parent_id)
values  (11, '일반 카메라', 4);
INSERT INTO Category (category_id, name, parent_id)
values  (12, '디지털 카메라', 4);
INSERT INTO Category (category_id, name, parent_id)
values  (13, '헤드폰', 5);
--depth3
INSERT INTO Category (category_id, name, parent_id)
values  (14, 'SAMSONG', 7);
INSERT INTO Category (category_id, name, parent_id)
values  (15, 'ELG', 7);
INSERT INTO Category (category_id, name, parent_id)
values  (16, 'BAIO', 8);
INSERT INTO Category (category_id, name, parent_id)
values  (17, 'ASOS', 8);
INSERT INTO Category (category_id, name, parent_id)
values  (18, 'REIKA', 12);
INSERT INTO Category (category_id, name, parent_id)
values  (19, 'SANY', 13);
INSERT INTO Category (category_id, name, parent_id)
values  (20, 'ATTLE', 13);

