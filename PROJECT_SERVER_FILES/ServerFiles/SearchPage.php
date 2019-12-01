<?php
include_once "dbConnect.php";
$q=$_POST['seach_key'];
$SQL=mysql_query("select * from products WHERE title LIKE '%".$q."%' LIMIT 0,12") or die(mysql_error());

$data=array();

while($row=mysql_fetch_array($SQL))
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