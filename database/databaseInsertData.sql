#TestData
INSERT INTO users(email)
VALUES('sungl@iu.edu'), ('zhenga@iu.edu'), ('smartin@iu.edu'), ('gvillaco@iu.edu'), ('jefftran@iu.edu');

INSERT INTO category(categoryName)
VALUES('Entertainment'), ('Food'), ('Housing'), ('Misc'), ('Retail'), ('Transportation');

FOOD, RETAIL, ENTERTAINMENT, TRANSPORTATION, HOUSING, MISC
INSERT INTO receipt(categoryID, usersID, title, receiptDate, total, imagePath, street, city, st, zipCode)
VALUES(, 1, 'birthdayParty', '2020/01/28', '10.00', 'desktop/images', '129 East 7th St', 'Bloomington', 'IN', '47408');

UPDATE receipt
SET receiptDate = '2020/01/28' WHERE receiptID = 4;

INSERT INTO receipt(categoryID, usersID, title, receiptDate, total, imagePath, street, city, st, zipCode)
VALUES(1, 2, 'KrogerGroceries', '2020/02/03', '23.00', 'desktop/images', '115 East 10th St', 'Bloomington', 'IN', '47408');
INSERT INTO receipt(categoryID, usersID, title, receiptDate, total, imagePath, street, city, st, zipCode)

##USER 1
INSERT INTO receipt(categoryID, usersID, title, receiptDate, total, imagePath, street, city, st, zipCode)
VALUES(1, 1, 'RollerSkating', '2020/01/15', '16.50', 'desktop/images', '259 Watergrove Drive', 'Bloomington', 'IN', '47408');

INSERT INTO receipt(categoryID, usersID, title, receiptDate, total, imagePath, street, city, st, zipCode)
VALUES(2, 1, 'Wendys', '2020/01/28', '23.87', 'desktop/images', '11 West 7th St', 'Bloomington', 'IN', '47408');

INSERT INTO receipt(categoryID, usersID, title, receiptDate, total, imagePath, street, city, st, zipCode)
VALUES(3, 1, 'Rent', '2020/12/01', '10.00', 'desktop/images', '10 N. Lincoln St.', 'Bloomington', 'IN', '47408');

INSERT INTO receipt(categoryID, usersID, title, receiptDate, total, imagePath, street, city, st, zipCode)
VALUES(4, 1, 'BathAndBodyWorks', '2019/11/28', '30.57', 'desktop/images', '59 East College Mall Drive', 'Bloomington', 'IN', '47408');

INSERT INTO receipt(categoryID, usersID, title, receiptDate, total, imagePath, street, city, st, zipCode)
VALUES(5, 1, 'H&M', '2020/02/02', '10.00', 'desktop/images', '117 South Drive', 'Bloomington', 'IN', '47408');

INSERT INTO receipt(categoryID, usersID, title, receiptDate, total, imagePath, street, city, st, zipCode)
VALUES(6, 1, 'Uber', '2019/12/28', '35.00', 'desktop/images', '45 WeatherMill Rd.', 'Bloomington', 'IN', '47408');

UPDATE receipt 
SET vendor = 'Skateclub' WHERE receiptID = 1;

UPDATE receipt 
SET vendor = 'Wendys' WHERE receiptID = 2; 

UPDATE receipt 
SET vendor = 'HoosierCourt' WHERE receiptID = 3;

UPDATE receipt
SET vendor = 'Bath&BodyWorks' WHERE receiptID = 4;

UPDATE receipt
SET vendor = 'H&M' WHERE receiptID = 5;

UPDATE receipt
SET vendor = 'Uber' WHERE receiptID = 6; 

INSERT INTO items(receiptID, item, price)
VALUES (1 , 'Skate Rental', '10.00');

INSERT INTO items(receiptID, item, price)
VALUES (2, 'Spicy Chicken Nuggets', '1.99'), (2, 'Medium Fries', '1.99'), (2, 'Chocolate Frosty', '0.99');

INSERT INTO items(receiptID, item, price)
VALUES (3, 'Rent', '520.00');

INSERT INTO items(receiptID, item, price)
VALUES (4, 'Body Mist', '9.99'), (4, 'Thousand Wishes', '11.99');

