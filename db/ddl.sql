-- partner
if table not exists create table partners (
    id            bigint auto_increment primary key comment 'ID',
    partner_token varchar(255) not null comment 'partner_token',
    partner_name  varchar(255) not null comment '파트너명',
    business_no   varchar(255) not null comment '사업자등록번호',
    phoneNumber   varchar(255) not null comment '사업자전화번호',
    email         varchar(255) not null comment 'email',
    status        varchar(30)  not null default 'ENABLE' comment '상태',
    created_at    datetime(6) not null comment '생성 일시',
    updated_at    datetime(6) null comment '수정 일시'
) comment 'partners' charset = utf8mb4;