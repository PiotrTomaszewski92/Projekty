<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');
//session_start(); //we need to call PHP's session object to access it through CI
class Project extends CI_Controller {
    public function __construct(){
        parent::__construct();

        $this->load->model('Projecting');
        $this->load->model('User');
        $this->load->helper(array('form', 'url'));
        $this->load->library('form_validation');
        $this->load->library('table');
        $this->load->library('email');


    }


    function index()
    {
        $session_data = $this->session->userdata('logged_in');
        if($session_data)
        {
            $data['username'] = $session_data['username'];
            $data['id'] = $session_data['id'];
           // $data['id_proj']=$_GET['row'];
           // echo 'weszlo';
            $tab = $this->Projecting->showProject($_GET['row'],$data['id']);


            if (isset($_GET['invite'])){
                $this->invite();
            } else if (isset($_GET['inviteclient'])){
                $this->inviteclient();
            }

            if (isset($_GET['row']) && isset($_GET['del'])) {
                $this->Projecting->delPerson($_GET['row'], $_GET['del']);
            }


            $manag = $this->management();
            $tab['wynik'] =$manag['wynik'];
           // print_r($tab);
            if($tab){
                $tab['ileUserow'] = $this->Projecting->countPeople($_GET['row']);
                $this->load->helper('form');
                $this->load->view('project_view', $tab);


            }else{
                echo 'Nie znaleziono projektu';
            }
        }
        else
        {
            //If no session, redirect to login page
            redirect('login', 'refresh');
        }
    }

    function generuj_haslo($dlugosc){
        $pattern='1234567890qwertyuioplkjhgfdsazxcvbnm';
        $key='';
        for($i=0; $i<$dlugosc; $i++){
            $key.=$pattern{rand(0,35)};
        }
        return $key;
    }

    function registration($username,$password,$status,$name,$surname,$email,$telephone,$address)
    {
        $wyn = $this->User->addUser($username,md5($password),$status,$name,$surname,$email,$telephone,$address);
        if($wyn==true)
            return true;
        else
            return false;
    }

    function invite(){
        $session_data = $this->session->userdata('logged_in');
        if($session_data) {
            if ($this->input->post('programmer')) {
                $email = $this->input->post('programmer');
                $ile = $this->Projecting->searchemail($email);

                if($ile>0){
                    $this->email->from('syste@zarzadzanie.pl', 'System zarządzania projektem informatycznym');
                    $this->email->to($email);
                    $this->email->subject('Zaproszenie do projektu');
                    $this->email->message('W systemie do zarządzania projektem informatycznym zostałeś/zostałaś zaproszony/a do projektu. Zaloguj się, aby sprawdzić szczegóły.');
                    $this->email->send();
                    $this->Projecting->addPerson($email, $_GET['row'], 2);
                }else{
                    $pass = $this->generuj_haslo(8);
                    $this->registration($email,$pass,'Nowy pracownik','Anonim','Anonim',$email,0,'');

                    $this->email->from('syste@zarzadzanie.pl', 'System zarządzania projektem informatycznym');
                    $this->email->to($email);
                    $this->email->subject('Zaproszenie do projektu');
                    $this->email->message('W systemie do zarządzania projektem informatycznym zostałeś/zostałaś zaproszona do projektu. Nie posiadasz jeszcze konta. dlatego system wygenerował dla Ciebie dane logowania. Twój login to adres email. HASŁO: '.$pass);
                    $this->email->send();
                    $this->Projecting->addPerson($email, $_GET['row'], 2);
                }


            } elseif (isset($_GET['row'])) {
                $tab['invite'] = 1;
                $this->load->helper('form');
                $this->load->view('inv_manage_view', $tab);
            } else {
                echo 'musi być nr row ';
            }
        }
        else
        {
            //If no session, redirect to login page
            redirect('login', 'refresh');
        }
    }

