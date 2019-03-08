<?php
if($_SERVER['REQUEST_METHOD']=='GET'){

  include("config/koneksi.php");

  $username = $_GET['username'];
  $password = md5($_GET['password']);

  $sql = "SELECT * FROM users WHERE username='$username' AND password = '$password'";
  $result = mysqli_query($status,$sql);
  $total_results = mysqli_num_rows($result);

    $res = array();
    if($total_results != 0){
      array_push($res, array("pesan"=>"Login Berhasil"));
    }else{
      array_push($res, array("pesan"=>"Login Gagal"));
    }

  echo json_encode($res);
  mysqli_close($status);
}
?>