<?php
Class Projecting extends CI_Model
{
    public $hash_table = array();


    function showProject($idp, $idu)
    {
        //echo'weszlo '.$idp.' - '.$idu.'<br>';
        $this->db->select('*');
        $this->db->from('project');
        $this->db->join('proj_user', 'project.id_proj = proj_user.id_proj');
        $this->db->join('users', 'users.id = proj_user.id');
        $this->db->where('proj_user.id_proj= ' . $idp);
        $this->db->where('proj_user.id= ' . $idu);
        $this->db->where('proj_user.id_type=1');

        $query = $this->db->get();

        if ($query->num_rows() == 1) {
            return $query->row_array();
        } else {
            return false;
        }
    }

    function showProjectForOthers($idp)
    {
        //echo'weszlo '.$idp.' - '.$idu.'<br>';
        $this->db->select('*');
        $this->db->from('project');
        $this->db->join('proj_user', 'project.id_proj = proj_user.id_proj');
        $this->db->join('users', 'users.id = proj_user.id');
        $this->db->where('proj_user.id_proj= ' . $idp);
        $this->db->where('proj_user.id_type=1');

        $query = $this->db->get();

        if ($query->num_rows() == 1) {
            return $query->row_array();
        } else {
            return false;
        }
    }

    function countPeople($id_proj)
    {
        $this->db->select('id');
        $this->db->from('proj_user');
        $this->db->where('id_proj= ' . $id_proj);

        $query = $this->db->get();

        if ($query->num_rows() > 0) {
            return $query->num_rows();
        } else {
            return 0;
        }
    }

    function showPeople($id_proj)
    {
        $this->db->select('users.id AS `ajdi`, name, surname, email, type');
        $this->db->from('users');
        $this->db->join('proj_user', 'users.id=proj_user.id');
        $this->db->join('type', 'type.id_type = proj_user.id_type');
        $this->db->where('proj_user.id_proj= ' . $id_proj);

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

    function showDeploy($id_proj)
    {
        $this->db->select('users.id, name, surname');
        $this->db->from('users');
        $this->db->join('proj_user', 'users.id=proj_user.id');
        $this->db->where('proj_user.id_proj= ' . $id_proj);

        $query = $this->db->get();
        $tab = '<option value="0" selected></option>';
        if ($query->num_rows() > 0) {
            foreach ($query->result_array() as $row) {
                $tab .= '<option value="'.$row['id'].'">' . $row['name'] . ' ' . $row['surname'] . '</option>';
            }
            return $tab;
        } else {
            return false;
        }
    }


    function searchemail($mejl)
    {
        $this->db->select('email');
        $this->db->from('users');
        $this->db->where("email= '" . $mejl . "'");

        $query = $this->db->get();
        if ($query->num_rows() > 0)
            return 1;
        else
            return 0;
    }


//selecselect users.id, count(id_proj) as ile from users inner join proj_user on users.id = proj_user.id where users.email="admin@o2.pl"
    function addPerson($email, $proj, $type)
    {
        $this->db->select('id');
        $this->db->from('users');
        $this->db->where('email= "' . $email . '"');

        $query = $this->db->get();
        $query = $query->row_array();
        // echo 'AJDI: '.$query['id'].'<br />';
        if ($query['id'] > 0) {
            // echo 'isperson = '.$this->isPerson($query['id'],$proj,$type);
            if ($this->isPerson($query['id'], $proj, $type) == 0) {
                $data = array(
                    'id_proj' => $proj,
                    'id' => $query['id'],
                    'id_type' => $type
                );
                $this->db->insert('proj_user', $data);
                return "dodano";
            } else {
                return "Użytkownik został już wcześniej dodany";
            }

        } else {
            return "Adres email jest niepoprawny lub użytkownik już został dodany";
        }
    }

    function delPerson($idproj, $id)
    {
        $this->db->delete('proj_user', array('id' => $id, 'id_proj' => $idproj));
    }

    function delRows($id, $idproj)
    {
        $this->db->delete('tasks_proj'.$idproj, array('id' => $id));
    }


    function isPerson($id, $idproj, $idtype)
    {
        $this->db->select('id');
        $this->db->from('proj_user');
        $this->db->where('id= ' . $id);
        $this->db->where('id_proj= ' . $idproj);

        $query = $this->db->get();
        return $query->num_rows();
    }

    function showMax($proj)
    {
        $this->db->select_max('id');
        $query = $this->db->get('tasks_proj'.$proj);
        if($query->num_rows() > 0) {
            $res2 = $query->result_array();
            return $res2[0]['id']+1;
        }

    }

    function addRowTask($id, $rodzic, $max){
        $data = array(
            'id' => $max ,
            'dependence' => $rodzic
        );
        $this->db->insert('tasks_proj'.$id, $data);

    }

    function updateTask($row,$ajdi,$dejta){
        $this->db->where('id', $ajdi);
        $this->db->update('tasks_proj'.$row, $dejta);
    }



//SELECT  `name` ,  `surname`
//FROM users
//INNER JOIN proj_user ON proj_user.id = users.id
//WHERE proj_user.id_proj =1

    function load($row, $intt, $st, $idd, $uzytkownicy)
    {
        $test = $intt;
//        if($row['checked']==1){$colorBg='greenBg';}else if($row['checked']==-1){$colorBg='redBg';} else {$colorBg='';}

        if (isset($row['id']))
            if ($row['checked']!=1) {
                if (($test == 1) || isset($row['subitems'])) {
                    if($row['checked']==1){$colorBg='greenBg';}else if($row['checked']==-1){$colorBg='redBg';} else {$colorBg='';}
                    echo '<li class="' . $st . '' . $test . ' '.$colorBg.'" key="' . $row['id'] . '" depend="' . $row['dependence'] . '"><span>' . str_replace("_", ".", $st) . '' . $test . '</span> <input type="text" name="1." class="form-control" placeholder="Tytuł zadania" value="' . $row['title'] . '"><input name="1." class="form-control" placeholder="Opis" type="text" value="' . $row['description'] . '"><input name="1." class="form-control" type="date"  '; if((strtotime($row['data_start'])<strtotime("now"))&&(strtotime($row['data_stop'])<strtotime("now"))&&(strtotime($row['data_start'])>0)&&(strtotime($row['data_stop'])>0)){echo 'style="border: 3px solid red"';} else if ((strtotime($row['data_start'])<strtotime("now"))&&(strtotime($row['data_stop'])>strtotime("now"))){echo 'style="border: 3px solid green"';   }else echo'style=""';    echo' placeholder="Data rozpoczęcia" value="' . $row['data_start'] . '" key="'.strtotime($row['data_start']).'"><input name="1." type="date" class="form-control" '; if((strtotime($row['data_start'])<strtotime("now"))&&(strtotime($row['data_stop'])<strtotime("now"))&&(strtotime($row['data_start'])>0)&&(strtotime($row['data_stop'])>0)){echo 'style="border: 3px solid red"';} else if ((strtotime($row['data_start'])<strtotime("now"))&&(strtotime($row['data_stop'])>strtotime("now"))){echo 'style="border: 3px solid green"'; }else echo'style=""';      echo'  placeholder="Data zakończenia" value="' . $row['data_stop'] . '" key="'.strtotime($row['data_stop']).'"><select name="1." class="form-control" name="nazwa">';
                    $i = 0;
                    foreach ($uzytkownicy as $row2) {
                        if ($row2['ajdi'] === $row['user_id']) {
                            echo '<option value="' . $row2['ajdi'] . '" selected>' . $row2['name'] . '  ' . $row2['surname'] . '</option>';
                            $i = 1;
                        } else
                            echo '<option value="' . $row2['ajdi'] . '">' . $row2['name'] . ' ' . $row2['surname'] . '</option>';
                    };
                    if ($i === 0)
                        echo '<option value="0" selected></option>';


                    echo '</select>';
                    if (($row['done'] == 1) && (($row['checked'] == null)||($row['checked'] == -1) ) && ($row['iscorrect'] == null))
                        echo '<a href="#"><span class="glyphicon glyphicon-thumbs-up" aria-hidden="true"></span></a><a href="#"><span class="glyphicon glyphicon-thumbs-down" aria-hidden="true"></span></a>';
                    echo '</li>';

                } else {
                    if($row['checked']==1){$colorBg='greenBg';}else if($row['checked']==-1){$colorBg='redBg';} else {$colorBg='';}
                    echo '<li class="' . $st . '' . $test . ' '.$colorBg.'" key="' . $row['id'] . '" depend="' . $row['dependence'] . '"><span>' . str_replace("_", ".", $st) . '' . $test . '</span> <input type="text" name="1." class="form-control" placeholder="Tytuł zadania" value="' . $row['title'] . '"><input name="1." class="form-control" placeholder="Opis" type="text" value="' . $row['description'] . '"><input name="1." class="form-control" type="date" '; if((strtotime($row['data_start'])<strtotime("now"))&&(strtotime($row['data_stop'])<strtotime("now"))&&(strtotime($row['data_start'])>0)&&(strtotime($row['data_stop'])>0)){echo 'style="border: 3px solid red"';} else if ((strtotime($row['data_start'])<strtotime("now"))&&(strtotime($row['data_stop'])>strtotime("now"))){echo 'style="border: 3px solid green"'; }else echo'style=""';      echo'  placeholder="Data rozpoczęcia" value="' . $row['data_start'] . '"><input name="1." type="date" class="form-control" '; if((strtotime($row['data_start'])<strtotime("now"))&&(strtotime($row['data_stop'])<strtotime("now"))&&(strtotime($row['data_start'])>0)&&(strtotime($row['data_stop'])>0)){echo 'style="border: 3px solid red"';} else if ((strtotime($row['data_start'])<strtotime("now"))&&(strtotime($row['data_stop'])>strtotime("now"))){echo 'style="border: 3px solid green"'; } else echo'style=""';     echo'  placeholder="Data zakończenia" value="' . $row['data_stop'] . '"><select name="1." class="form-control" name="nazwa">';
                    $i = 0;
                    foreach ($uzytkownicy as $row2) {
                        if ($row2['ajdi'] === $row['user_id']) {
                            echo '<option value="' . $row2['ajdi'] . '" selected>' . $row2['name'] . '  ' . $row2['surname'] . '</option>';
                            $i = 1;
                        } else
                            echo '<option value="' . $row2['ajdi'] . '"> ' . $row2['name'] . ' ' . $row2['surname'] . '</option>';
                    };
                    if ($i === 0)
                        echo '<option value="0" selected></option>';


                    if (($row['done'] == 1) && (($row['checked'] == null)||($row['checked'] == -1) )&& ($row['iscorrect'] == null))
                        echo ' </select><a href="#"><span class="glyphicon glyphicon-thumbs-up" aria-hidden="true"></span></a><a href="#"><span class="glyphicon glyphicon-thumbs-down" aria-hidden="true"></span></a>';
                    else
                        echo '</select><a href="#" class="down"><span class="glyphicon glyphicon-arrow-right" aria-hidden="true"></span></a>';


                    echo '</li>';
                }
            } else{
                if($row['checked']==1){$colorBg='greenBg';}else if($row['checked']==-1){$colorBg='redBg';} else {$colorBg='';}
                echo '<li class="' . $st . '' . $test . ' '.$colorBg.'" key="'.$row['id'].'" depend="'.$row['dependence'].'"><span>' . str_replace("_", ".", $st) . '' . $test . '</span> <input type="text" name="1."  class="form-control" placeholder="Tytuł zadania" value="' . $row['title'] . '" disabled><input name="1." class="form-control" placeholder="Opis" type="text" value="' . $row['description'] . '" disabled><input name="1." class="form-control" type="date" placeholder="Data rozpoczęcia" value="' . $row['data_start'] . '" disabled><input name="1." type="date" class="form-control" placeholder="Data zakończenia" value="' . $row['data_stop'] . '" disabled><select name="1." class="form-control" name="nazwa" disabled>';
                $i = 0;
                foreach ($uzytkownicy as $row2) {
                    if ($row2['ajdi'] === $row['user_id']) {
                        echo '<option value="'.$row2['ajdi'].'" selected>' . $row2['name'] . '  '.$row2['surname'].'</option>';
                        $i = 1;
                    } else
                        echo '<option value="'.$row2['ajdi'].'">' . $row2['name'] . ' '.$row2['surname'].'</option>';
                };
                if ($i === 0)
                    echo '<option value="0" selected></option>';


                if($test===1)
                    echo '</select></li>';
                else
                    echo '</select></li>';
            }
        else
            echo '';


        if (!empty($row['subitems'])) {
            ($test == 0) ? $st = '' : $st = $st . '' . $test . '_';
            $test = 0;

            echo '<ul class="' . $st . '0">';

            foreach ($row['subitems'] as $subrow) {
                $test++;
                $this->load($subrow, $test, $st, $idd, $uzytkownicy);
            }
            echo '</ul>';
        }

        echo '</li>';
    }


    function getTasks($id)
    {
        $this->db->from('tasks_proj' . $id);
        $query = $this->db->get();
        $query2 = $query->result_array();

        $all = array();

        $uzytkownicy = $this->showPeople($id);
//        print_r($uzytkownicy);

        foreach ($query2 as &$row) {
            if (!$row['dependence']) {
                $row['dependence'] = 0;
            }
            $all[$row['id']] = $row;
            if (!isset($all[$row['dependence']]['subitems'])) {
                $all[$row['dependence']]['subitems'] = array();
            }
            $all[$row['dependence']]['subitems'][] = &$all[$row['id']];
        }

        echo '<ul id="tasks" class="0 form-group form-inline">';
        $this->load($all[0], 0, '', $id, $uzytkownicy);
        echo '</ul>';


        return $all[0]['subitems'];


    }

    function updateCheck($row, $task, $wart)
    {
        $data = array(
            'checked' => $wart
        );

        $this->db->where('id', $task);
        $this->db->update('tasks_proj'.$row, $data);


    }
}

?>