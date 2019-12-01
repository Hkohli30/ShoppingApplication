<?php
include_once "dbConnect.php";


	SELECT ALL THE DATA IN THE CATEGORIES TABLE AND MAKE A JSON OBJECT
	
	$DATA_FETCH = ;
	$data = array();

	while($row =mysql_fetch_array($DATA_FETCH))
	{
		array_push($data,array(
			
			'category' => $row[0],
			'subcategory' => row[1],
			'sub-sub-category' => row[2]
		));
	}
	
	echo json_encode(array('results' => $data));
?>