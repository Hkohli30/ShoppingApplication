<?php
include_once "dbConnect.php";
$bann1="http://dealingindia.com/mobile/img/bann1.jpg"; //banner main front big
$bann2="http://dealingindia.com/mobile/img/bann2.jpg"; //banner main front big


$data=array();

$data['banner']=array();
$data['top_prod']=array();
$data['recent_prod']=array();


array_push($data['banner'],array(
'banner1'=> $bann1,
'banner2' => $bann2
));





$status="true";
$tit=mysql_query("select * from products where status='$status' ORDER BY view DESC LIMIT 5") or die(mysql_error());

		while($row=mysql_fetch_array($tit))
		{
			
			$image_64=base64_encode($i);
				array_push($data['top_prod'],array
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
		//recent products
		$tit=mysql_query("select * from products where status='$status' ORDER BY id DESC LIMIT 5") or die(mysql_error());

		while($row=mysql_fetch_array($tit))
		{
				array_push($data['recent_prod'],array
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




