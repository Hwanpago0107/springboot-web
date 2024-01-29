--Item
INSERT INTO Item (dtype, item_id, name, price, stockquantity, discount, filename, filepath, filesize)
values  ('item', 1, '노트북', 1000000, 10, 0.3, 'product01.png', '/upload/img/product01.png', 100);
INSERT INTO Item (dtype, item_id, name, price, stockquantity, discount, filename, filepath, filesize)
values  ('item', 2, '헤드폰', 200000, 8, 0.25, 'product02.png', '/upload/img/product02.png', 100);
INSERT INTO Item (dtype, item_id, name, price, stockquantity, discount, filename, filepath, filesize)
values  ('item', 3, '최신형 노트북', 1500000, 11, 0.2, 'product03.png', '/upload/img/product03.png', 100);
INSERT INTO Item (dtype, item_id, name, price, stockquantity, discount, filename, filepath, filesize)
values  ('item', 4, '태블릿PC', 600000, 900, 0.4, 'product04.png', '/upload/img/product04.png', 100);
INSERT INTO Item (dtype, item_id, name, price, stockquantity, discount, filename, filepath, filesize)
values  ('item', 5, '최신형 헤드폰', 99000, 10, 0.5, 'product05.png', '/upload/img/product05.png', 100);
INSERT INTO Item (dtype, item_id, name, price, stockquantity, discount, filename, filepath, filesize)
values  ('item', 6, '랩탑', 600000, 12, 0.1, 'product06.png', '/upload/img/product06.png', 100);
INSERT INTO Item (dtype, item_id, name, price, stockquantity, discount, filename, filepath, filesize)
values  ('item', 7, '스마트폰', 850000, 11, 0.1, 'product07.png', '/upload/img/product07.png', 100);
INSERT INTO Item (dtype, item_id, name, price, stockquantity, discount, filename, filepath, filesize)
values  ('item', 8, '최신형 랩탑', 800000, 900, 0.15, 'product08.png', '/upload/img/product08.png', 100);
INSERT INTO Item (dtype, item_id, name, price, stockquantity, filename, filepath, filesize)
values  ('item', 9, '디지털카메라', 400000, 900, 'product09.png', '/upload/img/product09.png', 100);

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
values  (1, now(), 'ORDER', 1, 1, '1', '배송빠르게 부탁드려요');
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

---Cartegory