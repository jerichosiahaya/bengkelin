<?php

header('Content-type:application/json;chartset=utf-8');
include "conn.php";

if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    $idBooking = $_POST['idBooking'];

    $q = mysqli_query($conn, "SELECT * FROM servis_detail WHERE idBooking = '$idBooking'");

    $response = array();

    if (mysqli_num_rows($q) > 0) {
        $response["data"] = array();
        while ($r = mysqli_fetch_array($q)) {
            $servis = array();
            $servis["idBooking"] = $r["idBooking"];
            $servis["detail"] = $r["detail"];
            $servis["biaya"] = $r["biaya"];

            array_push($response["data"], $servis);
        }

        $response["success"] = 1;
        $response["message"] = "Data berhasil dibaca";
        echo json_encode($response, JSON_PRETTY_PRINT);
    } else {
        $response["success"] = 0;
        $response["message"] = "Tidak ada data";
        echo json_encode($response, JSON_PRETTY_PRINT);
    }
}
