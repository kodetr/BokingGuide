<?php
	// deklarasi variable untuk koneksi ke MySQL
	$hostname = "localhost";
	$username = "id8729186_kodetr";
	$password = "qwertyuiop";
	$database = "id8729186_db_boking";
	$url_foto_wisatawan = "http://webkodetr.000webhostapp.com/foto_wisatawan/";
	$url_foto_guide = "http://webkodetr.000webhostapp.com/foto_guide/";
	$url_vidio = "http://webkodetr.000webhostapp.com/vidio/";
	$url_foto_destinasi = "http://webkodetr.000webhostapp.com/foto_destinasi/";
    $url_vidio_destinasi = "http://webkodetr.000webhostapp.com/vidio_destinasi/";

	// membuat koneksi ke MySQL
	$status = mysqli_connect($hostname, $username, $password, $database);
	if (!$status){
		die("Koneksi ke MySQL gagal dilakukan!<br>");
	}

?>
