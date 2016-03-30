<?php include_once('header.php') ?>
<h2>Panel projektu: <?php echo $tab['title']; ?> </h2>
<?php include_once('nav_icon.php') ?>

<div id="container">


    <div id="contright">
        <?php
        echo '<table>';
        echo ' <tr><td><strong>Data utworzenia: </strong>'.$show['date_open'].'<br><strong>Data rozpoczęcia: </strong>'.$show['date_start'].'<br><strong>Data planowego zakończenia: </strong> '.$show['date_end'].'</td><td><strong>Autor: </strong>'.$show['name'].' '.$show['surname'].'<br>'.$show['status'].'<br><strong>Kontakt: </strong>'.$show['email'].', '.$show['telephone'].'</td></tr>';
        echo '</table>';
        ?>

    </div>

    <div class="tab-content">
        <div id="home" class=" tab-pane fade in dataDiv active">
            <h3>Zadania</h3>
<!--            --><?//
            $tmpl = array(
                'table_open'            => '<table class="table table-hover table-tasks">'

            );
            if ($tab2){
                //$tmpl = array ( 'table_open'  => '<table class="table table-condensed table-bordered table-hover">' );

                $okna='';
                $this->table->set_template($tmpl);

                $this->table->set_heading(array('Nazwa', 'Opis', 'Dzień rozpoczęcia', 'Dzień zakończenia', 'Skończone', 'Sprawdzone', 'Potwierdzone'));
                foreach($tab2 as $s){

                    if(($s['done']!=null)&&($s['checked']!=null))
                        $checkbox = '<label class="lab_tab"><input type="checkbox" disabled checked ></label>';
                    else if($s['done']!=null)
                        $checkbox = '<label class="lab_tab"><input type="checkbox" checked  ></label>';
                    else
                        $checkbox = '<label class="lab_tab"><input type="checkbox" ></label>';

                    if($s['checked']==1){
                        $logo1='<span class="glyphicon glyphicon-ok" style="color:green;" aria-hidden="true"></span>';
                    }else if($s['checked']==-1){
                        $logo1='<span class="glyphicon glyphicon-remove" style="color:red;" aria-hidden="true"></span>';
                    }else{
                        $logo1='';
                    }

                    if($s['iscorrect']==1){
                        $logo2='<span class="glyphicon glyphicon-ok" style="color:green;" aria-hidden="true"></span>';
                    }else if($s['iscorrect']==-1){
                        $logo2='<a href="#" data-toggle="modal" data-target="#myModal'.$s['id'].'"><span class="glyphicon glyphicon-alert" style="color:red;" aria-hidden="true"></span></a>';
                        $okna.='<div class="modal fade" id="myModal'.$s['id'].'" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                                    <div class="modal-dialog" >
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                                <h4 class="modal-title" id="myModalLabel">'.$s['title'].'</h4>
                                            </div>
                                            <div class="modal-body">'.$s['correctdescript'].'</div>
                                        </div>
                                    </div>
                                </div>';

                    }else{
                        $logo2='';
                    }




                    $cell = array('data' => $s['title'], 'class' => $s['id']);
                    $this->table->add_row($cell,$s['description'],$s['data_start'],$s['data_stop'],$checkbox,$logo1,$logo2);
                }
                echo $this->table->generate();
                echo $okna;


            } else echo'<div class="alert alert-info" role="alert">Aktualnie nie jesteś przydzielony do projektów.</div>';
            ?>

        </div>














</div>