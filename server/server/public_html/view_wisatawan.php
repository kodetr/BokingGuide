<?php

 //Importing the database connection
 require_once('config/koneksi.php');

 //SQL query to fetch data of a range
 $sql = "SELECT * from tbl_wisatawan";

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
       "jk"=>$row['jk'],
       "kontak"=>$row['kontak'],
       "foto"=>$url_foto_wisatawan."".$row['foto'],
       "nama_guide"=>$row['nama_guide'],
       "tgl_mulai"=>$row['tgl_mulai'],
       "tgl_akhir"=>$row['tgl_akhir'],
       "tujuan_wisata"=>$row['tujuan_wisata'],
       "biaya"=>$row['biaya'],
       "status"=>$row['status'],
       "id_guide"=>$row['id_guide']
     ));
   }
}
echo json_encode($res);
mysqli_close($status);

?>