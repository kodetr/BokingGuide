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
     $id_guide = $row['id'];

    //   if(empty($id_guide)){
    //      array_push($res, array(
    //       "id"=>$row['id'],
    //       "nama"=>$row['nama'],
    //       "umur"=>$row['umur'],
    //       "agama"=>$row['agama'],
    //       "bahasa"=>$row['bahasa'],
    //       "kontak"=>$row['kontak'],
    //       "lokasi"=>$row['lokasi'],
    //       "jk"=>$row['jk'],
    //       "foto"=>$url_foto_guide."".$row['foto'],
    //       "vidio"=>$url_vidio."".$row['vidio'],
    //       "jmh_rating"=>$row['jmh_rating'],
    //       "jmh_num"=>$row['jmh_num'],
    //       "rating"=>$row['rating'],
    //       "tag"=>$row['tag']
    //      ));
    //   }else{
     $result_wisatawan =  mysqli_query($status,"SELECT * from tbl_wisatawan where id_guide='$id_guide'");
     $row2 = mysqli_fetch_array($result_wisatawan);
     
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
           "nama_wisatawan"=>$row2['nama'],
           "tgl_mulai"=>$row2['tgl_mulai'],
           "tgl_akhir"=>$row2['tgl_akhir']
         )); 
    //   }
     
   }
}
echo json_encode($res);
mysqli_close($status);

?>