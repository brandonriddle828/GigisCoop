
/*
DROP TABLE Customers;
DROP TABLE Sales;
DROP TABLE Flock;
DROP TABLE Pricing;
DROP TABLE Expenses;
*/



CREATE TABLE Customers(
CustomerID		int				NOT NULL AUTO_INCREMENT,
First_Name    	varchar(20)	    NOT NULL,
Last_Name   	varchar(20)	    NOT NULL,
Phone     		varchar(15)    	NOT NULL,
PRIMARY KEY  (CustomerID)
);

CREATE TABLE Sales(
SaleID					int				NOT NULL AUTO_INCREMENT,
PriceID					int				NOT NULL,
CustomerID				int				NOT NULL,
Type     				varchar(50)	    NOT NULL,
Breed					varchar(50)		NOT NULL,
FlockID 				int				NOT NULL,
Date_Sold   			date	    	NOT NULL,
PRIMARY KEY  (SaleID)
);

CREATE TABLE Flock(
FlockID					int				NOT NULL AUTO_INCREMENT,
Breed					varchar(50)		NOT NULL,
Hens					int				NOT NULL,
Roosters				int				NOT NULL,
Hatch_Date   			date	    	NOT NULL,
PRIMARY KEY  (FlockID)
);

CREATE TABLE Pricing(
PriceID					int				NOT NULL AUTO_INCREMENT,
FlockID					int				NOT NULL,
Type					varchar(50)		NOT NULL,
Breed					varchar(50)		NOT NULL,
Sex						varchar(10)		NOT NULL,
Price					double			NOT NULL,
PRIMARY KEY  (PriceID)
);

CREATE TABLE Expenses (
ExpenseID				int				NOT NULL AUTO_INCREMENT,
FlockID					int				NOT NULL,
Type					varchar(50)		NOT NULL,
Item_Name				varchar(50)		NOT NULL,
Cost					double			NOT NULL,
Date_Purchased   		date	    	NOT NULL,
PRIMARY KEY  (ExpenseID)
);
