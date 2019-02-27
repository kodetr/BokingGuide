<?php
if($_SERVER['REQUEST_METHOD']=='POST'){

  $id = $_GET['id'];
  $nama = $_POST['nama'];
  $umur = $_POST['umur'];
  $agama = $_POST['agama'];
  $bahasa = $_POST['bahasa'];
  $jk = $_POST['jk'];
  $kontak = $_POST['kontak'];

// membuat query
$sql = "UPDATE tbl_wisatawan SET nama='$nama', umur='$umur', agama='$agama', bahasa='$bahasa', jk='$jk', kontak='$kontak' 
        WHERE id = '$id'";

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