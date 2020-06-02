<?php

header('Content-type:application/json;chartset=utf-8');
include "conn.php";

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $email = $_POST['email'];


    $q = mysqli_query(
        $conn,
        "SELECT user_booking.idBooking, user_car.tipeKendaraan, servis.jenisServis, user_booking.pickup,user_booking.tanggal, user_booking.estimasi, user_booking.total_biaya
    FROM user_booking 
    JOIN user_car ON user_booking.idKendaraan = user_car.idKendaraan 
    JOIN servis ON user_booking.idServis = servis.idServis 
    WHERE user_car.email = '$email'"
    );

    $response = array();

    if (mysqli_num_rows($q) > 0) {
        $response["data"] = array();
        while ($r = mysqli_fetch_array($q)) {
            $kendaraan = array();
            $kendaraan["idBooking"] = $r["idBooking"];
            $kendaraan["tipeKendaraan"] = $r["tipeKendaraan"];
            $kendaraan["jenisServis"] = $r["jenisServis"];
            $kendaraan["pickup"] = $r["pickup"];
            $kendaraan["tanggal"] = $r["tanggal"];
            $kendaraan["estimasi"] = $r["estimasi"];
            $kendaraan["total_biaya"] = $r["total_biaya"];
            $kendaraan["email"] = $r["email"];

            array_push($response["data"], $kendaraan);
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
