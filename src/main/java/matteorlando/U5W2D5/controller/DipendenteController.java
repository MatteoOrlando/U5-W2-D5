package matteorlando.U5W2D5.controller;

import jakarta.validation.Valid;
import matteorlando.U5W2D5.entities.Dipendente;
import matteorlando.U5W2D5.repository.DipendenteRepository;
import matteorlando.U5W2D5.services.DipendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/dipendenti")
public class DipendenteController {

    @Autowired
    private DipendenteService dipendenteService;
    @Autowired
    private DipendenteRepository dipendenteRepository;
    @PostMapping("/")
    public ResponseEntity<Dipendente> createDipendente(@Valid @RequestBody Dipendente dipendente) {
        return dipendenteService.createDipendente(dipendente);
    }

    @GetMapping("/")
    public ResponseEntity<List<Dipendente>> getAllDipendenti() {
        return dipendenteService.getAllDipendenti();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dipendente> getDipendenteById(@PathVariable("id") UUID id) {
        return dipendenteService.getDipendenteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Dipendente> updateDipendente(@PathVariable("id") UUID id, @RequestBody Dipendente dipendenteDetails) {
        return dipendenteService.updateDipendente(id, dipendenteDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteDipendente(@PathVariable("id") UUID id) {
        return dipendenteService.deleteDipendente(id);
    }

    @PostMapping("/{id}/upload-image")
    public ResponseEntity<String> uploadImage(@PathVariable("id") UUID id, @RequestParam("file") MultipartFile file) {
        return dipendenteService.uploadImage(id, file);
    }
    @GetMapping("/existsByEmail")
    public ResponseEntity<Boolean> existsByEmail(@RequestParam String email) {
        boolean exists = dipendenteRepository.existsByEmail(email);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }

    @GetMapping("/findByEmail")
    public ResponseEntity<Dipendente> findByEmail(@RequestParam String email) {
        Optional<Dipendente> dipendente = dipendenteRepository.findByEmail(email);
        return dipendente.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
