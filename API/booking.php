<?php
header('Content-type:application/json;chartset=utf-8');
include "conn.php";

if (isset($_POST['idBooking']) && isset($_POST['idKendaraan']) && isset($_POST['idServis']) && isset($_POST['tanggal']) && isset($_POST['pickup'])) {

    $idBooking = $_POST['idBooking'];
    $idKendaraan = $_POST['idKendaraan'];
    $idServis = $_POST['idServis'];
    $tanggal = $_POST['tanggal'];
    $pickup = $_POST['pickup'];

    $q = mysqli_query($conn, "INSERT INTO user_booking (idBooking, idKendaraan, idServis, tanggal, pickup) VALUES ('$idBooking', '$idKendaraan', '$idServis', '$tanggal', '$pickup')");
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
