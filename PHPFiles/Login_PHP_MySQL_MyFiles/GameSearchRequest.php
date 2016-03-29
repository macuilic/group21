<?php

$con = mysqli_connect("mysql1.000webhost.com", "a2276114_lotte", "Eswarim.11", "a2276114_profile");


$game = $_POST["Game"];


$selectquery = mysqli_prepare($con , "SELECT * FROM tournaments WHERE Game = ?");
mysqli_stmt_bind_param($selectquery, "s" , $game);
mysqli_stmt_execute($selectquery);

mysqli_stmt_store_result($selectquery);
mysqli_stmt_bind_result($selectquery , $ID , $tournamentname , $game, $region);

$tournament = array();

while(mysqli_stmt_fetch($selectquery))
{
	$tournament[tournamentname] = $tournamentname;
	$tournament[game] = $game;
	$tournament[region] = $region;
}

echo json_encode($tournament);

mysqli_stmt_close($selectquery);
mysqli_close($con);
?>