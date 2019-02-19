<?php
if($_SERVER['REQUEST_METHOD']=='POST'){

  $nama = $_POST['nama'];
  $umur = $_POST['umur'];
  $agama = $_POST['agama'];
  $bahasa = $_POST['bahasa'];
  $jk = $_POST['jk'];
  $kontak = $_POST['kontak'];
  $foto = $_POST['foto'];

// membuat query
$sql = "INSERT INTO tbl_wisatawan (nama, umur, agama, bahasa, jk, kontak, foto) 
        VALUES ('$nama','$umur','$agama','$bahasa','$jk','$kontak','$foto')";

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