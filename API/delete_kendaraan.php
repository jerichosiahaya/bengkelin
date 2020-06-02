<?php
header('Content-type:application/json;chartset=utf-8');
include "conn.php";


if (isset($_POST['idKendaraan']) && isset($_POST['jenisKendaraan']) && isset($_POST['tipeKendaraan']) && isset($_POST['tahunKeluaran'])) {
    $idKendaraan = $_POST['idKendaraan'];
    $jenisKendaraan = $_POST['jenisKendaraan'];
    $tipeKendaraan = $_POST['tipeKendaraan'];
    $tahunKeluaran = $_POST['tahunKeluaran'];

  $q = mysqli_query($conn, "DELETE FROM user_car WHERE idKendaraan = $idKendaraan ");
  
  $response = array();

   if ($q) {
        $response["success"] = 1;
        $response["message"] = "Data sukses dihapus";
        echo json_encode($response);
    } else {
        $response["success"] = 0;
        $response["message"] = "Data gagal dihapus";
        echo json_encode($response);
    }
} else {
    $response["success"] = -1;
    $response["message"] = "Data kosong";
    echo json_encode($response);
}
