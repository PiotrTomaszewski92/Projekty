<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');
//session_start(); //we need to call PHP's session object to access it through CI
class See extends CI_Controller {
    public function __construct(){
        parent::__construct();

        $this->load->model('Projecting');
        $this->load->model('Work_see');
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
            $tab['tab2'] = $this->Work_see->tasksToClient($_GET['row']); //2 - pracownik, 3 - klient
            $tab['tab'] = $this->Work_see->showAutor($_GET['row']);
            $tab['show'] = $this->Projecting->showProjectForOthers($_GET['row']);

//            echo'<pre>';
//            print_r($tab2);
//            echo'</pre>';

            if($tab){
                $this->load->helper('form');
                $this->load->view('see_view',  $tab);


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

    function good()
    {
        $session_data = $this->session->userdata('logged_in');
        if($session_data)
        {
            $row = $_POST['wor'];
            if($this->Work_see->good($_POST['idd'],$row.''))
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

    function bad()
    {
        $session_data = $this->session->userdata('logged_in');
        if($session_data)
        {
            $proj = $_POST['proj'];
            if($this->Work_see->bad($_POST['zad'],$_POST['messag'],$proj.''))
                echo '<div class="alert alert-danger" role="alert">Zaktualizowano</div>';
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