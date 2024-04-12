package matteorlando.U5W2D5.services;

import jakarta.validation.Valid;
import matteorlando.U5W2D5.entities.Dipendente;
import matteorlando.U5W2D5.repository.DipendenteRepository;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequestMapping("/dipendenti")
public class DipendenteService {

    @Autowired
    private DipendenteRepository dipendenteRepository;

    public DipendenteService(DipendenteRepository dipendenteRepository) {
        this.dipendenteRepository = dipendenteRepository;
    }

    // Metodo per creare un nuovo dipendente
    @PostMapping("/")
    public ResponseEntity<Dipendente> createDipendente(@Valid @RequestBody Dipendente dipendente) {
        Dipendente newDipendente = dipendenteRepository.save(dipendente);
        return new ResponseEntity<>(newDipendente, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<Dipendente>> getAllDipendenti() {
        List<Dipendente> dipendenti = dipendenteRepository.findAll();
        return new ResponseEntity<>(dipendenti, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dipendente> getDipendenteById(@PathVariable("id") UUID id) {
        Optional<Dipendente> dipendente = dipendenteRepository.findById(id);
        if (dipendente.isPresent()) {
            return new ResponseEntity<>(dipendente.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Dipendente> updateDipendente(@PathVariable("id") UUID id, @RequestBody Dipendente dipendenteDetails) {
        Optional<Dipendente> dipendente = dipendenteRepository.findById(id);
        if (dipendente.isPresent()) {
            Dipendente updatedDipendente = dipendente.get();
            updatedDipendente.setUsername(dipendenteDetails.getUsername());
            updatedDipendente.setNome(dipendenteDetails.getNome());
            updatedDipendente.setCognome(dipendenteDetails.getCognome());
            updatedDipendente.setEmail(dipendenteDetails.getEmail());
            dipendenteRepository.save(updatedDipendente);
            return new ResponseEntity<>(updatedDipendente, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteDipendente(@PathVariable("id") UUID id) {
        try {
            dipendenteRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{id}/upload-image")
    public ResponseEntity<String> uploadImage(@PathVariable("id") UUID id, @RequestParam("file") @NotNull MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Il file dell'immagine è vuoto.", HttpStatus.BAD_REQUEST);
        }

        try {
            // Salva l'immagine sul filesystem
            String fileName = id + "_" + file.getOriginalFilename();
            String uploadDir = "/path/to/upload/directory";
            String filePath = uploadDir + File.separator + fileName;

            File dest = new File(filePath);
            FileUtils.writeByteArrayToFile(dest, file.getBytes());


            Optional<Dipendente> optionalDipendente = dipendenteRepository.findById(id);
            if (optionalDipendente.isPresent()) {
                Dipendente dipendente = optionalDipendente.get();
                dipendente.setImmagine(filePath);
                dipendenteRepository.save(dipendente);
                return new ResponseEntity<>("Upload dell'immagine completato con successo.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Dipendente non trovato.", HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            return new ResponseEntity<>("Errore durante l'upload dell'immagine: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
