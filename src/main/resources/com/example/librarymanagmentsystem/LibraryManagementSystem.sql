create database LibraryManagementSystem;
use LibraryManagementSystem;
create table admin(
username int primary key,
password varchar(50),
Email varchar(50)
);
CREATE TABLE author (
    aID INT PRIMARY KEY,
    aName VARCHAR(30) NOT NULL,
    FamilyName VARCHAR(30) NOT NULL,
    Type VARCHAR(30) NOT NULL,
    Email VARCHAR(30) NOT NULL,
    phoneNumber INT NOT NULL,
    Date_of_birth varchar(30) NOT NULL
);

-- Insert an author
INSERT INTO author (aID,aName, FamilyName, Type, Email, phoneNumber, Date_of_birth)
VALUES (1,'AuthorName', 'FamilyName','Person', 'noor@gmail.com', 59956, '2002-05-14');


UPDATE author 
SET 
	aID=2,
	aName = 'noor',
      FamilyName='Hamayel',
      Type='team',
      Email='noorHamayel@gmail.com',
      phoneNumber = 8341,
      Date_of_birth='2012-12-12'
WHERE
    aID = 1;
SELECT *
FROM author;

-- Delete an author
DELETE  FROM author WHERE aID = 1;
SELECT * FROM author  WHERE aID = 2;
SELECT * FROM author;
create table Member(
    id int Not NULL ,
    name varchar(30) NOT NULL,
    dataOfBirth varchar(32) , 
    email varchar(35), 
	phone int ,
    address varchar(30),
    gender varchar(20),
    dataOfSubscribing varchar(32) , 
    dataOfCanceling varchar(32) , 
    primary key(id)
    );
    select * from Member;  
insert into Member(id, name , dataOfBirth,email,phone ,address ,gender, dataOfSubscribing ,dataOfCanceling)
    values(1,'Elaf','10-10-2009','a',22,'b','female','2/4','2/5');
insert into Member(id, name , dataOfBirth,email,phone ,address ,gender, dataOfSubscribing ,dataOfCanceling)
    values(2,'Jod','10-11-2002','-----',213,'----','male','2/5/2019','10/5/2019');
insert into Member(id, name , dataOfBirth,email,phone ,address ,gender, dataOfSubscribing ,dataOfCanceling)
    values(3,'Leen','2-12-2009','-----',540,'----','female','10/10/2022','2/12/2022');
insert into Member(id, name , dataOfBirth,email,phone ,address ,gender, dataOfSubscribing ,dataOfCanceling)
    values(4,'Youns','7-3-2008','-----',059,'----','male','3/3/2023','26/3/2023');

CREATE TABLE books (
    bookId INT PRIMARY KEY,
    title VARCHAR(255),
    ISBN INT,
    editionNo INT,
    numberOfPages INT,
    authorId INT,
    publisherId INT,
    category VARCHAR(255),
    availability INT,
    FOREIGN KEY (authorId) REFERENCES author(aID),
    FOREIGN KEY (publisherId) REFERENCES publishers(id)
);
CREATE TABLE publishers (
    id INT PRIMARY KEY,
    name VARCHAR(255),
    address VARCHAR(255),
    email VARCHAR(255),
    phone VARCHAR(20)
);
insert into admin (username,password) values (1201512,'1182002');
delete from admin where username =1201512;
CREATE TABLE borrowing (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    borrowing_date DATE,
    due_date DATE,
    member_id INT,
    book_id INT,
    FOREIGN KEY (member_id) REFERENCES Member(id),
    FOREIGN KEY (book_id) REFERENCES books(bookId)
);
select * from member;
insert into member(id, name , dataOfBirth,email,phone ,address ,gender, dataOfSubscribing ,dataOfCanceling)
    values(1,'Elaf','10-10-2009','a',22,'b','female','2/4','2/5');
insert into member(id, name , dataOfBirth,email,phone ,address ,gender, dataOfSubscribing ,dataOfCanceling)
    values(2,'Jod','10-11-2002','-----',213,'----','male','2/5/2019','10/5/2019');
insert into member(id, name , dataOfBirth,email,phone ,address ,gender, dataOfSubscribing ,dataOfCanceling)
    values(3,'Leen','2-12-2009','-----',540,'----','female','10/10/2022','2/12/2022');
insert into member(id, name , dataOfBirth,email,phone ,address ,gender, dataOfSubscribing ,dataOfCanceling)
    values(4,'Youns','7-3-2008','-----',059,'----','male','3/3/2023','26/3/2023');
