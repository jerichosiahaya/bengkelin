<?php
header('Content-type:application/json;chartset=utf-8');
include "conn.php";

if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    $jenisKendaraan = $_POST['jenisKendaraan'];
    $tipeKendaraan = $_POST['tipeKendaraan'];
    $tahunKeluaran = $_POST['tahunKeluaran'];
    $email = $_POST['email'];

    $q = mysqli_query($conn, "INSERT INTO user_car (jenisKendaraan, tipeKendaraan, tahunKeluaran, email) VALUES ('$jenisKendaraan', '$tipeKendaraan', '$tahunKeluaran', '$email')");
    $response = array();

    if ($q) {
        $response["success"] = 1;
        $response["message"] = "Data sukses ditambah";
        echo json_encode($response);
    } else {
        $response["success"] = 0;
        $response["message"] = "Data gagal ditambah";
        echo json_encode($response);
    }
} else {
    $response["success"] = 1;
    $response["message"] = "Data kosong";
    echo json_encode($response);
}
