-- =========================
-- USERS & EMPLOYEES TABLES
-- =========================

CREATE TABLE IF NOT EXISTS User (
    id INT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS Employee (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT UNIQUE NOT NULL,
    role_id INT UNIQUE NOT NULL,  -- One-to-One relationship with Roles
    firstname VARCHAR(100) NOT NULL,
    lastname VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES User(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES Roles(id) ON DELETE CASCADE
);


-- Role Table (e.g., Manager, Waiter, Chef)
CREATE TABLE IF NOT EXISTS Role (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) UNIQUE NOT NULL
);

-- =========================
-- TABLE MANAGEMENT
-- =========================

CREATE TABLE IF NOT EXISTS `Table` (
    id INT PRIMARY KEY AUTO_INCREMENT,
    table_number INT UNIQUE NOT NULL,
    status ENUM('Available', 'Occupied', 'Reserved') DEFAULT 'Available',
    capacity INT NOT NULL CHECK (capacity > 0),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- =========================
-- MENU & INGREDIENTS TABLES
-- =========================

CREATE TABLE IF NOT EXISTS Menu (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS Ingredient (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL UNIQUE,
    quantity INT NOT NULL CHECK (quantity >= 0),
    unit VARCHAR(50) NOT NULL, -- Example: grams, liters, pieces
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS MenuIngredient (
    id INT PRIMARY KEY AUTO_INCREMENT,
    menu_id INT NOT NULL,
    ingredient_id INT NOT NULL,
    quantity_required INT NOT NULL CHECK (quantity_required > 0),
    FOREIGN KEY (menu_id) REFERENCES Menu(id) ON DELETE CASCADE,
    FOREIGN KEY (ingredient_id) REFERENCES Ingredient(id) ON DELETE CASCADE
);

-- =========================
-- ORDER & INVENTORY SYSTEM
-- =========================

CREATE TABLE IF NOT EXISTS Orders (
    id INT PRIMARY KEY AUTO_INCREMENT,
    table_id INT NOT NULL,
    waiter_id INT NOT NULL,
    status ENUM('Pending', 'Processing', 'Cooking', 'Ready', 'Served', 'Cancelled') DEFAULT 'Pending',
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (table_id) REFERENCES `Table`(id) ON DELETE CASCADE,
    FOREIGN KEY (waiter_id) REFERENCES Waiter(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS OrderDetails (
    id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT NOT NULL,
    menu_id INT NOT NULL,
    quantity INT NOT NULL CHECK (quantity > 0),
    FOREIGN KEY (order_id) REFERENCES Orders(id) ON DELETE CASCADE,
    FOREIGN KEY (menu_id) REFERENCES Menu(id) ON DELETE CASCADE
);

-- =========================
-- PAYMENT PROCESSING (Only Records Transactions)
-- =========================

CREATE TABLE IF NOT EXISTS Payment (
    id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT NOT NULL UNIQUE,
    total_amount DECIMAL(10,2) NOT NULL CHECK (total_amount >= 0),
    payment_method ENUM('Cash', 'Credit Card', 'Debit Card') NOT NULL,
    paid_amount DECIMAL(10,2) NOT NULL CHECK (paid_amount >= 0),
    change_amount DECIMAL(10,2) NOT NULL CHECK (change_amount >= 0),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES Orders(id) ON DELETE CASCADE
);

-- =========================
-- RESERVATIONS (Optional)
-- =========================

CREATE TABLE IF NOT EXISTS Reservation (
    id INT PRIMARY KEY AUTO_INCREMENT,
    customer_name VARCHAR(255) NOT NULL,
    table_id INT NOT NULL UNIQUE,
    reservation_time DATETIME NOT NULL,
    status ENUM('Pending', 'Confirmed', 'Cancelled') DEFAULT 'Pending',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (table_id) REFERENCES `Table`(id) ON DELETE CASCADE
);
