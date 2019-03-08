<?php
if($_SERVER['REQUEST_METHOD']=='POST'){
    
  $id = $_GET['id'];
  $nama = $_POST['nama'];
  $jenis = $_POST['jenis'];
  $lokasi = $_POST['lokasi'];
  $keterangan = $_POST['keterangan'];

// membuat query
$sql = "UPDATE tbl_destinasi SET nama='$nama', jenis='$jenis', lokasi='$lokasi', keterangan='$keterangan' WHERE id='$id' ";

//Importing our db connection script
include("config/koneksi.php");

//Executing query to database
  if(mysqli_query($status,$sql)){
      echo 'Berhasil diubah';
  }else{
      echo 'Gagal diubah';
  }

  //Closing the database
  mysqli_close($status);
}
?>