package matteorlando.U5W2D5.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Dispositivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo;
    private String stato;

    //un dispositivo puó essere posseduto solo da un dipendente alla volta
    @ManyToOne
    @JoinColumn(name = "dipendente_id")
    private Dipendente dipendente;

}