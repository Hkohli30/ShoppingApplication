<?php
include_once "dbConnect.php";
$username=$_POST['username'];

	
		//SEND PROFILE PIC, USER PIC, SHOPNAME, EMAIL,location
	
	$DATA_FETCH = ;
	$data = array();

	while($row =mysql_fetch_array($DATA_FETCH))
	{
		array_push($data,array(
			
			'dp' => $row[0],
			'pic' => row[1],
			'shopname' => row[2],
			'email'=>row[3],
			'location'=>row[4],
			'phone'=>row[5],
			'state'=>row[6],
			'city'=>row[7]
		));
	}
	
	echo json_encode(array('results' => $data));
	
?>