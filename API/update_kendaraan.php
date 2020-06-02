<?php
header('Content-type:application/json;chartset=utf-8');
include "conn.php";

if (isset($_POST['idKendaraan']) && isset($_POST['jenisKendaraan']) && isset($_POST['tipeKendaraan']) && isset($_POST['tahunKeluaran'])) {
    $idKendaraan = $_POST['idKendaraan'];
    $jenisKendaraan = $_POST['jenisKendaraan'];
    $tipeKendaraan = $_POST['tipeKendaraan'];
    $tahunKeluaran = $_POST['tahunKeluaran'];

    $query = "UPDATE user_car SET jenisKendaraan='$jenisKendaraan', tipeKendaraan='$tipeKendaraan', tahunKeluaran='$tahunKeluaran' WHERE idKendaraan ='$idKendaraan'";
    $q = mysqli_query($conn, $query);
    $response = array();

    if ($q) {
        $response["success"] = 1;
        $response["message"] = "Data sukses diupdate";
        echo json_encode($response);
    } else {
        $response["success"] = 0;
        $response["message"] = "Data gagal diupdate";
        echo json_encode($response);
    }
} else {
    $response["success"] = -1;
    $response["message"] = "Data kosong";
    echo json_encode($response);
}
