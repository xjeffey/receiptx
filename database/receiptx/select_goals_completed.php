<?php
$conn=mysqli_connect("db.sice.indiana.edu", "i494f19_team43","my+sql=i494f19_team43","i494f19_team43");

if (mysqli_connect_errno())
	{echo nl2br("Failed to connect to MySQL: " . mysqli_connect_error()."\n"); }


$var_email = mysqli_real_escape_string($conn, $_POST['email']);

$sql = "SELECT COUNT(gl.goalID) AS goalsCompleted
FROM goal AS gl, goal_details AS gd, users as u
WHERE gl.goalID = gd.goalID
AND u.usersID = gl.usersID
AND u.usersID = '$var_email'
AND gl.status = 'Success'";


$result = mysqli_query($conn, $sql);
$json_array['completed'] = array();

if($result->num_rows > 0){
    while($row = mysqli_fetch_assoc($result))
    {
        array_push($json_array['completed'], $row);
    }
    echo json_encode($json_array);
}
else{
    echo "0 results";
}

	
// Close Connection
mysqli_close($conn);
?>


