<?php
  if( !empty($_GET['linia']) && !empty($_GET['poczatek']) && !empty($_GET['action']) ) {
        $l = $_GET['linia'];
        $p = $_GET['poczatek'];
        
        switch($_GET['action']) {
            case 'add':
                dodajDoUlubionych($l, $p);
				exit('<p style="text-align:center">Pomyślnie dodano !</p>');
                break;
            case 'delete':
                usunZUlubionych($l, $p);
				exit('<p style="text-align:center">Pomyślnie usunięto !</p>');
                break;
            default: break;
        }
    }
	
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

function dodajDoUlubionych($liniaId, $przystanekPoczatkowy) {
    $current = isset($_COOKIE['fav']) ? explode(',', base64_decode($_COOKIE['fav'])) : array();
    
    if (! in_array($liniaId, $current)) {
        $current[] = $liniaId . '%-%' . $przystanekPoczatkowy;
    }
    
    $lines = base64_encode(implode(',', $current));
    
    $x = setcookie('fav', $lines, time() + 3600 * 24 * 365, '/mpk');
}

function usunZUlubionych($liniaId, $przystanekPoczatkowy) {
    $current = isset($_COOKIE['fav']) ? explode(',', base64_decode($_COOKIE['fav'])) : array();
    
    foreach ($current as $key => $line) {
        if ($line == $liniaId . '%-%' . $przystanekPoczatkowy) {
            unset($current[$key]);
            break;
        }
    }
    
    $current = array_values($current);
    
    $lines = base64_encode(implode(',', $current));
    
    setcookie('fav', $lines, time() + 3600 * 24 * 365, '/mpk');
}
dodajDoUlubionych(1, 973);
?>
<!doctype html>
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



<!---STYLESHEETS--->

<link rel="stylesheet" type="text/css" href="css/style.css" media="all">

<link rel="stylesheet" type="text/css" href="scripts/magnific-popup/dist/magnific-popup.css" media="all">

<link rel="stylesheet" type="text/css" href="css/reset.css" media="all">

</head>

<body class="rozkladzik-tablica">
 
 <?php

mysql_connect('localhost', "elegant_mpk", 'elegant_mpk_admin') or die('error');
mysql_select_db('elegant_mpk') or die('nie moge polaczyc');
mysql_query("SET NAMES utf8");
ini_set('display_errors', '1');

function zwieksz($liiniia) {
    $query = "SELECT * FROM zanteresowanieLinia WHERE liniaId=" . $liiniia;
    
    $result = mysql_query($query);
    $row = mysql_fetch_array($result);
    if ($row != "") {
       // echo $row['liniaId'] . ' - ' . $row['stopienZainteresowania'] . '<br/>';
        $up = $row['stopienZainteresowania'] + 1;
       // echo 'Nowe: ' . $row['liniaId'] . ' - ' . $up;
        mysql_query("UPDATE zanteresowanieLinia SET stopienZainteresowania = '" . $up . "' WHERE liniaId=" . $liiniia .
             ";");
    } else {
        mysql_query("INSERT INTO zanteresowanieLinia VALUES ('" . $liiniia . "',1);");
    }
}

    if( !empty($_GET['linia']) && !empty($_GET['poczatek']) && !empty($_GET['action']) ) {
        $l = $_GET['linia'];
        $p = $_GET['poczatek'];
        
        switch($_GET['action']) {
            case 'add':
                dodajDoUlubionych($l, $p);
                break;
            case 'delete':
                usunZUlubionych($l, $p);
                break;
            default: break;
        }
        
        exit('Pomyślnie dodano !  - Polecam: Piotr Tomaszewski');
    }
	
	
    