INSERT INTO items(receiptID, item, price)
VALUES (5, 'TShirt', '50.00'), (5, 'Hoodie', '1.99'), (5, 'Socks', '3.99'), (5, 'Dress', '10.99');

INSERT INTO items(receiptID, item, price)
VALUES (6, 'Ride', '6.99');

-------------------------------------------------------------------------
##USER 2

VALUES('Entertainment'), ('Food'), ('Housing'), ('Misc'), ('Retail'), ('Transportation')

##receipt Month of July#######
INSERT INTO receipt(categoryID, usersID, title, receiptDate, total, imagePath, street, city, st, zipCode, vendor)
VALUES(1, 2, 'Rollerblading', '2019/07/10', '13.00', 'desktop/images', '8543 Southern Drive', 'Bloomington', 'IN', '47408', 'RollerBlading Place');

INSERT INTO receipt(categoryID, usersID, title, receiptDate, total, imagePath, street, city, st, zipCode, vendor)
VALUES(2, 2, 'McDonalds', '2019/07/20', '22.50', 'desktop/images', '110 18th St.', 'Bloomington', 'IN', '47408', 'McDonalds');

INSERT INTO receipt(categoryID, usersID, title, receiptDate, total, imagePath, street, city, st, zipCode, vendor)
VALUES(3, 2, 'Rent', '2019/07/01', '520.00', 'desktop/images', '10 N. Lincoln St.', 'Bloomington', 'IN', '47408', 'Hoosier Court');

INSERT INTO receipt(categoryID, usersID, title, receiptDate, total, imagePath, street, city, st, zipCode, vendor)
VALUES(4, 2, 'Friends Birthday', '2019/07/16', '80.97', 'desktop/images', '59 S. State Mall Drive', 'Bloomington', 'IN', '47408', 'Party City');

INSERT INTO receipt(categoryID, usersID, title, receiptDate, total, imagePath, street, city, st, zipCode, vendor)
VALUES(5, 2, 'Shopping', '2019/07/03', '12.99', 'desktop/images', '117 South Drive', 'Bloomington', 'IN', '47408', 'Francescas');

INSERT INTO receipt(categoryID, usersID, title, receiptDate, total, imagePath, street, city, st, zipCode, vendor)
VALUES(6, 2, 'Bus', '2019/07/23', '45.00', 'desktop/images', '45 S. Michigan Avenue', 'Bloomington', 'IN', '47408', 'Lyft');


#Items Month of July######
INSERT INTO items(receiptID, item, price)
VALUES (26 , 'RollerBlades', '13.00');

INSERT INTO items(receiptID, item, price)
VALUES (27, 'Medium Fries', '1.99'),(27, 'Big Mac Meal', '8.99'), (27, 'Chicken Fingers Meal', '11.52');

INSERT INTO items(receiptID, item, price)
VALUES (28, 'Rent', '520.00');

INSERT INTO items(receiptID, item, price)
VALUES (29, 'Balloons', '9.99'), (29, 'Cake', '31.99'), (29, 'Alcohol', '38.59');

INSERT INTO items(receiptID, item, price)
VALUES (30, 'TShirt', '12.99');

INSERT INTO items(receiptID, item, price)
VALUES (31, 'BusTicket', '45.00');


##receipt Month of August#######
INSERT INTO receipt(categoryID, usersID, title, receiptDate, total, imagePath, street, city, st, zipCode, vendor)
VALUES(1, 2, 'RockClimbing', '2019/08/08', '13.00', 'desktop/images', '906 North Dunn Street', 'Bloomington', 'IN', '47408', 'HoosierHeights');

INSERT INTO receipt(categoryID, usersID, title, receiptDate, total, imagePath, street, city, st, zipCode, vendor)
VALUES(2, 2, 'McDonalds', '2019/08/25', '22.50', 'desktop/images', '110 18th St.', 'Bloomington', 'IN', '47408', 'McDonalds');

INSERT INTO receipt(categoryID, usersID, title, receiptDate, total, imagePath, street, city, st, zipCode, vendor)
VALUES(3, 2, 'Rent', '2019/08/01', '520.00', 'desktop/images', '10 N. Lincoln St.', 'Bloomington', 'IN', '47408', 'Hoosier Court');

