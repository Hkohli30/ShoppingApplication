<?php

include_once "dbConnect.php";
$a=$_POST['email'];
$b=$_POST['password'];
$row=mysql_query("select * from register where email='$a' && password='$b'")or die(mysql_error());
$row_ema=mysql_query("select * from register where email='$a'")or die(mysql_error());
if(mysql_num_rows($row_ema)>0)
{
	if(mysql_num_rows($row) > 0)
	{
		echo "login Successful";
	}
	else //password nt match
	{
		$error="Password not match";
	}
}
else
{
	$error="Email not match";
}
 echo $error;
?>