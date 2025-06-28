-- Tables creation 

create table BankUsers(
UserId varchar(10) primary key,
UserName varchar(30)not null,
Passkey varchar(20) not null,
Balance int not null,
Pin int );

-- Create a new Table transactions and map it with BankUsers with help of foreign key

CREATE TABLE transactions (
    Userid VARCHAR(10) NOT NULL,
    Tid INT PRIMARY KEY AUTO_INCREMENT,
    T_Date DATE DEFAULT  (curdate()),
    amount INT NOT NULL,
    t_type BOOLEAN NOT NULL,
   foreign key(UserId) references BankUsers(UserId));

alter table transactions
modify t_type boolean default 0;

-- Crate a new table Transfer Transactions for handling money transfers and map them to BankUsers

create table transferTransactions(
ttid int primary key auto_increment,
SenderId varchar(10) not null,
amount int not null,
ReceiverId varchar(10) not null,
Foreign Key(SenderId) references BankUsers(UserId),
Foreign key(ReceiverId) references BankUsers(UserId)
);


-- Test Users--

insert into bankUsers
values("23081050","Test User”,"testing",5500000,6969);

insert into bankUsers
values("23081049","Dummy User”,"IamDUMMY",2000000,6789);

-- add some Procedures

delimiter $$
create procedure getUser(in id varchar(20))
begin
Select UserName from BankUsers where UserId = id;
end $$

delimiter $$
create procedure getPass(in id varchar(20))
begin
Select Passkey from BankUsers where UserId = id;
end $$

delimiter $$
create procedure getBalance(in id varchar(20))
begin
Select Balance from BankUsers where UserId = id;
end $$

delimiter $$
create procedure getPin(in id varchar(20))
begin
Select Pin from BankUsers where UserId = id;
end $$

delimiter $$
create procedure newPin(in id varchar(10),in newPin int)
begin
update Bankusers
set Pin = newPin where UserId = id;
end $$

delimiter $$
Create procedure InsertTrans(in id varchar(10),in amount int)
begin
Insert into Transactions (UserId,amount)
values(id,amount);
end $$

delimiter $$
Create procedure TransMoney(in Uid varchar(10),in amount int,in Rid varchar(10))
begin
Insert into Transactions
values(Uid,amount,Rid);
end $$



-- Create a Triggers for directly handling transfer operations

DELIMITER $$

CREATE TRIGGER updatebalance
AFTER INSERT ON transactions
FOR EACH ROW
BEGIN
   UPDATE bankusers 
   SET balance = balance - NEW.amount
   WHERE Userid = NEW.Userid;
END $$

DELIMITER ;


delimiter **
create trigger updateReceiverBalance
after insert on transferTransactions
for each row
begin 
update Bankusers
set balance = balance + 2*new.amount 
where UserId = new.ReceiverId;
end **


delimiter **
create trigger InsertData
after insert on transferTransactions
for each row
begin 
Insert into transactions(userid,amount,t_type)
values(new.ReceiverId,new.amount,1);

Insert into transactions(userid,amount)
values(new.Senderid,new.amount);
end **



-- Create a view to see the AmountTransffered 

      
Create view TransferDetails as
Select 
b.Username as SenderName,
b.UserId as SenderId, 
 k.amount as Amount,
r.UserId as ReceiverID, 
r.Username as ReceiverUserName
from BankUsers b right join transferTransactions k
on b.UserId = k.SenderId
left join
BankUsers r
on k.ReceiverId = r.UserId;

