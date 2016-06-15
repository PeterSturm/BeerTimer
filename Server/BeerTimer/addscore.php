<?php
/**
 * Created by PhpStorm.
 * User: anthor
 * Date: 2015.05.13.
 * Time: 18:45
 */

    include 'db.php';

    $name = $_GET['name'];
    $time = $_GET['time'];


    $query = "INSERT INTO scores (Name, Time) VALUES ('$name', '$time')";

    if (!mysqli_query($db, $query)) {
        die('Error: ' . mysqli_error($db));
    }

    $result = mysqli_query($db, "SELECT BestTime FROM users WHERE Name = '$name'");
    $row = mysqli_fetch_array($result);
    $besttime = $row['BestTime'];
    if( strcmp($besttime, "") == 0 || strcmp($besttime, $time) > 0)
    {
        $query = "UPDATE users SET BestTime = '$time' WHERE Name = '$name'";

        if (!mysqli_query($db, $query)) {
            die('Error: ' . mysqli_error($db));
        }
    }
?>