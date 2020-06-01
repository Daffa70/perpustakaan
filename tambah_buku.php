<?php
include "koneksi.php";

$id_admin = $_POST['id_admin'];
$judul = $_POST['judul'];
$sinopsis = $_POST['sinopsis'];
$pengarang = $_POST['pengarang'];
$tanggalrilis = $_POST['tanggal'];
$gambar = '';

if (isset($_POST['gambar']) && !empty($_POST['gambar'])) {
	$gambar = $_POST['gambar'];
}

$sql = "INSERT INTO buku (id_admin, judul_buku, pengarang_buku, sinopsis_buku ,foto_buku , tanggal_rilisbuku) VALUES ($id_admin, '$judul', '$pengarang', '$sinopsis' ,'$gambar' , '$tanggalrilis')";

$result = array();

if ($conn->query($sql) === TRUE) {
	$result['status'] = 0;
	$result['message'] = "Tambah Buku sukses";
}
else {
	$result['status'] = 1;
	$result['message'] = "Error: ".$conn->error;
}

$conn->close();
echo json_encode($result);