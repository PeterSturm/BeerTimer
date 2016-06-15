<?php
/**
 * Created by PhpStorm.
 * User: anthor
 * Date: 2015.05.13.
 * Time: 12:04
 */

	$db_host = "mysql3.000webhost.com";
	$db_database = "a8055317_beertr";
	$db_user = "a8055317_admin";
	$db_pass = "sl01pingbeer";

	$db = mysqli_connect($db_host, $db_user, $db_pass, $db_database);

    if(mysqli_connect_errno())
        echo 'ERROR';
?>