if ((isset($_GET['idd'])) && (isset($_GET['przyst'])) && (isset($_GET['start']))) {
    // $do_podzialu=$_POST['search'];
    
    $idPoczatek = $_GET['start'];
    $liniaId = $_GET['idd'];
    $przyst = $_GET['przyst'];
    
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
    $dodaje='add';
	$akc=0;
    echo "<br><br>";
	foreach($xxx as $key => $x) {
		if(($x['liniaId']==$liniaId)&&($x['przystanekPoczatowy']==$przyst)) {$akc=1;}
		
			
	}
	if($akc==1){
	$dodaje='delete';
	echo '<div class = "rozklad-numer"><div class ="rozklad-numer-linii">' . $liniaId . '<a class="gwiazdka_new active"  href="linie.php?linia='.$liniaId .'&poczatek='.$przyst.'&action='.$dodaje.'">ala </a></div>';
	}else {
	
    echo '<div class = "rozklad-numer"><div class ="rozklad-numer-linii">' . $liniaId . '<a class="gwiazdka_new" href="linie.php?linia='.$liniaId .'&poczatek='.$przyst.'&action='.$dodaje.'">ala </a></div>';
	}
   $k = 1;
    foreach ($przystanki as $przystanek) {
        $ile = count($przystanki);
        if ($k == $ile) {
            $docel = $przystanek['id'];
        }
        $k ++;
    }
    $que = mysql_query("SELECT nazwa FROM przystanki WHERE id='" . $idPoczatek . "';");
    $rowww = mysql_fetch_array($que);
    echo ' <div class ="zmiana-trasy"><a href="linie.php?idd=' . $liniaId . '&start=' . $docel . '&przyst=' . $docel .
         '"">(Zmień kierunek na: <b>' . $rowww['nazwa'] . '</b>)</a></div></div>';
    echo '<div class = "wykaz">
				<div class = "lista-przystankow">
					<ul class = "przystanki-wypisane">';
    $przed = 0;
    $ile = count($przystanki);
    $i = 0;
    foreach ($przystanki as $przystanek) {
        
        if ($przystanek['id'] == $przyst) {
            $i = 0;
            $przed = 1;
            echo '<li><a href="linie.php?idd=' . $liniaId . '&start=' . $idPoczatek . '&przyst=' . $przystanek['id'] .
                 '"><b>[' . $i . ']  ' . $przystanek['nazwa'] . '</b></a></li>';
            $i ++;
        } else 
            if ($przed == 1) {
                echo '<li><a href="linie.php?idd=' . $liniaId . '&start=' . $idPoczatek . '&przyst=' . $przystanek['id'] .
                     '">[' . $i . ']  ' . $przystanek['nazwa'] . '</a></li>';
                $i ++;
            } else {
                echo '<li class="gray"><a href="linie.php?idd=' . $liniaId . '&start=' . $idPoczatek . '&przyst=' .
                     $przystanek['id'] . '">   ' . $przystanek['nazwa'] . '</a></li>';
            }
    }
    
    echo '</ul>
				</div>
				
				<div class = "rozklad-tablica">
					<table class = "rozklad-jazdy">';
    $godz_aktual = date('H');
    $min_aktual = date('i');
    
    // $godz_aktual=5;
    // $min_aktual=20;
    
    // echo $godz_aktual.':'.$min_aktual;
    foreach ($przystanki as $przystanek) {
        if ($przystanek['id'] == $przyst) {
            
            $ile = count($przystanek['odjazdy']);
            $array = preg_split('/[:]/', $przystanek['odjazdy'][0]);
            
            $godzina = $array[0];
            $minuty = $array[1];
            $pierwsza_godz = $godzina;
            $nocne = 0;
            $array = preg_split('/[:]/', $przystanek['odjazdy'][$i]);
            if (($array[0] >= 23) || ($array[0] < 4))
                $nocne = 1;
            echo '<tr><th class = "hour">' . $godzina . '</th><td class = "min gray"> ';
            $k = 0;
            
            for ($i = 0; $i < $ile; $i ++) {
                
                $array = preg_split('/[:]/', $przystanek['odjazdy'][$i]);
                
                $array2 = preg_split('/[, ]/', $array[1]);
                if ($godzina != $array[0]) {
                    $godzina = $array[0];
                    
                    if (($godzina < $godz_aktual)) {
                        echo '</td></tr><tr><th class = "hour">' . $godzina . '</th><td class = "min gray">';
                    } else {
                        echo '</td></tr><tr><th class = "hour">' . $godzina . '</th><td class = "min">';
                    }
                }
                
                foreach ($array2 as $a) {
                    
                    if (($a < $min_aktual) && ($array[0] == $godz_aktual) && ($k == 0)) {
                        echo '<i>' . $a . '</i>, ';
                    }
                    
                    if ((($a >= $min_aktual) && ($array[0] >= $godz_aktual) && ($k == 0)) ||
                         (($a >= 0) && ($array[0] > $godz_aktual) && ($k == 0))) {
                        echo '<b>' . $a . '</b>, ';
                        $k = 1;
                    } else 
                        if (($k == 1) || (($k == 0) && ($array[0] != $godz_aktual)))
                            echo $a . ', ';
                }
                // OGARNAC JESCZE NOCNE AUTOBUSY. JAK PIERWSZA GODZINA ODJAZDU JEST 23 TO TAM ZOSTAJE NIEBIESKIE TLO, GDY JEST 00 TO IDZIE ŁADNIE
            }
            
            echo ' </td></tr>';
        }
    }
    echo '</table>
				</div>
			</div>';
} 

else 
    if (isset($_GET['id']) && (! isset($_GET['przystanek']))) {
        $id = $_GET['id'];
        
        zwieksz($id);
        echo '<div class = "przystanek-kierunek">
		<h3 class ="kierunek">Wybierz przystanek początkowy</h3>';
        
        $result = mysql_query(
            "SELECT distinct(liniaId), (select nazwa from przystanki where id=przystanekPoczatkowy) as nazwaa, przystanekPoczatkowy from odjazdy  where liniaId=" .
                 $id . ";");
        while ($row = mysql_fetch_array($result)) {
            echo '<p><a class ="kierunek-nazwa" href="linie.php?idd=' . $row['liniaId'] . '&start=' .
                 $row['przystanekPoczatkowy'] . '&przyst=' . $row['przystanekPoczatkowy'] . '">' . $row['nazwaa'] .
                 '</a></p><br />';
        }
        echo '</div>';
    }
?>
	
	
	
</body>

</html>