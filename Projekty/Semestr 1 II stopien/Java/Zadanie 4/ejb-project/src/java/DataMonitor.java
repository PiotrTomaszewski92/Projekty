
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateful;
import pl.jrj.data.IDataMonitor;
@Stateful
public class DataMonitor implements IDataMonitor {
 
    private List<Double> elementList;
    private int iterator = 0;
   
    @Override
    public boolean register(int hwork, String album) {
       
        if(hwork == 3 && album.equals("104896")) {
            createList();
            return true;
        } else {
            return false;
        }
    }
 
    @Override
    public boolean hasNext() {
        return elementList.size() > iterator;
    }
 
    @Override
    public double next() {
        return
            hasNext() ? elementList.get(iterator++) : 0.0;
    }
   
    private void createList() {
        this.elementList = new ArrayList<Double>();
        this.elementList.add(1.2);
        this.elementList.add(2.4);
        this.elementList.add(4.1);
       
        this.elementList.add(1.0);
        this.elementList.add(1.0);
        this.elementList.add(1.0);
        this.elementList.add(0.4);
       
        this.elementList.add(2.0);
        this.elementList.add(3.0);
        this.elementList.add(4.0);
        this.elementList.add(0.6);
       
        this.elementList.add(4.1);
        this.elementList.add(4.2);
        this.elementList.add(4.3);
        this.elementList.add(0.8);
       
        this.elementList.add(-1.0);
        this.elementList.add(-3.0);
        this.elementList.add(-4.0);
        this.elementList.add(2.2);
    }
}