<?php
include 'conn.php';
// $idBooking = $_GET['idBooking'];
if (isset($_POST['submit'])) {
    $idBooking = $_POST['idBooking'];
    $estimasi = $_POST['estimasi'];
    $status_kendaraan = $_POST['status_kendaraan'];
    $query = "UPDATE user_booking SET estimasi = '$estimasi', status_kendaraan = '$status_kendaraan' WHERE idBooking = '$idBooking'";
    $result = mysqli_query($conn, $query);
    if ($result) {
        header('Location: index.php');
    } else {
        echo "NOT OKAY";
    }
}
