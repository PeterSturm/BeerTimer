<?php
/**
 * Created by PhpStorm.
 * User: anthor
 * Date: 2015.05.13.
 * Time: 12:04
 */

	$db_host = "mysql.hostinger.in";
	$db_database = "u505374377_beert";
	$db_user = "u505374377_admin";
	$db_pass = "WZ45pYkjJOb";

	$db = mysqli_connect($db_host, $db_user, $db_pass, $db_database);

    if(mysqli_connect_errno())
        echo 'ERROR';
?>
