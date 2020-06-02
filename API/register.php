<?php
header('Content-type:application/json;chartset=utf-8');
include "conn.php";

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
  $nohp = $_POST['noHp'];
  $email = $_POST['email'];
  $nama = $_POST['nama'];
  $alamat = $_POST['alamat'];
  $password = $_POST['password'];
  //$password = password_hash($password, PASSWORD_DEFAULT);

  $query = "INSERT INTO user (noHp, email ,nama, alamat, password) VALUES ('$nohp','$email','$nama','$alamat','$password')";
  $q = mysqli_query($conn, $query);
  $response = array();

  if ($q) {
    $response["success"] = 1;
    $response["message"] = "Data berhasil ditambah";
    echo json_encode($response);
  } else {
    $response["success"] = 0;
    $response["message"] = "Data gagal ditambah";
    echo json_encode($response);
  }
} else {
  $response["success"] = -1;
  $response["message"] = "Data kosong";
  echo json_encode($response);
}
