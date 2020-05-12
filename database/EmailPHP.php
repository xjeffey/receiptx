#Find a way to insert a given email into the user table if it does not exist in it. 
<?php

$conn=mysqli_connect("db.sice.indiana.edu","i494f19_team43","my+sql=i494f19_team43", "i494f19_team43");

if (!$conn)
	{die("Failed to connect to MySQL: " . $conn->connect_error); }
else 
	{ echo "Established Database Connection <br>" ;}

// Grab Results
$var_email = mysqli_real_escape_string($con, $_POST['Email']);
echo 'var '.$var_email;
// Create SQL Statement
$sql = "INSERT INTO users(email) VALUES('$var_email')";
$result = mysqli_query($con, $sql);

// Display Results
if (!$result)
	{echo "1 record added";}
	
else
	{ die('SQL Error: ' .mysqli_error($conn));}
	
mysqli_close($conn);
?>

