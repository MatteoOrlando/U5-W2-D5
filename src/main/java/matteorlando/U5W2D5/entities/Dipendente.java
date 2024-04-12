package matteorlando.U5W2D5.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name="dipendente")
@ToString
@Setter
@Getter

public class Dipendente {
    @Id
    @GeneratedValue
    private UUID id;

    @NotBlank(message = "Il campo 'username' non può essere vuoto")
    private String username;

    @NotBlank(message = "Il campo 'nome' non può essere vuoto")
    private String nome;

    @NotBlank(message = "Il campo 'cognome' non può essere vuoto")
    private String cognome;

    @Email(message = "Deve essere un'email valida")
    @NotBlank(message = "Il campo 'email' non può essere vuoto")
    private String email;

    @Setter
    @Getter
    private String immagine;

    //un dipendente puó avere piu dispositivi
    @OneToMany(mappedBy = "dipendente", cascade = CascadeType.ALL)
    private List<Dispositivo> dispositivi;

}