<?php
    if($_SERVER['REQUEST_METHOD']=='GET'){

    $id=$_GET['id'];

    include("config/koneksi.php");

    if(!empty($id)){
      unlink("foto_destinasi/".$id.".jpeg");
      unlink("foto_destinasi/".$id.".jpg");
      unlink("foto_destinasi/".$id.".gif");
      unlink("foto_destinasi/".$id.".x-png");
      unlink("foto_destinasi/".$id.".png");
      unlink("foto_destinasi/".$id.".png");

      unlink("vidio_guide/".$id.".mp4");
    }

      // membuat query
      $sql="DELETE FROM tbl_destinasi WHERE id = '$id'";

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