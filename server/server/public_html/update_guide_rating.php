<?php
if($_SERVER['REQUEST_METHOD']=='POST'){
    
  $id = $_GET['id'];
  $rating = $_POST['rating'];
  $jmh_rating = $_POST['jmh_rating'];
  $jmh_num = $_POST['jmh_num'];

// membuat query
$sql = "UPDATE tbl_guide SET rating='$rating', jmh_rating='$jmh_rating', jmh_num='$jmh_num' WHERE id='$id' ";

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