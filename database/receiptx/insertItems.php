

<?php
$conn=mysqli_connect("db.sice.indiana.edu", "i494f19_team43","my+sql=i494f19_team43","i494f19_team43");

if (mysqli_connect_errno())
	{echo nl2br("Failed to connect to MySQL: " . mysqli_connect_error()."\n"); }

	
$var_receiptID = mysqli_real_escape_string($conn, $_POST['receiptID']);
$var_item = mysqli_real_escape_string($conn, $_POST['item']);
$var_price = mysqli_real_escape_string($conn, $_POST['price']);


$query="INSERT INTO items (receiptID, item, price) VALUES ('$var_receiptID', '$var_item', '$var_price')";
if (mysqli_query($conn, $query))
	{echo "1 record added";}
Else
	{ die('SQL Error: ' . mysqli_error($conn) ); }
mysqli_close($conn);
?>
