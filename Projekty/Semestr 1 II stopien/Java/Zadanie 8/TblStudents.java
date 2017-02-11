import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Klasa TblStudents pozwalająca zmapować tabelę (tbl_students) z bazy. 
 * @author Piotr Tomaszewski
 */
@Entity
@Table(name = "tbl_students")
public class TblStudents implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Size(max = 255)
    @Column(name = "firstName")
    private String firstName;
    @Size(max = 255)
    @Column(name = "lastName")
    private String lastName;

    /**
     * Konstruktor bezparametrowy TblStudents
     */
    public TblStudents() {
    }

    /**
     * Konstruktor jednoparametrowy przyjmujący wartość id
     * @param id - wartość id z tabeli
     */
    public TblStudents(Integer id) {
        this.id = id;
    }

    /**
     * Metoda getId będąca getterem dla id studenta
     * @return wartość id studenta
     */
    public Integer getId() {
        return id;
    }

    /**
     * Metoda setId będąca setterem id studenta
     * @param id wartość id studenta
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Metoda getFirstName będąca getterem dla imienia studenta
     * @return 
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Metoda setFirstName będąca setterem dla imienia studenta
     * @param firstName 
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Metoda getLastName będąca getterem dla nazwiska studenta
     * @return 
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Metoda setLastName będąca setterem dla nazwiska studenta
     * @param lastName 
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Metoda hashCode sprawdzająca zależność jeżeli wartość hashCode 
     * dla 2 obiektów jest taka sama, to obiekty te mogą być 
     * równoznaczne.
     * @return wartość (1/0 - true/false) jeśli obiekty są równe
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /**
     * Metoda equals jest mechanizmem pozwalającym porównać czy obiekty 
     * znaczą to samo niż czy są tym samym obiektem. 
     * @param object - wartość do sprawdzenia
     * @return wartość true/false jeśli wartości zmiennych są równe.
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof TblStudents)) {
            return false;
        }
        TblStudents other = (TblStudents) object;
        if ((this.id == null && other.id != null) 
                || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    /**
     * Metoda toString wywoływana automatycznie, kiedy obiekt 
     * ma zostać przedstawiony jako wartość tekstowa.
     * @return wartość tekstowa z obiektu.
     */
    @Override
    public String toString() {
        return "l.TblStudents[ id=" + id + " ]";
    }
    
}
