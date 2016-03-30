<?php
Class Work_see extends CI_Model
{

    function showAutor($idp){
        $this->db->select('*');
        $this->db->from('project');
        $this->db->join('proj_user', 'project.id_proj = proj_user.id_proj');
        $this->db->join('users', 'users.id = proj_user.id');
        $this->db->where('proj_user.id_proj= ' . $idp);
        $this->db->where('proj_user.id_type=1');

        $query = $this->db->get();

        if ($query->num_rows() >= 1) {
            return $query->row_array();
        } else {
            return false;
        }
    }


    function showProject23($idp, $idu, $type)
    {
       // echo'weszlo '.$idp.' - '.$idu.'<br>';
        $this->db->select('*');
        $this->db->from('project');
        $this->db->join('proj_user', 'project.id_proj = proj_user.id_proj');
        $this->db->join('users', 'users.id = proj_user.id');
        $this->db->where('proj_user.id_proj= ' . $idp);
        $this->db->where('proj_user.id= ' . $idu);
        $this->db->where('proj_user.id_type='.$type);

        $query = $this->db->get();

        if ($query->num_rows() == 1) {
            return $query->row_array();
        } else {
            return false;
        }
    }

    function showTasks($idp, $idu)
    {
        // echo'weszlo '.$idp.' - '.$idu.'<br>';
        $this->db->select('*');
        $this->db->from('tasks_proj'.$idp);
        $this->db->where('user_id= ' . $idu);

        $query = $this->db->get();
        $tab = array();
        if ($query->num_rows() > 0) {
            foreach ($query->result_array() as $row) {
                array_push($tab, $row);
            }
            return $tab;
        } else {
            return false;
        }
    }

    function tasksToClient($idp)
    {
        // echo'weszlo '.$idp.' - '.$idu.'<br>';
        $this->db->select('*');
        $this->db->from('tasks_proj'.$idp);
        $this->db->where('done= 1');
        $this->db->where('checked= 1');

        $query = $this->db->get();
        $tab = array();
        if ($query->num_rows() > 0) {
            foreach ($query->result_array() as $row) {
                array_push($tab, $row);
            }
            return $tab;
        } else {
            return false;
        }
    }

    function done($idzad, $idp)
    {
        $data=array();
        $this->db->select('done');
        $this->db->from('tasks_proj'.$idp);
        $this->db->where('id= ' . $idzad);

        $query = $this->db->get();
        foreach ($query->result() as $row)
        {
            if($row->done==null){
                $data = array(
                    'done' => 1
                );

            }
        }
        $this->db->where('id', $idzad);
        $this->db->update('tasks_proj'.$idp, $data);
        return true;


    }

    function good($id, $row){
        $data = array(
            'iscorrect' => 1,
            'END' => 1
        );
        $this->db->where('id', $id);
        $this->db->update('tasks_proj'.$row, $data);
        return true;
    }

    function bad($id,$messag,$row){
        $data = array(
            'iscorrect' => -1,
            'correctdescript' => $messag

        );
        $this->db->where('id', $id);
        $this->db->update('tasks_proj'.$row, $data);
        return true;
    }


}

?>