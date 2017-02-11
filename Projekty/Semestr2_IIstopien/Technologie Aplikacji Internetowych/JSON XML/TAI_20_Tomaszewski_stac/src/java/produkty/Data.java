/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package produkty;

import java.util.List;

/**
 *
 * @author Procislaw
 */
public class Data {
    private int id;
    private String title;
    private String img;
    private String opis;
    private Double cena;
    private Integer ilosc;
    private List<String>  baza;
    private String czas;
    
    public Data(){
        
    }
    
    public Data(int id, String title,String img,String opis,Double cena,Integer ilosc,List<String> baza,String czas){
        this.id=id;
        this.title=title;
        this.img=img;
        this.opis=opis;
        this.cena=cena;
        this.ilosc=ilosc;
        this.baza=baza;
        this.czas=czas;
    }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }  

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getOpis() {
            return opis;
        }

        public void setOpis(String opis) {
            this.opis = opis;
        }

        public Double getCena() {
            return cena;
        }

        public void setCena(Double cena) {
            this.cena = cena;
        }

        public Integer getIlosc() {
            return ilosc;
        }

        public void setIlosc(Integer ilosc) {
            this.ilosc = ilosc;
        }

        public List<String> getBaza() {
            return baza;
        }

        public void setBaza(List<String> baza) {
            this.baza = baza;
        }

        public String getCzas() {
            return czas;
        }

        public void setCzas(String czas) {
            this.czas = czas;
        }

    @Override
    public String toString() {
        return "Data{" + "id=" + id + ", title=" + title + ", img=" + img + ", opis=" + opis + ", cena=" + cena + ", ilosc=" + ilosc + ", baza=" + baza + ", czas=" + czas + '}';
    }

        

    
        
}
