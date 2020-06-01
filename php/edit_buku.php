<?php
include "koneksi.php";

$id_admin = $_POST['id_admin'];
$judul = $_POST['judul'];
$sinopsis = $_POST['sinopsis'];
$pengarang = $_POST['pengarang'];
$tanggalrilis = $_POST['tanggal'];
$gambar = '';
$id_buku = $_POST['id_buku'];

if (isset($_POST['gambar']) && !empty($_POST['gambar'])) {
	$gambar = $_POST['gambar'];
}

$sql = "UPDATE buku set id_admin = $id_admin , judul_buku = '$judul' , pengarang_buku = '$pengarang' , sinopsis_buku = '$sinopsis' ,foto_buku = '$gambar' , tanggal_rilisbuku = '$tanggalrilis' 
        WHERE id_buku = '$id_buku' ";

$result = array();

if ($conn->query($sql) === TRUE) {
	$result['status'] = 0;
	$result['message'] = "Edit sukses";
}
else {
	$result['status'] = 1;
	$result['message'] = "Error: ".$conn->error;
}

$conn->close();
echo json_encode($result);
?>
