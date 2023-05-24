CREATE TABLE `users` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(255),
    `email` VARCHAR(255),
    `password` VARCHAR(255),
    `state` BOOLEAN,
    `dtype` VARCHAR(31) NOT NULL 
);

CREATE TABLE `producers` (
    `id` BIGINT NOT NULL PRIMARY KEY,
    `nif` VARCHAR(255),
    `type` VARCHAR(255),
    `quota` DOUBLE,
    FOREIGN KEY (`id`) REFERENCES `users`(`id`)
);

CREATE TABLE `validators` (
    `id` BIGINT NOT NULL PRIMARY KEY,
    FOREIGN KEY (`id`) REFERENCES `users`(`id`)
);

CREATE TABLE `files` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `creationDate` DATETIME,
    `title` VARCHAR(255),
    `description` TEXT,
    `state` VARCHAR(255),
    `size` INT,
    `numViews` INT,
    `numDownloads` INT,
    `producer_id` BIGINT,
    FOREIGN KEY (`producer_id`) REFERENCES `producers`(`id`)
);

CREATE TABLE `files_keyWords` (
    `File_id` BIGINT NOT NULL,
    `keyWords` VARCHAR(255),
    FOREIGN KEY (`File_id`) REFERENCES `files`(`id`)
);
