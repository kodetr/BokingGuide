<?php
if($_SERVER['REQUEST_METHOD']=='POST'){

  $nama = $_POST['nama'];
  $umur = $_POST['umur'];
  $agama = $_POST['agama'];
  $bahasa = $_POST['bahasa'];
  $jk = $_POST['jk'];
  $kontak = $_POST['kontak'];
  $foto = $_POST['foto'];
  $nama_guide = $_POST['nama_guide'];
  $tgl_mulai = $_POST['tgl_mulai'];
  $tgl_akhir = $_POST['tgl_akhir'];

    // membuat query
    $sql = "INSERT INTO tbl_wisatawan (nama, umur, agama, bahasa, jk, kontak, foto, nama_guide, tgl_mulai, tgl_akhir) 
        VALUES ('$nama','$umur','$agama','$bahasa','$jk','$kontak','$foto', '$nama_guide','$tgl_mulai','$tgl_akhir')";

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