<?php
if($_SERVER['REQUEST_METHOD']=='POST'){
    
  $id = $_GET['id'];
  $nama = $_POST['nama'];
  $umur = $_POST['umur'];
  $agama = $_POST['agama'];
  $bahasa = $_POST['bahasa'];
  $kontak = $_POST['kontak'];
  $lokasi = $_POST['lokasi'];
  $jk = $_POST['jk'];

// membuat query
$sql = "UPDATE tbl_guide SET nama='$nama', umur='$umur', agama='$agama', bahasa='$bahasa', kontak='$kontak', lokasi='$lokasi', jk='$jk' WHERE id='$id' ";

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