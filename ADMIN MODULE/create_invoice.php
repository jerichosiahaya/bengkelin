<?php
session_start();
if ($_SESSION['username'] != "adminbengkelin") {
    header("Location: login.php");
}
include 'conn.php';
$idBooking = $_GET['idBooking'];
?>

<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<!--<![endif]-->

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>cPanel | Bengkelin</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="">
    <style>
        html,
        body {
            font-family: monospace;
        }
    </style>
</head>

<body>
    <p>Create Invoice for booking: <b> <?php echo $idBooking ?> </b> </p>
    <form action="create_invoice_proses.php">
        <label for="pembayaran">Pilih pembayaran:</label>
        <select id="pembayaran" name="pembayaran">
            <option value="1">Tunai</option>
            <option value="2">Non-Tunai</option>
        </select>
        <p>
            <label for="status">Status Kendaraan</label>
            <select id="status" name="status">
                <option value="Pending">Pending</option>
                <option value="Dibayar">Dibayar</option>
            </select>
            <p>
                <input type="hidden" name="idBooking" value="<?php echo $idBooking ?>">
                <input type="submit" value="Submit">
    </form>
    <script src="" async defer></script>
</body>

</html>