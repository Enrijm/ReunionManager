package enrique.application.reunionManager.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import static javax.persistence.FetchType.EAGER;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reventao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false,updatable = false)
    public Long id;
    public String name;
    public String password;
    public String aka;
    public String comments;
    public int numbers_cans_day;
    public int money_to_put;
    public String disponibility;
    @ManyToMany(fetch = EAGER)
    private Collection<Role> roles = new ArrayList<>();

    @Override
    public String toString(){
        return "Reventao{ " +  "Id_Reventao: " + id
        + "Nombre: " + name + " Aka: " + aka +
        " Comentarios: " + comments +
        " Latas cerveza por dia: " + numbers_cans_day +
        " Dinero a poner: " + money_to_put + " €"+
        " Disponibilidad: " + disponibility + " }";
    }

}
