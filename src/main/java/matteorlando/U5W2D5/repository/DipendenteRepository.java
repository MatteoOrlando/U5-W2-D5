package matteorlando.U5W2D5.repository;

import matteorlando.U5W2D5.entities.Dipendente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DipendenteRepository extends JpaRepository<Dipendente, UUID> {

    boolean existsByEmail(String email);

    Optional<Dipendente> findByEmail (String email);


}