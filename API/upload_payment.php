<?php
include 'conn.php';
if ($_SERVER['REQUEST_METHOD'] == 'POST') {

	$image = $_POST["image"];
	$idBooking = $_POST["idBooking"];
	$picName = $_POST["picName"];
	$sql = "INSERT INTO imageinfo (idBooking, picName) VALUES ('$idBooking', '$picName')";
	$upload_path = "uploads/$picName.jpg";

	if (mysqli_query($conn, $sql)) {
		file_put_contents($upload_path, base64_decode($image));
		echo json_encode(array('response' => 'Image Uploaded Successfully'));
	} else {
		echo json_encode(array('response' => 'Image Upload Failed :P'));
	}
} else {
	echo json_encode(array('response' => 'Database Error'));
}
mysqli_close($conn);
