Create table users(
usersID int auto_increment,
email varchar(75) not null unique,
primary key (usersID)
)
engine = innodb;

Create table category(
categoryID int auto_increment,
primary key (categoryID)
)
engine = innodb;
ALTER TABLE category
ADD categoryName varchar(30);
<<<<<<< HEAD

=======
>>>>>>> 1780e48fe6f95dea4debe62c7035e550403416eb

Create table goal(
goalID int auto_increment,
usersID int,
setAmount decimal(10, 2),
status varchar(75),
FOREIGN KEY (usersID) REFERENCES users(usersID),
PRIMARY KEY (goalID)
)
engine = innodb;

Create table report(
reportID int auto_increment,
total decimal(10, 2),
reportDate date,
usersID int, 
foreign key (usersID) REFERENCES users(usersID),
primary key (reportID)
)
engine = innodb;

Create table goal_details(
goalID int,
usersID int,
dateStart date not null,
dateEnd date,
foreign key (usersID) REFERENCES users(usersID),
foreign key (goalID) REFERENCES goal(goalID)
)
engine = innodb;

Create table items(
itemID int auto_increment,
receiptID int,
item varchar(75) not null,
price decimal(10, 2),
quantity int,
foreign key (receiptID) REFERENCES receipt(receiptID),
primary key (itemID)
)
engine = innodb;
ALTER TABLE items
DROP COLUMN quantity;

Create table receipt(
receiptID int auto_increment,
categoryID int,
usersID int, 
title varchar(75) NOT NULL,
receiptDate date,
address varchar(75),
subTotal decimal(10,2),
total decimal(10,2),
imagePath varchar(256),
foreign key (usersID) REFERENCES users(usersID),
foreign key (categoryID) REFERENCES category(categoryID),
primary key (receiptID)
)
engine = innodb;
ALTER TABLE receipt
DROP COLUMN address;
ALTER TABLE receipt
DROP COLUMN subTotal;
ALTER TABLE receipt
DROP COLUMN imagePath;
ALTER TABLE receipt
ADD imagePath varchar(256);

ALTER TABLE receipt
ADD street varchar(100);
ALTER TABLE receipt
ADD city varchar(25);
ALTER TABLE receipt
ADD st varchar(25);
ALTER TABLE receipt
ADD zipCode varchar(10);