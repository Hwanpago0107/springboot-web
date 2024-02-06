---Cartegory
--depth 1
INSERT INTO Category (category_id, name)
values  (0, 'ALL CATEGORIES');
INSERT INTO Category (category_id, name, parent_id, depth)
values  (1, '노트북', 0, '1');
INSERT INTO Category (category_id, name, parent_id, depth)
values  (2, '모바일', 0, '1');
INSERT INTO Category (category_id, name, parent_id ,depth)
values  (3, '카메라', 0, '1');
INSERT INTO Category (category_id, name, parent_id, depth)
values  (4, '악세서리', 0, '1');
INSERT INTO Category (category_id, name, parent_id, depth)
values  (5, '기타', 0, '1');
--depth 2
INSERT INTO Category (category_id, name, parent_id, depth)
values  (6, '일반 노트북', 1, '2');
INSERT INTO Category (category_id, name, parent_id, depth)
values  (7, '랩탑', 1, '2');
INSERT INTO Category (category_id, name, parent_id, depth)
values  (8, '스마트폰', 2, '2');
INSERT INTO Category (category_id, name, parent_id, depth)
values  (9, '태블릿', 2, '2');
INSERT INTO Category (category_id, name, parent_id, depth)
values  (10, '일반 카메라', 3, '2');
INSERT INTO Category (category_id, name, parent_id, depth)
values  (11, '디지털 카메라', 3, '2');
INSERT INTO Category (category_id, name, parent_id, depth)
values  (12, '헤드폰', 4, '2');
--depth3
INSERT INTO Category (category_id, name, parent_id, depth)
values  (13, 'SAMSONG', 6, '3');
INSERT INTO Category (category_id, name, parent_id, depth)
values  (14, 'ELG', 6, '3');
INSERT INTO Category (category_id, name, parent_id, depth)
values  (15, 'BAIO', 7, '3');
INSERT INTO Category (category_id, name, parent_id, depth)
values  (16, 'ASOS', 7, '3');
INSERT INTO Category (category_id, name, parent_id, depth)
values  (17, 'REIKA', 11, '3');
INSERT INTO Category (category_id, name, parent_id, depth)
values  (18, 'SANY', 12, '3');
INSERT INTO Category (category_id, name, parent_id, depth)
values  (19, 'ATTLE', 12, '3');
INSERT INTO Category (category_id, name, parent_id, depth)
values  (20, 'GELEXY', 8, '3');
INSERT INTO Category (category_id, name, parent_id, depth)
values  (21, 'GELEXY', 9, '3');

