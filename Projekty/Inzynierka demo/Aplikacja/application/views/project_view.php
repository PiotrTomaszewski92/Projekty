<?php include_once('header.php') ?>
<h2>Panel projektu: <?php echo $title ?> </h2>
<?php include_once('nav_icon.php') ?>

<div id="container">
<!--    <div id="contleft">-->
<!--        <ul class="list-group">-->
<!--            <a  --><?php //echo'href="project/invite?row='.$_GET['row'].'">'; ?><!-- <li class="list-group-item">Zaproś do projektu</li></a>-->
<!--            <a --><?php //echo'href="project/inviteclient?row='.$_GET['row'].'">'; ?><!--<li class="list-group-item">Zaproś klienta</li></a>-->
<!--            <a --><?php //echo'href="project/management?row='.$_GET['row'].'">'; ?><!--<li class="list-group-item">Zarządzaj osobami<span class="badge">--><?php //echo $ileUserow; ?><!--</span></li></a>-->
<!--            <a --><?php //echo'href="project/addtask?row='.$_GET['row'].'">'; ?><!--<li class="list-group-item">DODAJ ZADANIA</li></a>-->
<!--        </ul>-->
<!--    </div>-->

    <div id="contright">
        <?php
        echo '<table>';
        echo ' <tr><td><strong>Data utworzenia: </strong>'.$date_open.'<br><strong>Data rozpoczęcia: </strong>'.$date_start.'<br><strong>Data planowego zakończenia: </strong> '.$date_end.'</td><td><strong>Autor: </strong>'.$name.' '.$surname.'<br>'.$status.'<br><strong>Kontakt: </strong>'.$email.', '.$telephone.'</td></tr>';
        echo '</table>';
        ?>

    </div>

    <ul class="nav nav-pills mainDiv">
        <li class="active"><a data-toggle="tab" href="#home">Zaproś do projektu</a></li>
        <li><a data-toggle="tab" href="#menu1">Zaproś klienta</a></li>
        <li><a data-toggle="tab" href="#menu2">Zarządzaj osobami<span class="badge"><?php echo $ileUserow; ?></span></a></li>
        <li><a  <?php echo'href="project/addtask?row='.$_GET['row'].'">'; ?>Dodaj zadania</a></li>
    </ul>

    <div class="tab-content">
        <div id="home" class=" tab-pane fade in dataDiv active">
            <h3>Zaproś pracownika do projektu</h3>
            <?php
            echo form_open('project?row='.$_GET['row'].'&invite');
            echo'    <div class="alert" id="alertprog" role="alert" style="display:none;"></div>
                     <input type="email" size="20" class="form-control" autocomplete="off" id="programmer" name="programmer" placeholder="Podaj adres e-mail" onkeypress="" />
                     <input type="submit" class="btn btn-primary btn-lg" disabled="disabled" role="button" value="Dodaj"/>
                </form>';

            ?>

        </div>
        <div id="menu1" class="tab-pane fade dataDiv">
            <h3>Zaproś klienta</h3>
            <?php
            echo form_open('project?row='.$_GET['row'].'&inviteclient');
            echo'<div class="alert" id="alertboss" role="alert" style="display:none;"></div>
                 <input type="email" size="20" class="form-control" id="boss" autocomplete="off" name="boss" placeholder="Podaj adres e-mail" onkeypress="" />
                 <input type="submit" class="btn btn-primary btn-lg" disabled="disabled" role="button" value="Dodaj"/>
                </form>';
            ?>
        </div>
        <div id="menu2" class="tab-pane fade dataDiv">
            <h3>Zarządzaj pracownikami</h3>

            <?php
            $tmpl = array(
                'table_open' => '<table class="table table-condensed table-bordered table-hover">'
            );
            $this->table->set_template($tmpl);
            $this->table->set_heading(array('Imię i Nazwisko', 'Email', 'Typ'));
            foreach($wynik as $s){
                if($s['type']=='właściciel')
                    $this->table->add_row($s['name'].' '.$s['surname'],$s['email'],$s['type']);
                else
                    $this->table->add_row($s['name'].' '.$s['surname'].' <a href="project?row='.$_GET['row'].'&del='.$s['ajdi'].'"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></a>',$s['email'],$s['type']);
            }
            echo $this->table->generate();



            ?>


        </div>


    </div>







</div>