import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Klasa TblCourses pozwalająca zmapować tabelę (tbl_courses) z bazy. 
 * @author Piotr Tomaszewski
 */
@Entity
@Table(name = "tbl_courses")
public class TblCourses implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Size(max = 255)
    @Column(name = "courseName")
    private String courseName;

    /**
     * Konstruktor bezparametrowy TblCourses
     */
    public TblCourses() {
    }

    /**
     * Konstruktor jednoparametrowy przyjmujący wartość id
     * @param id - wartość id z tabeli
     */
    public TblCourses(Integer id) {
        this.id = id;
    }

    /**
     * Metoda getId będąca getterem dla id kursu
     * @return wartość id kursu
     */
    public Integer getId() {
        return id;
    }

    /**
     * Metoda setId będąca setterem id kursu
     * @param id wartość id kursu
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Metoda getCourseName będąca getterem dla nazwy kursu
     * @return nazwa kursu
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * Metoda setCourseName będąca setterem nazwy kursu
     * @param courseName nazwa kursu
     */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
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
        if (!(object instanceof TblCourses)) {
            return false;
        }
        TblCourses other = (TblCourses) object;
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
        return "l.TbCourses[ id=" + id + " ]";
    }
    
}
