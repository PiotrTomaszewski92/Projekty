<?php

if(isset($invite)){
    echo form_open('project/invite?row='.$_GET['row']);
        echo'<label for="programmer">Podaj adres e-mail</label>
             <input type="text" size="20" id="programmer" name="programmer" onkeypress="" />
             <input type="submit" value="Dodaj"/>
     </form>';



}
elseif(isset($inviteclient)){

    echo form_open('project/inviteclient?row='.$_GET['row']);
    echo'<label for="boss">Podaj adres e-mail</label>
             <input type="text" size="20" id="boss" name="boss" onkeypress="" />
             <input type="submit" value="Dodaj"/>
     </form>';






}elseif(isset($management)){
    $tmpl = array(
        'table_open' => '<table class="table table-condensed table-bordered table-hover">'
    );
    $this->table->set_template($tmpl);
    $this->table->set_heading(array('Imię i Nazwisko', 'Email', 'Typ'));
    foreach($wynik as $s){
        if($s['type']=='owner')
            $this->table->add_row($s['name'].' '.$s['surname'],$s['email'],$s['type']);
        else
            $this->table->add_row($s['name'].' '.$s['surname'],$s['email'],$s['type'],'<button><a href="management?row='.$_GET['row'].'&del='.$s['id'].'">Przejdz</a></button>');
    }
    echo $this->table->generate();
}else{
    echo'nie znaleziono';
}


?>