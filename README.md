# Epam-Final-Project

<b>Задание:</b>

Система Интернет-магазин. Адимнистратор осуществляет ведение каталога Товаров. Клиент делает и оплачивает Заказ на Товары.
Администратор может занести неплательщиков в черный список.

-------------------------------------------------------------------------------------------------------------------------------

<b>Реализован проект интернет-магазина.</b>

Admin 
Может добавлять товары и редактировать уже существующие, включая изменение цены и наличия, загрузку фотографии, 
изменение описания. Администратор подтверждает (отправдяет) заказ покупателю. Если покупалеть не отлатил товар
его можно внести в черный список.
User
Может добавлять и удалять товары из корзины. Может оформить и оплатить заказ. Если после оформления заказа, но
до его оплаты измнилвсь цена на товар цена заказа рассчитывается по цене на момент оформления заказа. Покупатель,
внесенный в черный список не может зайти в свой личный кабинет.
Не авторизованный пользователь
Может просматривать товары. При попытке добавить товар в корзину перенаправляется на страницу входа.

-------------------------------------------------------------------------------------------------------------------------------

<b>Схема базы данных:</b>

<img src="https://github.com/Kseniya-hash/Epam-Final-Project/blob/main/FinalProject/%D1%81%D1%85%D0%B5%D0%BC%D0%B0%20%D0%B1%D0%B4.png">

-------------------------------------------------------------------------------------------------------------------------------

<b>Код для создания базы данных:</b>

-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema dubovik_shop
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema dubovik_shop
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `dubovik_shop` DEFAULT CHARACTER SET utf8 ;
USE `dubovik_shop` ;

-- -----------------------------------------------------
-- Table `dubovik_shop`.`roles`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `dubovik_shop`.`roles` ;

