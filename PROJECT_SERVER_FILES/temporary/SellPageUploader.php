<?php

	include_once "dbConnect.php";
	
	$USERNAME = $_POST['username'];
	$PASSWORD = $_POST['password'];
	$LOGIN_TYPE  = $_POST['login_type'];  // normal , facebook

	$TITLE = $_POST['title'];
	$DESCPRITION = $_POST['descp'];
	$PRODUCT_CONDITION = $_POST['condition'];
	$SELLING_PRICE  = $_POST['selling_price'];
	$CATEGORY  = $_POST['category'];
	$SUB_CATEGORY = $_POST['sub_category'];
	$MOBILE = $_POST['mobile'];
	$ALT_MOBILE  = $_POST['alt_mobile'];
	$PAYMENT_METHOD = $_POST['payment_methods'];	// BB split the values 11 
	$DELIVERY_TYPE = $_POST['delivery_type'];
	$IMAGE_1 = $_POST['IMAGE_1'];
	$IMAGE_2 = $_POST['IMAGE_2'];
	$IMAGE_3 = $_POST['IMAGE_3'];
	$IMAGE_4 = $_POST['IMAGE_4'];
	
	
	if(USERNAME AND PASSWORD MATCHES AND THE LOGIN TYPE IS normal  )
	{
		// SQL TO INSERT THE DATA IN THE WEBPAGE
		echo 'successfully inserted';
	}
	else if (USERNAME AND LOGIN TYPE IS FACEBOOK)
	{
		// SQL TO INSERT THE DATA IN THE WEBPAGE	
		echo 'successfully inserted'; 
	}

	else if(username and password do not match)
	{
		echo 'password match error'; 
	}
	
?>