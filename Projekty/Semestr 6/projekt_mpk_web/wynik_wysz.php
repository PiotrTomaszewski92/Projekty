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
		<link rel="stylesheet" href="//code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css">
		  <script src="//code.jquery.com/jquery-1.10.2.js"></script>
		  <script src="//code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
		 <?php
					$query = mysql_query(	"SELECT distinct(liniaId), (select nazwa from przystanki where id=przystanekPoczatkowy) as nazwaa, przystanekPoczatkowy from odjazdy ");
					//$row=mysql_fetch_row(mysql_query($query));
						
					$data = array();
					while($row = mysql_fetch_array($query))
					{
						$data[] = $row['liniaId'].", od: ".$row['nazwaa'];
					}
			?>
			<script type="text/javascript">
				$(function() {
					var availableTags = <?php echo json_encode($data); ?>;
					$( "#tags" ).autocomplete({ source: availableTags });
				});
			</script>

		<!---STYLESHEETS--->

		<link rel="stylesheet" type="text/css" href="css/style.css" media="all">

		<link rel="stylesheet" type="text/css" href="scripts/magnific-popup/dist/magnific-popup.css" media="all">

		<link rel="stylesheet" type="text/css" href="css/reset.css" media="all">

	</head>

	<body class = "rozklad_jazdy">

	<section class="billboard">

		<header>

			<div class="wrapper">

				<a href="index.php"><img src="img/logo.png" class="h_logo" alt="" title=""></a>

				<nav>

					<ul class="main_nav">

						<li><a href="index.php">Home</a></li>

						<li class="current"><a href="rozklad.php">Sprawdź rozkład</a></li>

						<li><a href="wyszukaj.php">Wyszukiwarka połączeń</a></li>

						<li><a href="kontakt.php">Kontakt</a></li>

					</ul>

				</nav>

			</div>

		</header>



		<section class="caption">

			<div class ="rozklad">

				<div class ="page-title">

					<h2>Sprawdź rozkład</h2>

				</div>
				
				<form action="wynik_wysz.php" name="add" method="post" class="wyszukiwarka">
					<table>
							<tr><td> <label for="tags"></label><input type="text" id="tags" class="szukaj" name="search" placeholder="Wyszukaj interesujący Cię numer linii"/></td><td><input type="submit" class="przycisk" name="wyslij" value="Szukaj"/></td></tr>
					</table>
				</form>
				
				<?php
				$dostep=1;
				function multiexplode ($delimiters,$string) {
    
					$ready = str_replace($delimiters, $delimiters[0], $string);
					$launch = explode($delimiters[0], $ready);
					return  $launch;
				}
				
				if(isset($_POST['wyslij'])){
				$do_podzialu=$_POST['search'];
						$vals=multiexplode(array(",","od",":"),$do_podzialu);
						for($i=1;$i<(strlen($vals[3]));$i++) {$nazwa = $nazwa.$vals[3][$i];}
						$result = mysql_query("SELECT * FROM przystanki where nazwa like '".$nazwa."';");
						$row = mysql_fetch_array($result);
							//echo '<br/>'.$row['id'].', nazwa: '.$row['nazwa'].'<br />';
							
				$idPoczatek = $row['id'];
				$liniaId = $vals[0];
				$przyst = $row['id'];
				$dostep=0;
				}
				
				
				else if ((isset($_GET['idd']))&&(isset($_GET['przyst']))&&(isset($_GET['start']))){
					$idPoczatek = $_GET['start'];
					$liniaId = $_GET['idd'];
					$przyst = $_GET['przyst'];
					$dostep=0;
				}
				
				
		
		//echo 'Linia: '.$liniaId.', początek: '.$idPoczatek.', przystanek aktywny: '.$przyst;
		
		if($dostep==0){
		$query = "SELECT * FROM przystankiOdjazdy as po LEFT JOIN przystanki as p ON(po.przystanekId = p.id) WHERE po.odjazdId IN (SELECT po.odjazdId FROM `przystankiOdjazdy` as po LEFT JOIN `odjazdy` as o ON (po.odjazdId = o.id) WHERE po.przystanekId = {$idPoczatek} AND o.liniaId = '{$liniaId}' AND po.przystanekPoczatkowy = 1)";
		$result = mysql_query($query);
		$data = array();
		while($row = mysql_fetch_array($result)) {
			$data[] = $row;
		}
		
		$przystanki = array();
		
		foreach($data as $przystanek) {
			if( !isset($przystanki[$przystanek['przystanekId']]) ) {
				$przystanki[$przystanek['przystanekId']] = array(
					'id' => $przystanek['przystanekId'],
					'nazwa' => $przystanek['nazwa'], 
					'odjazdy' => array()
				);
			}
			$przystanki[$przystanek['przystanekId']]['odjazdy'][] = $przystanek['godzina'];
		}
		
		echo"<br><br>";
		 echo '<div class = "rozklad-numer"><div class ="rozklad-numer-linii">' . $liniaId . '<a class="gwiazdka_new" href="linie.php?linia='.$liniaId .'&poczatek='.$przyst.'&action=add">ala </a></div>';
		$k=1;
		foreach($przystanki as $przystanek){
		$ile = count($przystanki);
		if($k==$ile)
		{echo' <div class ="opis-numer-linii">Kierunek: '.$przystanek['nazwa'].'</div>';
		$docel = $przystanek['id'];}
		$k++;
		}
		$que = mysql_query("SELECT nazwa FROM przystanki WHERE id='".$idPoczatek."';");
		$rowww = mysql_fetch_array($que);
		echo' <div class ="zmiana-trasy"><a href="wynik_wysz.php?idd='.$liniaId.'&start='.$docel.'&przyst='.$docel.'"">(Zmień kierunek na: <b>'.$rowww['nazwa'].'</b>)</a></div></div>';
		echo '<div class = "wykaz">
				<div class = "lista-przystankow">
					<ul class = "przystanki-wypisane">'; 
						$przed=0;
						$ile=count($przystanki);
						$i=0;
						foreach($przystanki as $przystanek){
							
							if ($przystanek['id']==$przyst) { 
								$i=0;
								$przed=1;
								echo'<li><a href="wynik_wysz.php?idd='.$liniaId.'&start='.$idPoczatek.'&przyst='.$przystanek['id'].'"><b>['.$i.']  '.$przystanek['nazwa'].'</b></a></li>';
								$i++;
							}
							else if($przed==1) {
								echo'<li><a href="wynik_wysz.php?idd='.$liniaId.'&start='.$idPoczatek.'&przyst='.$przystanek['id'].'">['.$i.']  '.$przystanek['nazwa'].'</a></li>';
								$i++;
							}
							else{
								echo'<li class="gray"><a href="wynik_wysz.php?idd='.$liniaId.'&start='.$idPoczatek.'&przyst='.$przystanek['id'].'">   '.$przystanek['nazwa'].'</a></li>';
							}
						}
						
						
					echo'</ul>
				</div>
				
				<div class = "rozklad-tablica">
					<table class = "rozklad-jazdy">';
						$godz_aktual=date('H');
						$min_aktual=date('i');
						
						//$godz_aktual=12;
						//$min_aktual=20;
						
						//echo '<div>'.$godz_aktual.':'.$min_aktual.'</div>';
						foreach($przystanki as $przystanek){
							if($przystanek['id']==$przyst) {	
								
									$ile = count($przystanek['odjazdy']);
										$array=preg_split('/[:]/',$przystanek['odjazdy'][0]);
										
										$godzina=$array[0];
										$minuty=$array[1];
										
										$nocne=0;
										$array=preg_split('/[:]/',$przystanek['odjazdy'][$i]);
											if(($array[0]>=23)||($array[0]<4)) $nocne=1;
											//echo 'nocny: '.$nocne;
										//if(($godzina>=23)&&($godzina<=3)) $nocne=1;
										
										/*for($i=0;$i<$ile;$i++){
										$arrayz=preg_split('/[:]/',$przystanek['odjazdy'][$i]);
										$h=$arrayz[0][0];
										$m=$arrayz[0][1];
										
										$arrayzz=preg_split('/[:]/',$przystanek['odjazdy'][($i+1)]);
										$hh=$arrayzz[0][0];
										$mm=$arrayzz[0][1];

										echo 'joł: '.$arrayz[0][0].':'.$arrayz[0][1].' || '.$arrayzz[0][0].':'.$arrayzz[0][1].'<br />';}*/
										//echo 'nocny: '.$nocne;
										echo '<tr><th class = "hour">'.$godzina.'</th><td class = min> ';
										$k=0;
										
										for($i=0;$i<$ile;$i++){
											
											$array=preg_split('/[:]/',$przystanek['odjazdy'][$i]);
											
											$array2=preg_split('/[, ]/',$array[1]);
											if($godzina!=$array[0]) {
												$godzina=$array[0]; 
												
												if(($godzina<$godz_aktual)) {
													echo '</td></tr><tr><th class = "hour">'.$godzina.'</th><td class = "min gray">';	
												}
												else{
													echo '</td></tr><tr><th class = "hour">'.$godzina.'</th><td class = "min">';
												}
											}
											
											foreach($array2 as $a){
											
												if(($a<$min_aktual)&&($array[0]==$godz_aktual)&&($k==0))
													echo '<i>'.$a.'</i>, ';
												if((($a>=$min_aktual)&&($array[0]>=$godz_aktual)&&($k==0))||(($a>=0)&&($array[0]>$godz_aktual)&&($k==0)))
													{echo '<b>'.$a.'</b>, ';$k=1;}
												else if (($k==1)||(($k==0)&&($array[0]!=$godz_aktual)))
													echo $a.', ';
											
											
											}
											//OGARNAC JESCZE NOCNE AUTOBUSY. JAK PIERWSZA GODZINA ODJAZDU JEST 23 TO TAM ZOSTAJE NIEBIESKIE TLO, GDY JEST 00 TO IDZIE ŁADNIE
										}
											
								echo' </td></tr>';
								}					
						}
					echo'</table>
				</div>
			</div>';}
			
			else echo '<p>Sorry,nie masz tu dostępu.</p>';
						
				?>
					

			</div>

		</section>

	</section><!-- Billboard End -->



	<footer>

		<hr class="footer_sp"/>

		<p class="rights">Copyright© 2014 Tomaszewski, Sokulski, Szydlak</p>

	</footer>



</body>

</html>