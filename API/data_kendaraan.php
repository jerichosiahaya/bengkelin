<?php
header('Content-type:application/json;chartset=utf-8');
include "conn.php";
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $email = $_POST['email'];

    $q = mysqli_query($conn, "SELECT * FROM user_car WHERE email='$email'");
    $response = array();

    if (mysqli_num_rows($q) > 0) {
        $response["data"] = array();
        while ($r = mysqli_fetch_array($q)) {
            $kendaraan = array();
            $kendaraan["idKendaraan"] = $r["idKendaraan"];
            $kendaraan["jenisKendaraan"] = $r["jenisKendaraan"];
            $kendaraan["tipeKendaraan"] = $r["tipeKendaraan"];
            $kendaraan["tahunKeluaran"] = $r["tahunKeluaran"];
            $kendaraan["email"] = $r["email"];
            array_push($response["data"], $kendaraan);
        }
        $response["success"] = 1;
        $response["message"] = "Data berhasil dibaca";
        echo json_encode($response, JSON_PRETTY_PRINT); # Saya tambah JSON_PRETTY_PRINT supaya formatnya bagus
    } else {
        $response["success"] = 0;
        $response["message"] = "Tidak ada data";
        echo json_encode($response, JSON_PRETTY_PRINT); # Saya tambah JSON_PRETTY_PRINT supaya formatnya bagus
    }
}
