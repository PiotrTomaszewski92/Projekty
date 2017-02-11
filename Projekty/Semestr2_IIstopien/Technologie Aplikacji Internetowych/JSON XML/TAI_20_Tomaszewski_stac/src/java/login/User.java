/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author Procislaw
 */
//@Entity
//@Table(name = "HelloUser")
public class User implements Serializable 
{
   // @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_user;
    private String login;
    private String haslo;    
    private String typ;
    private String imie;
    private String nazwisko;
    private String email;
    
    
    public User (Long id_user, String login, String haslo, String typ, String imie, String nazwisko, String email){
        this.id_user=id_user;
        this.login=login;
        this.haslo=haslo;
        this.typ=typ;
        this.imie=imie;
        this.nazwisko=nazwisko;
        this.email=email;
        
    }

    //@Override
    public String toString() {
        return "User{" + "id_user=" + id_user + ", login=" + login + ", haslo=" + haslo + ", typ=" + typ + ", imie=" + imie + ", nazwisko=" + nazwisko + ", email=" + email + '}';
    }
    
    

    
    

    // getters, setters, no-arg constructor

    public Long getId_user() {
        return id_user;
    }

    public void setId_user(Long id_user) {
        this.id_user = id_user;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getHaslo() {
        return haslo;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
