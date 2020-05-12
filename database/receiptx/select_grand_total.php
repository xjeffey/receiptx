<?php
$conn=mysqli_connect("db.sice.indiana.edu", "i494f19_team43","my+sql=i494f19_team43","i494f19_team43");

if (mysqli_connect_errno())
	{echo nl2br("Failed to connect to MySQL: " . mysqli_connect_error()."\n"); }


$var_email = mysqli_real_escape_string($conn, $_POST['email']);
$var_month = mysqli_real_escape_string($conn, $_POST['month']);
$var_year = mysqli_real_escape_string($conn, $_POST['year']);

$sql = "SELECT SUM(r.total) AS monthTotal
FROM receipt as r, users as u, category as c
WHERE u.email = '$var_email' AND c.categoryID = r.categoryID and u.usersID = r.usersID
AND month(receiptDate) = '$var_month' and year(receiptDate) = '$var_year'
GROUP BY month(receiptDate) AND year(receiptDate)";


$result = mysqli_query($conn, $sql);
$json_array['monthlyTotal'] = array();

if($result->num_rows > 0){
    while($row = mysqli_fetch_assoc($result))
    {
        array_push($json_array['monthlyTotal'], $row);
        
        
    }
    echo json_encode($json_array);
}
else{
    echo "0 results";
}

	
// Close Connection
mysqli_close($conn);
?>


