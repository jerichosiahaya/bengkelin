<?php
include 'conn.php';
$query = "UPDATE user_booking c INNER JOIN (SELECT idBooking, SUM(biaya) as total FROM servis_detail GROUP BY idBooking) x ON c.idBooking = x.idBooking SET c.total_biaya = x.total";
$result = mysqli_query($conn, $query);
if ($result) {
    header('Location: index.php');
} else {
    echo "NOT OKAY";
}
