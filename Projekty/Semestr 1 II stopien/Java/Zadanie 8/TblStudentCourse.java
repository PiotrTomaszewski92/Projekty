
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Klasa TblStudentCourse pozwalająca zmapować tabelę 
 * (tbl_student_course) z bazy. 
 * @author Piotr Tomaszewski
 */
@Entity
@Table(name = "tbl_student_course")
@IdClass(TblStudentCoursePk.class)
public class TblStudentCourse implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id    
    @NotNull
    @Column(name = "studentId")
    private int studentId;
    @Id    
    @NotNull
    @Column(name = "courseId")
    private int courseId;
    @Column(name = "mark")
    private long mark;

    /**
     * Metoda getStudentId będąca getterem dla id studenta
     * @return id studenta
     */
    public int getStudentId() {
        return studentId;
    }

    /**
     * Metoda setStudentId będąca setterem dla id studenta
     * @param studentId id studenta
     */
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    /**
     * Metoda getCourseId będąca getterem dla id kursu
     * @return id kursu
     */
    public int getCourseId() {
        return courseId;
    }

    /**
     * Metoda setCourseId będąca setterem dla id kursu
     * @param courseId id kursu
     */
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    /**
     * Metoda getMark będąca getterem dla oceny 
     * @return wartosc oceny
     */
    public long getMark() {
        return mark;
    }

    /**
     * Metoda setMark będąca setterem dla oceny
     * @param mark wartosc oceny
     */
    public void setMark(int mark) {
        this.mark = mark;
    }

   

   /**
    * Metoda hashCode sprawdzająca zależność jeżeli wartość hashCode 
    * dla 2 obiektów jest taka sama, to obiekty te mogą być 
    * równoznaczne.
    * @return wartość (1/0 - true/false) jeśli obiekty są równe
    */
    @Override
    public int hashCode() {
        int result = studentId;
        result = 31 * result + courseId;
        result = 31 * result + (int) (mark ^ (mark >>> 32));
        return result;
    }
    
     /**
     * Metoda equals jest mechanizmem pozwalającym porównać czy obiekty 
     * znaczą to samo niż czy są tym samym obiektem. 
     * @param obj - wartość do sprawdzenia
     * @return wartość true/false jeśli wartości zmiennych są równe.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) 
            return false;
        if (this == obj) 
            return true;
        

        TblStudentCourse that = (TblStudentCourse) obj;

        return studentId == that.studentId && courseId == that.courseId
                && mark == that.mark;

    }

    /**
     * Metoda toString wywoływana automatycznie, kiedy obiekt 
     * ma zostać przedstawiony jako wartość tekstowa.
     * @return wartość tekstowa z obiektu.
     */
    @Override
    public String toString() {
        return "StudentCourse[sId="+studentId+", cId="
                +courseId+", mark="+mark+']';
    }
}
