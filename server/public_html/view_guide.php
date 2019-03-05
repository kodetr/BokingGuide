<?php

 //Importing the database connection
 require_once('config/koneksi.php');

 //SQL query to fetch data of a range
 $sql = "SELECT * from tbl_guide";

 //Getting result
 $result = mysqli_query($status,$sql);
 // mengambil jumlah baris hasil query
 $total_results = mysqli_num_rows($result);
 //Adding results to an array
 $res = array();
 if ($total_results == 0){
   array_push($res, array("pesan"=>"Data belum ada!"));
 }else{

 while($row = mysqli_fetch_array($result)){
     array_push($res, array(
       "id"=>$row['id'],
       "nama"=>$row['nama'],
       "umur"=>$row['umur'],
       "agama"=>$row['agama'],
       "bahasa"=>$row['bahasa'],
       "kontak"=>$row['kontak'],
       "lokasi"=>$row['lokasi'],
       "jk"=>$row['jk'],
       "foto"=>$url_foto_guide."".$row['foto'],
       "vidio"=>$url_vidio."".$row['vidio'],
       "jmh_rating"=>$row['jmh_rating'],
       "jmh_num"=>$row['jmh_num'],
       "rating"=>$row['rating'],
       "tag"=>$row['tag'],
     ));
   }
}
echo json_encode($res);
mysqli_close($status);

?>