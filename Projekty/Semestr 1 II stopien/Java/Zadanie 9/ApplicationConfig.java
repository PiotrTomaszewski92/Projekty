
import java.util.Set;
import javax.ws.rs.core.Application;

/**
 * Klasa ApplicationConfig wygenerowana automatycznie 
 * pozwalająca uruchomić technologię JAX-WS wraz 
 * z udostępnieniem klas i zasobów RESTowych.
 * @author Tomaszewski
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    /**
     * Metoda getClasses wywołuje kolejne dodawanie zasobów 
     * do listy i zwraca ich zbiór.
     * @return lista zasobów projektu
     */
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

   /**
    * Metoda addRestResourceClasses dodaje do listy 
    * RESTowe klasy zdefiniowane w projekcie.
    * @param resources - zasób do dodania
    */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(Teryt.class);
    }
    
}
