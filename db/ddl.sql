use `order`;

-- partner
if table not exists create table partners (
    id            bigint auto_increment primary key comment 'ID',
    partner_token varchar(255) not null comment 'partner_token',
    partner_name  varchar(255) not null comment '파트너명',
    business_no   varchar(255) not null comment '사업자등록번호',
    email         varchar(255) not null comment 'email',
    status        varchar(30)  not null default 'ENABLE' comment '상태',
    created_at    datetime(6) not null comment '생성 일시',
    updated_at    datetime(6) null comment '수정 일시'
) comment 'partners' charset = utf8mb4;

-- items
create table items
(
    id         bigint auto_increment primary key comment 'ID',
    item_token varchar(255) not null comment 'item_token',
    partner_id bigint       not null comment '파트너 ID',
    item_name  varchar(255) not null comment '상품명',
    item_price int(11) not null comment '상품 가격',
    status     varchar(30)  not null default 'PREPARE' comment '상태',
    deleted_at datetime(6) null comment '삭제 일시',
    created_at datetime(6) not null comment '생성 일시',
    updated_at datetime(6) null comment '수정 일시',

    FOREIGN KEY (partner_id) REFERENCES partners (id)
) comment 'items' charset = utf8mb4;

ALTER TABLE items ADD CONSTRAINT uq_item_token UNIQUE (item_token);

-- item option group
create table item_option_groups
(
    id                     bigint auto_increment primary key comment 'ID',
    item_id                bigint      not null comment '상품 ID',
    ordering               tinyint(3) not null comment '정렬순서',
    item_option_group_name varchar(30) not null comment '옵션그룹명 (색상, 사이즈 등)',
    created_at             datetime(6) not null comment '생성 일시',
    updated_at             datetime(6) null comment '수정 일시',

    FOREIGN KEY (item_id) REFERENCES items (id)
) comment 'item_option_groups' charset = utf8mb4;

-- item_option
create table item_options
(
    id                   bigint auto_increment primary key comment 'ID',
    item_option_group_id bigint      not null comment '상품 옵션 그룹 ID',
    ordering             tinyint(3) not null comment '정렬순서',
    item_option_name     varchar(30) not null comment '옵션명 (빨강, 파랑, XL, L)',
    item_option_price    int(11) not null comment '상품 옵션 가격',
    created_at           datetime(6) not null comment '생성 일시',
    updated_at           datetime(6) null comment '수정 일시',
    FOREIGN KEY (item_option_group_id) REFERENCES item_option_groups (id)
) comment 'item_options' charset = utf8mb4;

--stock
create table stocks
(
    id                   bigint auto_increment primary key comment 'ID',
    item_id           varchar(255) not null unique,
    remain               int not null default 0 comment  '남은 수량',
    created_at           datetime(6) not null comment '생성 일시',
    updated_at           datetime(6) null comment '수정 일시',

    FOREIGN KEY (item_token) REFERENCES items(item_token)
) comment 'stocks' charset = utf8mb4;


-- order
create table orders
(
    id                bigint auto_increment primary key comment 'ID',
    order_token       varchar(255) not null comment 'order_token',
    pay_method        varchar(30)  not null comment '결제수단',
    receiver_name     varchar(30)  not null comment '수령자명',
    receiver_phone    varchar(30)  not null comment '수령자 휴대폰번호',
    receiver_zipcode  varchar(10)  not null comment '수령자 우편번호',
    receiver_address1 varchar(255) not null comment '수령자 주소1',
    receiver_address2 varchar(255) not null comment '수령자 주소2',
    etc_message       varchar(255) not null comment '남기는 말',
    status            varchar(30)  not null default 'INIT' comment '상태',
    ordered_at        datetime(6) not null comment '주문 일시',
    created_at        datetime(6) not null comment '생성 일시',
    updated_at        datetime(6) null comment '수정 일시'
) comment 'orders' charset = utf8mb4;

-- order_items
create table order_items
(
    id              bigint auto_increment primary key comment 'ID',
    order_id        bigint       not null comment 'order_id',
    order_count     tinyint      not null comment '주문갯수',
    partner_id      bigint       not null comment '파트너 ID',
    item_id         bigint       not null comment '상품 ID',
    item_name       varchar(255) not null comment '상품명',
    item_token      varchar(30)  not null comment '상품 token',
    item_price      int(11) not null comment '상품 가격',
    delivery_status varchar(30)  not null default 'BEFORE_DELIVERY' comment '상태',
    created_at      datetime(6) not null comment '생성 일시',
    updated_at      datetime(6) null comment '수정 일시',

    FOREIGN KEY (partner_id) REFERENCES partners(id),
    FOREIGN KEY (order_id) REFERENCES orders(id)
) comment 'order_items' charset = utf8mb4;


create table order_item_option_groups
(
    id                     bigint auto_increment primary key comment 'ID',
    order_item_id          bigint       not null comment 'order_item_id',
    ordering               tinyint(3) not null comment '정렬순서',
    item_option_group_name varchar(255) not null comment '상품 옵션 그룹명',
    created_at             datetime(6) not null comment '생성 일시',
    updated_at             datetime(6) null comment '수정 일시',
    FOREIGN KEY (order_item_id) REFERENCES order_items(id)
) comment 'order_item_option_groups' charset = utf8mb4;

create table order_item_options
(
    id                         bigint auto_increment primary key comment 'ID',
    order_item_option_group_id bigint       not null comment 'order_item_option_group_id',
    ordering                   tinyint(3) not null comment '정렬순서',
    item_option_name           varchar(255) not null comment '상품 옵션명',
    item_option_price          int(11) not null comment '상품 옵션 가격',
    created_at                 datetime(6) not null comment '생성 일시',
    updated_at                 datetime(6) null comment '수정 일시',

    FOREIGN KEY (order_item_option_group_id) REFERENCES order_item_option_groups(id)
) comment 'order_item_options' charset = utf8mb4;
