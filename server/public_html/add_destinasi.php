<?php
if($_SERVER['REQUEST_METHOD']=='POST'){

  $nama = $_POST['nama'];
  $jenis = $_POST['jenis'];
  $lokasi = $_POST['lokasi'];
  $keterangan = $_POST['keterangan'];
  $foto = $_POST['foto'];

// membuat query
$sql = "INSERT INTO tbl_destinasi (nama, jenis, lokasi, keterangan, foto) 
       VALUES ('$nama','$jenis','$lokasi','$keterangan','$foto')";

//Importing our db connection script
include("config/koneksi.php");

//Executing query to database
  if(mysqli_query($status,$sql)){
      echo 'Berhasil disimpan';
  }else{
      echo 'Gagal disimpan';
  }

  //Closing the database
  mysqli_close($status);
}
?>