INSERT INTO receipt(categoryID, usersID, title, receiptDate, total, imagePath, street, city, st, zipCode, vendor)
VALUES(4, 2, 'SoccerTicket', '2019/08/18', '60.00', 'desktop/images', '302 North Harden Street', 'Indianapolis', 'IN', '46077', 'IndyEleven');

INSERT INTO receipt(categoryID, usersID, title, receiptDate, total, imagePath, street, city, st, zipCode, vendor)
VALUES(5, 2, 'Shopping', '2019/08/09', '16.99', 'desktop/images', '117 South Drive', 'Bloomington', 'IN', '47408', 'H&M');

INSERT INTO receipt(categoryID, usersID, title, receiptDate, total, imagePath, street, city, st, zipCode, vendor)
VALUES(6, 2, 'Bus', '2019/08/27', '55.00', 'desktop/images', '45 S. Michigan Avenue', 'Bloomington', 'IN', '47408', 'BloomingtonBus');


#Items Month of August########
INSERT INTO items(receiptID, item, price)
VALUES (32 , 'RockClimbing Rentals', '13.00');

INSERT INTO items(receiptID, item, price)
VALUES (33, 'Medium Coke', '1.99'), (33, 'Mighty Kids Meal', '8.99'), (33, 'Chicken Fingers Meal', '11.52');

INSERT INTO items(receiptID, item, price)
VALUES (34, 'Rent', '520.00');

INSERT INTO items(receiptID, item, price)
VALUES (35, 'SoccerTicket', '60.00');

INSERT INTO items(receiptID, item, price)
VALUES (36, 'TShirt', '16.99');

INSERT INTO items(receiptID, item, price)
VALUES (37, 'BusTicket', '55.00');

##receipt Month of September#######
INSERT INTO receipt(categoryID, usersID, title, receiptDate, total, imagePath, street, city, st, zipCode, vendor)
VALUES(1, 2, 'Movies', '2019/09/15', '16.50', 'desktop/images', '801 Duo Drive', 'Bloomington', 'IN', '47408', 'AMC Theaters');

INSERT INTO receipt(categoryID, usersID, title, receiptDate, total, imagePath, street, city, st, zipCode, vendor)
VALUES(2, 2, 'Hardees', '2019/09/21', '24.50', 'desktop/images', '221 Southern Monroe', 'Bloomington', 'IN', '47408', 'Hardees');

INSERT INTO receipt(categoryID, usersID, title, receiptDate, total, imagePath, street, city, st, zipCode, vendor)
VALUES(3, 2, 'Rent', '2019/09/01', '520.00', 'desktop/images', '10 N. Lincoln St.', 'Bloomington', 'IN', '47408', 'Hoosier Court');

INSERT INTO receipt(categoryID, usersID, title, receiptDate, total, imagePath, street, city, st, zipCode, vendor)
VALUES(4, 2, 'Basketball Ticket', '2019/09/28', '75.00', 'desktop/images', '210 North Harden Street', 'Indianapolis', 'IN', '46077', 'PacersGame');

INSERT INTO receipt(categoryID, usersID, title, receiptDate, total, imagePath, street, city, st, zipCode, vendor)
VALUES(5, 2, 'Shopping', '2019/09/12', '18.99', 'desktop/images', '117 South Drive', 'Bloomington', 'IN', '47408', 'H&M');

INSERT INTO receipt(categoryID, usersID, title, receiptDate, total, imagePath, street, city, st, zipCode, vendor)
VALUES(6, 2, 'Bus', '2019/09/27', '55.00', 'desktop/images', '45 S. Michigan Avenue', 'Bloomington', 'IN', '47408', 'BloomingtonBus');


#Items Month of September
INSERT INTO items(receiptID, item, price)
VALUES (38 , 'MovieTickets', '16.50');

INSERT INTO items(receiptID, item, price)
VALUES (39, 'Medium Coke', '2.99'), (39, '50 Count Nugget Meal', '9.99'), (39, 'Big Burger Deal', '11.52');

INSERT INTO items(receiptID, item, price)
VALUES (40, 'Rent', '520.00');

INSERT INTO items(receiptID, item, price)
VALUES (41, 'SoccerTicket', '75.00');

INSERT INTO items(receiptID, item, price)
VALUES (42, 'Jeans', '18.99');

