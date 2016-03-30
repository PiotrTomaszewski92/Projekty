<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');
//session_start(); //we need to call PHP's session object to access it through CI
class Config extends CI_Controller {
	public function __construct(){
		parent::__construct();
		$this->load->model('User');
		$this->load->helper(array('form', 'url'));
		$this->load->library('form_validation');
		$this->load->library('email');
		
		
	}
 
 
 function index()
 {
	$session_data = $this->session->userdata('logged_in');
   if($session_data)
   {
     $data['username'] = $session_data['username'];
	 $data['id'] = $session_data['id'];
	 $dane['dane'] =  $this->User->show_data($data['id']);
	 $this->load->helper('form');
	 $this->load->view('config_view', $dane);
   }
   else
   {
     //If no session, redirect to login page
     redirect('login', 'refresh');
   }
 }
 
 function update(){
	$session_data = $this->session->userdata('logged_in');
   if($session_data)
   {
	   $session_data['name']=$_POST['status'];
				if($this->User->update_data($session_data['id'],$_POST['status'],$_POST['name'],$_POST['surname'],$_POST['email'],$_POST['telephone'],$_POST['address']))
				{

					return true;}

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
			$old=$_POST['oldpass'];
			$new=$_POST['newpass'];
			$repass=$_POST['repass'];

//			$old = $this->input->post('oldpass');
//			$new = $this->input->post('newpass');
//				$this->input->post('repass');
					//jeśli w js mozna zrobic spr czy hasla nowe się zgadzaja to pominać tego ifa:
					if ($new===$repass){
//						echo 'Nowe hasła są takie same<br>';
						$query = $this->User->checkOldPass($session_data['id'],md5($old));
						if($query){
							echo'<div class="alert alert-success msgg" role="alert"><strong>Zaktualizowano hasło</strong></div>';
							$query = $this->User->newPass($session_data['id'],md5($new));
							//redirect('config', 'refresh');
						}else{
							echo'<div class="alert alert-danger msgg" role="alert">NIE zaktualizowano - niepoprawne stare hasło</div>';
						}
					}
					else{
						echo '<div class="alert alert-danger msgg" role="alert">NIE zaktualizowano - niepoprawnie wprowadzone nowe hasła</div>';
					}

		}else{
		 //If no session, redirect to login page
		redirect('login', 'refresh');
		}
	}

	function contact(){
		$session_data = $this->session->userdata('logged_in');
		if($session_data) {

				$email = $this->input->post('emailproblem');
				$title = $this->input->post('titleproblem');
				$descript = $this->input->post('textproblem');


					$this->email->from($email, 'System kontaktu z klientem');
					$this->email->to('piotrekha15@gmail.com');
					$this->email->subject($title);
					$this->email->message($descript);
					$this->email->send();


			redirect('home', 'refresh');
		}
		else
		{
			//If no session, redirect to login page
			redirect('login', 'refresh');
		}



	}
}?>