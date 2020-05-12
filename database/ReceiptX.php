#User insert
<?php
$conn=mysqli_connect("db.sice.indiana.edu", "i494f19_team43","my+sql=i494f19_team43","i494f19_team43");

if (mysqli_connect_errno())
	{echo nl2br("Failed to connect to MySQL: " . mysqli_connect_error()."\n"); }
else 
	{ echo nl2br("Established Database Connection \n"); }
	

$var_email = mysqli_real_escape_string($conn, $_POST['email']);

$query="INSERT INTO users (email) VALUES ('$var_email')";
if (mysqli_query($conn, $query))
	{echo "1 record added";}
Else
	{ die('SQL Error: ' . mysqli_error($conn) ); }
mysqli_close($conn);
?>


#Items insert
<?php
$conn=mysqli_connect("db.sice.indiana.edu", "i494f19_team43","my+sql=i494f19_team43","i494f19_team43");

if (mysqli_connect_errno())
	{echo nl2br("Failed to connect to MySQL: " . mysqli_connect_error()."\n"); }
else 
	{ echo nl2br("Established Database Connection \n"); }
	

$var_item = mysqli_real_escape_string($conn, $_POST['item']);
$var_price = mysqli_real_escape_string($conn, $_POST['price']);


$query="INSERT INTO items (item, price) VALUES ('$var_item','$var_price')";
if (mysqli_query($conn, $query))
	{echo "1 record added";}
Else
	{ die('SQL Error: ' . mysqli_error($conn) ); }
mysqli_close($conn);
?>


#receipt insert
<?php
$conn=mysqli_connect("db.sice.indiana.edu", "i494f19_team43","my+sql=i494f19_team43","i494f19_team43");

if (mysqli_connect_errno())
	{echo nl2br("Failed to connect to MySQL: " . mysqli_connect_error()."\n"); }
else 
	{ echo nl2br("Established Database Connection \n"); }
	

$var_title = mysqli_real_escape_string($conn, $_POST['title']);
$var_receiptDate = mysqli_real_escape_string($conn, $_POST['receiptDate']);
$var_address = mysqli_real_escape_string($conn, $_POST['address']);
$var_subTotal = mysqli_real_escape_string($conn, $_POST['subTotal']);
$var_total = mysqli_real_escape_string($conn, $_POST['total']);

$query="INSERT INTO report (title, receiptDate, address, subTotal, total, imagePath) 
VALUES ('$var_item','$var_price','$var_quantity')";
if (mysqli_query($conn, $query))
	{echo "1 record added";}
Else
	{ die('SQL Error: ' . mysqli_error($conn) ); }
mysqli_close($conn);
?>


#report insert
<?php
$conn=mysqli_connect("db.sice.indiana.edu", "i494f19_team43","my+sql=i494f19_team43","i494f19_team43");

if (mysqli_connect_errno())
	{echo nl2br("Failed to connect to MySQL: " . mysqli_connect_error()."\n"); }
else 
	{ echo nl2br("Established Database Connection \n"); }
	

$var_total = mysqli_real_escape_string($conn, $_POST['total']);
$var_reportDate = mysqli_real_escape_string($conn, $_POST['reportDate']);


$query="INSERT INTO report (total, reportDate) VALUES ('$var_total', '$var_reportDate')";
if (mysqli_query($conn, $query))
	{echo "1 record added";}
Else
	{ die('SQL Error: ' . mysqli_error($conn) ); }
mysqli_close($conn);
?>


#goal insert
<?php
$conn=mysqli_connect("db.sice.indiana.edu", "i494f19_team43","my+sql=i494f19_team43","i494f19_team43");

if (mysqli_connect_errno())
	{echo nl2br("Failed to connect to MySQL: " . mysqli_connect_error()."\n"); }
else 
	{ echo nl2br("Established Database Connection \n"); }
	

$var_setAmount = mysqli_real_escape_string($conn, $_POST['setAmount']);
$var_status = mysqli_real_escape_string($conn, $_POST['status']);

$query="INSERT INTO goal (setAmount, status) VALUES ('$var_setAmount', '$var_status')";
if (mysqli_query($conn, $query))
	{echo "1 record added";}
Else
	{ die('SQL Error: ' . mysqli_error($conn) ); }
mysqli_close($conn);
?>


#goal_details insert
<?php
$conn=mysqli_connect("db.sice.indiana.edu", "i494f19_team43","my+sql=i494f19_team43","i494f19_team43");

if (mysqli_connect_errno())
	{echo nl2br("Failed to connect to MySQL: " . mysqli_connect_error()."\n"); }
else 
	{ echo nl2br("Established Database Connection \n"); }
	

$var_dateStart = mysqli_real_escape_string($conn, $_POST['dateStart']);
$var_dateEnd = mysqli_real_escape_string($conn, $_POST['dateEnd']);

$query="INSERT INTO goal_details (dateStart, dateEnd) VALUES ('$var_dateStart', '$var_dateEnd')";
if (mysqli_query($conn, $query))
	{echo "1 record added";}
Else
	{ die('SQL Error: ' . mysqli_error($conn) ); }
mysqli_close($conn);
?>



