<?php
include "koneksi.php";

$nama = $_POST['nama'];
$username = $_POST['username'];
$password = $_POST['password'];
$alamat = $_POST['alamat'];
$hp = $_POST['hp'];

$sql = "INSERT INTO anggota (nama_anggota, username, password, alamat, hp) VALUES ('$nama', '$username', md5('$password'), '$alamat', '$hp')";

$result = array();

if ($conn->query($sql) === TRUE) {
	$result['status'] = 0;
	$result['message'] = "Registrasi berhasil";
}
else {
	$result['status'] = 1;
	$result['message'] = "Error: ".$conn->error;
}

$conn->close();
echo json_encode($result);