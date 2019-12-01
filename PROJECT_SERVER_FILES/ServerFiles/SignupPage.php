<?php
	include_once "dbConnect.php";
	
	$email = $_POST['email'];
	$password = $_POST['password'];
	$mobile = $_POST['mobile'];
	
	
		if( $email == '' || $password == '' || $mobile == '')
		{
			echo 'Empty Data Received'; 
		}
		else
		{
			
			$email_exist=mysql_query("select * from register where email='$email'");
			if(mysql_num_rows($email_exist)>0)
			{
			$ema_error="Email Already Exists";
			echo $ema_error;
			}
			else
			{
			mysql_query("insert into register(email,password,cpassword,mobile) values('$email','$passowrd','$passowrd','$mobile')") or die(mysql_error());
			mkdir("../".$email);
			echo "inserted";
			}
		}
		
	

?>