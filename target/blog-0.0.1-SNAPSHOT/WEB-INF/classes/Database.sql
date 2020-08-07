create schema if not exists blog collate utf8_bin;

create table if not exists t_tag
(
    id bigint auto_increment
        primary key,
    name varchar(255) null
);

create table if not exists t_type
(
    id bigint auto_increment
        primary key,
    name varchar(255) not null
);

create table if not exists t_user
(
    id bigint auto_increment
        primary key,
    avatar varchar(255) null,
    create_time datetime null,
    email varchar(255) null,
    nickname varchar(255) null,
    password varchar(255) null,
    type int null,
    update_time datetime null,
    username varchar(255) null
);

create table if not exists t_blog
(
    id bigint auto_increment
        primary key,
    appreciation bit not null,
    commentabled bit not null,
    content longtext null,
    create_time datetime null,
    description varchar(255) null,
    first_picture varchar(255) null,
    flag varchar(255) null,
    published bit not null,
    recommend bit not null,
    share_statement bit not null,
    title varchar(255) null,
    update_time datetime null,
    views int null,
    type_id bigint null,
    user_id bigint null,
    constraint FK292449gwg5yf7ocdlmswv9w4j
        foreign key (type_id) references t_type (id),
    constraint FK8ky5rrsxh01nkhctmo7d48p82
        foreign key (user_id) references t_user (id)
);

create table if not exists t_blog_tags
(
    blogs_id bigint not null,
    tags_id bigint not null,
    constraint FK5feau0gb4lq47fdb03uboswm8
        foreign key (tags_id) references t_tag (id),
    constraint FKh4pacwjwofrugxa9hpwaxg6mr
        foreign key (blogs_id) references t_blog (id)
);

create table if not exists t_comment
(
    id bigint auto_increment
        primary key,
    admin_comment bit not null,
    avatar varchar(255) null,
    content varchar(255) null,
    create_time datetime null,
    email varchar(255) null,
    nickname varchar(255) null,
    blog_id bigint null,
    parent_comment_id bigint null,
    constraint FK4jj284r3pb7japogvo6h72q95
        foreign key (parent_comment_id) references t_comment (id),
    constraint FKke3uogd04j4jx316m1p51e05u
        foreign key (blog_id) references t_blog (id)
);