INSERT INTO items(receiptID, item, price)
VALUES (43, 'BusTicket', '55.00');

##receipt Month of October
INSERT INTO receipt(categoryID, usersID, title, receiptDate, total, imagePath, street, city, st, zipCode, vendor)
VALUES(1, 2, 'Movies', '2019/10/11', '16.50', 'desktop/images', '801 Duo Drive', 'Bloomington', 'IN', '47408', 'AMC Theaters');

INSERT INTO receipt(categoryID, usersID, title, receiptDate, total, imagePath, street, city, st, zipCode, vendor)
VALUES(2, 2, 'Chick Fil A', '2019/10/28', '20.50', 'desktop/images', '73 South Country', 'Bloomington', 'IN', '47408', 'Chick Fil A');

INSERT INTO receipt(categoryID, usersID, title, receiptDate, total, imagePath, street, city, st, zipCode, vendor)
VALUES(3, 2, 'Rent', '2019/10/01', '520.00', 'desktop/images', '10 N. Lincoln St.', 'Bloomington', 'IN', '47408', 'Hoosier Court');

INSERT INTO receipt(categoryID, usersID, title, receiptDate, total, imagePath, street, city, st, zipCode, vendor)
VALUES(4, 2, 'Basketball Ticket', '2019/10/30', '85.00', 'desktop/images', '210 North Harden Street', 'Indianapolis', 'IN', '46077', 'PacersGame');

INSERT INTO receipt(categoryID, usersID, title, receiptDate, total, imagePath, street, city, st, zipCode, vendor)
VALUES(5, 2, 'Shopping', '2019/10/12', '23.49', 'desktop/images', '117 South Drive', 'Bloomington', 'IN', '47408', 'H&M');

INSERT INTO receipt(categoryID, usersID, title, receiptDate, total, imagePath, street, city, st, zipCode, vendor)
VALUES(6, 2, 'Bus', '2019/10/27', '55.00', 'desktop/images', '45 S. Michigan Avenue', 'Bloomington', 'IN', '47408', 'BloomingtonBus');


#Items Month of October
INSERT INTO items(receiptID, item, price)
VALUES (44 , 'MovieTickets', '16.50');

INSERT INTO items(receiptID, item, price)
VALUES (45, 'Medium Coke', '2.99'), (45, ' Kids Meal', '5.99'), (45, 'Big Burger Deal', '11.52');

INSERT INTO items(receiptID, item, price)
VALUES (46, 'Rent', '520.00');

INSERT INTO items(receiptID, item, price)
VALUES (47, 'Basketball Ticket', '85.00');

INSERT INTO items(receiptID, item, price)
VALUES (48, 'Jeans', '23.49');

INSERT INTO items(receiptID, item, price)
VALUES (49, 'BusTicket', '55.00');

##receipt Month of November
INSERT INTO receipt(categoryID, usersID, title, receiptDate, total, imagePath, street, city, st, zipCode, vendor)
VALUES(1, 2, 'Rock Climbing', '2019/11/12', '13.50', 'desktop/images', '906 North Dunn Street', 'Bloomington', 'IN', '47408', 'HoosierHeights');

INSERT INTO receipt(categoryID, usersID, title, receiptDate, total, imagePath, street, city, st, zipCode, vendor)
VALUES(2, 2, 'Steak n Shake', '2019/11/01', '20.00', 'desktop/images', '876 East 10th Street', 'Bloomington', 'IN', '47408', 'Steak n Shake');

INSERT INTO receipt(categoryID, usersID, title, receiptDate, total, imagePath, street, city, st, zipCode, vendor)
VALUES(3, 2, 'Rent', '2019/11/01', '520.00', 'desktop/images', '10 N. Lincoln St.', 'Bloomington', 'IN', '47408', 'Hoosier Court');

INSERT INTO receipt(categoryID, usersID, title, receiptDate, total, imagePath, street, city, st, zipCode, vendor)
VALUES(4, 2, 'Basketball Ticket', '2019/11/08', '40.00', 'desktop/images', '210 North Harden Street', 'Indianapolis', 'IN', '46077', 'PacersGame');

