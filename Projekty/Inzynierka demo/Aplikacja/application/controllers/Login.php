<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Login extends CI_Controller {

  function __construct()
  {
    parent::__construct();
    $this->load->model('User');
    $this->load->library('email');
  }

  function index()
  {
    $this->load->helper('form');
    $this->load->view('login_view');
  }

  function generuj_haslo($dlugosc){
    $pattern='1234567890qwertyuioplkjhgfdsazxcvbnm';
    $key='';
    for($i=0; $i<$dlugosc; $i++){
      $key.=$pattern{rand(0,35)};
    }
    return $key;
  }

  function registration()
  {
    $wyn = $this->User->addUser($_POST['username'],md5($_POST['password']),$_POST['status'],$_POST['name'],$_POST['surname'],$_POST['email'],$_POST['telephone'],$_POST['address']);
    if($wyn==true)
      return true;
    else
      return false;
  }

  function remind()
  {
    $nowehaslo = $this->generuj_haslo(8);



          $this->email->from('syste@zarzadzanie.pl', 'System zarządzania projektem informatycznym');
          $this->email->to($_POST['email']);
          //$this->email->to('piotr@tomaszew.com');

          $this->email->subject('Przypomnienie hasła');
          $this->email->message('Twoje nowe hasło: '.$nowehaslo);

          $this->email->send();


    $wyn = $this->User->remind($_POST['email'],md5($nowehaslo));
    if($wyn==true)
      echo 1;
    else
      echo 0;
  }

  function ismail(){
    $wyn = $this->User->ismail($_POST['email']);
    if($wyn==0)
      echo 0;
    else if($wyn==1)
      echo 1;
    else
      echo -1;
  }

}

?>