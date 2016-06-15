<?php
/**
 * Created by PhpStorm.
 * User: anthor
 * Date: 2015.05.13.
 * Time: 13:58
 */

    include 'db.php';

    $name = $_GET['name'];


    $result = mysqli_query($db, "SELECT Name FROM users WHERE Name = '$name'");
    $row = mysqli_fetch_array($result);

    if(mysqli_num_rows($result) > 0)
    {
        echo 'taken';
    }
    else
    {
        $query = "INSERT INTO users (Name) VALUES ('$name')";

        if (!mysqli_query($db, $query)) {
            die('Error: ' . mysqli_error($db));
        }
        echo 'ok';
    }

?>