INSERT INTO receipt(categoryID, usersID, title, receiptDate, total, imagePath, street, city, st, zipCode, vendor)
VALUES(5, 2, 'Shopping', '2019/11/18', '50.00', 'desktop/images', '117 South Drive', 'Bloomington', 'IN', '47408', 'Dicks Sporting Goods');

INSERT INTO receipt(categoryID, usersID, title, receiptDate, total, imagePath, street, city, st, zipCode, vendor)
VALUES(6, 2, 'Bus', '2019/11/16', '55.00', 'desktop/images', '45 S. Michigan Avenue', 'Bloomington', 'IN', '47408', 'BloomingtonBus');


#Items Month of November
INSERT INTO items(receiptID, item, price)
VALUES (50 , 'Rock Climbing Rentals', '13.50');

INSERT INTO items(receiptID, item, price)
VALUES (51, 'Large Sprite', '3.49'), (51, 'Bacon Lovers Meal', '5.99'), (51, 'Kids Meal', '3.99'), (51, ' Triple Cheeseburger Meal', '6.53');

INSERT INTO items(receiptID, item, price)
VALUES (52, 'Rent', '520.00');

INSERT INTO items(receiptID, item, price)
VALUES (53, 'Basketball Ticket', '40.00');

INSERT INTO items(receiptID, item, price)
VALUES (54, 'Socks', '25.00'), (54, 'Nike Shirt', '25.00');

INSERT INTO items(receiptID, item, price)
VALUES (55, 'BusTicket', '55.00');

##receipt Month of December
INSERT INTO receipt(categoryID, usersID, title, receiptDate, total, imagePath, street, city, st, zipCode, vendor)
VALUES(1, 2, 'Roller Skating', '2019/12/07', '15.50', 'desktop/images', '808 South Indiana Avenue', 'Indianapolis', 'IN', '46077', 'IndianapolisSkates');

INSERT INTO receipt(categoryID, usersID, title, receiptDate, total, imagePath, street, city, st, zipCode, vendor)
VALUES(2, 2, 'Steak n Shake', '2019/12/03', '20.00', 'desktop/images', '876 East 10th Street', 'Bloomington', 'IN', '47408', 'Steak n Shake');

INSERT INTO receipt(categoryID, usersID, title, receiptDate, total, imagePath, street, city, st, zipCode, vendor)
VALUES(3, 2, 'Rent', '2019/12/01', '520.00', 'desktop/images', '10 N. Lincoln St.', 'Bloomington', 'IN', '47408', 'Hoosier Court');

INSERT INTO receipt(categoryID, usersID, title, receiptDate, total, imagePath, street, city, st, zipCode, vendor)
VALUES(4, 2, 'Basketball Ticket', '2019/12/25', '60.00', 'desktop/images', '210 North Harden Street', 'Indianapolis', 'IN', '46077', 'PacersGame');

INSERT INTO receipt(categoryID, usersID, title, receiptDate, total, imagePath, street, city, st, zipCode, vendor)
VALUES(5, 2, 'Shopping', '2019/12/27', '86.00', 'desktop/images', '117 South Drive', 'Bloomington', 'IN', '47408', 'GNC');

INSERT INTO receipt(categoryID, usersID, title, receiptDate, total, imagePath, street, city, st, zipCode, vendor)
VALUES(6, 2, 'Bus', '2019/12/16', '55.00', 'desktop/images', '45 S. Michigan Avenue', 'Bloomington', 'IN', '47408', 'BloomingtonBus');


#Items Month of December
INSERT INTO items(receiptID, item, price)
VALUES (56 , 'Roller Skates', '15.50');

INSERT INTO items(receiptID, item, price)
VALUES (57, 'Large Sprite', '3.49'), (57, 'Bacon Lovers Meal', '5.99'), (57, 'Kids Meal', '3.99'), (57, ' Triple Cheeseburger Meal', '6.53');

INSERT INTO items(receiptID, item, price)
VALUES (58, 'Rent', '520.00');

INSERT INTO items(receiptID, item, price)
VALUES (59, 'Basketball Ticket', '60.00');

INSERT INTO items(receiptID, item, price)
VALUES (60, 'Protein', '50.00'), (60, 'Preworkout', '36.00');

INSERT INTO items(receiptID, item, price)
VALUES (61, 'BusTicket', '55.00');

