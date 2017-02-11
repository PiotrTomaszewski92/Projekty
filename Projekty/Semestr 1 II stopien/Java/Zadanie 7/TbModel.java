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
 * Klasa TbModel mapująca tabelę tb_model
 * @author Piotr Tomaszewski
 */
@Entity
@Table(name = "tb_model")
public class TbModel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @NotNull
    @Column(name = "Id")
    private Integer id;
    @Size(max = 255)
    @Column(name = "model")
    private String model;

    /**
     * Konstruktor bezargumentowy TbModel
     */
    public TbModel() {
    }

    /**
     * Konstruktor jednoargumentowy TbModel
     * @param id - wartość id z tabeli tb_model 
     */
    public TbModel(Integer id) {
        this.id = id;
    }

    /**
     * Metoda getId będąca getterem dla kolumny Id
     * @return wartość Id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Metoda setId będąca setterem dla kolumny Id
     * @param id wartość Id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Metoda getModel będąca getterem dla kolumny model
     * @return wartość model
     */
    public String getModel() {
        return model;
    }

    /**
     * Metoda setModel będąca setterem dla kolumny model
     * @param model wartość model
     */
    public void setModel(String model) {
        this.model = model;
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
        if (!(object instanceof TbModel)) {
            return false;
        }
        TbModel other = (TbModel) object;
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
        return "p.TbModel[ id=" + id + " ]";
    }
    
}
