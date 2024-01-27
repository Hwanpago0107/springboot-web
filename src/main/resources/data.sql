--item
INSERT INTO ITEM (dtype, item_id, name, price, stockquantity, discount, filename, filepath, filesize)
values  ('item', 1, '노트북', 1000000, 10, 0.3, 'product01.png', '/upload/img/product01.png', 100);
INSERT INTO ITEM (dtype, item_id, name, price, stockquantity, discount, filename, filepath, filesize)
values  ('item', 2, '헤드폰', 200000, 2, 0.25, 'product02.png', '/upload/img/product02.png', 100);
INSERT INTO ITEM (dtype, item_id, name, price, stockquantity, discount, filename, filepath, filesize)
values  ('item', 3, '최신형 노트북', 1500000, 11, 0.2, 'product03.png', '/upload/img/product03.png', 100);
INSERT INTO ITEM (dtype, item_id, name, price, stockquantity, discount, filename, filepath, filesize)
values  ('item', 4, '태블릿PC', 600000, 900, 0.4, 'product04.png', '/upload/img/product04.png', 100);
INSERT INTO ITEM (dtype, item_id, name, price, stockquantity, discount, filename, filepath, filesize)
values  ('item', 5, '최신형 헤드폰', 99000, 10, 0.5, 'product05.png', '/upload/img/product05.png', 100);
INSERT INTO ITEM (dtype, item_id, name, price, stockquantity, discount, filename, filepath, filesize)
values  ('item', 6, '랩탑', 600000, 12, 0.1, 'product06.png', '/upload/img/product06.png', 100);
INSERT INTO ITEM (dtype, item_id, name, price, stockquantity, discount, filename, filepath, filesize)
values  ('item', 7, '스마트폰', 850000, 11, 0.1, 'product07.png', '/upload/img/product07.png', 100);
INSERT INTO ITEM (dtype, item_id, name, price, stockquantity, discount, filename, filepath, filesize)
values  ('item', 8, '최신형 랩탑', 800000, 900, 0.15, 'product08.png', '/upload/img/product08.png', 100);
INSERT INTO ITEM (dtype, item_id, name, price, stockquantity, filename, filepath, filesize)
values  ('item', 9, '디지털카메라', 400000, 900, 'product09.png', '/upload/img/product09.png', 100);

-- users
INSERT INTO USERS (id, email, nickname)
values (1, 'blessu0107@gmail.com', 'SH K');

--cart
INSERT INTO CART (id, user_id)
values  (1, 1);

--cartitem
INSERT INTO CARTITEM (id, quantity, cart_id, item_id)
values  (1, 1, 1, 1);
INSERT INTO CARTITEM (id, quantity, cart_id, item_id)
values  (2, 3, 1, 2);
INSERT INTO CARTITEM (id, quantity, cart_id, item_id)
values  (3, 3, 1, 3);

--delivery
INSERT INTO DELIVERY (delivery_id)
values  (1);
INSERT INTO DELIVERY (delivery_id)
values  (2);

----orders
INSERT INTO ORDERS (order_id, orderdate, status, delivery_id, user_id)
values  (1, now(), 'ORDER', 1, 1);
INSERT INTO ORDERS (order_id, orderdate, status, delivery_id, user_id)
values  (2, now(), 'ORDER', 2, 1);

----orderitem
INSERT INTO ORDERITEM (order_item_id, count, orderprice, item_id, order_id)
values  (1, 1, 1000, 1, 1);
INSERT INTO ORDERITEM (order_item_id, count, orderprice, item_id, order_id)
values  (2, 4, 40000, 2, 1);
INSERT INTO ORDERITEM (order_item_id, count, orderprice, item_id, order_id)
values  (3, 5, 6000, 3, 1);
INSERT INTO ORDERITEM (order_item_id, count, orderprice, item_id, order_id)
values  (4, 3, 3000, 1, 2);
INSERT INTO ORDERITEM (order_item_id, count, orderprice, item_id, order_id)
values  (5, 3, 30000, 2, 2);