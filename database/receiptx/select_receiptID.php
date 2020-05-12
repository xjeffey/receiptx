<?php
$conn=mysqli_connect("db.sice.indiana.edu", "i494f19_team43","my+sql=i494f19_team43","i494f19_team43");

if (mysqli_connect_errno())
	{echo nl2br("Failed to connect to MySQL: " . mysqli_connect_error()."\n"); }


$var_email = mysqli_real_escape_string($conn, $_POST['email']);
$var_title = mysqli_real_escape_string($conn, $_POST['title']);
$var_categoryID = mysqli_real_escape_string($conn, $_POST['categoryID']);
$var_receiptDate = mysqli_real_escape_string($conn, $_POST['receiptDate']);
$var_total = mysqli_real_escape_string($conn, $_POST['total']);
$var_street = mysqli_real_escape_string($conn, $_POST['street']);
$var_city = mysqli_real_escape_string($conn, $_POST['city']);
$var_st = mysqli_real_escape_string($conn, $_POST['st']);
$var_zipCode = mysqli_real_escape_string($conn, $_POST['zipCode']);
$var_vendor = mysqli_real_escape_string($conn, $_POST['vendor']);

$sql = "SELECT r.receiptID FROM receipt as r, users as u
WHERE r.usersID = u.usersID AND r.title = '$var_title' AND r.categoryID = '$var_categoryID' AND r.receiptDate = '$var_receiptDate' AND
r.total = '$var_total' AND r.street = '$var_street' AND r.city = '$var_city' AND r.st = '$var_st' AND
r.zipCode = '$var_zipCode' AND r.vendor = '$var_vendor' AND u.email = '$var_email'";


$result = mysqli_query($conn, $sql);
$json_array['receiptID'] = array();

if($result->num_rows > 0){
    while($row = mysqli_fetch_assoc($result))
    {
        array_push($json_array['receiptID'], $row);
        
        
    }
    echo json_encode($json_array);
}
else{
    echo "0 results";
}

	
// Close Connection
mysqli_close($conn);
?>