CREATE TABLE IF NOT EXISTS `dubovik_shop`.`roles` (
  `r_id` BIGINT NOT NULL,
  `unq_r_name` VARCHAR(20) CHARACTER SET 'utf8' NOT NULL,
  PRIMARY KEY (`r_id`),
  UNIQUE INDEX `r_role_name_UNIQUE` (`unq_r_name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `dubovik_shop`.`users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `dubovik_shop`.`users` ;

CREATE TABLE IF NOT EXISTS `dubovik_shop`.`users` (
  `u_id` BIGINT NOT NULL AUTO_INCREMENT,
  `u_phone` VARCHAR(12) CHARACTER SET 'utf8' NOT NULL,
  `unq_u_login` VARCHAR(45) NOT NULL,
  `u_password` VARCHAR(50) CHARACTER SET 'utf8' NOT NULL,
  `u_name` VARCHAR(45) CHARACTER SET 'utf8' NOT NULL,
  `u_e-mail` VARCHAR(320) CHARACTER SET 'utf8' NULL,
  `u_is_blacklisted` TINYINT(1) NOT NULL,
  `u_r_id` BIGINT NOT NULL,
  PRIMARY KEY (`u_id`),
  UNIQUE INDEX `unq_u_login_UNIQUE` (`unq_u_login` ASC) VISIBLE,
  INDEX `u_r_id_idx` (`u_r_id` ASC) VISIBLE,
  CONSTRAINT `u_r_id`
    FOREIGN KEY (`u_r_id`)
    REFERENCES `dubovik_shop`.`roles` (`r_id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `dubovik_shop`.`order_statuses`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `dubovik_shop`.`order_statuses` ;

CREATE TABLE IF NOT EXISTS `dubovik_shop`.`order_statuses` (
  `os_id` BIGINT NOT NULL AUTO_INCREMENT,
  `unq_os_name` VARCHAR(45) CHARACTER SET 'utf8' NOT NULL,
  PRIMARY KEY (`os_id`),
  UNIQUE INDEX `unq_os_name_UNIQUE` (`unq_os_name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `dubovik_shop`.`orders`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `dubovik_shop`.`orders` ;

CREATE TABLE IF NOT EXISTS `dubovik_shop`.`orders` (
  `o_id` BIGINT NOT NULL AUTO_INCREMENT,
  `o_u_id` BIGINT NOT NULL,
  `o_os_id` BIGINT NOT NULL,
  `o_date` TIMESTAMP NOT NULL,
  PRIMARY KEY (`o_id`),
  INDEX `o_os_id_idx` (`o_os_id` ASC) VISIBLE,
  INDEX `o_u_id_idx` (`o_u_id` ASC) VISIBLE,
  CONSTRAINT `o_u_id`
    FOREIGN KEY (`o_u_id`)
    REFERENCES `dubovik_shop`.`users` (`u_id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `o_os_id`
    FOREIGN KEY (`o_os_id`)
    REFERENCES `dubovik_shop`.`order_statuses` (`os_id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `dubovik_shop`.`product_categories`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `dubovik_shop`.`product_categories` ;

CREATE TABLE IF NOT EXISTS `dubovik_shop`.`product_categories` (
  `pc_id` BIGINT NOT NULL AUTO_INCREMENT,
  `unq_pc_name` VARCHAR(50) CHARACTER SET 'utf8' NOT NULL,
  PRIMARY KEY (`pc_id`),
  UNIQUE INDEX `unq_pc_name_UNIQUE` (`unq_pc_name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `dubovik_shop`.`products`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `dubovik_shop`.`products` ;

CREATE TABLE IF NOT EXISTS `dubovik_shop`.`products` (
  `p_id` BIGINT NOT NULL AUTO_INCREMENT,
  `unq_p_name` VARCHAR(100) CHARACTER SET 'utf8' NOT NULL,
  `p_pc_id` BIGINT NOT NULL,
  `p_description` VARCHAR(1000) CHARACTER SET 'utf8' NULL,
  `p_quantity` INT NOT NULL,
  `p_weight` INT NULL,
  `p_length` INT NULL,
  `p_high` INT NULL,
  `p_width` INT NULL,
  `p_photopath` VARCHAR(100) NULL,
  PRIMARY KEY (`p_id`),
  UNIQUE INDEX `p_name_UNIQUE` (`unq_p_name` ASC) VISIBLE,
  INDEX `p_pc_id_idx` (`p_pc_id` ASC) VISIBLE,
  CONSTRAINT `p_pc_id`
    FOREIGN KEY (`p_pc_id`)
    REFERENCES `dubovik_shop`.`product_categories` (`pc_id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `dubovik_shop`.`sales`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `dubovik_shop`.`sales` ;

CREATE TABLE IF NOT EXISTS `dubovik_shop`.`sales` (
  `s_o_id` BIGINT NOT NULL,
  `s_p_id` BIGINT NOT NULL,
  `s_quantity` INT NOT NULL,
  INDEX `op_o_id_idx` (`s_o_id` ASC) VISIBLE,
  INDEX `op_p_id_idx` (`s_p_id` ASC) VISIBLE,
  PRIMARY KEY (`s_p_id`, `s_o_id`),
  CONSTRAINT `op_o_id`
    FOREIGN KEY (`s_o_id`)
    REFERENCES `dubovik_shop`.`orders` (`o_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `op_p_id`
    FOREIGN KEY (`s_p_id`)
    REFERENCES `dubovik_shop`.`products` (`p_id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `dubovik_shop`.`price_logs`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `dubovik_shop`.`price_logs` ;

CREATE TABLE IF NOT EXISTS `dubovik_shop`.`price_logs` (
  `pl_id` BIGINT NOT NULL AUTO_INCREMENT,
  `pl_p_id` BIGINT NOT NULL,
  `pl_purchase_price` INT NOT NULL,
  `pl_selling_price` INT NOT NULL,
  `pl_date` TIMESTAMP NOT NULL,
  PRIMARY KEY (`pl_id`, `pl_p_id`),
  INDEX `pl_p_id_idx` (`pl_p_id` ASC) VISIBLE,
  CONSTRAINT `pl_p_id`
    FOREIGN KEY (`pl_p_id`)
    REFERENCES `dubovik_shop`.`products` (`p_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `dubovik_shop`.`comments`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `dubovik_shop`.`comments` ;

CREATE TABLE IF NOT EXISTS `dubovik_shop`.`comments` (
  `c_id` BIGINT NOT NULL AUTO_INCREMENT,
  `c_u_id` BIGINT NOT NULL,
  `c_p_id` BIGINT NOT NULL,
  `c_text` VARCHAR(1000) CHARACTER SET 'utf8' NULL,
  `c_rating` TINYINT NOT NULL,
  PRIMARY KEY (`c_id`),
  INDEX `c_u_id_idx` (`c_u_id` ASC) VISIBLE,
  INDEX `c_p_id_idx` (`c_p_id` ASC) VISIBLE,
  CONSTRAINT `c_u_id`
    FOREIGN KEY (`c_u_id`)
    REFERENCES `dubovik_shop`.`users` (`u_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `c_p_id`
    FOREIGN KEY (`c_p_id`)
    REFERENCES `dubovik_shop`.`products` (`p_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

USE `dubovik_shop` ;

-- -----------------------------------------------------
-- Placeholder table for view `dubovik_shop`.`products_for_menu`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dubovik_shop`.`products_for_menu` (`p_id` INT, `unq_p_name` INT, `unq_pc_name` INT, `p_description` INT, `p_photopath` INT, `pm_comment_count` INT, `pm_average_rating` INT, `pl_selling_price` INT, `p_quantity` INT);

-- -----------------------------------------------------
-- View `dubovik_shop`.`products_for_menu`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `dubovik_shop`.`products_for_menu`;
DROP VIEW IF EXISTS `dubovik_shop`.`products_for_menu` ;
USE `dubovik_shop`;
CREATE  OR REPLACE VIEW `products_for_menu` AS
SELECT `p_id`, `unq_p_name`, `unq_pc_name`, `p_description`, `p_photopath`, COUNT(`c_id`) AS `pm_comment_count`, AVG(`c_rating`) AS `pm_average_rating`, `pl_selling_price`, `p_quantity`
 FROM `products` p 
 LEFT JOIN `product_categories` pc
 ON p.p_pc_id = pc.pc_id
 LEFT JOIN `comments` c 
 ON  p.p_id = c.c_p_id 
 LEFT JOIN (SELECT * FROM `price_logs` pl
 INNER JOIN (
  SELECT `pl_p_id`, MAX(`pl_date`) AS `pl_date`
   FROM `price_logs` GROUP BY `pl_p_id`
) AS max USING (`pl_p_id`, `pl_date`))
 AS price ON p.p_id = price.pl_p_id 
 GROUP BY p.p_id;

ALTER TABLE `products`
ADD CONSTRAINT `CHK_non_negative_quantity`
CHECK (`p_quantity` >= 0);

ALTER TABLE `products`
ADD CONSTRAINT `CHK_positive_metrics`
CHECK (`p_weight` > 0 && `p_length` > 0 && `p_high` > 0 && `p_width` > 0);

ALTER TABLE `price_logs`
ADD CONSTRAINT `CHK_positive_price`
CHECK (`pl_purchase_price` > 0 && `pl_selling_price` > 0);
ALTER TABLE `sales`
ADD CONSTRAINT `CHK_positive_quantity`
CHECK (`s_quantity` > 0);

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-------------------------------------------------------------------------------------------------------------------------------

<b>Код для наполнения базы данных данными:</b>

-- INSERT ROLES

INSERT INTO `dubovik_shop`.`roles` (`r_id`,`unq_r_name`) VALUES (1,'admin');
INSERT INTO `dubovik_shop`.`roles` (`r_id`,`unq_r_name`) VALUES (2,'user');


-- INSERT USERS

INSERT INTO `dubovik_shop`.`users` (`u_id`,`u_phone`,`unq_u_login`,`u_password`,`u_name`,`u_e-mail`,`u_is_blacklisted`,`u_r_id`) 
VALUES (1,'375336248896','admin','tLfKMfbKu4VSyTu9KL6O+HKwkV/+d9t/VUrvQYZqNio=','admin','',0,1);
INSERT INTO `dubovik_shop`.`users` (`u_id`,`u_phone`,`unq_u_login`,`u_password`,`u_name`,`u_e-mail`,`u_is_blacklisted`,`u_r_id`) 
VALUES (2,'375291110034','vict','tLfKMfbKu4VSyTu9KL6O+HKwkV/+d9t/VUrvQYZqNio=','Виктор','VMart@gmail.com',0,2);
INSERT INTO `dubovik_shop`.`users` (`u_id`,`u_phone`,`unq_u_login`,`u_password`,`u_name`,`u_e-mail`,`u_is_blacklisted`,`u_r_id`) 
VALUES (3,'375336011021','mari', 'tLfKMfbKu4VSyTu9KL6O+HKwkV/+d9t/VUrvQYZqNio=','maria',NULL,0,2);

-- INSERT ORDER_STATUSES

INSERT INTO `dubovik_shop`.`order_statuses` (`os_id`,`unq_os_name`) VALUES (1,'оформлен');
INSERT INTO `dubovik_shop`.`order_statuses` (`os_id`,`unq_os_name`) VALUES (2,'оплачен');
INSERT INTO `dubovik_shop`.`order_statuses` (`os_id`,`unq_os_name`) VALUES (3,'отправлен');


-- INSERT PRODUCT_CATEGORIES

INSERT INTO `dubovik_shop`.`product_categories` (`pc_id`,`unq_pc_name`) VALUES (1,'Самокаты');
INSERT INTO `dubovik_shop`.`product_categories` (`pc_id`,`unq_pc_name`) VALUES (2,'Скейтборды');

-- INSERT PRODUCTS

INSERT INTO `dubovik_shop`.`products` (`p_id`,`unq_p_name`,`p_pc_id`,`p_description`,`p_quantity`,`p_weight`,`p_length`,`p_high`,`p_width`, `p_photopath`) 
VALUES (1,'Самокат Ridex Flower',1,'Пластиковая дека заканчивается удобным и интуитивным ножным тормозом, расположенным над задним колесом. Диаметр передних колёс в 120 мм помогает меньше ощущать неровности покрытия, а яркий дизайн и светящиеся колесики самоката точно понравятся ребенку и его друзьям!',4,NULL,NULL,NULL,NULL,'/photo1.jpg');
INSERT INTO `dubovik_shop`.`products` (`p_id`,`unq_p_name`,`p_pc_id`,`p_description`,`p_quantity`,`p_weight`,`p_length`,`p_high`,`p_width`, `p_photopath`) 
VALUES (2,'Самокат двухколесный Ridex Trigger black/blue',1,'Резиновые колеса диаметром 200 мм минимизируют вибрации при передвижении по любым типам городского покрытия. За быстрое передвижение отвечают шустрые подшипники ABEC-9, а дисковые тормоза позволят избежать неприятных столкновений на вашем пути. Крепкая алюминиевая дека способная выдержать вес до 110 кг. Стильный и лаконичный дизайн приятен глазу, подтверждая ваш статус на дороге.',3,NULL,NULL,129,NULL,'/photo2.jpg');
INSERT INTO `dubovik_shop`.`products` (`p_id`,`unq_p_name`,`p_pc_id`,`p_description`,`p_quantity`,`p_weight`,`p_length`, `p_high`,`p_width`, `p_photopath`) 
VALUES (3,'Скейтборд Ridex Mincer',2,'Стильный и лакончиный скейтборд для тех, кто знает себе цену и уверен в своих силах. Доска из 9-ти кленовых слоев способна выдерживать трюки повышенной сложности, а алюминиевая подвеска не подведет в трудной ситуации. Бушинги с жесткостью 95А сделают приземления проще и добавят устойчивости. Подшипники ABEC-5 Carbon добавят отличный накат, а колеса с жесткостью 85А обеспечат хорошей скоростью на площадке и в парке. Шкурка 80АВ отличается своей надежностью и цепкостью, а потому можно быть спокойным за податливость доски. Если ты уверен в своих силах и ищешь скейтборд, который подчеркнёт твои лучшие качества, то Mincer – лучший выбор прямо здесь и сейчас!',1,NULL,100,NULL,NULL,'/photo3.jpg');


-- INSERT PRICE_LOGS

INSERT INTO `dubovik_shop`.`price_logs` (`pl_id`,`pl_p_id`,`pl_purchase_price`,`pl_selling_price`,`pl_date`) VALUES (1,1,3500,5000,'2021-06-04 18:25:08');
INSERT INTO `dubovik_shop`.`price_logs` (`pl_id`,`pl_p_id`,`pl_purchase_price`,`pl_selling_price`,`pl_date`) VALUES (2,2,15000,20000,'2021-06-04 18:25:10');
INSERT INTO `dubovik_shop`.`price_logs` (`pl_id`,`pl_p_id`,`pl_purchase_price`,`pl_selling_price`,`pl_date`) VALUES (3,3,3500,5000,'2021-06-04 18:25:11');
INSERT INTO `dubovik_shop`.`price_logs` (`pl_id`,`pl_p_id`,`pl_purchase_price`,`pl_selling_price`,`pl_date`) VALUES (4,1,3700,5400,'2021-07-04 17:21:48');

-- INSERT ORDERS

INSERT INTO `dubovik_shop`.`orders` (`o_id`,`o_u_id`,`o_os_id`,`o_date`) VALUES (1,2,3,'2021-06-28 18:25:54');
INSERT INTO `dubovik_shop`.`orders` (`o_id`,`o_u_id`,`o_os_id`,`o_date`) VALUES (2,2,1,'2021-07-15 12:30:11');
INSERT INTO `dubovik_shop`.`orders` (`o_id`,`o_u_id`,`o_os_id`,`o_date`) VALUES (3,3,2,'2021-08-25 15:03:45');

-- INSERT SALES

INSERT INTO `dubovik_shop`.`sales` (`s_o_id`,`s_p_id`,`s_quantity`) VALUES (1,1,1);
INSERT INTO `dubovik_shop`.`sales` (`s_o_id`,`s_p_id`,`s_quantity`) VALUES (1,2,2);
INSERT INTO `dubovik_shop`.`sales` (`s_o_id`,`s_p_id`,`s_quantity`) VALUES (2,3,3);
INSERT INTO `dubovik_shop`.`sales` (`s_o_id`,`s_p_id`,`s_quantity`) VALUES (3,1,1);

-- INSERT COMMENTS

INSERT INTO `dubovik_shop`.`comments` (`c_id`,`c_u_id`,`c_p_id`,`c_text`,`c_rating`) VALUES (1,2,1,NULL,5);
INSERT INTO `dubovik_shop`.`comments` (`c_id`,`c_u_id`,`c_p_id`,`c_text`,`c_rating`) VALUES (2,3,1,'Так себе',3);
INSERT INTO `dubovik_shop`.`comments` (`c_id`,`c_u_id`,`c_p_id`,`c_text`,`c_rating`) VALUES (3,2,3,NULL,4);
