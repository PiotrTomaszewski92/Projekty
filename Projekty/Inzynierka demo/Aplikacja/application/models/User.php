<?php
Class User extends CI_Model
{
	function login($username, $password)
	{
		$this -> db -> select('id, username, password, name, email');
		$this -> db -> from('users');
		$this -> db -> where('username = ' . "'" . $username . "'"); 
		$this -> db -> where('password = ' . "'" . MD5($password) . "'"); 
		$this -> db -> limit(1);

		$query = $this -> db -> get();

		if($query -> num_rows() == 1)
		{
			return $query->result();
		}
		else
		{
			return false;
		}

	}
	
	function show_data($id)
	{
		$this -> db -> from('users');
		$this -> db -> where('id = ' .  $id); 
		$this -> db -> limit(1);

		$query = $this -> db -> get();

		if($query -> num_rows() == 1)
		{
			return $query->row_array();
		}
		else
		{
			return false;
		}

	}
	
	function update_data($id,$status,$name,$surname,$email,$telephone,$address)
	{
		$data = array(
				'status' => $status,
				'name' => $name,
				'surname' => $surname,
				'email' => $email,
				'telephone' => $telephone,
				'address' => $address,
				'username' => $email,

		);

		if($id>0){
		$this->db->where('id', $id);
		$this->db->update('users', $data);
		return true;
		}
		else return false;
	}
	
	function checkOldPass($id,$old){
		
		$this -> db -> select('password');
		$this -> db -> from('users');
		$this -> db -> where('id = ' .$id); 
		$this -> db -> where('password = ' . "'" . $old . "'"); 
		$this -> db -> limit(1);
		
		$query = $this -> db -> get();
		if($query -> num_rows() == 1)
		{
			return $query->row_array();
		}
		else
		{
			return false;
		}
	
	}
	
	function newPass($id,$tab)
	{
		$data = array(
           'password' => $tab
        );
		if($id>0){
		$this->db->where('id', $id);
		$this->db->update('users', $data); 
		return true;
		}
		else return false;
	}
	
	function projects($id,$i)
	{
	$this->db->select('*');
	$this->db->from('project');
	$this->db->join('proj_user', 'project.id_proj = proj_user.id_proj');
	$this->db->where('proj_user.id= '.$id);
	$this->db->where('proj_user.id_type= '.$i);

	$query = $this->db->get();
	//echo $query->num_rows()."<br>";
	
	$tab=array();
	if($query->num_rows()>0)
		{
			//$query=$query->row_array();
			//print_r ($query);
			foreach ($query->result_array() as $row)
			{
				array_push($tab,$row);
			}
			
		}
		else
		{
			$tab=null;
		}
	return $tab;
	}

	function addUser($username, $password, $status, $name, $surname, $email, $telephone, $address)
	{
		$data = array(
				'username' => $username ,
				'password' => $password ,
				'status' => $status ,
				'name' => $name ,
				'surname' => $surname ,
				'email' => $email ,
				'telephone' => $telephone ,
				'address' => $address
		);

		$this->db->insert('users', $data);
		return true;

	}

	function ismail($mail){
		$this->db->select('id');
		$this->db->from('users');
		$this->db->where("email= '" . $mail . "'");
		$query = $this->db->get();
		if ($query->num_rows() == 0) {
			return 0;
		}else if ($query->num_rows() > 0){
			return 1;
		} else {
			return -1;
		}

	}

	function remind($mail, $pass){
		$data = array(
				'password' => $pass
		);
		$this->db->where('username', $mail);
		$this->db->update('users', $data);
		return true;

	}
}
?>