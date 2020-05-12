

<?php
$conn=mysqli_connect("db.sice.indiana.edu", "i494f19_team43","my+sql=i494f19_team43","i494f19_team43");

if (mysqli_connect_errno())
	{echo nl2br("Failed to connect to MySQL: " . mysqli_connect_error()."\n"); }



$var_categoryID = mysqli_real_escape_string($conn, $_POST['categoryID']);
$var_usersID = mysqli_real_escape_string($conn, $_POST['usersID']);
$var_title = mysqli_real_escape_string($conn, $_POST['title']);
$var_receiptDate = mysqli_real_escape_string($conn, $_POST['receiptDate']);
$var_street = mysqli_real_escape_string($conn, $_POST['street']);
$var_city = mysqli_real_escape_string($conn, $_POST['city']);
$var_st = mysqli_real_escape_string($conn, $_POST['st']);
$var_zipCode = mysqli_real_escape_string($conn, $_POST['zipCode']);
$var_total = mysqli_real_escape_string($conn, $_POST['total']);
$var_vendor = mysqli_real_escape_string($conn, $_POST['vendor']);
$var_imagePath = mysqli_real_escape_string($conn, $_POST['imagePath']);


$query="INSERT INTO receipt (categoryID, usersID, title, receiptDate, street, city, st, zipCode, total, vendor, imagePath) 
VALUES ('$var_categoryID', '$var_usersID', '$var_title', '$var_receiptDate', '$var_street', '$var_city', '$var_st', '$var_zipCode',
 '$var_total', '$var_vendor', '$var_imagePath')";
if (mysqli_query($conn, $query))
	{echo "1 record added";}
Else
	{ die('SQL Error: ' . mysqli_error($conn) ); }
mysqli_close($conn);
?>



