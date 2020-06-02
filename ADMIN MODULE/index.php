<?php
session_start();
if ($_SESSION['username'] != "adminbengkelin") {
    header("Location: login.php");
}
include 'conn.php';
$query = "SELECT * FROM user_booking a INNER JOIN user_car b ON a.idKendaraan = b.idKendaraan INNER JOIN user c on b.email = c.email INNER JOIN servis d ON a.idServis = d.idServis";
$result = mysqli_query($conn, $query);
function rupiah($angka)
{
    $hasil_rupiah = number_format($angka, 0, ',', '.');
    return $hasil_rupiah;
}
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
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bulma@0.8.0/css/bulma.min.css">
    <style>
        html,
        body {
            font-family: monospace;
        }
    </style>
</head>

<body>
    <table class="table is-bordered">
        <thead>
            <tr>
                <th>No</th>
                <th>ID Booking</th>
                <th>ID Kendaraan</th>
                <th>Jenis Servis</th>
                <th>Nama Pemilik</th>
                <th>Tanggal Servis</th>
                <th>Estimasi</th>
                <th>Status</th>
                <th>Total biaya</th>
                <th>Update / Rincian Servis</th>

            </tr>

            <?php
            $no = 1;
            while ($servis_data = mysqli_fetch_array($result)) {
                $idBooking = $servis_data['idBooking'];
                $status_kendaraan = $servis_data['status_kendaraan'];
                $estimasi = $servis_data['estimasi'];
                echo "<form action='update.php' method='post' name='edit'>";
                echo "<tr>";
                echo "<td>" . $no . "</td>";
                echo "<td>" . $idBooking . "</td>";
                echo "<td>" . $servis_data['idKendaraan'] . "</td>";
                echo "<td>" . $servis_data['jenisServis'] . "</td>";
                echo "<td>" . $servis_data['nama'] . "</td>";
                echo "<td>" . $servis_data['tanggal'] . "</td>";
                echo "<input class='input' name='idBooking' type='hidden' placeholder='Masukkan Estimasi' value=' $idBooking '>";
                echo "<td> <input class='input' name='estimasi' type='text' placeholder='Masukkan Estimasi' value=' $estimasi '> </td>";
                echo "<td> <input class='input' name='status_kendaraan' type='text' placeholder='Masukkan Status Kendaraan' value=' $status_kendaraan '> </td>";
                echo "<td>" . rupiah($servis_data['total_biaya']) . "</td>";
                echo "<td><button class='button is-warning' name='submit'>UPDATE</button> </form>| <a href='servis_detail.php?idBooking=$idBooking'><button class='button is-primary' name='servis_detail'>DETAIL</button></a> </td>";
                $no++;
            }
            ?>
    </table>
    <a href='logout.php'><button class='button is-danger' name='logout' style="float: right">LOGOUT</button></a>
    <a href='refresh.php'><button class='button is-primary' name='refresh' style="float: right">REFRESH</button></a>
    <script src="" async defer></script>
</body>

</html>