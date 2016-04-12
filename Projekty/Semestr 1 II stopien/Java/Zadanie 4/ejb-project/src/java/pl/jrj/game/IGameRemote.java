package pl.jrj.game;
import javax.ejb.Remote;
@Remote
public interface IGameRemote {
public boolean register(int hwork, String album); // hwork - numer zadania, album – numer albumu studenta
} 