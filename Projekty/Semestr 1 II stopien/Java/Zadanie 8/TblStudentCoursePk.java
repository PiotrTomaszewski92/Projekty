import java.io.Serializable;


/**
 * Klasa TblStudentCoursePK pozwalająca zmapować tabelę 
 * (tbl_student_course) z wykorzystaniem klucza złożonego.
 * @author Piotr Tomaszewski
 */
public class TblStudentCoursePk implements Serializable {

    private static final long serialVersionUID = 1L;
    private int studentId;
    private int courseId;

    /**
     * Metoda getStudentId będąca getterem dla id studenta
     * @return 
     */
    public int getStudentId() {
        return studentId;
    }

   /**
    * Metoda setStudentId będąca setterem dla id studenta
    * @param studentId 
    */
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    /**
     * Metoda getCourseId będąca getterem dla id kursu
     * @return 
     */
    public int getCourseId() {
        return courseId;
    }

   /**
    * Metoda setCourseId
    * @param courseId  będąca setterem dla id kursu
    */
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
    
    /**
    *  Metoda hashCode sprawdzająca zależność jeżeli wartość hashCode 
    * dla 2 obiektów jest taka sama, to obiekty te mogą być 
    * równoznaczne.
    * @return wartość (1/0 - true/false) jeśli obiekty są równe
    */
    @Override
    public int hashCode() {
        int result = studentId;
        result = 31 * result + courseId;
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
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        TblStudentCoursePk that = (TblStudentCoursePk) obj;

        return studentId == that.studentId
                && courseId == that.courseId;

    }

  
   /**
    * Metoda toString wywoływana automatycznie, kiedy obiekt 
    * ma zostać przedstawiony jako wartość tekstowa.
    * @return wartość tekstowa z obiektu.
    */
    @Override
    public String toString() {
        return "StudentCoursePK[studentId="+studentId+
                ", courseId="+courseId+"]";
    }
}