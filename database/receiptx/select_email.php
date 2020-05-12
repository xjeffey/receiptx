
<?php
$conn=mysqli_connect("db.sice.indiana.edu", "i494f19_team43","my+sql=i494f19_team43","i494f19_team43");
if (mysqli_connect_errno())
	{echo nl2br("Failed to connect to MySQL: " . mysqli_connect_error()."\n"); }
	
$sql = "SELECT email FROM users";
$result = mysqli_query($conn, $sql);
$json_array['emails'] = array();

if($result->num_rows > 0){
    while($row = mysqli_fetch_assoc($result))
    {
        if($row['email'] != ""){
            array_push($json_array['emails'], $row);
        }
        
    }
    echo json_encode($json_array);
}
else{
    echo "0 results";
}

	
// Close Connection
mysqli_close($conn);
?>