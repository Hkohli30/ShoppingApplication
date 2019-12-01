<?php
include_once "dbConnect.php";
$username=$_POST['username'];
$password = $_POST['password'];
$login_type = $_POST['login_type'];

$email=$_POST['email'];
$mobile=$_POST['mobile'];
$message=$_POST['message'];

	if((USERNAME AND PASSWORD MATCHES AND LOGIN TYPE IS NORMAL) OR (LOGINTYPE IS FACEBOOK))
	{
		//INSERT THE REQUEST
		echo 'successfully updated';
	}
	else
	{
		echo 'password match error'; 
	}
	
?>