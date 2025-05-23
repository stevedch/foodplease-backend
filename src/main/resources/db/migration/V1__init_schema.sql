
CREATE TABLE IF NOT EXISTS "menu_item" (
                                           id SERIAL PRIMARY KEY,
                                           name VARCHAR(255) NOT NULL,
    description TEXT,
    price NUMERIC(10,2),
    image_url TEXT
    );


CREATE TABLE IF NOT EXISTS "users" (
                                      id SERIAL PRIMARY KEY,
                                      name VARCHAR(255),
    username VARCHAR(100) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password TEXT NOT NULL,
    phone VARCHAR(20),
    address TEXT
    );


CREATE TABLE IF NOT EXISTS "order" (
                                       id SERIAL PRIMARY KEY,
                                       order_date TIMESTAMP,
                                       status VARCHAR(50),
    total_amount NUMERIC(10,2),
    quantity INTEGER,
    user_id INTEGER REFERENCES "users"(id) ON DELETE CASCADE
    );


CREATE TABLE IF NOT EXISTS "order_menu_items" (
                                                  id SERIAL PRIMARY KEY,
                                                  order_id INTEGER REFERENCES "order"(id) ON DELETE CASCADE,
    menu_item_id INTEGER REFERENCES "menu_item"(id) ON DELETE CASCADE
    );

INSERT INTO menu_item (id, name, description, price, image_url) VALUES
    (1, 'Margherita Pizza', 'Classic pizza with fresh mozzarella and basil.', 8.5, '');
INSERT INTO menu_item (id, name, description, price, image_url) VALUES
    (2, 'Chicken Sandwich', 'Grilled chicken breast with lettuce and tomato.', 7.0, '');
INSERT INTO menu_item (id, name, description, price, image_url) VALUES
    (3, 'Veggie Burger', 'A hearty burger made with black beans and quinoa.', 6.5, '');
INSERT INTO menu_item (id, name, description, price, image_url) VALUES
    (4, 'Caesar Salad', 'Romaine lettuce with Caesar dressing, croutons and parmesan.', 4.95, '');
INSERT INTO menu_item (id, name, description, price, image_url) VALUES
    (5, 'Beef Tacos', 'Three soft shell tacos with seasoned beef and salsa.', 6.25, '');
INSERT INTO menu_item (id, name, description, price, image_url) VALUES
    (6, 'Spaghetti Bolognese', 'Classic Italian pasta with slow-cooked meat sauce.', 8.5, '');
INSERT INTO menu_item (id, name, description, price, image_url) VALUES
    (7, 'BBQ Ribs', 'Tender pork ribs with smoky barbecue sauce.', 12.99, '');
INSERT INTO menu_item (id, name, description, price, image_url) VALUES
    (8, 'Sushi Platter', 'Assorted fresh sushi rolls and nigiri.', 14.0, '');
INSERT INTO menu_item (id, name, description, price, image_url) VALUES
    (9, 'Fried Chicken', 'Crispy golden-brown fried chicken with spices.', 9.75, '');
INSERT INTO menu_item (id, name, description, price, image_url) VALUES
    (10, 'Pad Thai', 'Stir-fried rice noodles with tofu, peanuts and lime.', 7.99, '');
INSERT INTO menu_item (id, name, description, price, image_url) VALUES
    (11, 'Grilled Salmon', 'Fresh salmon fillet grilled to perfection.', 13.5, '');
INSERT INTO menu_item (id, name, description, price, image_url) VALUES
    (12, 'Burrito Bowl', 'Layered rice, beans, salsa, and grilled chicken.', 6.95, '');
INSERT INTO menu_item (id, name, description, price, image_url) VALUES
    (13, 'Pho', 'Vietnamese noodle soup with beef and fresh herbs.', 7.5, '');
INSERT INTO menu_item (id, name, description, price, image_url) VALUES
    (14, 'Pancake Stack', 'Fluffy pancakes served with syrup and butter.', 4.25, '');
INSERT INTO menu_item (id, name, description, price, image_url) VALUES
    (15, 'Cheesecake Slice', 'Creamy cheesecake on a graham cracker crust.', 3.95, '');
INSERT INTO menu_item (id, name, description, price, image_url) VALUES
    (16, 'Fruit Smoothie', 'Blend of fresh seasonal fruits with yogurt.', 3.5, '');
INSERT INTO menu_item (id, name, description, price, image_url) VALUES
    (17, 'Fish & Chips', 'Crispy battered fish with fries and tartar sauce.', 9.99, '');
INSERT INTO menu_item (id, name, description, price, image_url) VALUES
    (18, 'Ramen', 'Japanese noodle soup with pork, egg and vegetables.', 8.25, '');
INSERT INTO menu_item (id, name, description, price, image_url) VALUES
    (19, 'Grilled Veggie Sandwich', 'Roasted vegetables on toasted sourdough.', 5.75, '');
INSERT INTO menu_item (id, name, description, price, image_url) VALUES
    (20, 'Lentil Soup', 'Hearty lentil soup with carrots and herbs.', 4.8, '');