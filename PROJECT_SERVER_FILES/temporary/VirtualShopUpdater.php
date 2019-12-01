<?php

	include_once "dbConnect.php";
	
	$USERNAME = $_POST['username'];
	$PASSWORD = $_POST['password'];
	$LOGIN_TYPE  = $_POST['login_type'];  // normal , facebook

	$SHOP_NAME = $_POST['shop_name'];
	$EMAIL = $_POST['email'];
	$ABOUT = $_POST['about'];
	$WHATSAPP_NO  = $_POST['whatapp_no'];
	$STATE  = $_POST['state'];
	$CITY = $_POST['city'];
	
	
	
	if((USERNAME AND PASSWORD MATCHES AND LOGIN TYPE IS NORMAL) OR (USERNAME LOGIN_TYPE IS FACEBOOK) )
	{
		// UPDATE USERS SHOP
		echo 'successfully updated';
	}
	else if(username and password do not match)
	{
		echo 'password match error'; 
	}
	
?>