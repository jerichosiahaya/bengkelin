<?php
session_start();
if ($_SESSION['username'] != "adminbengkelin") {
    header("Location: login.php");
}
include 'conn.php';
$idBooking = $_GET['idBooking'];
$query = "SELECT * FROM transaksi WHERE transaksi.idBooking = '$idBooking'";
$result = mysqli_query($conn, $query);
if (mysqli_num_rows($result) == 1) {
    header("Location: invoice.php?idBooking=$idBooking");
} else {
    header("Location: create_invoice.php?idBooking=$idBooking");
}
