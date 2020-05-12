<?php
$conn=mysqli_connect("db.sice.indiana.edu", "i494f19_team43","my+sql=i494f19_team43","i494f19_team43");

if (mysqli_connect_errno())
	{echo nl2br("Failed to connect to MySQL: " . mysqli_connect_error()."\n"); }

$var_email = mysqli_real_escape_string($conn, $_POST['email']);
$var_vendor = mysqli_real_escape_string($conn, $_POST['vendor']);

$sql = "SELECT i.item, r.receiptID, r.title, r.categoryID, r.receiptDate, r.total, r.street, r.city, r.st, r.zipCode, r.vendor, r.imagePath
FROM receipt AS r, items AS i, users AS u
WHERE u.email = '$var_email'
AND r.usersID = u.usersID
AND r.receiptID = i.receiptID
AND r.vendor LIKE '$var_vendor';";
$result = mysqli_query($conn, $sql);
$json_array['vendorSearch'] = array();

if($result->num_rows > 0){
    while($row = mysqli_fetch_assoc($result))
    {
        array_push($json_array['vendorSearch'], $row);
        
        
    }
    echo json_encode($json_array);
}
else{
    echo "0 results";
}

	
// Close Connection
mysqli_close($conn);
?>

