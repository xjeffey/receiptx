#####HOME SCREEN/Login screen--------------------------------------------------------
##TOTAL of all receipts for the month
SELECT SUM(r.total) AS monthTotal
FROM receipt as r, users as u, category as c
WHERE u.email = '$var_email' AND c.categoryID = r.categoryID and u.usersID = r.usersID
AND month(receiptDate) = '$var_month' and year(receiptDate) = '$var_year'
GROUP BY month(receiptDate) AND year(receiptDate);

##sum of receipts by category for a single month
SELECT c.categoryName, SUM(r.total) AS catTotal
FROM receipt as r, users as u, category as c
WHERE u.email = 'var_email' AND 
r.categoryID = c.categoryID AND u.usersID = r.usersID 
AND month(receiptDate) = '$var_month' 
AND year(receiptDate) = '$var_year'
GROUP BY c.categoryName;

## select all emails to see if it already exist 
SELECT email
FROM users;

##RECEIPT PAGE---------------------------------------------------------------
##display all receipt all its attributes 
SELECT r.receiptID, r.title, c.categoryName, r.receiptDate, r.total, r.street, r.city, r.st, r.zipCode, r.vendor
FROM receipt as r, users as u, items as i, category as c
WHERE r.receiptID = i.receiptID AND c.categoryID = r.categoryID AND r.usersID = u.usersID AND
u.email = 'cosmicca@live.com';

##TESTING COMMENT


##RECEIPT PAGE
##display all items with price
SELECT r.receiptID, i.item, i.price
FROM items as i, receipt as r, users as u
WHERE r.receiptID = i.receiptID AND u.usersID = r.usersID AND r.receiptID = 1 AND
u.email = 'sungl@iu.edu';

##PROFILE PAGE---------------------------------------------------------

##count of all the receipts they've entered (profile page)
SELECT COUNT(r.receiptID) AS receiptsEntered, u.usersID AS User
FROM receipt AS r, users AS u
WHERE r.usersID = u.usersID
AND u.usersID = '2';

##select # of goals that user has succesfully completed 
SELECT COUNT(gl.goalID) AS goalsEntered, u.usersID AS User
FROM goal AS gl, goal_details AS gd, users as u
WHERE gl.goalID = gd.goalID
AND u.usersID = gl.usersID
AND u.usersID = '2'
AND gl.status = 'Success';

##Count ## of receipts user has attempted 
SELECT COUNT(gl.goalID) AS goalsEntered, u.usersID AS User
FROM goal AS gl, goal_details AS gd, users as u
WHERE gl.goalID = gd.goalID
AND u.usersID = gl.usersID
AND u.usersID = '2';

##sum of all receipts by user(how much they've spent')
SELECT u.usersID AS userID, SUM(r.total) AS userTotal
FROM receipt AS r, users AS u
WHERE r.usersID = '2'
AND r.usersID = u.usersID;

#######Filtering ----------------------------------------
##81A. By Date(recent to latest)

SELECT r.receiptID, r.title, r.categoryID, r.receiptDate, r.total, r.street, r.city, r.st, r.zipCode, r.vendor, r.imagePath
FROM receipt AS r, users AS u
WHERE r.usersID = u.usersID
AND u.email = '$var_email'
ORDER BY r.receiptDate DESC;


#Latest to recent
SELECT r.receiptID, r.title, r.categoryID, r.receiptDate, r.total, r.street, r.city, r.st, r.zipCode, r.vendor, r.imagePath
FROM receipt AS r, users AS u
WHERE r.usersID = u.usersID
AND u.email = '$var_email'
ORDER BY r.receiptDate ASC;

##81B. By Total(highest to lowest)
SELECT r.receiptID, r.title, r.categoryID, r.receiptDate, r.total, r.street, r.city, r.st, r.zipCode, r.vendor, r.imagePath
FROM receipt AS r, users AS u
WHERE u.email = '$var_email'
AND r.usersID = u.usersID
ORDER BY r.total DESC;

#lowest to highest
SELECT r.receiptID, r.title, r.categoryID, r.receiptDate, r.total, r.street, r.city, r.st, r.zipCode, r.vendor, r.imagePath
FROM receipt AS r, users AS u
WHERE u.email = '$var_email'
AND r.usersID = u.usersID
ORDER BY r.total ASC;

##81C.  By Vendor(alphabetical A-Z & Z-A)
SELECT r.receiptID, r.title, r.categoryID, r.receiptDate, r.total, r.street, r.city, r.st, r.zipCode, r.vendor, r.imagePath
FROM receipt AS r, users AS u
WHERE r.usersID = u.usersID AND u.email = '$var_email'
ORDER BY r.vendor DESC;

SELECT r.receiptID, r.title, r.categoryID, r.receiptDate, r.total, r.street, r.city, r.st, r.zipCode, r.vendor, r.imagePath 
FROM receipt AS r, users AS u
WHERE r.usersID = u.usersID AND u.email = '$var_email'
ORDER BY r.vendor ASC;

