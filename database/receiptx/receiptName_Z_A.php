<?php
$conn=mysqli_connect("db.sice.indiana.edu", "i494f19_team43","my+sql=i494f19_team43","i494f19_team43");

if (mysqli_connect_errno())
	{echo nl2br("Failed to connect to MySQL: " . mysqli_connect_error()."\n"); }


$var_email = mysqli_real_escape_string($conn, $_POST['email']);

$sql = "SELECT r.receiptID, r.title, c.categoryName, r.receiptDate, r.total, r.street, r.city, r.st, r.zipCode, r.vendor, r.imagePath
FROM receipt as r, items as i, users as u, category as c
WHERE c.categoryID = r.categoryID AND r.usersID = u.usersID AND
u.email = '$var_email'
GROUP BY r.receiptID
ORDER BY r.title DESC";


$result = mysqli_query($conn, $sql);
$json_array['allReceipt'] = array();

if($result->num_rows > 0){
    while($row = mysqli_fetch_assoc($result))
    {
        array_push($json_array['allReceipt'], $row);
        
        
    }
    echo json_encode($json_array);
}
else{
    echo "0 results";
}

	
// Close Connection
mysqli_close($conn);
?>
