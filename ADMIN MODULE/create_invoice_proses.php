<?php
session_start();
if ($_SESSION['username'] != "adminbengkelin") {
    header("Location: login.php");
}
include 'conn.php';
$idBooking = $_GET['idBooking'];
$idPembayaran = $_GET['pembayaran'];
$statusTransaksi = $_GET['status'];
$query = "INSERT INTO transaksi (idPembayaran, idBooking, statusTransaksi, biaya) VALUES ('$idPembayaran', '$idBooking', '$statusTransaksi', (SELECT SUM(biaya) FROM servis_detail WHERE servis_detail.idBooking = $idBooking GROUP BY idBooking))";
$result = mysqli_query($conn, $query);
if ($result) {
    header("Location: invoice.php?idBooking=$idBooking");
} else {
    echo "BAD REQUEST";
    echo("Error description: " . mysqli_error($conn));
}