##81D. By ReceiptName (Z-A)
SELECT r.receiptID, r.title, r.categoryID, r.receiptDate, r.total, r.street, r.city, r.st, r.zipCode, r.vendor, r.imagePath
FROM receipt AS r, users AS u
WHERE r.usersID = u.usersID AND u.email = '$var_email'
ORDER BY r.title DESC;

#A-Z
SELECT r.receiptID, r.title, r.categoryID, r.receiptDate, r.total, r.street, r.city, r.st, r.zipCode, r.vendor, r.imagePath
FROM receipt AS r, users AS u
WHERE u.email = '$var_email'
AND r.usersID = u.usersID
ORDER BY r.title ASC;

##81E. All receipts in X month(recent to latest)
SELECT r.receiptID, r.title, r.categoryID, r.receiptDate, r.total, r.street, r.city, r.st, r.zipCode, r.vendor, r.imagePath
FROM receipt AS r, users AS u
WHERE u.email = '$var_email'
AND r.usersID = u.usersID
AND month(r.receiptDate) = '$var_month' AND year(r.receiptDate) = '$var_year'
ORDER BY r.receiptDate DESC;

#LATEST TO RECENT
SELECT r.receiptID, r.title, r.categoryID, r.receiptDate, r.total, r.street, r.city, r.st, r.zipCode, r.vendor, r.imagePath
FROM receipt AS r, users AS u
WHERE u.email = '$var_email'
AND r.usersID = u.usersID
AND month(r.receiptDate) = '$var_month' AND year(r.receiptDate) = '$var_year'
ORDER BY r.receiptDate ASC;

##81F.  All receipts in X Year (recent to latest)
SELECT r.receiptID, r.title, r.categoryID, r.receiptDate, r.total, r.street, r.city, r.st, r.zipCode, r.vendor, r.imagePath
FROM receipt AS r, users AS u
WHERE u.email = '$var_email'
AND r.usersID = u.usersID
AND year(r.receiptDate) = '$var_year'
ORDER BY r.receiptDate DESC;

##LATEST TO RECENT
SELECT r.receiptID, r.title, r.categoryID, r.receiptDate, r.total, r.street, r.city, r.st, r.zipCode, r.vendor, r.imagePath
FROM receipt AS r, users AS u
WHERE u.email = '$var_email'
AND r.usersID = u.usersID
AND year(r.receiptDate) = '$var_year'
ORDER BY r.receiptDate ASC;

##81G. All receipts from X store (A-Z)
SELECT r.receiptID, r.title, r.categoryID, r.receiptDate, r.total, r.street, r.city, r.st, r.zipCode, r.vendor, r.imagePath
FROM receipt AS r, users AS u
WHERE u.email = '$var_email'
AND r.usersID = u.usersID
ORDER BY r.vendor DESC;

##81H. All receipts w/ X item (recent to latest)
SELECT r.receiptID, r.title, r.categoryID, r.receiptDate, r.total, r.street, r.city, r.st, r.zipCode, r.vendor, r.imagePath
FROM receipt AS r, users AS u, items AS i
WHERE u.email = '$var_email'
AND r.usersID = u.usersID
AND r.receiptID = i.receiptID
AND i.item = '$var_item'
ORDER BY r.receiptDate DESC;

#LATEST TO RECENT
SELECT r.receiptID, r.title, r.categoryID, r.receiptDate, r.total, r.street, r.city, r.st, r.zipCode, r.vendor, r.imagePath
FROM receipt AS r, users AS u, items AS i
WHERE u.email = '$var_email'
AND r.usersID = u.usersID
AND r.receiptID = i.receiptID
AND i.item = '$var_item'
ORDER BY r.receiptDate ASC;
________________________________________________________________________________________-
#SEARCH FUNCTION 
SELECT i.item, r.receiptID, r.title, r.categoryID, r.receiptDate, r.total, r.street, r.city, r.st, r.zipCode, r.vendor, r.imagePath
FROM receipt AS r, items AS i, users AS u
WHERE u.email = '$var_email'
AND r.usersID = u.usersID
AND r.receiptID = i.receiptID
AND r.vendor LIKE '$var_vendor';

SELECT i.item, r.receiptID, r.title, r.categoryID, r.receiptDate, r.total, r.street, r.city, r.st, r.zipCode, r.vendor, r.imagePath
FROM receipt AS r, items AS i, users AS u
WHERE u.email = '$var_email'
AND r.usersID = u.usersID
AND r.receiptID = i.receiptID
AND i.item LIKE '$var_item';

SELECT i.item, r.receiptID, r.title, r.categoryID, r.receiptDate, r.total, r.street, r.city, r.st, r.zipCode, r.vendor, r.imagePath
FROM receipt AS r, items AS i, users AS u
WHERE u.email = '$var_email'
AND r.usersID = u.usersID
AND r.receiptID = i.receiptID
AND r.title LIKE '$var_title';

