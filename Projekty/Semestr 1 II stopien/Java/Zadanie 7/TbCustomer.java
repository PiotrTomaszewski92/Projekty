import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Klasa TbCustomer mapująca tabelę tb_customer
 * @author Piotr Tomaszewski
 */
@Entity
@Table(name = "tb_customer")
public class TbCustomer implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @NotNull
    @Column(name = "Id")
    private Integer id;
    @Size(max = 255)
    @Column(name = "firstName")
    private String firstName;
    @Size(max = 255)
    @Column(name = "lastName")
    private String lastName;
    
    
    /**
     * Konstruktor bezparametrowy TbCustomer
     */
    public TbCustomer() {
    }

    /**
     * Konstruktor jednoparametrowy przyjmujący wartość id
     * @param id - wartość id z tabeli tb_customer
     */
    public TbCustomer(Integer id) {
        this.id = id;
    }

    /**
     * Metoda getId będąca getterem dla id klienta
     * @return wartość id klienta
     */
    public Integer getId() {
        return id;
    }

    /**
     * Metoda setId będąca setterem id klienta
     * @param id wartość id klienta
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Metoda getFirstName będąca getterem dla imienia klienta
     * @return imię klienta
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Metoda setFirstName będąca setterem dla imienia klienta
     * @param firstName  imię klienta
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Metoda getLastName będąca getterem dla nazwiska klienta
     * @return nazwisko klienta
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Metoda setLastName będąca setterem dla nazwiska klienta
     * @param lastName nazwisko klienta
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
        if (!(object instanceof TbCustomer)) {
            return false;
        }
        TbCustomer other = (TbCustomer) object;
        if ((this.id == null && other.id != null) || 
                (this.id != null && !this.id.equals(other.id))) {
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
        return "p.TbCustomer[ id=" + id + " ]";
    }
    
}
