<?php
include 'conn.php';
if (isset($_POST['add'])) {
    $idBooking = $_POST['idBooking'];
    $detail = $_POST['detail'];
    $biaya = $_POST['biaya'];
    $query = "INSERT INTO servis_detail (idBooking, detail, biaya) VALUES ('$idBooking', '$detail', '$biaya')";
    $result = mysqli_query($conn, $query);
    if ($result) {
        header('Location: ' . $_SERVER['HTTP_REFERER']);
    } else {
        echo "NOT GOOD";
    }
}
