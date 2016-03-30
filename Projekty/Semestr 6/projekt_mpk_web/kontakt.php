<!doctype html>
<?php
    $db = mysqli_connect('hostname','username','password','databasename');
	mysql_query("SET NAMES 'utf8'");
	
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
		<script type="text/javascript" src="scripts/magnific-popup/dist/jquery.magnific-popup.js"></script>
		<script type="text/javascript" src="scripts/popup.js"></script>
		<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
		<script type="text/javascript" src="scripts/validation.js"></script>
		<!---STYLESHEETS--->
		<link rel="stylesheet" type="text/css" href="css/style.css" media="all">
		<link rel="stylesheet" type="text/css" href="scripts/magnific-popup/dist/magnific-popup.css" media="all">
		<link rel="stylesheet" type="text/css" href="css/reset.css" media="all">
	</head>
	<body>
		<section class="billboard">

			<header>

				<div class="wrapper">

					<a href="index.php"><img src="img/logo.png" class="h_logo" alt="" title=""></a>

					<nav>

						<ul class="main_nav">

							<li><a href="index.php">Home</a></li>

							<li><a href="rozklad.php">Sprawdź rozkład</a></li>

							<li><a href="wyszukaj.php">Wyszukiwarka połączeń</a></li>

							<li class="current"><a href="kontakt.php">Kontakt</a></li>

						</ul>

					</nav>

				</div>

			</header>



			<section class="caption">

				<div class ="rozklad">
				
					<div class ="page-title">

						<h2>Kontakt</h2>

					</div>
				
					<div id="formularz">
						
						<form name="enq" method="post" action="kontakt_php.php" onsubmit="return validation();">
								<fieldset>
								    
								<input type="text" name="name" id="name" value=""  class="input-block-level" placeholder="Imię i nazwisko" />
								<input type="text" name="email" id="email" value="" class="input-block-level" placeholder="Email" />
								<textarea rows="11" name="message" id="message" class="input-block-level" placeholder="Treść wiadomości"></textarea>
								   
								<div class="sumbit">
									<input type="submit" value="Wyślij" name="submit" id="button-blue" class="btn btn-info pull-right" title="Kliknij, aby wysłać wiadomość!" />
									<div class = "ease">
									</div>
								</div>
									
								</fieldset>
						</form>
					
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