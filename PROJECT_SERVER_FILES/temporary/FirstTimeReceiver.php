<?php
include_once "dbConnect.php";


	//SELECT ALL THE DATA IN THE CATEGORIES TABLE AND MAKE A JSON OBJECT
	
	$DATA_FETCH = "select * from category" ;
	$data = array();

	while($row =mysql_fetch_array($DATA_FETCH))
	{
		array_push($data,array(
			
			'category' => $row[1],
			'subcategory' => row[2]
		));
	}
	
	echo json_encode(array('results' => $data));
?>