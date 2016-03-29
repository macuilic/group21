<?php
	$con = mysqli_connect("mysql1.000webhost.com", "a2276114_lotte", "g21Leag7", "a2276114_profile");


	$selectquery = mysqli_prepare($con, "SELECT * FROM tournaments");

	$result = mysqli_query($con, $selectquery);
	

		mysqli_stmt_execute($selectquery);
 	
		mysqli_stmt_store_result($selectquery);
		mysqli_stmt_bind_result($selectquery, $ID, $name, $game, $region );
	
	$tournaments = array();


	while ($array = mysqli_stmt_fetch($selectquery)) {
		
			$tournaments[ID] = $array[ID];
			$tournaments[name] = $array[name]:
			$tournaments[game] = $array[game];
			$tournaments[region] = $array[region];

			}

	echo json_encode($tournaments)	;
	
	mysqli_stmt_close($selectquery);
	
	mysqli_close($con);

?>