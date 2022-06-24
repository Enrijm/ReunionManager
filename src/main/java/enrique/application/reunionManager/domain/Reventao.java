package enrique.application.reunionManager.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Reventao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false,updatable = false)
    public Long id;
    public String name;
    public String aka;
    public String comments;
    public int numbers_cans_day;
    public Long money_to_put;
    public String disponibility;

    public Reventao() {}

    public Reventao(String name, String aka, String comments, int numbers_cans_day, Long money_to_put, String disponibility) {
        this.name = name;
        this.aka = aka;
        this.comments = comments;
        this.numbers_cans_day = numbers_cans_day;
        this.money_to_put = money_to_put;
        this.disponibility = disponibility;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAka() {
        return aka;
    }

    public void setAka(String aka) {
        this.aka = aka;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getNumbers_cans_day() {
        return numbers_cans_day;
    }

    public void setNumbers_cans_day(int numbers_cans_day) {
        this.numbers_cans_day = numbers_cans_day;
    }

    public Long getMoney_to_put() {
        return money_to_put;
    }

    public void setMoney_to_put(Long money_to_put) {
        this.money_to_put = money_to_put;
    }

    public String getDisponibility() {
        return disponibility;
    }

    public void setDisponibility(String disponibility) {
        this.disponibility = disponibility;
    }

    @Override
    public String toString(){
        return "Reventao{ " +  "Id_Reventao: " + id
        + "Nombre: " + name + " Aka: " + aka +
        " Comentarios extra: " + comments +
        " Latas cerveza por dia: " + numbers_cans_day +
        " Dinero a poner: " + money_to_put + " €"+
        " Disponibilidad: " + disponibility + " }";
    }
}
