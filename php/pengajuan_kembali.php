<?php
    include"koneksi.php";
    $id_booking = $_POST['id_booking'];
    $status_booking = "Pengajuan pengembalian";
    $denda = $_POST['denda'];

    $sql = "UPDATE book set status_booking = '$status_booking', denda = '$denda',status_booking = '$status_booking' WHERE id_book = '$id_booking'";

    $result = array();
    if($conn->query($sql) === TRUE){
        $result['status'] = 0;
        $result['message'] = "Update success";
    }
    else{
        $result['status'] = 1;
        $result['message'] = "Error: ".$conn->error;
    }

    $conn->close();
    echo json_encode($result);

    
?>
