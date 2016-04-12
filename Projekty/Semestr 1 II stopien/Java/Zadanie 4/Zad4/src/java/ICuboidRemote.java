
import java.util.List;
import javax.ejb.Remote;

/**
 * Interfejs ICuboidRemote zawierający deklaracje metod wykorzystywanych 
 * w klasie Cuboid niezbędny do użycia bean'a przekazującego listę punktów
 * z servletu do klasy Cuboid
 * @author Procislaw
 * @version 1.0
 */
@Remote
public interface ICuboidRemote {
    double actions (int i, int n, float sX, float sY, double field);
    double general (List<Float> arr);
    void prepare (List<Float> arr, int i);
    void graham(Cuboid.Point2d[] tan, int n);
    void addElemStact(int nr);
    void remElemStact();
    int det(int a,int b,int c);
    void stackAddRem(int i, Cuboid.Point2d[] tan, int l);
    double countFields(int i, double field, int stcSize);
}
