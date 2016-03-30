<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');
//session_start(); //we need to call PHP's session object to access it through CI
class Home extends CI_Controller {
		public function __construct(){
		parent::__construct();
			$this->load->model('User');
			$this->load->library('table');
			$this->load->helper( array('form', 'url'));
		}
		
 
 function index()
 {
   if($this->session->userdata('logged_in'))
   {
     $session_data = $this->session->userdata('logged_in');
	 $session_data['stworzone']=true;
	 
	 $proj=array();
	 for ($i=1; $i<=3; $i++){
		array_push($proj, $this->User->projects( $session_data['id'],$i));
	 }
	 //if($proj[0])
		//echo 'dziala'; else echo 'nie dziala';
	 
	 $session_data['stworzone']=$proj[0];
	 $session_data['obserwowane']=$proj[2];
	 $session_data['wykonywane']=$proj[1];
	 
	 //print_r ($proj[1]);
       $result = $this->User->show_data($session_data['id']);
       $session_data['name']=$result['name'];
	 $this->load->view('home_view', $session_data);
	
   }
   else
   {
     //If no session, redirect to login page
     redirect('login', 'refresh');
   }
 }
 
 function logout()
 {
   $this->session->unset_userdata('logged_in');
   session_destroy();
   redirect('home', 'refresh');
 }
 

}
 
?>