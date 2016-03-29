<?php
	$con = mysqli_connect("mysql1.000webhost.com", "a2276114_lotte", "g21Leag7", "a2276114_profile");
	
	
	
	$email = $_POST["Email"];
	$firstname = $_POST["FirstName"];
	$lastname = $_POST["LastName"];
	$password = $_POST["Password"];
	$username = $_POST["Username"];

	$insertquery = mysqli_prepare($con, "INSERT INTO profile (FirstName, LastName, Email, Password, Username) VALUES(?,?,?,?,?)");
	mysqli_stmt_bind_param($insertquery, "sssss", $firstname, $lastname, $email, $password, $username);

	mysqli_stmt_execute($insertquery);

	mysqli_stmt_close($insertquery);

	mysqli_close($con);

?>