<?php
header('Content-type:application/json;chartset=utf-8');
include 'conn.php';

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $email = $_POST['email'];
    $password = $_POST['password'];
    $sql = " SELECT * FROM user WHERE user.email = '$email' ";
    $result = mysqli_query($conn, $sql);
    $response = array();
    $index = array();
    $row = $result->fetch_assoc();
    $p = $row["password"];

    if (mysqli_num_rows($result) === 1) {
        $response["login"] = array();
        if ($password == $p) {

            $index["email"] = $row["email"];
            $index["nama"] = $row["nama"];
            $index["alamat"] = $row["alamat"];
            $index["noHp"] = $row["noHp"];

            array_push($response["login"], $index);

            $response["success"] = 1;
            $response["message"] = "success";

            echo json_encode($response, JSON_PRETTY_PRINT);
            mysqli_close($conn);
        } else {
            $response["success"] = 0;
            $response["message"] = "error";
            echo json_encode($response);
            mysqli_close($conn);
        }
    }
}
