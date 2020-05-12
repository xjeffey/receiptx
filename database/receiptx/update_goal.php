<?php
$conn=mysqli_connect("db.sice.indiana.edu", "i494f19_team43","my+sql=i494f19_team43","i494f19_team43");

if (mysqli_connect_errno())
	{echo nl2br("Failed to connect to MySQL: " . mysqli_connect_error()."\n"); }


$var_goalID = mysqli_real_escape_string($conn, $_POST['goalID']);
$var_setAmount = mysqli_real_escape_string($conn, $_POST['setAmount']);
$var_status = mysqli_real_escape_string($conn, $_POST['status']);


$sql = "UPDATE goal
SET status = '$var_status'
WHERE setAmount = '$var_setAmount' AND goalID = '$var_goalID'";
if (mysqli_query($conn, $sql))
	{echo "record updated";}
Else
	{ die('SQL Error: ' . mysqli_error($conn) ); }
mysqli_close($conn);
?>
