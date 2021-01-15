CREATE TABLE `users`
(
    `id`              char(36)     NOT NULL,
    `first_name`      varchar(100) NOT NULL,
    `last_name`       varchar(100) NOT NULL,
    `email`           varchar(100) NOT NULL,
    PRIMARY KEY (`id`),
    `description`     varchar(100) DEFAULT NULL,
    `profile_picture` varchar(100) DEFAULT NULL
);

CREATE TABLE `communities`
(
    `id`    char(36)     NOT NULL,
    `title` varchar(100) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `questions`
(
    `id`           char(36)     NOT NULL,
    `title`        varchar(100) NOT NULL,
    `body`         varchar(500) NOT NULL,
    `created_at`   datetime     NOT NULL,
    `active`       varchar(10) DEFAULT 'TRUE',
    `user_id`      char(36)     NOT NULL,
    `community_id` char(36) NOT NULL,
    PRIMARY KEY (`id`),
    KEY            `questions_FK_users` (`user_id`),
    KEY `questions_FK_communities` (`community_id`),
    CONSTRAINT `questions_FK_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `questions_FK_communities` FOREIGN KEY (`community_id`) REFERENCES `communities` (`id`)
);

CREATE TABLE `answers`
(
    `id`          char(36)     NOT NULL,
    `body`        varchar(500) NOT NULL,
    `created_at`  datetime     NOT NULL,
    `question_id` char(36)     NOT NULL,
    `user_id`     char(36)     NOT NULL,
    PRIMARY KEY (`id`),
    KEY           `answers_FK_questions` (`question_id`),
    KEY           `answers_FK_users` (`user_id`),
    CONSTRAINT `answers_FK_questions` FOREIGN KEY (`question_id`) REFERENCES `questions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `answers_FK_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `votes`
(
    `id`        char(36) NOT NULL,
    `user_id`   char(36) NOT NULL,
    `answer_id` char(36) NOT NULL,
    PRIMARY KEY (`id`),
    KEY         `votes_FK_users` (`user_id`),
    KEY         `votes_FK_answers` (`answer_id`),
    CONSTRAINT `votes_FK_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `votes_FK_answers` FOREIGN KEY (`answer_id`) REFERENCES `answers` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

create table tags
(
    title        varchar(100) not null,
    id           char(36)     not null
        primary key,
    question_id  char(36)     null,
    community_id char(36)     null,
    constraint tags_FK_questions
        foreign key (question_id) references questions (id)
);
CREATE TABLE `users_communities`
(
    `user_id`      char(36) NOT NULL,
    `community_id` char(36) NOT NULL,
    `joined_at`    datetime NOT NULL,
    PRIMARY KEY (`user_id`, `community_id`)
);
