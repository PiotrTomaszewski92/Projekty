import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Klasa TbInsurance mapująca tabelę tb_insurance
 * @author Piotr Tomaszewski
 */
@Entity
@Table(name = "tb_insurance")
public class TbInsurance implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @NotNull
    @Column(name = "Id")
    private Integer id;
    @Column(name = "customerId")
    private Integer customerId;
    @Column(name = "modelId")
    private Integer modelId;
    @Column(name = "dateFrom")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateFrom;
    @Column(name = "dateTo")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTo;

    /**
     * Konstruktor bezargumentowy TbInsurance
     */
    public TbInsurance() {
    }

    /**
     * Konstruktor jednoargumentowy TbInsurance
     * @param id - wartość id z tabeli tb_insurance
     */
    public TbInsurance(Integer id) {
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
     * Metoda getCustomerId będąca getterem dla kolumny customerId
     * @return wartość customerId
     */
    public Integer getCustomerId() {
        return customerId;
    }

    /**
     * Metoda setCustomerId będąca setterem dla kolumny customerId
     * @param customerId wartość customerId
     */
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    /**
     * Metoda getModelId będąca getterem dla kolumny modelId
     * @return wartość modelId
     */
    public Integer getModelId() {
        return modelId;
    }

    /**
     * Metoda setModelId będąca setterem dla kolumny modelId
     * @param modelId wartość modelId
     */
    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    /**
     * Metoda getDateFrom będąca getterem dla kolumny dateFrom
     * @return Wartość dateFrom
     */
    public Date getDateFrom() {
        return dateFrom;
    }
    /**
     * Metoda setDateFrom będąca setterem dla kolumny dateFrom
     * @param dateFrom Wartość dateFrom
     */
    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    /**
     * Metoda getDateTo będąca getterem dla kolumny dateTo
     * @return Wartość dateTo
     */
    public Date getDateTo() {
        return dateTo;
    }

    /**
     * Metoda setDateTo będąca setterem dla kolumny dateTo
     * @param dateTo Wartość dateTo
     */
    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
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
        if (!(object instanceof TbInsurance)) {
            return false;
        }
        TbInsurance other = (TbInsurance) object;
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
        return "p.TbInsurance[ id=" + id + " ]";
    }
    
}
