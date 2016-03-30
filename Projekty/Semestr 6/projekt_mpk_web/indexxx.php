
<?php
function getUlubioneLinie() {
    $current = isset($_COOKIE['fav']) ? explode(',', base64_decode($_COOKIE['fav'])) : array();
    
    $data = array();
    
    foreach($current as $c) {
        list($linia, $poczatek) = explode('%-%', $c, 2);
        $data[] = array(
            'liniaId' => $linia,
            'przystanekPoczatowy' => $poczatek
        );
    }
    
    return $data;
}
$xxx=getUlubioneLinie();

//print_r($xxx); 


?>
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

		<script type="text/javascript" src="scripts/jquery.js"></script>

		<script type="text/javascript" src="scripts/main.js"></script>
		

		<script type="text/javascript" src="scripts/placeholders.jquery.min.js"></script>

		<!---STYLESHEETS--->

		<link rel="stylesheet" type="text/css" href="css/style.css" media="all">

		<link rel="stylesheet" type="text/css" href="css/reset.css" media="all">

	</head>

	<body>
<?php



	
	
		?>


	<section class="billboard">

		<header>

			<div class="wrapper">

				<a href="index.php"><img src="img/logo.png" class="h_logo" alt="" title=""></a>

				<nav>

					<ul class="main_nav">

						<li class="current"><a href="index.php">Home</a></li>

						<li><a href="rozklad.php">Sprawdź rozkład</a></li>

						<li><a href="wyszukaj.php">Wyszukiwarka połączeń</a></li>

						<li><a href="kontakt.php">Kontakt</a></li>

					</ul>

				</nav>

			</div>

		</header>


		
		<section class = "ulubione caption">
		<div class = "ulubione-tytul"><h2>Najbliższe ulubione kursy</h2></div>
				<div class = "ulubione-godzina">Dziś jest <span class ="data"><?php echo date("d.m.Y");?></span></span>, godzina <span class ="czas"><?php echo date("H:i");?></span></div>
				<table class ="ulubione-kursy">
					<tr class ="odd first"><td class = "tabelaLabel">Linia</td><td class = "tabelaLabel">Przystanek początkowy</td><td class = "tabelaLabel">Przystanek końcowy</td><td class = "tabelaLabel">Godzina</td><td class = "tabelaLabel">Odjazd za</td></tr>
		<?php 
		
		$limit = 8;
				foreach($xxx as $key => $x) {
				if( $key >= $limit ) break;
				//echo $x['liniaId'].' - '.$x['przystanekPoczatowy'].'<br />';
					$idPoczatek = $x['przystanekPoczatowy'];
					$liniaId = $x['liniaId'];
					$przyst = $x['przystanekPoczatowy'];
					
			$query = "SELECT * FROM przystankiOdjazdy as po LEFT JOIN przystanki as p ON(po.przystanekId = p.id) WHERE po.odjazdId IN (SELECT po.odjazdId FROM `przystankiOdjazdy` as po LEFT JOIN `odjazdy` as o ON (po.odjazdId = o.id) WHERE po.przystanekId = {$idPoczatek} AND o.liniaId = '{$liniaId}' AND po.przystanekPoczatkowy = 1)";
			$result = mysql_query($query);
			$data = array();
			while ($row = mysql_fetch_array($result)) {
				$data[] = $row;
			}
			
			$przystanki = array();
			
			foreach ($data as $przystanek) {
				if (! isset($przystanki[$przystanek['przystanekId']])) {
					$przystanki[$przystanek['przystanekId']] = array(
						'id' => $przystanek['przystanekId'],
						'nazwa' => $przystanek['nazwa'],
						'odjazdy' => array()
					);
				}
				$przystanki[$przystanek['przystanekId']]['odjazdy'][] = $przystanek['godzina'];
			}
			 $k = 1;
			foreach ($przystanki as $przystanek) {
				$ile = count($przystanki);
				if ($k == $ile) {
					$docel = $przystanek['id'];
				}
				$k ++;
			}
			//echo $idPoczatek.' || '.$docel;
			//echo "SELECT * FROM przystanki WHERE id='".$idPoczatek."';";
			
			$quee = mysql_query("SELECT * FROM przystanki WHERE id='{$idPoczatek}'");
			$rowww = mysql_fetch_array($quee, MYSQL_ASSOC);
			
			$quee2 = mysql_query("SELECT * FROM przystanki WHERE id='{$docel}'");
			$rowww2 = mysql_fetch_array($quee2, MYSQL_ASSOC);
			
			 $godz_aktual = date('H');
			$min_aktual = date('i');
			$godzina = date('H:i:s');
			
			
			$q = "SELECT godzina FROM odjazdy WHERE przystanekPoczatkowy = '{$idPoczatek}' AND liniaId = '{$liniaId}' AND godzina >= '{$godzina}'";
			
			$quee3 = mysql_query($q);
			$rowww3 = mysql_fetch_array($quee3, MYSQL_ASSOC);
			$time1 = time();
			$time2 = strtotime(date('Y-m-d').' '.$rowww3['godzina']);
			
			$time = $time2 - $time1;
			
			if( $time > 3600 ) {
			    $timeO = round($time / 3600) . ' h';
			} else {
			    $timeO = round($time/60) . ' min';
			}
			
			echo '<tr class ="even"><td>'.$liniaId.'</td><td>'.$rowww['nazwa'].'</td><td>'.$rowww2['nazwa'].'<td>'.$rowww3['godzina'].'</td><td>'.$timeO.'</td></tr>';
			
		}
	?>
		
				
				
				</table>
				
		</section>

	</section><!-- Billboard End -->

	

	<section class="services wrapper">

			<div class="S_icons">

				<a href="rozklad.php"><img src="img/S1.png" alt="" title=""/></a>

				<hr class="sp"/>

				<a href="wyszukaj.php"><img src="img/S2.png" alt="" title=""/></a>

				<hr class="sp"/>

				<a href ="kontakt.php"><img src="img/S3.png" alt="" title=""/></a>

			</div>

			<ul class="desc">

				<li>

					<h3>Zobacz rozkład jazdy linii</h3>

					<!--<p>Nasz system wygląda przepięknie, prawda?</p> -->

				</li>

				<li>

					<h3>Wyszukaj połączenie</h3>

					<!--<p>Szybkość naszego serwisu jest piorunująca, nie musisz czekać 5 sekund na kolejną stronę ;)</p>-->

				</li>

				<li>

					<h3>Skontaktuj się z nami</h3>

					<!--<p>Pracujemy w pocie czoła, abyś miał jak najmniej powodów do narzekania na Nas :)</p>-->

				</li>

			</ul>
		<section class="caption_without_abs caption">

			<p class="cap_title">Informator MPK Kraków</p>

			<p class="cap_desc">made by Tomaszewski, Sokulski, Szydlak</p>

		</section>
			

	</section><!-- services End -->



	<footer>

		<hr class="footer_sp"/>

		<p class="rights">Copyright© 2015 Tomaszewski, Sokulski, Szydlak</p>

	</footer>



</body>

</html>