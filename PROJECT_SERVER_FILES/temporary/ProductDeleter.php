<?php

	include_once "dbConnect.php";
	
	$USERNAME = $_POST['username'];
	$PASSWORD = $_POST['password'];
	$LOGIN_TYPE = $_POST['logint_type'];
	
	$PRODUCT_ID = $_POST['product_id'];
	
	if((USERNAME AND PASSWORD MATCH AND LOGIN_TYPE IS NORMAL) && (USERNAME EXISTS AND LOGIN_TYPE IS ABNORMAL))
	{
		if(PRODUCT_ID IS VALID (I.E. THE PRODUCT TO BE DELETED IS THE USER'S PRODUCT))
		{
			DELETED THE PRODUCT
			echo $PRODUCT_ID." DELETED";
		}
		else{
			echo $USERNAME.' unauthorized';
		}
	}
	else{
		echo 'password match error'
	}

?>