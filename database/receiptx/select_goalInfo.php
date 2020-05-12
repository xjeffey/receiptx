<?php
$conn=mysqli_connect("db.sice.indiana.edu", "i494f19_team43","my+sql=i494f19_team43","i494f19_team43");

if (mysqli_connect_errno())
	{echo nl2br("Failed to connect to MySQL: " . mysqli_connect_error()."\n"); }

$var_email = mysqli_real_escape_string($conn, $_POST['email']);
$var_date = mysqli_real_escape_string($conn, $_POST['date']);

$sql = "SELECT g.setAmount, g.status, g.goalID
FROM goal as g, users as u, goal_details as gd
WHERE u.usersID = g.usersID AND g.goalID = gd.goalID AND u.email = '$var_email' 
AND (YEAR(gd.dateStart) = YEAR('$var_date') AND MONTH(gd.dateStart) = MONTH('$var_date'))";


$result = mysqli_query($conn, $sql);
$json_array['goalID'] = array();

if($result->num_rows > 0){
    while($row = mysqli_fetch_assoc($result))
    {
        array_push($json_array['goalID'], $row);
        
        
    }
    echo json_encode($json_array);
}
else{
    echo "0 results";
}

	
// Close Connection
mysqli_close($conn);
?>