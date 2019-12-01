<?php
include_once "dbConnect.php";
$status="true";
$email=$_POST['seller_email'];

$cover="../mobile/dp/cover!".$email.".jpg"; //cover image
$dp="../mobile/dp/dp!".$email.".jpg"; //display pic


//arrays
$data=array();
$data['shop_image']=array();
$data['product_data']=array();


array_push($data['shop_image'],array(
'cover_image'=> $cover,
'profile_image' => $dp
));

//products fetch
$product_SQL=mysql_query("select * from products where email='$email' && status='$status' ORDER BY id DESC LIMIT 12") or die(mysql_error());

while($row=mysql_fetch_array($product_SQL))
		{
				array_push($data,array
				(
				'id' => $row[0],
				
				'title' => $row[3],
				'condition' => $row[5],
				'sp' => $row[10],
				
				'state' => $row[16].", ".$row[15],
				'condition' => $row[5],
				'image' => "http://dealingindia.com/mobile/".$row[17]
				));
		}
		echo json_encode(array('results' => $data));

?>