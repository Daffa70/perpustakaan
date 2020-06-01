<?php

include 'koneksi.php';

if(isset($_GET["id_penulis"]))
	$id_penulis = $_GET["id_penulis"];
if(isset($_GET["isi"]))
	$isi = $_GET["isi"];
if(isset($_GET["tgl_awal"]))
	$tgl_awal = $_GET["tgl_awal"];
if (isset($_GET["tgl_akhir"]))
	$tgl_akhir = $_GET["tgl_akhir"];

$sql = "SELECT * FROM buku";


$query = $conn->query($sql);

$result = array();
if ($query) {
	$list = array();
	while ($row = $query->fetch_assoc()) {
		$buku['id'] = $row['id_buku'];
		$buku['nama_pengarang'] = $row['pengarang_buku'];
		$buku['judul'] = $row['judul_buku'];
		$buku['sinopsis_buku'] = $row['sinopsis_buku'];
        $buku['foto_buku'] = $row['foto_buku'];
        $buku['tanggal_rilisbuku'] = $row['tanggal_rilisbuku'];
		$buku['created_at'] = $row['tanggal_tambahbuku'];

		array_push($list, $buku);
	}
	$result['status'] = 0;
	$result['message'] = "Success";
	$result['data'] = $list;
} else {
	$result['status'] = 1;
	$result['message'] = "0 result";
}

$conn->close();
echo json_encode($result);