##receipt Month of January
INSERT INTO receipt(categoryID, usersID, title, receiptDate, total, imagePath, street, city, st, zipCode, vendor)
VALUES(1, 2, 'Movies', '2019/10/11', '16.50', 'desktop/images', '801 Duo Drive', 'Bloomington', 'IN', '47408', 'AMC Theaters');

INSERT INTO receipt(categoryID, usersID, title, receiptDate, total, imagePath, street, city, st, zipCode, vendor)
VALUES(2, 2, 'Steak n Shake', '2019/01/13', '20.00', 'desktop/images', '876 East 10th Street', 'Bloomington', 'IN', '47408', 'Steak n Shake');

INSERT INTO receipt(categoryID, usersID, title, receiptDate, total, imagePath, street, city, st, zipCode, vendor)
VALUES(3, 2, 'Rent', '2019/01/25', '520.00', 'desktop/images', '10 N. Lincoln St.', 'Bloomington', 'IN', '47408', 'Hoosier Court');

INSERT INTO receipt(categoryID, usersID, title, receiptDate, total, imagePath, street, city, st, zipCode, vendor)
VALUES(4, 2, 'Basketball Ticket', '2019/01/26', '35.00', 'desktop/images', '210 North Harden Street', 'Indianapolis', 'IN', '46077', 'PacersGame');

INSERT INTO receipt(categoryID, usersID, title, receiptDate, total, imagePath, street, city, st, zipCode, vendor)
VALUES(5, 2, 'Shopping', '2019/01/27', '100.00', 'desktop/images', '117 South Drive', 'Bloomington', 'IN', '47408', 'Francescas');

INSERT INTO receipt(categoryID, usersID, title, receiptDate, total, imagePath, street, city, st, zipCode, vendor)
VALUES(6, 2, 'Bus', '2019/01/21', '55.00', 'desktop/images', '45 S. Michigan Avenue', 'Bloomington', 'IN', '47408', 'BloomingtonBus');


#Items Month of January
INSERT INTO items(receiptID, item, price)
VALUES (62 , 'Roller Skates', '16.50');

INSERT INTO items(receiptID, item, price)
VALUES (63, 'Large Sprite', '3.49'), (63, 'Bacon Lovers Meal', '5.99'), (63, 'Kids Meal', '3.99'), (63, ' Triple Cheeseburger Meal', '6.53');

INSERT INTO items(receiptID, item, price)
VALUES (64, 'Rent', '520.00');

INSERT INTO items(receiptID, item, price)
VALUES (65, 'Basketball Ticket', '35.00');

INSERT INTO items(receiptID, item, price)
VALUES (66, 'Dress', '28.99'), (66, 'Socks', '10.99'), (66,'Skirt', '25.02'), (66,'Shoes','35.00');

INSERT INTO items(receiptID, item, price)
VALUES (67, 'BusTicket', '55.00');

##receipt for User 2 Month of February
INSERT INTO receipt(categoryID, usersID, title, receiptDate, total, imagePath, street, city, st, zipCode, vendor)
VALUES(1, 2, 'Movies', '2020/02/15', '16.50', 'desktop/images', '801 Duo Drive', 'Bloomington', 'IN', '47408', 'AMC Theaters');

INSERT INTO receipt(categoryID, usersID, title, receiptDate, total, imagePath, street, city, st, zipCode, vendor)
VALUES(2, 2, 'McDonalds', '2020/02/15', '22.50', 'desktop/images', '110 18th St.', 'Bloomington', 'IN', '47408', 'McDonalds');

INSERT INTO receipt(categoryID, usersID, title, receiptDate, total, imagePath, street, city, st, zipCode, vendor)
VALUES(3, 2, 'Rent', '2020/02/01', '520.00', 'desktop/images', '10 N. Lincoln St.', 'Bloomington', 'IN', '47408', 'Hoosier Court');

INSERT INTO receipt(categoryID, usersID, title, receiptDate, total, imagePath, street, city, st, zipCode, vendor)
VALUES(4, 2, 'Shopping for Birthday', '2020/02/13', '80.97', 'desktop/images', '59 S. State Mall Drive', 'Bloomington', 'IN', '47408', 'Party City');

