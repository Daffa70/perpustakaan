<?php
    include "koneksi.php";
    
    $id_buku  = $_POST['id_buku'];
    $id_anggota = $_POST['id_anggota'];
    $tanggal_pinjam = $_POST['tanggal_pinjam'];

    $sql = "SELECT * FROM book WHERE id_anggota = $id_anggota AND status_booking = 'dibooking' ";

    $query = $conn->query($sql);

    $result = array();
    if ($query->num_rows <= 2) {
        $sql1 = "INSERT INTO book (id_buku,id_anggota,tanggal_pinjam, status_booking) VALUES ($id_buku, $id_anggota, '$tanggal_pinjam', 'dibooking')";

        if ($conn->query($sql1) === TRUE) {
            $result['status'] = 0;
            $result['message'] = "Booking sukses";
        }
        else{
            $result['status'] = 1;
            $result['message'] = "Booking error".$conn->error;
        }
    }
    else{
        $result['status'] = 2;
        $result['message'] = "Anda sudah melakukan limit peminjaman";
    }
    $conn->close();
    echo json_encode($result);
?>