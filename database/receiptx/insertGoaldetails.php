
<?php
$conn=mysqli_connect("db.sice.indiana.edu", "i494f19_team43","my+sql=i494f19_team43","i494f19_team43");

if (mysqli_connect_errno())
	{echo nl2br("Failed to connect to MySQL: " . mysqli_connect_error()."\n"); }

$var_goalID = mysqli_real_escape_string($conn, $_POST['goalID']);
$var_usersID = mysqli_real_escape_string($conn, $_POST['usersID']);
$var_dateStart = mysqli_real_escape_string($conn, $_POST['dateStart']);
$var_dateEnd = mysqli_real_escape_string($conn, $_POST['dateEnd']);

$query="INSERT INTO goal_details (goalID, usersID, dateStart, dateEnd) VALUES ('$var_goalID','$var_usersID', '$var_dateStart', '$var_dateEnd')";
if (mysqli_query($conn, $query))
	{echo "1 record added";}
Else
	{ die('SQL Error: ' . mysqli_error($conn) ); }
mysqli_close($conn);
?>

