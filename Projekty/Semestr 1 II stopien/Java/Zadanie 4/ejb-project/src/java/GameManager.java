/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.ejb.Stateful;
import javax.ejb.LocalBean;
import pl.jrj.game.IGameRemote;

/**
 *
 * @author Procislaw
 */
@Stateful
@LocalBean
public class GameManager implements IGameRemote{

    @Override
    public boolean register(int hwork, String album) {
       if (hwork == 4 && album.equals("104896")) 
           return true;
       else
           return false;
    }
    
}
