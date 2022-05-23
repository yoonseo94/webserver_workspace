--============================================
-- 관리자계정
--============================================
-- web계정 생성
alter session set "_oracle_script" = true; -- c##으로 시작하지 않는 일반계정생성 허용

create user web
identified by web
default tablespace users;

grant connect, resource to web;

alter user web quota unlimited on users;


--============================================
-- web계정
--============================================
-- 회원테이블 생성
create table member (
    member_id varchar2(15),
    password varchar2(300) not null,
    member_name varchar2(50) not null,
    member_role char(1) default 'U' not null,
    gender char(1),
    birthday date,
    email varchar2(200),
    phone char(11) not null,
    address varchar2(200),
    hobby varchar2(200),
    enroll_date date default sysdate,
    constraint pk_member_id primary key(member_id),
    constraint ck_member_role check(member_role in ('U', 'A')),
    constraint ck_member_gender check(gender in ('M', 'F')),
    constraint uq_member_email unique(email)
);


--회원 추가
insert into member
values (
    'honggd', '1234', '홍길동', 'U', 'M', to_date('20000909','yyyymmdd'),
    'honggd@naver.com', '01012341234', '서울시 강남구', '운동,등산,독서', default
);

insert into member
values (
    'qwerty', '1234', '쿠어티', 'U', 'F', to_date('19900215','yyyymmdd'),
    'qwerty@naver.com', '01012341234', '서울시 송파구', '운동,등산', default
);

insert into member
values (
    'admin', '1234', '관리자', 'A', 'M', to_date('19801010','yyyymmdd'),
    'admin@naver.com', '01056785678', '서울시 관악구', '게임,독서', default
);

insert into member
values (
    'yoogs', '1234', '유관순', 'U', 'F', null,
    null, '01012344321', null, null, default
);

select * from member;
commit;

desc member;



-- 페이징쿼리 
-- 1. rownum
-- 2. row_number
select
    *
from (
    select
        row_number() over(order by enroll_date desc) rnum,
        m.*
    from
        member m) m
where
    rnum between 21 and 30;

--select * from ( select row_number() over(order by enroll_date desc) rnum, m.* from member m) m where rnum between ? and ?    

/*
1한페이지당 표시할 컨텐츠 : 10
--------------------------------------
1page : 1 ~ 10
2page : 11 ~ 20
3page : 21 ~ 30
...
11page : 101 ~ 110
12page : 111 ~ 120

*/

-- 게시판 
create table board (
    no number,
    title varchar2(100) not null,
    member_id varchar2(20),
    content varchar2(4000) not null,
    read_count number default 0,
    reg_date date default sysdate,
    constraint pk_board_no primary key(no),
    constraint fk_board_member_id foreign key(member_id) references member(member_id) on delete set null
);

create sequence seq_board_no;

create table attachment (
    no number,
    board_no number not null,
    original_filename varchar2(255) not null, -- 업로드한 파일명
    renamed_filename varchar2(255) not null, -- 저장된 파일명
    reg_date date default sysdate,
    constraint pk_attachment_no primary key(no),
    constraint fk_attachment_board_no foreign key(board_no) references board(no) on delete cascade
);

create sequence seq_attachment_no;












