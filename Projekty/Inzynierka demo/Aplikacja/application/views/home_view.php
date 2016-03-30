<?php include_once('header.php') ?>
    <h2>Witaj <?php echo $name; ?>!</h2>
	<?php include_once('nav_icon.php') ?>
	<ul class="nav nav-pills mainDiv" id="selectorsDiv"  >
		  <li role="presentation" id="btn1" onclick="loadArticle(1)" class="active" ><a href="#">Wykonywane</a></li>
		  <li role="presentation" id="btn2" onclick="loadArticle(2)"><a href="#">Obserwowane</a></li>
		  <li role="presentation" id="btn3" onclick="loadArticle(3)"><a href="#">Stworzone</a></li>
		</ul>
		
		<div id="div1" class="dataDiv">
			<?
            $tmpl = array(
                'table_open'            => '<table class="table table-hover tab-proj">'

            );
				if ($wykonywane){
					//$tmpl = array ( 'table_open'  => '<table class="table table-condensed table-bordered table-hover">' );


					$this->table->set_template($tmpl);
					
					$this->table->set_heading(array('Nazwa', 'Dzień Utworzenia', 'Dzień rozpoczęcia', 'Dzień zakończenia'));
					foreach($wykonywane as $s){
						$cell = array('data' => $s['title'], 'class' => $s['id_proj'], 'name' => 1);
						$this->table->add_row($cell,$s['date_open'],$s['date_start'],$s['date_end']);
					}
					echo $this->table->generate();
				
				
				
				} else echo'<div class="alert alert-info" role="alert">Aktualnie nie jesteś przydzielony do projektów.</div>';
			?>
		</div>
		<div id="div2" class="dataDiv">
			<?
				if ($obserwowane){
					//$tmpl = array ( 'table_open'  => '<table class="table table-condensed table-bordered table-hover">' );
					$this->table->set_template($tmpl);
					
					$this->table->set_heading(array('Nazwa', 'Dzień Utworzenia', 'Dzień rozpoczęcia', 'Dzień zakończenia'));
					foreach($obserwowane as $s){
						$cell = array('data' => $s['title'], 'class' => $s['id_proj'], 'name' => 2);
						$this->table->add_row($cell,$s['date_open'],$s['date_start'],$s['date_end']);
					}
					echo $this->table->generate();
				
				
				
				} else echo'<div class="alert alert-info" role="alert">Aktualnie nie obserwujesz projektów.</div>';
			?>
		</div>
		<div id="div3" class="dataDiv">
			<?
			
				if ($stworzone){
					//$tmpl = array ( 'table_open'  => '<table class="table table-condensed table-bordered table-hover">' );

					$this->table->set_template($tmpl);
					
					$this->table->set_heading(array('Nazwa', 'Dzień Utworzenia', 'Dzień rozpoczęcia', 'Dzień zakończenia'));
					foreach($stworzone as $s){
						$cell = array('data' => $s['title'], 'class' => $s['id_proj']);
//						$this->table->add_row($s['id_proj'],$s['title'],$s['date_open'],$s['date_start'],$s['date_end'],'<a href="project?row='.$s['id_proj'].'">'..'</a>');
						$this->table->add_row($cell,$s['date_open'],$s['date_start'],$s['date_end']);
					}
					echo $this->table->generate();
					echo'<a class="btn btn-info" role="button" data-toggle="modal" data-target="#myModal">DODAJ KOLEJNY</a>';
				} else 
					echo'<div class="alert alert-info" role="alert">Nie stworzyłeś do tej pory projektu.</div><a class="btn btn-info" role="button" data-toggle="modal" data-target="#myModal">DODAJ PIERWSZY PROJEKT</a>';
			?>

			<!--================================================================================  Modal -->
			<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
				<div class="modal-dialog" >
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
							<h4 class="modal-title" id="myModalLabel">Nowy projekt</h4>
						</div>
						<div class="modal-body">
							<form method="POST" accept-charset="utf-8" >

								<input type="name" class="form-control displ" id="rejestr1" placeholder="Nazwa projektu" name="name" required/><span>*</span>
								<label for="date_start">Data rozpoczęcia projektu</label><input type="date" class="form-control displ" id="rejestr2" placeholder="Data rozpoczęcia" name="date_start" required/><span>*</span>
								<label for="date_end">Data zakończenia projektu</label><input type="date" class="form-control displ" id="rejestr3"  placeholder="Data planowanego zakońćzenia" name="date_end" required /><span>*</span>



								<input type="close" class="btn btn-default" style="width:120px;" data-dismiss="modal" role="button" value="Zamknij"/>
								<input type="submit" id="sendAddProj" class="btn btn-primary" style="width:120px; margin-right: 17px; float:right;" role="button" value="Dodaj"/>

							</form>

						</div>
					</div>
				</div>
			</div>


			<!-- ================================================================================================ -->
		</div>
	
<?php include_once('footer.php') ?>	