    function inviteclient(){
        $session_data = $this->session->userdata('logged_in');
        if($session_data) {
            if ($this->input->post('boss')) {

                $email = $this->input->post('boss');
                $ile = $this->Projecting->searchemail($email);

                if($ile>0){
                    $this->email->from('syste@zarzadzanie.pl', 'System zarządzania projektem informatycznym');
                    $this->email->to($email);
                    $this->email->subject('Zaproszenie do projektu');
                    $this->email->message('W systemie do zarządzania projektem informatycznym zostałeś/zostałaś zaproszony/a do projektu. Zaloguj się, aby sprawdzić szczegóły.');
                    $this->email->send();
                    $this->Projecting->addPerson($email, $_GET['row'], 3);
                }else{
                    $pass = $this->generuj_haslo(8);
                    $this->registration($email,$pass,'Nowy pracownik','Anonim','Anonim',$email,0,'');

                    $this->email->from('syste@zarzadzanie.pl', 'System zarządzania projektem informatycznym');
                    $this->email->to($email);
                    $this->email->subject('Zaproszenie do projektu');
                    $this->email->message('W systemie do zarządzania projektem informatycznym zostałeś/zostałaś zaproszona do projektu. Nie posiadasz jeszcze konta. dlatego system wygenerował dla Ciebie dane logowania. Twój login to adres email. HASŁO: '.$pass);
                    $this->email->send();
                    $this->Projecting->addPerson($email, $_GET['row'], 3);
                }

            } elseif (isset($_GET['row'])) {
                $tab['inviteclient'] = 1;
                $this->load->helper('form');
                $this->load->view('inv_manage_view', $tab);
            } else {
                echo 'musi być nr row ';
            }
        }
        else
        {
            //If no session, redirect to login page
            redirect('login', 'refresh');
        }
    }

    function management(){

        $session_data = $this->session->userdata('logged_in');
        if($session_data) {
            // if($this->input->post('boss')){
            //$wynik=$this->Projecting->addPerson($this->input->post('boss'),$_GET['row'],2);
            // echo 'Wynik: '.$wynik;

            if (isset($_GET['row']) && isset($_GET['del'])) {
                $wynik = $this->Projecting->delPerson($_GET['row'], $_GET['del']);
            }

            if (isset($_GET['row'])) {
                $tab['management'] = 1;
                $tab['wynik'] = $this->Projecting->showPeople($_GET['row']);

                return $tab;
            } else {
                echo 'musi być nr row ';
            }
        }
        else
        {
            //If no session, redirect to login page
            redirect('login', 'refresh');
        }
    }

    function addtask(){

        $session_data = $this->session->userdata('logged_in');

        if($session_data) {

            if (isset($_GET['row'])) {
                $tab['management'] = 1;
                $tab = $this->Projecting->showProject($_GET['row'],$session_data['id']);

                echo'   <link rel="stylesheet" href="'.base_url('css/style.css').'" >
                        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
                        <script src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
                        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>';

                echo '<body id="header_grey"><h2>Zadania projektu: '.$tab['title'].'</h2>
                <a href="../project?row='.$_GET['row'].'"><button type="button" class="btn btn-primary trzytrzy"><span class="glyphicon glyphicon-menu-left" aria-hidden="true"></span>Wróc</button></a>
                <button type="button" class="btn btn-success trzytrzy zapiszwszystko"><span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span> Zapisz wszystko</button>
                <button type="button" class="btn btn-danger trzytrzy usunwiersz"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span> Usuń wiersz</button>

                ';

                $tasks['tasks']= $this->Projecting->getTasks($_GET['row']);

                $this->load->view('addtask_view',$tasks);
            } else {
                echo 'musi być nr row ';
            }
        }
        else
        {
            //If no session, redirect to login page
            redirect('login', 'refresh');
        }


    }

    function delRows(){
        $session_data = $this->session->userdata('logged_in');
        if($session_data) {
            if (isset($_POST['row'])) {
                $tab=$_POST['row2'];
                if (count($tab)==1){
                    $this->Projecting->delRows($tab, $_POST['row']);
                }
                else {

                    for ($i = 0; $i < count($tab); $i++) {
                        $this->Projecting->delRows($tab[$i], $_POST['row']);
                    }
                }
            } else {
                echo 'musi być nr row ';
            }
        }
        else
        {
            //If no session, redirect to login page
            redirect('login', 'refresh');
        }
    }

