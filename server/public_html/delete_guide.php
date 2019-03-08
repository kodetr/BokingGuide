<?php
    if($_SERVER['REQUEST_METHOD']=='GET'){

    $id=$_GET['id'];

    include("config/koneksi.php");

    if(!empty($id)){
      unlink("foto_guide/".$id.".jpeg");
      unlink("foto_guide/".$id.".jpg");
      unlink("foto_guide/".$id.".gif");
      unlink("foto_guide/".$id.".x-png");
      unlink("foto_guide/".$id.".png");
      unlink("foto_guide/".$id.".png");

      unlink("vidio_guide/".$id.".mp4");
    }

      // membuat query
      $sql="DELETE FROM tbl_guide WHERE id = '$id'";

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