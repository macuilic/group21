<?php
	$con = mysqli_connect("mysql1.000webhost.com", "a2276114_lotte", "g21Leag7", "a2276114_profile");

	$password = $_POST["Password"];
	$username = $_POST["Username"];

	$selectquery = mysqli_prepare($con, "SELECT * FROM profile WHERE Username = ? AND Password = ?");
	
	mysqli_stmt_bind_param($selectquery, "ss", $username, $password);

	mysqli_stmt_execute($selectquery);
 	
		mysqli_stmt_store_result($selectquery);
		mysqli_stmt_bind_result($selectquery, $ID, $firstname, $username, $email, $password, $lastname );
	
	$user = array();

	while (mysqli_stmt_fetch($selectquery)) {
		
		$user[firstname] = $firstname;
		$user[lastname] = $lastname;
		$user[email] = $email;
		$user[username] = $username;
		$user[password] = $password;

			}

	echo json_encode($user)	;
	
	mysqli_stmt_close($selectquery);
	

	
	

	mysqli_close($con);

?>