    function searchemail(){
        $session_data = $this->session->userdata('logged_in');
        if($session_data) {
                $mejl = $this->Projecting->searchemail($_POST['mail']);
                if ($mejl == 1) {
                    echo 1;
                } else {
                    echo 0;
                }

        }
        else
        {
            //If no session, redirect to login page
            redirect('login', 'refresh');
        }

    }

    function showMe(){
        $session_data = $this->session->userdata('logged_in');
        if($session_data) {
            $uzytkownicy=$this->Projecting->showDeploy($_POST['row']);
            $uzytkownicy.="|||||";
            $max=$this->Projecting->showMax($_POST['row']);
            $uzytkownicy.=$max;

            $this->Projecting->addRowTask($_POST['row'],$_POST['rodzic'],$max);
            echo $uzytkownicy;
        }
        else
        {
            //If no session, redirect to login page
            redirect('login', 'refresh');
        }

    }

    function updateTask(){
        $session_data = $this->session->userdata('logged_in');
        if($session_data) {
            $this->Projecting->updateTask($_POST['row'],$_POST['ajdi'],$_POST['dejta']);
        }
        else
        {
            //If no session, redirect to login page
            redirect('login', 'refresh');
        }

    }

    function updateCheck(){
        $session_data = $this->session->userdata('logged_in');
        if($session_data) {
            $this->Projecting->updateCheck($_POST['row'],$_POST['task'],$_POST['wart']);
        }
        else
        {
            //If no session, redirect to login page
            redirect('login', 'refresh');
        }

    }

    /*function update(){
        $session_data = $this->session->userdata('logged_in');
        if($session_data)
        {
            $this->form_validation->set_rules('name', 'Imię', 'required|xss_clean');
            $this->form_validation->set_rules('surname', 'Nazwisko', 'required|xss_clean');
            $this->form_validation->set_rules('email', 'Email', 'required|xss_clean');
            $this->form_validation->set_rules('telephone', 'Telefon', 'required|xss_clean');

            if ($this->form_validation->run() == TRUE)
            {
                $this->input->post('name');
                $this->input->post('surname');
                $this->input->post('email');
                $this->input->post('telephone');
                $this->input->post('address');

                $tab =  $this->input->post(array('name', 'surname', 'email', 'telephone', 'address'), TRUE);
                $tab['username'] = $this->input->post('email');
                //echo var_dump($tab);
                $this->User->update_data($session_data['id'],$tab);
                redirect('config', 'refresh');
            }else{
                echo 'error';
                $this->form_validation->set_message('required', '<div class="alert alert-danger" role="alert">Uzupełnij wszystkie pola</div>');
            }
        }
        else{
            //If no session, redirect to login page
            redirect('login', 'refresh');
        }
    }

    function password(){
        $session_data = $this->session->userdata('logged_in');
        if($session_data)
        {
            $this->form_validation->set_rules('oldpass', 'Password', 'required');
            $this->form_validation->set_rules('newpass', 'New Password', 'required');
            $this->form_validation->set_rules('repass', 'Retype Password', 'required');

            if ($this->form_validation->run() == TRUE)
            {
                $old = $this->input->post('oldpass');
                $new = $this->input->post('newpass');
                $this->input->post('repass');
                //jeśli w js mozna zrobic spr czy hasla nowe się zgadzaja to pominać tego ifa:
                if ($new===$this->input->post('repass')){
                    echo 'Nowe hasła są takie same<br>';
                    $query = $this->User->checkOldPass($session_data['id'],md5($old));
                    if($query){
                        echo'hasło z bazy poprawne<br>';
                        $query = $this->User->newPass($session_data['id'],md5($new));
                        //redirect('config', 'refresh');
                    }else{
                        echo'hasło z bazy niepoprawne<br>';
                    }
                }
                else{
                    echo 'Nowe hasła nie są takie same<br>';
                }
            }else{
                echo 'error';
                //$this->form_validation->set_message('required', '<div class="alert alert-danger" role="alert">Uzupełnij wszystkie pola</div>');
            }
        }else{
            //If no session, redirect to login page
            redirect('login', 'refresh');
        }
    }
    */
}?>