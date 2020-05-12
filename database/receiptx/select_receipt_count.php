<?php
$conn=mysqli_connect("db.sice.indiana.edu", "i494f19_team43","my+sql=i494f19_team43","i494f19_team43");

if (mysqli_connect_errno())
	{echo nl2br("Failed to connect to MySQL: " . mysqli_connect_error()."\n"); }


$var_email = mysqli_real_escape_string($conn, $_POST['email']);

$sql = "SELECT COUNT(r.receiptID) AS receiptsEntered
FROM receipt AS r, users AS u
WHERE r.usersID = u.usersID
AND u.email = '$var_email'";


$result = mysqli_query($conn, $sql);
$json_array['count'] = array();

if($result->num_rows > 0){
    while($row = mysqli_fetch_assoc($result))
    {
        array_push($json_array['count'], $row);
    }
    echo json_encode($json_array);
}
else{
    echo "0 results";
}

	
// Close Connection
mysqli_close($conn);
?>