INSERT INTO receipt(categoryID, usersID, title, receiptDate, total, imagePath, street, city, st, zipCode, vendor)
VALUES(5, 2, 'Shopping', '2020/02/03', '12.99', 'desktop/images', '117 South Drive', 'Bloomington', 'IN', '47408', 'Francescas');

INSERT INTO receipt(categoryID, usersID, title, receiptDate, total, imagePath, street, city, st, zipCode, vendor)
VALUES(6, 2, 'WorktoHome', '2020/02/10', '35.00', 'desktop/images', '45 E. Lansing', 'Bloomington', 'IN', '47408', 'Lyft');


##Items for User 2 Month of February 
INSERT INTO items(receiptID, item, price)
VALUES (20 , 'Movie Tickets', '16.50');

INSERT INTO items(receiptID, item, price)
VALUES (21, 'Medium Fries', '1.99'), (21, 'Big Mac Meal', '8.99'), (21, 'Chicken Fingers Meal', '11.52');

INSERT INTO items(receiptID, item, price)
VALUES (22, 'Rent', '520.00');

INSERT INTO items(receiptID, item, price)
VALUES (23, 'Balloons', '9.99'), (23, 'Cake', '31.99'), (23, 'Alcohol', '38.59');

INSERT INTO items(receiptID, item, price)
VALUES (24, 'TShirt', '50.00');

INSERT INTO items(receiptID, item, price)
VALUES (25, 'Ride', '35.00');

##INSERT DATA FOR GOALS TABLE 
INSERT INTO goal(usersID, setAmount, status)
VALUES(2, '700.00', 'Success');

**havent inserted data into database(doublecheck later)

1 July- $694.46
2 August - $687.49
3 Sepetember - $709.99
4 October - $720.49
5 November - $698.5	
6 December - $756.5
7 January - $746.5
8 February - $687.96

INSERT INTO goal(usersID, setAmount, status)
VALUES(2, '700.00', 'Success');

INSERT INTO goal(usersID, setAmount, status)
VALUES(2, '700.00', 'Failed');

INSERT INTO goal(usersID, setAmount, status)
VALUES(2, '700.00', 'Failed');

INSERT INTO goal(usersID, setAmount, status)
VALUES(2, '700.00', 'Success');

INSERT INTO goal(usersID, setAmount, status)
VALUES(2, '700.00', 'Failed');

INSERT INTO goal(usersID, setAmount, status)
VALUES(2, '700.00', 'Failed');

INSERT INTO goal(usersID, setAmount, status)
VALUES(2, '700.00', 'Success');


##INSERT DATA FOR goal_details table
INSERT INTO goal_details(goalID, usersID, dateStart, dateEnd)
VALUES(1, 2, '2019/07/01', '2019/07/31');

INSERT INTO goal_details(goalID, usersID, dateStart, dateEnd)
VALUES(2, 2, '2019/08/01', '2019/08/31');

INSERT INTO goal_details(goalID, usersID, dateStart, dateEnd)
VALUES(3, 2, '2019/09/01', '2019/09/30');

INSERT INTO goal_details(goalID, usersID, dateStart, dateEnd)
VALUES(4, 2, '2019/10/01', '2019/10/31');

INSERT INTO goal_details(goalID, usersID, dateStart, dateEnd)
VALUES(5, 2, '2019/11/01', '2019/11/30');

INSERT INTO goal_details(goalID, usersID, dateStart, dateEnd)
VALUES(6, 2, '2019/12/01', '2019/12/31');

INSERT INTO goal_details(goalID, usersID, dateStart, dateEnd)
VALUES(7, 2, '2020/01/01', '2020/01/31');

INSERT INTO goal_details(goalID, usersID, dateStart, dateEnd)
VALUES(8, 2, '2020/02/01', '2020/02/29');



--------------------------------------------------------------------------------------------------------------------------------

SELECT *
FROM receipt as r, items as i
WHERE r.receiptID = i.receiptID 
AND r.receiptID = 4;

SELECT r.receiptID, r.title, r.usersID, r.total
FROM receipt as r, users as u
WHERE u.usersID = 2; 
________________________________________________________________________________________
SELECT * 
FROM items as i, receipt as r
WHERE i.receiptID = r.receiptID 
AND i.receiptID = 21;
DELETE FROM items WHERE itemID='41';
