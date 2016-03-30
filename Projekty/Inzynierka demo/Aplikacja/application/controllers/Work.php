<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');
//session_start(); //we need to call PHP's session object to access it through CI
class Work extends CI_Controller {
    public function __construct(){
        parent::__construct();

        $this->load->model('Projecting');
        $this->load->model('Work_see');
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
            $tab['tab2'] = $this->Work_see->showTasks($_GET['row'],$data['id']); //2 - pracownik, 3 - klient
            $tab['tab'] = $this->Work_see->showAutor($_GET['row']);
            $tab['show'] = $this->Projecting->showProjectForOthers($_GET['row']);
//echo ($_GET['row'].' - '.$data['id']);
//            echo'<pre>';
//            print_r($tab['show']);
//            echo'</pre>';

            if($tab){
                $this->load->helper('form');
                $this->load->view('work_view',  $tab);


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

    function done()
    {
        $session_data = $this->session->userdata('logged_in');
        if($session_data)
        {
            $row = $_POST['wor'];
            if($this->Work_see->done($_POST['idd'],$row.''))
                echo '<div class="alert alert-success" role="alert">Zaktualizowano</div>';
            else
                echo '<div class="alert alert-danger" role="alert">Wystąpił błąd</div>';
        }
        else
        {
            //If no session, redirect to login page
            redirect('login', 'refresh');
        }

    }

}?>