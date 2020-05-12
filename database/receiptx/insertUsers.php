
<?php
$conn=mysqli_connect("db.sice.indiana.edu", "i494f19_team43","my+sql=i494f19_team43","i494f19_team43");

if (mysqli_connect_errno())
	{echo nl2br("Failed to connect to MySQL: " . mysqli_connect_error()."\n"); }


$var_email = mysqli_real_escape_string($conn, $_POST['email']);

$query="INSERT INTO users (email) VALUES ('$var_email')";
if (mysqli_query($conn, $query))
	{echo "1 record added";}
Else
	{ die('SQL Error: ' . mysqli_error($conn) ); }
mysqli_close($conn);
?>
