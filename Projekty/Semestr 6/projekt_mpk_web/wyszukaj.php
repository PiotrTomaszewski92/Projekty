<!doctype html>

<?php

    mysql_connect('localhost',"elegant_mpk",'elegant_mpk_admin') or die ('error');
	mysql_select_db('elegant_mpk') or die ('nie moge polaczyc') ;
	mysql_query("SET NAMES utf8");
	ini_set('display_errors', '1');
	
	

?>



<html lang="pl"> 

	<head>

		<meta charset="UTF-8">

		<meta name="author" content="Tomaszewski, Sokulski, Szydlak">

		<title>Informator MPK Kraków</title>

		<!---SCRIPTS--->

		<script type="text/javascript" src="scripts/html5.js"></script>

		<script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>

		<script type="text/javascript" src="scripts/magnific-popup/dist/jquery.magnific-popup.js"></script>		

		<script type="text/javascript" src="scripts/popup.js"></script>

		<script type="text/javascript" src="scripts/jquery.js"></script>

		<script type="text/javascript" src="scripts/placeholders.jquery.min.js"></script>

		<script type="text/javascript" src="scripts/main.js"></script>
		
		<script type="text/javascript" src="scripts/dateapi.js"></script>
		
		<script type="text/javascript" src="scripts/datepicker.js"></script>
		
		<script type="text/javascript" src="scripts/time.js"></script>
		<link rel="stylesheet" href="//code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css">
		  <script src="//code.jquery.com/jquery-1.10.2.js"></script>
		  <script src="//code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
		 <?php
					$query = mysql_query(	"SELECT nazwa from przystanki; ");
					//$row=mysql_fetch_row(mysql_query($query));
						
					$data = array();
					while($row = mysql_fetch_array($query))
					{
						$data[] = $row['nazwa'];
					}
			?>
			<script type="text/javascript">
				$(function() {
					var availableTags = <?php echo json_encode($data); ?>;
					$( "#tags" ).autocomplete({ source: availableTags });
				});
				
				$(function() {
					var availableTags = <?php echo json_encode($data); ?>;
					$( "#tags2" ).autocomplete({ source: availableTags });
				});
			</script>



		<!---STYLESHEETS--->

		<link rel="stylesheet" type="text/css" href="css/style.css" media="all">

		<link rel="stylesheet" type="text/css" href="scripts/magnific-popup/dist/magnific-popup.css" media="all">

		<link rel="stylesheet" type="text/css" href="css/reset.css" media="all">
		 
	</head>

	<body class = "wyszukaj">
	
	

	<section class="billboard">

		<header>

			<div class="wrapper">

				<a href="index.php"><img src="img/logo.png" class="h_logo" alt="" title=""></a>

				<nav>

					<ul class="main_nav">

						<li><a href="index.php">Home</a></li>

						<li><a href="rozklad.php">Sprawdź rozkład</a></li>

						<li class="current"><a href="wyszukaj.php">Wyszukiwarka połączeń</a></li>

						<li><a href="kontakt.php">Kontakt</a></li>

					</ul>

				</nav>

			</div>

		</header>



		<section class="caption">

			<div class ="rozklad">
			
				

				<div class ="page-title">

					<h2>Wyszukaj połączenia</h2>
					
					<div class = "przystanek">
				
					<form action="wyszukaj.php" name="add" method="POST" class="wyszukiwarka">
						<table class = "wyszukaj-tabela">
								<tr><td><label for="tags2"></label><input type="text" id="tags2" class="szukaj" name="s_od" list="identyfikator" placeholder="Przystanek początkowy"/></td></tr>
								<tr><td></td></tr>
								<tr><td><label for="tags"></label><input type="text" id="tags" class="szukaj" name="s_do" placeholder="Przystanek końcowy"/></td></tr>
								<tr><td></td></tr>
						</table>
												
						<div class = "czas">
							<a href="#" class ="wTyl btn btn-subtract" onclick="change(-10);" name="button">Czas do tyłu</a>
							
								<input type="text" name="czas" value="<?php echo date("H:i");?>" id="input-time" data-minutes="110"/>
							
							<a href="#" class ="doPrzodu btn btn-add"  onclick="change(10);" name="button">Czas do przodu</a>					
						</div>
						
						<input type="submit" class="przycisk" name="wyslij" value="Szukaj połączenia"/>
					</form>
					
					</div>
					
					<?php
							require('wojtek.php');
							ini_set('display_errors', '1');
							$base = "http://www.m.rozkladzik.pl/krakow/";
							if((isset($_POST['s_od']))&&(isset($_POST['s_do']))&&(isset($_POST['czas']))){
							$s_od=$_POST['s_od'];
							$s_do=$_POST['s_do'];
							$hour=$_POST['czas'];
							$array=preg_split('/[:]/',$hour);
							
							if($s_od ==$s_do) echo'<p class="alert">Nazwa przystanku początkowego i końcowego nie może być taka sama.</p>';
							else if(($s_od=="")||($s_do=="")||($hour==""))echo'<p class="alert">Uzupełnij wszystkie pola.</p>';
							else if ((($array[0]<=-1)||($array[0]>=24))||(($array[1]<=-1)||($array[1]>=60))) echo'<p class="alert">Nie poprawny format godziny.</p>';
							
							
							else{
							//linia od:
							$result = mysql_query("SELECT * FROM przystanki where nazwa like '".$s_od."';");
							$row = mysql_fetch_array($result);
							$s_od=$row['nazwa'];
							$s_od_id=$row['id'];
							//linia do:
							$result2 = mysql_query("SELECT * FROM przystanki where nazwa like '".$s_do."';");
							$row2 = mysql_fetch_array($result2);
							$s_do=$row2['nazwa'];
							$s_do_id=$row2['id'];
							
						
							$from=$s_od;
							$to=$s_do;
							
							$przystanek1Id = $s_od_id;
							$przystanek2Id = $s_do_id; 
	
function getString($str, $base) {
	if( is_null($str) ) {
		return null;
	}
	$x = new mycurl("{$base}wyszukiwarka_polaczen.html?fromSearch=".urlencode($str));
	$x->createCurl();
	preg_match_all('~<a href=\'(.*)\'(.*)>(.*)</a>~iU', $x->__tostring(), $matches);
	
	$index = 0;
	foreach($matches[3] as $key => $match) {
		if( $str == trim($match) ) {
			$index = $key;
		}
	}
	
	if( !empty($matches[1][$index]) ) {
		parse_str(parse_url($matches[1][$index], PHP_URL_QUERY ), $c);
		if( !empty($c['from']) ) {
			return $c['from'];
		}
	}
	
	return null;
}
$from = getString($from, $base);
$to = getString($to, $base);
if (($from==null)||($to==null)) echo '<p class="alert">Nie znaleziono połączenia. Spróbuj ponownie wpisać poprawne dane. </p>';


if( !is_null($from) && !is_null($to) ) {
	
	$from = urlencode($from);
	$to = urlencode($to);
	
	$url = "http://www.m.rozkladzik.pl/krakow/wyszukiwarka_polaczen.html?time={$hour}&day=1&profile=opt&minChangeTime=1&maxWalkChange=100&useOrIgnoreLines=use&ignoreUseLines=&from={$from}&to={$to}&js=1";
	$c = new mycurl($url);
	$c->createCurl();
	
	$parts = explode('<div class=\'route_row route_row_colapse\'>', $c->__tostring());
	array_shift($parts);
	$l = array_pop($parts);
	$l= explode('</table></div></div></div>', $l);
	$parts[] = $l[0].'</table></div></div></div>';
	
	foreach($parts as &$part) {
		$part = '<div>'.$part;
		
		$l = explode('<tr class=\'show_rest\'', $part);
		$l2 = explode('</tr>', $l[1], 2);
		
		$part = $l[0] . $l2[1];
		
		$part = preg_replace('~img\/~iU', '/mpk/img/mpk/', $part);
		$part = preg_replace('~<a class=\'map\'(.*)</a>~iU', '', $part);
	}
	
	$limit = 3;
	foreach($parts as $key => $part) {
  if( $key >= $limit ) break;
  echo $part;
}
}
							}
							
								
								/*else 
								
								
								echo'
									<div class="wyniki_wysz" style=" margin: auto; width: 800px; ">
										<div class="trasa">
											<div class="start"><b>Start: </b>'.$s_od.'</div>
											<div class="cel"><b>Cel: </b>'.$s_do.'</div>
											<div class="gwiazdka"></div>
										</div>
										<div class="wyniki_tab">
										<table>
											<tr><th>ODJ. ZA</th><th>Linia</th><th>Odjazd</th><th>Przyjazd</th><th>Przesiadka</th><th>Czas przejazdu</th><th></th></tr>
											
											<tr><td><a href = "linie.php?id=1" class = "popup-youtube">0:00</a></td><td>182</td><td>22:40</td><td>22:52</td><td>0x</td><td>0:12</td></a><td><a href="#"><img src="img/ico_down.png" /></a></td></tr>
												
											<tr class = "popup-youtube" onClick="location.href=\'linie.php?id=1\'"><td>0:29</td><td>182</td><td>23:10</td><td>23:22</td><td>0x</td><td>0:12</td><td><a href="#"><img src="img/ico_down.png" /></a></td></tr>
											
											<tr class = "popup-youtube border_end"" onClick="location.href=\'linie.php?id=1\'"><td>0:47</td><td>152</td><td>23:28</td><td>23:51</td><td>0x</td><td>0:23</td><td><a href="#"><img src="img/ico_down.png" /></a></td></tr>
											
											
												
										</table>
										</div>
									</div>
								
									<!--<table class="polaczenie" border="1">
										<tr>
											<td class ="z"></td>
											<td>10:03</td>
											<td>Politechnika</td>
											
										</tr>
										<tr class="liniaa">
											<td colspan="3"  >3</td>
										</tr>
										<tr>
											<td>Dworzec Główny</td>
											<td>10:06</td>
											<td class ="do"></td>
										</tr>
									</table>
									
									<p class="przesiadz">PRZESIADKA</p>
									
									<table class="polaczenie" border="1">
										<tr>
											<td class ="z"></td>
											<td>10:03</td>
											<td>Dworzec Główny</td>
										</tr>
										<tr class="liniaa">
											<td colspan="3">4</td>
										</tr>
										<tr>
											<td>Teatr Bagatela</td>
											<td>10:06</td>
											<td class ="do"></td>
										</tr>
									</table>
									<hr />
									<table class="polaczenie" border="1">
										<tr>
											<td class ="z"></td>
											<td>10:03</td>
											<td>Politechnika</td>
											<td></td>
											<td></td>
											<td></td>
										</tr>
										<tr class="liniaa">
											<td colspan="6">3</td>
										</tr>
										<tr>
											<td></td>
											<td></td>
											<td></td>
											<td>Dworzec Główny</td>
											<td>10:06</td>
											<td class ="do"></td>
										</tr>
									</table>-->
								';
								
								echo'
								<!--<div class = "wyniki">
									<div class = "poczatek">
										<div class ="z"></div>
										<div class = "nazwaPrzystanku">Politechnika</div>
										<div class = "czasPrzystanek">10:03</div>
										<div class = "numerLinii">3</div>
									</div>
									
									<div class = "poczatekPrzesiadka">
										<div class ="do"></div>
										<div class = "nazwaPrzystanku">(2.) Dworzec Główny</div>
										<div class = "czasPrzystanek">10:06</div>
									</div>
									<br />
									<div class = "przesiadka">
										<div class = "przesiadkaLabel">Przesiądź się na:</div>
										<div class ="z"></div>
										<div class = "nazwaPrzystanku">Dworzec Główny</div>
										<div class = "czasPrzystanek">10:11</div>
										<div class = "numerLinii">10</div>
									</div>
									
									<div class = "koniec">
										<div class ="do"></div>
										<div class = "nazwaPrzystanku">(6.) Korona</div>
										<div class = "czasPrzystanek">10:27</div>
									</div>
									
									<div class = "czasPodrozy">
										<div class = "czasPrzejazdu">Czas podróży: 24 min</div>
									</div>
								</div>-->
								';*/
							} 
					?>

				</div>
				
				
				
				
				
				
				

			</div>

		</section>

	</section><!-- Billboard End -->



	<footer>

		<hr class="footer_sp"/>

		<p class="rights">Copyright© 2015 Tomaszewski, Sokulski, Szydlak</p>

	</footer>



</body>

</html>