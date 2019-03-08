<?php

 //Importing the database connection
 require_once('config/koneksi.php');

    $jenis = $_GET['jenis'];

 //SQL query to fetch data of a range
 $sql = "SELECT * from tbl_destinasi WHERE jenis = '$jenis'";

 //Getting result
 $result = mysqli_query($status,$sql);
 // mengambil jumlah baris hasil query
 $total_results = mysqli_num_rows($result);
 //Adding results to an array
 $res = array();
 if ($total_results == 0){
   array_push($res, array("pesan"=> true));
 }else{

 while($row = mysqli_fetch_array($result)){
     array_push($res, array(
       "id"=>$row['id'],
       "nama"=>$row['nama'],
       "jenis"=>$row['jenis'],
       "lokasi"=>$row['lokasi'],
       "keterangan"=>$row['keterangan'],
       "foto"=>$url_foto_destinasi."".$row['foto'],
       "vidio"=>$url_vidio_destinasi."".$row['vidio'],
     ));
   }
}
echo json_encode($res);
mysqli_close($status);

?>