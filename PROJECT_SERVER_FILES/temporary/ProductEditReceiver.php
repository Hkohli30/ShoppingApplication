<?php

	include_once "dbConnect.php";
	
	$PRODUCT_ID = $_POST['produce_id'];
	$SQL = "select * from products where produce_id = ".$PRODUCT_ID;
	
	
	$data = array();
	
	while($row=mysql_fetch_array($SQL) )
{
			
			$email = $row[13];
	
			array_push($data['product'],array
				(
				//'id' => $row[0],
				//'view' => $row[1],
				'title' => $row[3],
				'descp' => $row[4],
				'condition' => $row[5],
				'cash' => $row[6],
				'online' => $row[7],				
				'courier' => $row[8],
				'store' => $row[9],				
				'sp' => $row[10],
				'category' => $row[11],
				'subcategory' => $row[12],
				'email' => $row[13],
				'mobile' => $row[14],
				'state' => $row[15],
				'city' => $row[16],
				'media_1' => "http://dealingindia.com/mobile/".$row[17],	
				'media_2' => "http://dealingindia.com/mobile/".$row[19],	
				'media_3' => "http://dealingindia.com/mobile/".$row[20],
				'media_4' => "http://dealingindia.com/mobile/".$row[21]	
				));
}


?>