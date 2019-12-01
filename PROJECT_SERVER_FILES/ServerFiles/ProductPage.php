<?php
include_once "dbConnect.php";
$pid="2111"; 
$email=0;
$status="true";

$SQL=mysql_query("select * from products where id='$pid' AND status='$status'") or die(mysql_error());
$subcategory_product=0;
$category_product=0;

$data = array();
$data['seller_information'] = array();
$data['product'] = array();
$data['related_products'] = array();

//main product loop
while($row=mysql_fetch_array($SQL) )
{
			$subcategory_product=$row[12];
			$category_product=$row[11];
			
	$email = $row[13];
			array_push($data['product'],array
				(
				'id' => $row[0],
				'view' => $row[1],
				'title' => $row[3],
				'descp' => $row[4],
				'condition' => $row[5],
				'cash' => $row[6],
				'online_pay' => $row[7],
				'courier' => $row[8],
				'store' => $row[9],
				'sp' => $row[10],
				'category' => $row[11],
				'subcategory' => $row[12],
				'email' => $row[13],
				'mobile' => $row[14],
				'state' => $row[16].", ".$row[15],
				'date' => $row[21],
				'time' => $row[22],
				
				'condition' => $row[5],
				'image' => "http://dealingindia.com/mobile/".$row[17],	
				'media_1' => "http://dealingindia.com/mobile/".$row[19],	
				'media_2' => "http://dealingindia.com/mobile/".$row[20],
				'media_3' => "http://dealingindia.com/mobile/".$row[21]	
				));
}


$SQL_SHOP=mysql_query("select * from shop where email='$email'") or die(mysql_error());
$count_shop=mysql_num_rows($SQL_SHOP);


//data fetch shop name
while(($row=mysql_fetch_array($SQL_SHOP)))
{
if($count_shop > 0)
{
	array_push($data['seller_information'],array
				(
				'shop_name' => $row[0]
				));
}
else
{	
	array_push($data['seller_information'],array
				(
				'shop_name' => $email
				));
	
}
}

		
//advertisement area
		$related_SQL=mysql_query("select * from products where subcategory='$subcategory_product' AND category='$category_product' AND status='$status' ORDER BY RAND() LIMIT 5") or die(mysql_error());		

		while($row=mysql_fetch_array($related_SQL))
		{
			
			array_push($data['related_products'],array
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