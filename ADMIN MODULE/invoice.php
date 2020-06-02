<?php
session_start();
if ($_SESSION['username'] != "adminbengkelin") {
    header("Location: login.php");
}
include 'conn.php';
$idBooking = $_GET['idBooking'];
$query = "SELECT transaksi.idTransaksi, transaksi.idBooking, transaksi.biaya, transaksi.statusTransaksi, pembayaran.metodePembayaran, user_booking.idKendaraan, user_booking.tanggal, user_booking.estimasi, user_booking.status_kendaraan, user_car.jenisKendaraan, user_car.tipeKendaraan, user_car.tahunKeluaran, user.nama, user.alamat, user.email, user.noHp FROM transaksi JOIN pembayaran ON transaksi.idPembayaran = pembayaran.idPembayaran JOIN user_booking ON transaksi.idBooking = user_booking.idBooking JOIN user_car ON user_booking.idKendaraan = user_car.idKendaraan JOIN user ON user_car.email = user.email WHERE transaksi.idBooking = $idBooking";
//$query = "SELECT t.idTransaksi, t.idBooking, t.biaya, p.metodePembayaran, b.tanggal, b.estimasi, c.jenisKendaraan, c.tipeKendaraan, c.tahunKeluaran, u.nama, u.alamat, u.noHp FROM transaksi t, pembayaran p, user_booking b, user_car c, user u WHERE t.idPembayaran = p.idPembayaran AND t.idBooking = $idBooking AND b.idKendaraan = c.idKendaraan AND c.email = u.email";
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
            margin: 0.5em;
        }

        h1 {
            font-size: 3em;
            font-weight: bold;
        }

        .wrapper {
            display: flex;
            flex-direction: row;
            margin-bottom: 1em;
        }

        .wrapper-satu {
            margin-right: 2em;
        }

        .p-mod {
            margin-bottom: 1em;
        }
    </style>
</head>

<body>

    <?php
    while ($invoice = mysqli_fetch_array($result)) {
        $NoInvoice = $invoice['idTransaksi'];
        $biaya = $invoice['biaya'];
        $metodePembayaran = $invoice['metodePembayaran'];
        $tanggalServis = $invoice['tanggal'];
        $estimasi = $invoice['estimasi'];
        $jenisKendaraan = $invoice['jenisKendaraan'];
        $tipeKendaraan = $invoice['tipeKendaraan'];
        $tahunKeluaran = $invoice['tahunKeluaran'];
        $nama = $invoice['nama'];
        $alamat = $invoice['alamat'];
        $noHp = $invoice['noHp'];
        $statusTransaksi = $invoice['statusTransaksi'];
    }
    ?>

    <p>
        <h1>Invoice</h1>
    </p>
    <div class="wrapper">
        <div class="wrapper-satu">
            <p>Billed To:</p>
            <b>
                <p><?php echo $nama ?></p>
                <p><?php echo $alamat ?></p>
                <p><?php echo $noHp ?></p>
            </b>
        </div>
        <div class="wrapper-satu">
            <p>Invoice Number: </p>
            <p><b><?php echo $NoInvoice ?></b> </p>
            <p> Kode Booking: </p>
            <p> <b><?php echo $idBooking ?></b></p>
        </div>
        <div class="wrapper-satu">
            <p>Tanggal Mulai Servis: </p>
            <p><b><?php echo $tanggalServis ?></b> </p>
            <p>Tanggal Selesai Servis: </p>
            <p> <b><?php echo $estimasi ?></b></p>
        </div>
        <div class="wrapper-satu">
            <p>Jenis Kendaraan: </p>
            <p><b><?php echo $jenisKendaraan ?></b> </p>
            <p>Tipe Kendaraan: </p>
            <p> <b><?php echo $tipeKendaraan ?></b></p>
        </div>
    </div>

    <b><u>
            <p style="margin-bottom: 0.5em">Detail Biaya Servis Kendaraan</p>
        </u></b>
    <table class=" table is-bordered mod">
        <thead>
            <tr>
                <th>No</th>
                <th>Detail</th>
                <th>Biaya</th>
            </tr>

            <?php
            $query2 = "SELECT * FROM servis_detail WHERE servis_detail.idBooking = $idBooking";
            $result2 = mysqli_query($conn, $query2);
            $no = 1;
            while ($servis_data = mysqli_fetch_array($result2)) {
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
    <p>Sub Total: <b><?php echo rupiah($biaya) ?></b> </p> <br>
    <p>Status Transaksi: <?php echo $statusTransaksi ?></p>
    <a href='index.php'>
        <p style="margin-top: 2em;">← Back to home</p>
    </a>
    <script src="" async defer></script>
</body>

</html>