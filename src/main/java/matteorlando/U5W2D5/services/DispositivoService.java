package matteorlando.U5W2D5.services;

import matteorlando.U5W2D5.entities.Dispositivo;
import matteorlando.U5W2D5.repository.DispositivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/dispositivi")
public class DispositivoService {

    @Autowired
    private DispositivoRepository dispositivoRepository;


    @PostMapping("/")
    public ResponseEntity<Dispositivo> createDispositivo(@RequestBody Dispositivo dispositivo) {
        Dispositivo newDispositivo = dispositivoRepository.save(dispositivo);
        return new ResponseEntity<>(newDispositivo, HttpStatus.CREATED);
    }


    @GetMapping("/")
    public ResponseEntity<List<Dispositivo>> getAllDispositivi() {
        List<Dispositivo> dispositivi = dispositivoRepository.findAll();
        return new ResponseEntity<>(dispositivi, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dispositivo> getDispositivoById(@PathVariable("id") Long id) {
        Optional<Dispositivo> dispositivo = dispositivoRepository.findById(id);
        if (dispositivo.isPresent()) {
            return new ResponseEntity<>(dispositivo.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Dispositivo> updateDispositivo(@PathVariable("id") Long id, @RequestBody Dispositivo dispositivoDetails) {
        Optional<Dispositivo> dispositivo = dispositivoRepository.findById(id);
        if (dispositivo.isPresent()) {
            Dispositivo updatedDispositivo = dispositivo.get();
            updatedDispositivo.setTipo(dispositivoDetails.getTipo());
            updatedDispositivo.setStato(dispositivoDetails.getStato());
            dispositivoRepository.save(updatedDispositivo);
            return new ResponseEntity<>(updatedDispositivo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteDispositivo(@PathVariable("id") Long id) {
        try {
            dispositivoRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
