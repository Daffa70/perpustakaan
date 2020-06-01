<?php
include "koneksi.php";

$username = $_POST['username'];
$password = $_POST['password'];

$sql = "SELECT * FROM admin WHERE username = '$username' AND password = '$password'";

$query = $conn->query($sql);

$result = array();
if ($query->num_rows > 0) {
	$row = $query->fetch_assoc();
	$result['status'] = 0;
	$result['message'] = "Login sukses";
	$result['data'] = $row['id_admin'];
}
else {
	$result['status'] = 1;
	$result['message'] = "Login gagal";
}

$conn->close();
echo json_encode($result);