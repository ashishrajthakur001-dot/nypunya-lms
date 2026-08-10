CREATE DATABASE IF NOT EXISTS nypunya_lms CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE nypunya_lms;

CREATE TABLE IF NOT EXISTS courses (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(160) NOT NULL,
  description VARCHAR(4000),
  status VARCHAR(20) NOT NULL,
  trainer_id BIGINT NULL,
  INDEX idx_course_status (status),
  INDEX idx_course_trainer (trainer_id)
);
