<?php
    if($_SERVER['REQUEST_METHOD']=='GET'){

    $id=$_GET['id'];

    include("config/koneksi.php");

    if(!empty($id)){
      unlink("foto_wisatawan/".$id.".jpeg");
      unlink("foto_wisatawan/".$id.".jpg");
      unlink("foto_wisatawan/".$id.".gif");
      unlink("foto_wisatawan/".$id.".x-png");
      unlink("foto_wisatawan/".$id.".png");
      unlink("foto_wisatawan/".$id.".png");
    }

      // membuat query
      $sql="DELETE FROM tbl_wisatawan WHERE id = '$id'";

        $result=array();
      //Executing query to database
      if(mysqli_query($status,$sql)){
         $result="Berhasil dihapus";
      }else{
          $result="Gagal dihapus";
      }

      echo json_encode(array("result"=>$result));
      //Closing the database
      mysqli_close($status);
}
 ?>