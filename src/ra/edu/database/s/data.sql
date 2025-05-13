CREATE TABLE admin (
        id INT PRIMARY KEY AUTO_INCREMENT,
        username VARCHAR(50) NOT NULL UNIQUE,
        password VARCHAR(255) NOT NULL
);

CREATE TABLE book (
        id INT PRIMARY KEY AUTO_INCREMENT,
        title VARCHAR(255) NOT NULL,
        author VARCHAR(255),
        category VARCHAR(100),
        quantity INT DEFAULT 0,
        publish_year INT
);

CREATE TABLE reader (
            id INT PRIMARY KEY AUTO_INCREMENT,
            username VARCHAR(100) NOT NULL,
            sex VARCHAR(10) NOT NULL,
            birthdate DATE NOT NULL,
            phone VARCHAR(20),
            email VARCHAR(100) UNIQUE
);

CREATE TABLE borrow (
            id INT PRIMARY KEY AUTO_INCREMENT,
            reader_id INT,
            borrow_date DATE NOT NULL,
            return_date DATE,
            status ENUM('BORROWED', 'RETURNED') NOT NULL,
            FOREIGN KEY (reader_id) REFERENCES reader(id)
);

CREATE TABLE borrow_details (
            borrow_id INT,
            book_id INT,
            quantity INT NOT NULL,
            PRIMARY KEY (borrow_id, book_id),
            FOREIGN KEY (borrow_id) REFERENCES borrow(id),
            FOREIGN KEY (book_id) REFERENCES book(id)
);

INSERT INTO admin (username, password) VALUES ('admin', '123456');

-- DROP PROCEDURE IF EXISTS get_all_books;

DELIMITER //

-- 1. Lấy tất cả sách sắp xếp theo số lượng giảm dần
CREATE PROCEDURE get_all_books()
BEGIN
SELECT * FROM book ORDER BY quantity DESC;
END //

-- 2. Lấy thông tin sách theo ID
CREATE PROCEDURE get_book_by_id(IN bookId INT)
BEGIN
SELECT * FROM book WHERE id = bookId;
END //

-- 3. Thêm sách mới
CREATE PROCEDURE add_book(
    IN p_title VARCHAR(255),
    IN p_author VARCHAR(255),
    IN p_year INT,
    IN p_quantity INT,
    IN p_category VARCHAR(100)
)
BEGIN
INSERT INTO book (title, author, publish_year, quantity, category)
VALUES (p_title, p_author, p_year, p_quantity, p_category);
END //

-- 4. Cập nhật thông tin sách
CREATE PROCEDURE update_book(
    IN p_id INT,
    IN p_title VARCHAR(255),
    IN p_author VARCHAR(255),
    IN p_year INT,
    IN p_quantity INT,
    IN p_category VARCHAR(100)
)
BEGIN
UPDATE book
SET title = p_title,
    author = p_author,
    publish_year = p_year,
    quantity = p_quantity,
    category = p_category
WHERE id = p_id;
END //

-- 5. Xóa sách theo ID
CREATE PROCEDURE delete_book_by_id(IN p_id INT)
BEGIN
DELETE FROM book WHERE id = p_id;
END //

-- 6. Tìm sách theo tên
CREATE PROCEDURE search_book_by_name(IN keyword VARCHAR(255))
BEGIN
SELECT * FROM book WHERE title LIKE keyword;
END //

-- 7. Sắp xếp sách theo tiêu đề tăng dần
CREATE PROCEDURE sort_books_by_title_asc()
BEGIN
SELECT * FROM book ORDER BY title ASC;
END //

-- 8. Sắp xếp sách theo tiêu đề giảm dần
CREATE PROCEDURE sort_books_by_title_desc()
BEGIN
SELECT * FROM book ORDER BY title DESC;
END //

DELIMITER ;

SELECT * FROM `reader` WHERE 1

    DELIMITER //
CREATE PROCEDURE get_all_readers()
BEGIN
SELECT * FROM reader;
END //

CREATE PROCEDURE get_reader_by_id(IN readerId INT)
BEGIN
SELECT * FROM reader WHERE id = readerId;
END //

CREATE PROCEDURE add_reader(
    IN username VARCHAR(100),
    IN sex BOOLEAN,
    IN birthdate DATE,
    IN phone VARCHAR(20),
    IN email VARCHAR(100)
)
BEGIN
INSERT INTO reader (username, sex, birthdate, phone, email)
VALUES (username, sex, birthdate, phone, email);
END //

CREATE PROCEDURE update_reader(
    IN readerId INT,
    IN username VARCHAR(100),
    IN sex BOOLEAN,
    IN birthdate DATE,
    IN phone VARCHAR(20),
    IN email VARCHAR(100)
)
BEGIN
UPDATE reader
SET username = username,
    sex = sex,
    birthdate = birthdate,
    phone = phone,
    email = email
WHERE id = readerId;
END //

CREATE PROCEDURE delete_reader(IN readerId INT)
BEGIN
DELETE FROM reader WHERE id = readerId;
END //

CREATE PROCEDURE search_reader_by_name(IN searchName VARCHAR(100))
BEGIN
SELECT * FROM reader WHERE username LIKE CONCAT('%', searchName, '%');
END //
DELIMITER ;
