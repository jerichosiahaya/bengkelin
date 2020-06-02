<?php
session_start();
if ($_SESSION['username'] != "adminbengkelin") {
    header("Location: login.php");
}
include 'conn.php';
$idBooking = $_GET['idBooking'];
// echo $idBooking;
$query = "SELECT * FROM servis_detail WHERE servis_detail.idBooking = $idBooking";
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
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bulma@0.8.0/css/bulma.min.css">

    <style>
        html,
        body {
            font-family: monospace;
            margin: 1em;
        }

        .mod {
            width: 30%;
        }
    </style>

</head>

<body>
    <a href="index.php">
        <p style="margin-bottom: 1em;">← Back to home</p>
    </a>
    <h1> Nomor booking: <b><?php echo $idBooking ?></b></h1>
    <table class="table is-bordered">
        <thead>
            <tr>
                <th>No</th>
                <th>Detail</th>
                <th>Biaya</th>
            </tr>

            <?php
            $no = 1;
            while ($servis_data = mysqli_fetch_array($result)) {
                // $idBooking = $servis_data['idBooking'];
                // $status_kendaraan = $servis_data['status_kendaraan'];
                // $estimasi = $servis_data['estimasi'];
                //  echo "<form action='update.php' method='post' name='edit'>";
                echo "<tr>";
                echo "<td>" . $no . "</td>";
                echo "<td>" . $servis_data['detail'] . "</td>";
                echo "<td>" . rupiah($servis_data['biaya']) . "</td>";
                // echo "<td>" . $servis_data['nama'] . "</td>";
                // echo "<td>" . $servis_data['tanggal'] . "</td>";
                // echo "<input class='input' name='idBooking' type='hidden' placeholder='Masukkan Estimasi' value=' $idBooking '>";
                // echo "<td> <input class='input' name='estimasi' type='text' placeholder='Masukkan Estimasi' value=' $estimasi '> </td>";
                // echo "<td> <input class='input' name='status_kendaraan' type='text' placeholder='Masukkan Status Kendaraan' value=' $status_kendaraan '> </td>";
                //  echo "<td><button class='button is-warning' name='submit'>UPDATE</button> </form>| <a href='servis_detail.php?idBooking=$idBooking'><button class='button is-primary' name='servis_detail'>DETAIL</button></a> </td>";
                $no++;
            }
            ?>
    </table>
    <h1>Tambah servis detail</h1>
    <form class="form mod" action='add_servis_detail.php' method='post' name='add'>
        <input class='input' name='idBooking' type='hidden' placeholder='Masukkan Estimasi' value=' <?php echo $idBooking ?>'>
        <input class='input' name='detail' type='text' placeholder='Masukkan Detail'>
        <input class='input' name='biaya' type='text' placeholder='Masukkan Biaya' style="margin-top: 1em;">
        <button class='button is-success' name='add' style="margin-top: 1em; margin-bottom: 1em;">SUBMIT</button>
    </form>
    <?php
    $query2 = "SELECT * FROM transaksi WHERE transaksi.idBooking = '$idBooking'";
    $result2 = mysqli_query($conn, $query2);
    if ($result2) {
        echo '<a href="check_invoice.php?idBooking=' . $idBooking . '"> Cek Invoice ID Booking ' . $idBooking . ' →</a>';
        // echo "<button class='button is-success' name='add' style= 'margin-bottom: 1em;'>SUBMIT</button>";
    }
    ?>
    <script src="" async defer></script>
</body>

</html>