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
                'table_open'            => '<table class="table table-hover table-see">'

            );
            if ($tab2){
                //$tmpl = array ( 'table_open'  => '<table class="table table-condensed table-bordered table-hover">' );


                $this->table->set_template($tmpl);
                $okna='';
                $this->table->set_heading(array('Nazwa', 'Opis', ''));
                foreach($tab2 as $s){

                    if(($s['checked']==1)&&($s['iscorrect']==null))
                        $lapki = '<a href="#"><span class="glyphicon glyphicon-thumbs-up" aria-hidden="true"></span></a><a href="#" data-toggle="modal" data-target="#myModal'.$s['id'].'"><span class="glyphicon glyphicon-thumbs-down" aria-hidden="true"></span></a>';
                    else if(($s['checked']==1)&&($s['iscorrect']==-1))
                        $lapki = '<span class="glyphicon glyphicon-remove" style="color:red; margin-right: 15px;" aria-hidden="true"></span><a href="#"><span class="glyphicon glyphicon-thumbs-up" aria-hidden="true"></span></a>';
                    else
                        $lapki= '<span class="glyphicon glyphicon-ok" style="color:green;" aria-hidden="true"></span>';

                    $okna.='<div class="modal fade" id="myModal'.$s['id'].'" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                                    <div class="modal-dialog" >
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                                <h4 class="modal-title" id="myModalLabel">'.$s['title'].'</h4>
                                            </div>
                                            <div class="modal-body">
                                            <form method="POST" accept-charset="utf-8" >
                                                   <textarea  class="form-control" rows="3" id="correctDescript'.$s['id'].'" placeholder="Wiadomość dla programisty" name="correctDescript"></textarea>

                                                    <input type="close" class="btn btn-default" style="width:120px; margin-top:10px;" data-dismiss="modal" role="button" value="Zamknij"/>
                                                    <input type="submit" id="sendCorrectDescript" key="'.$s['id'].'" class="btn btn-primary" style="width:120px; margin-top:10px; float:right;" role="button" value="Dodaj"/>

                                            </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>';


                    $cell = array('data' => $s['title'], 'class' => $s['id']);
                    $ikonki = array('data' => $lapki, 'class' => $s['id']);
                    $this->table->add_row($cell,$s['description'],$ikonki);
                }
                echo $this->table->generate();

                echo $okna;



            } else echo'<div class="alert alert-info" role="alert">Do tej pory nie wykonano zadań do sprawdzenia.</div>';
            ?>

        </div>














    </div>