<?php

include 'koneksi.php';
function dateDiffInDays($date1, $date2)  
{ 
    // Calulating the difference in timestamps 
    $diff = strtotime($date2) - strtotime($date1); 
      
    // 1 day = 24 hours 
    // 24 * 60 * 60 = 86400 seconds 
    return abs(round($diff / 86400)); 
} 
if(isset($_GET["id_anggota"]))
	$id_anggota = $_GET["id_anggota"];


$sql = "SELECT b.*, a.nama_anggota, k.judul_buku, k.foto_buku FROM book b JOIN anggota a ON b.id_anggota = a.id_anggota JOIN buku k ON b.id_buku = k.id_buku WHERE 1=1";

if (isset($id_anggota))
	$sql .= " AND b.id_anggota = $id_anggota";


$query = $conn->query($sql);

$result = array();
if ($query) {
	$list = array();
	while ($row = $query->fetch_assoc()) {
		$tanggal_pinjam = $row['tanggal_pinjam'];


		$dateDiff = dateDiffInDays(date("Y-m-d H:i:s"), $tanggal_pinjam); 

		if ($dateDiff < 4){
			$dendaHari = 0;
		}
		else{
			$dateDiff = $dateDiff - 4;
			$dendaHari = $dateDiff * 500;
		}
		$booking['id_book'] = $row['id_book'];
		$booking['id_buku'] = $row['id_buku'];
		$booking['id_anggota'] = $row['id_anggota'];
		$booking['nama_anggota'] = $row['nama_anggota'];
		$booking['judul_buku'] = $row['judul_buku'];
		$booking['tanggal_pinjam'] = $row['tanggal_pinjam'];
		$booking['tanggal_proses'] = $row['tanggal_proses'];
		$booking['denda'] = $dendaHari;
		$booking['status_booking'] = $row['status_booking'];
		$booking['foto_buku'] = $row['foto_buku'];
  
		array_push($list, $booking);
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