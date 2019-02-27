<?php

 if($_SERVER['REQUEST_METHOD']=='POST'){

	$id = $_GET['id'];

	require_once('config/koneksi.php');

	$original	= explode('.',$_FILES["upload_file"]["name"]);
	$extention	= array_pop($original);
	$file_path 	= $id.".".$extention;

	$upload	= move_uploaded_file($_FILES["upload_file"]["tmp_name"],"foto_destinasi/".$file_path);

	$sql = "UPDATE tbl_destinasi SET foto='$file_path' WHERE id='$id'";
	mysqli_query($status,$sql);

    if($upload){
        $result = array("result" => "Berhasil disimpan");
    }else{
        $result = array("result" => "Gagal disimpan");
    }

    echo json_encode($result);
	mysqli_close($status);
 }
?>