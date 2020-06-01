<?php
    include "koneksi.php";
    $id = $_POST['id_book'];
    $status = "Sudah Kembali";

    $sql = "UPDATE book set status_booking = '$status' WHERE id_book = '$id'";

    $result = array();
    if($conn->query($sql) === TRUE){
        $result['status'] = 0;
        $result['message'] = "Update Succses";
    }
    else{
        $result['status'] = 1;
        $result['message'] = "Error: ".$conn->error;
    }
    $conn->close();
    echo json_encode($result);
?>