--Item
INSERT INTO Item (dtype, item_id, name, price, stockquantity, discount, filename, filepath, filesize, category_id, description)
values  ('item', 1, 'SAMSONG 노트북', 1000000, 10, 0.3, 'product01.png', '/upload/img/product01.png', 100, 13, '삼성 최강 노트북입니다.');
INSERT INTO Item (dtype, item_id, name, price, stockquantity, discount, filename, filepath, filesize, category_id, description)
values  ('item', 2, 'SANY 헤드폰', 200000, 8, 0.25, 'product02.png', '/upload/img/product02.png', 100, 18, '짱짱 헤드폰입니다.');
INSERT INTO Item (dtype, item_id, name, price, stockquantity, discount, filename, filepath, filesize, category_id, description)
values  ('item', 3, 'ELG 노트북', 1500000, 11, 0.2, 'product03.png', '/upload/img/product03.png', 100, 14, '새로나온 RTX 최신형 노트북입니다.');
INSERT INTO Item (dtype, item_id, name, price, stockquantity, discount, filename, filepath, filesize, category_id, description)
values  ('item', 4, 'GELEXY TAB', 600000, 900, 0.4, 'product04.png', '/upload/img/product04.png', 100, 21, '가볍고 좋은 최강 태블릿.');
INSERT INTO Item (dtype, item_id, name, price, stockquantity, discount, filename, filepath, filesize, category_id, description)
values  ('item', 5, 'ATTLE 헤드폰', 99000, 10, 0.5, 'product05.png', '/upload/img/product05.png', 100, 19, '중국에서 직수입한 최신형 헤드폰!!');
INSERT INTO Item (dtype, item_id, name, price, stockquantity, discount, filename, filepath, filesize, category_id, description)
values  ('item', 6, 'BAIO 랩탑', 600000, 12, 0.1, 'product06.png', '/upload/img/product06.png', 100, 15, '실 무게 500g 초경량 랩탑.');
INSERT INTO Item (dtype, item_id, name, price, stockquantity, discount, filename, filepath, filesize, category_id, description)
values  ('item', 7, 'GELEXY S260', 850000, 11, 0.1, 'product07.png', '/upload/img/product07.png', 100, 20, 'AI 탑재 초강력 스마트폰');
INSERT INTO Item (dtype, item_id, name, price, stockquantity, discount, filename, filepath, filesize, category_id, description)
values  ('item', 8, 'ASOS 랩탑', 800000, 900, 0.15, 'product08.png', '/upload/img/product08.png', 100, 16, '랩탑계의 혁명, 최강 노트북');
INSERT INTO Item (dtype, item_id, name, price, stockquantity, discount, filename, filepath, filesize, category_id, description)
values  ('item', 9, 'REIKA 카메라', 400000, 800, 0, 'product09.png', '/upload/img/product09.png', 100, 17, '101미터 거리까지 보이는 디카');

-- users
INSERT INTO users (id, email, nickname)
values (1, 'blessu0107@gmail.com', 'SH K');

--Cart
INSERT INTO Cart (id, user_id)
values  (1, 1);

--CartItem
INSERT INTO CartItem (id, quantity, cart_id, item_id)
values  (1, 1, 1, 1);
INSERT INTO CartItem (id, quantity, cart_id, item_id)
values  (2, 3, 1, 2);
INSERT INTO CartItem (id, quantity, cart_id, item_id)
values  (3, 3, 1, 3);

--Delivery
INSERT INTO Delivery (delivery_id, zipcode, streetaddr, detailaddr, receiver, status)
values  (1, '00001', '서울시 관악구 봉천동 관악로 1길', '슈퍼빌라 101호', '홍길동', 'READY');
INSERT INTO Delivery (delivery_id, zipcode, streetaddr, detailaddr, receiver, status)
values  (2, '00002', '서울시 강남구 강남대로 1길', '타워팰리스 4001호', '임꺽정', 'COMP');

----orders
INSERT INTO orders (order_id, orderdate, status, delivery_id, user_id, payment, order_note)
values  (1, now() , 'ORDER', 1, 1, '1', '배송빠르게 부탁드려요');
INSERT INTO orders (order_id, orderdate, status, delivery_id, user_id, payment, order_note)
values  (2, now(), 'ORDER', 2, 1, '3', '문앞에 놔주세요');

----OrderItem
INSERT INTO OrderItem (order_item_id, count, orderprice, item_id, order_id)
values  (1, 1, 700000, 1, 1);
INSERT INTO OrderItem (order_item_id, count, orderprice, item_id, order_id)
values  (2, 4, 600000, 2, 1);
INSERT INTO OrderItem (order_item_id, count, orderprice, item_id, order_id)
values  (3, 5, 6000000, 3, 1);
INSERT INTO OrderItem (order_item_id, count, orderprice, item_id, order_id)
values  (4, 3, 2100000, 1, 2);
INSERT INTO OrderItem (order_item_id, count, orderprice, item_id, order_id)
values  (5, 3, 450000, 2, 2);

-- Wish
INSERT INTO Wish (id, user_id)
values  (1, 1);

-- WishItem
INSERT INTO WishItem (id, item_id, wish_id)
values  (1, 2, 1);
INSERT INTO WishItem (id, item_id, wish_id)
values  (2, 4, 1);
INSERT INTO WishItem (id, item_id, wish_id)
values  (3, 6, 1);
