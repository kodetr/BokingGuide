<?php
if($_SERVER['REQUEST_METHOD']=='POST'){
    
      $id = $_GET['id'];
      $status = $_POST['status'];

    // membuat query
    $sql = "UPDATE tbl_wisatawan SET status='$status'  WHERE id = '$id'";

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