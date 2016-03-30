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
					// Check connection
					
					
					echo "<div class = tramwaje>";
					
					$result = mysql_query("SELECT * FROM linie where (typ = 'tramwaj') AND (id between 0 and 59);");
					
					echo "<div class = tramwaj>";
					
					echo "<h3>Linie tramwajowe</h3>";
					
					while($row = mysql_fetch_array($result))
					{
						echo "<div class = linia>";	
						echo '<a class = "popup-youtube" href="linie.php?id='. $row['id'] .'">' . $row['id'] . '</a>';
						echo "</div>";
					}
					
					$result1 = mysql_query("SELECT * FROM linie where (typ = 'tramwaj') AND (id between 70 and 79)");
					
					echo "<div class = tramwaj>";
					
					echo "<h3>Linie tramwajowe zastępcze</h3>";
					
					while($row = mysql_fetch_array($result1))
					{
						echo "<div class = linia>";	
						echo '<a class = "popup-youtube" href="linie.php?id='. $row['id'] .'">' . $row['id'] . '</a>';
						echo "</div>";
					}
		
					$result2 = mysql_query("SELECT * FROM linie where (typ = 'tramwaj') AND (id between 60 and 69)");
					
					echo "<div class = tramwaj>";
					
					echo "<h3>Linie tramwajowe nocne</h3>";
					
					while($row = mysql_fetch_array($result2))
					{
						echo "<div class = linia>";	
						echo '<a class = "popup-youtube" href="linie.php?id='. $row['id'] .'">' . $row['id'] . '</a>';
						echo "</div>";
					}
					
					echo "</div>";
					echo "</div>";
					echo "</div>";
					echo "</div>";
					
					echo "<div class = autobusy>";
					
					$result3 = mysql_query("SELECT * FROM linie where (typ = 'autobus') AND (id between 100 AND 199)");
					
					echo "<div class = autobus>";
					
					echo "<h3>Linie autobusowe miejskie</h3>";
					
					while($row = mysql_fetch_array($result3))
					{
						echo "<div class = linia>";
						echo '<a class = "popup-youtube" href="linie.php?id=' . $row['id'] . '">' . $row['id'] . '</a>';
						echo "</div>";
					}
					
					$result4 = mysql_query("SELECT * FROM linie where (typ = 'autobus') AND (id between 200 AND 299)");
					
					echo "<div class = autobus>";
					
					echo "<h3>Linie autobusowe aglomeracyjne</h3>";
					
					while($row = mysql_fetch_array($result4))
					{
						echo "<div class = linia>";
						echo '<a class = "popup-youtube" href="linie.php?id='. $row['id'] .'">' . $row['id'] . '</a>';
						echo "</div>";
					}
					
					$result5 = mysql_query("SELECT * FROM linie where (typ = 'autobus') AND (id between 300 AND 399)");
					
					echo "<div class = autobus>";
					
					echo "<h3>Linie autobusowe aglomeracyjne przyspieszone</h3>";
					
					while($row = mysql_fetch_array($result5))
					{
						echo "<div class = linia>";
						echo '<a class = "popup-youtube" href="linie.php?id='. $row['id'] .'">' . $row['id'] . '</a>';
						echo "</div>";
					}
					
					$result6 = mysql_query("SELECT * FROM linie where (typ = 'autobus') AND (id between 400 AND 499)");
					
					echo "<div class = autobus>";
					
					echo "<h3>Linie autobusowe miejskie wspomagające</h3>";
					
					while($row = mysql_fetch_array($result6))
					{
						echo "<div class = linia>";
						echo '<a class = "popup-youtube" href="linie.php?id='. $row['id'] .'">' . $row['id'] . '</a>';
						echo "</div>";
					}		
					
					$result7 = mysql_query("SELECT * FROM linie where (typ = 'autobus') AND (id between 500 AND 599)");
					
					echo "<div class = autobus>";
					
					echo "<h3>Linie autobusowe miejskie przyspieszone</h3>";
					
					while($row = mysql_fetch_array($result7))
					{
						echo "<div class = linia>";
						echo '<a class = "popup-youtube" href="linie.php?id='. $row['id'] .'">' . $row['id'] . '</a>';
						echo "</div>";
					}
					
					$result8 = mysql_query("SELECT * FROM linie where (typ = 'autobus') AND (id between 600 AND 699)");
					
					echo "<div class = autobus>";
					
					echo "<h3>Linie autobusowe miejskie nocne</h3>";
					
					while($row = mysql_fetch_array($result8))
					{
						echo "<div class = linia>";
						echo '<a class = "popup-youtube" href="linie.php?id='. $row['id'] .'">' . $row['id'] . '</a>';
						echo "</div>";
					}		
					
					$result9 = mysql_query("SELECT * FROM linie where (typ = 'autobus') AND (id between 700 AND 799)");
					
					echo "<div class = autobus>";
					
					echo "<h3>Linie autobusowe zastępcze</h3>";
					
					while($row = mysql_fetch_array($result9))
					{
						echo "<div class = linia>";
						echo '<a class = "popup-youtube" href="linie.php?id='. $row['id'] .'">' . $row['id'] . '</a>';
						echo "</div>";
					}		
					
					$result10 = mysql_query("SELECT * FROM linie where (typ = 'autobus') AND (id between 900 AND 999)");
					
					echo "<div class = autobus>";
					
					echo "<h3>Linie autobusowe aglomeracyjne nocne</h3>";
					
					while($row = mysql_fetch_array($result10))
					{
						echo "<div class = linia>";
						echo '<a class = "popup-youtube" href="linie.php?id='. $row['id'] .'">' . $row['id'] . '</a>';
						echo "</div>";
					}		
					
					echo "</div>";
					echo "</div>";
					echo "</div>";		
					echo "</div>";
					echo "</div>";
					echo "</div>";		
					echo "</div>";
					echo "</div>";
					echo "</div>";
					
					
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