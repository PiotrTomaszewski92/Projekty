/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package produkty;


import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Procislaw
 */
@XmlRootElement
public class Lista {
    private List<Data> najpopularniejsze;

        public List<Data> getLista() {
            return najpopularniejsze;
        }
        @XmlElement
        public void setLista(List<Data> zadanie) {
            this.najpopularniejsze = zadanie;
        }

        @Override
        public String toString() {
            return "Lista{" + "najpopularniejsze=" + najpopularniejsze + '}';
        }
    
}
