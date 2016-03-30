<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');
//session_start(); //we need to call PHP's session object to access it through CI
class Addproj extends CI_Controller {
	public function __construct(){
		parent::__construct();
		$this->load->model('Projects');
		$this->load->helper(array('form', 'url'));
		$this->load->library('form_validation');
		
	}
 
 
 function index()
 {
	$session_data = $this->session->userdata('logged_in');
   if($session_data)
   {
	 $this->load->view('addproj_view');
   }
   else
   {
     //If no session, redirect to login page
     redirect('login', 'refresh');
   }
 }
 
  function add()
 {
	$session_data = $this->session->userdata('logged_in');
   if($session_data)
   {
	   	echo $_POST['name'].', '.$_POST['date_start'].', '.$_POST['date_end'];
			if($this->Projects->add($session_data['id'],$_POST['name'],$_POST['date_start'],$_POST['date_end']))
				return 1;
	   		else
		   		return 0;

   }
   else
   {
     //If no session, redirect to login page
     redirect('login', 'refresh');
   }
 }
/* function update(){
	$session_data = $this->session->userdata('logged_in');
   if($session_data)
   {
		$this->form_validation->set_rules('name', 'Imi�', 'required|xss_clean');
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
				$this->form_validation->set_message('required', '<div class="alert alert-danger" role="alert">Uzupe�nij wszystkie pola</div>');
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
					//je�li w js mozna zrobic spr czy hasla nowe si� zgadzaja to pomina� tego ifa:
					if ($new===$this->input->post('repass')){
						echo 'Nowe has�a s� takie same<br>';
						$query = $this->User->checkOldPass($session_data['id'],md5($old));
						if($query){
							echo'has�o z bazy poprawne<br>';
							$query = $this->User->newPass($session_data['id'],md5($new));
							//redirect('config', 'refresh');
						}else{
							echo'has�o z bazy niepoprawne<br>';
						}
					}
					else{
						echo 'Nowe has�a nie s� takie same<br>';
					}
			}else{
					echo 'error';
					//$this->form_validation->set_message('required', '<div class="alert alert-danger" role="alert">Uzupe�nij wszystkie pola</div>');
			}
		}else{
		 //If no session, redirect to login page
		redirect('login', 'refresh');
		}
	} 
	*/
}?>