<?php
Class Projects extends CI_Model
{
	/**
	 * @param $id
	 * @param $tab
	 * @return bool
     */
	function add($id, $name, $datastart, $dataend)
	{
		$this->load->dbforge();
		if($id>0){

			$data = array(
					'title' => $name ,
					'date_open' => date("Y-m-d") ,
					'date_start' => $datastart ,
					'date_end' => $dataend
			);

			
			$this->db->insert('project', $data);
			$insert_id = $this->db->insert_id();

			$tab2 = array(
			   'id' => $id ,
			   'id_type' => 1 ,
			   'id_proj' => $insert_id
			);
			$this->db->insert('proj_user', $tab2);

			/*TWORZENIE TABELI O NAZWIE ID PROJEKTU*/

			$this->dbforge->add_field(array(
					'id' => array(
							'type' => 'INT',
							'unsigned' => TRUE,
							'auto_increment' => TRUE,
					),
					'dependence' => array(
							'type' => 'INT',
							'null' => TRUE,
					),
					'title' => array(
							'type' =>'VARCHAR',
							'constraint' => '500',
					),
					'description' => array(
							'type' => 'TEXT',
							'null' => TRUE,
					),
					'data_start' => array(
							'type' => 'date',
					),
					'data_stop' => array(
							'type' => 'date',
					),
					'user_id' => array(
							'type' => 'INT',
					),
					'done' => array(
							'type' => 'BOOLEAN',
							'null' => TRUE,
					),
					'checked' => array(
							'type' => 'BOOLEAN',
							'null' => TRUE,
					),
					'iscorrect' => array(
							'type' => 'BOOLEAN',
							'null' => TRUE,
					),
					'correctdescript' => array(
							'type' => 'TEXT',
							'null' => TRUE,
					),
					'END' => array(
							'type' => 'BOOLEAN',
							'null' => TRUE,
					),
			));
			$this->dbforge->add_key('id', TRUE);
			$this->dbforge->create_table('tasks_proj'.$tab2['id_proj']);

			$tab3 = array(
					'title' => ''
			);
			$this->db->insert('tasks_proj'.$tab2['id_proj'], $tab3);

			
			return true;
		}
		else return false;
	}
	
function show_proj($id)
	{
		if($id>0){
			
			return true;
		}
		else return false;
	}
}
?>