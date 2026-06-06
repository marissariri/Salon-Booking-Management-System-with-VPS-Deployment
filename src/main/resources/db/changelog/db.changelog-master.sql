-- liquibase formatted sql

-- changeset marissa:1
CREATE TABLE `roles` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `role_name` VARCHAR(50) NOT NULL UNIQUE
);
-- rollback DROP TABLE `roles`;

-- changeset marissa:2
CREATE TABLE `users` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `full_name` VARCHAR(100) NOT NULL,
    `email` VARCHAR(100) NOT NULL UNIQUE,
    `password` VARCHAR(255) NOT NULL,
    `phone_number` VARCHAR(20),
    `role_id` BIGINT NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT `fk_user_role` FOREIGN KEY (`role_id`) REFERENCES `roles`(`id`)
);
-- rollback DROP TABLE `users`;

-- changeset marissa:3
CREATE TABLE `services` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `service_name` VARCHAR(100) NOT NULL,
    `description` TEXT,
    `price` DECIMAL(10,2) NOT NULL,
    `duration_minutes` INT NOT NULL,
    `status` ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE',
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
-- rollback DROP TABLE `services`;

-- changeset marissa:4
CREATE TABLE `beauticians` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `full_name` VARCHAR(100) NOT NULL,
    `specialty` VARCHAR(100),
    `status` ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE',
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
-- rollback DROP TABLE `beauticians`;

-- changeset marissa:5
CREATE TABLE `schedules` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `service_id` BIGINT NOT NULL,
    `beautician_id` BIGINT NOT NULL,
    `schedule_date` DATE NOT NULL,
    `start_time` TIME NOT NULL,
    `end_time` TIME NOT NULL,
    `max_slot` INT DEFAULT 1,
    `available_slot` INT DEFAULT 1,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT `fk_schedule_service` FOREIGN KEY (`service_id`) REFERENCES `services`(`id`),
    CONSTRAINT `fk_schedule_beautician` FOREIGN KEY (`beautician_id`) REFERENCES `beauticians`(`id`)
);
-- rollback DROP TABLE `schedules`;

-- changeset marissa:6
CREATE TABLE `bookings` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `user_id` BIGINT NOT NULL,
    `schedule_id` BIGINT NOT NULL,
    `service_name` VARCHAR(100) NOT NULL,
    `booking_date` DATE NOT NULL,
    `booking_status` ENUM('PENDING_PAYMENT', 'CONFIRMED', 'CANCELLED', 'COMPLETED') DEFAULT 'PENDING_PAYMENT',
    `notes` TEXT,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT `fk_booking_user` FOREIGN KEY (`user_id`) REFERENCES `users`(`id`),
    CONSTRAINT `fk_booking_schedule` FOREIGN KEY (`schedule_id`) REFERENCES `schedules`(`id`)
);
-- rollback DROP TABLE `bookings`;

-- changeset marissa:7
CREATE TABLE `payments` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `booking_id` BIGINT NOT NULL,
    `amount` DECIMAL(10,2) NOT NULL,
    `payment_method` VARCHAR(50),
    `payment_status` ENUM('PENDING', 'SUCCESS', 'FAILED') DEFAULT 'PENDING',
    `payment_date` TIMESTAMP NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT `fk_payment_booking` FOREIGN KEY (`booking_id`) REFERENCES `bookings`(`id`)
);
-- rollback DROP TABLE `payments`;

-- changeset marissa:8
-- Insert dummy data
INSERT INTO `roles` (`id`, `role_name`) VALUES (1, 'ADMIN'), (2, 'CUSTOMER');

INSERT INTO `users` (`id`, `full_name`, `email`, `password`, `phone_number`, `role_id`) VALUES 
(1, 'Admin Salon', 'admin@salon.com', 'password123', '081234567890', 1),
(2, 'Marissa Rahmania', 'marissa@gmail.com', 'password123', '089876543210', 2);

INSERT INTO `services` (`id`, `service_name`, `description`, `price`, `duration_minutes`, `status`) VALUES 
(1, 'Facial Glow', 'Facial untuk mencerahkan wajah', 250000.00, 60, 'ACTIVE'),
(2, 'Korean Nail Art', 'Manicure & Nail Art khas Korea', 150000.00, 90, 'ACTIVE');

INSERT INTO `beauticians` (`id`, `full_name`, `specialty`, `status`) VALUES 
(1, 'Sarah Lee', 'Facial Expert', 'ACTIVE'),
(2, 'Dina Amanda', 'Nail Artist', 'ACTIVE');

INSERT INTO `schedules` (`id`, `service_id`, `beautician_id`, `schedule_date`, `start_time`, `end_time`, `max_slot`, `available_slot`) VALUES 
(1, 1, 1, '2026-06-01', '10:00:00', '11:00:00', 1, 1),
(2, 2, 2, '2026-06-01', '13:00:00', '14:30:00', 2, 2);

-- untuk di pom.xml
--<dependency>
--   <groupId>org.liquibase</groupId>
--   <artifactId>liquibase-core</artifactId>
--</dependency>
