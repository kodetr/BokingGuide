<?php
if($_SERVER['REQUEST_METHOD']=='POST'){

  $nama = $_POST['nama'];
  $umur = $_POST['umur'];
  $agama = $_POST['agama'];
  $bahasa = $_POST['bahasa'];
  $kontak = $_POST['kontak'];
  $lokasi = $_POST['lokasi'];
  $jk = $_POST['jk'];
  $foto = $_POST['foto'];
  $vidio = $_POST['vidio'];

// membuat query
$sql = "INSERT INTO tbl_guide (nama, umur, agama, bahasa, kontak, lokasi, jk, foto, vidio, tag) 
       VALUES ('$nama','$umur','$agama','$bahasa','$kontak','$lokasi','$jk','$foto','$vidio','0')";

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