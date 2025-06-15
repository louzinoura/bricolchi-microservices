package com.bricolchi.annonce.controller;

import com.bricolchi.annonce.dto.*;
import com.bricolchi.annonce.service.AnnonceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/annonce")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AnnonceController {

    private final AnnonceService annonceService;

    @GetMapping
    public ResponseEntity<Page<AnnonceDTO>> getAllAnnonces(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {

        Page<AnnonceDTO> annonces = annonceService.getAllAnnonces(page, size, sortBy, sortDir);
        return ResponseEntity.ok(annonces);
    }

    @PostMapping
    public ResponseEntity<AnnonceDTO> createAnnonce(@RequestBody CreateAnnonceRequest request) {
        try {
            AnnonceDTO annonce = annonceService.createAnnonce(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(annonce);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnnonceDTO> getAnnonceById(@PathVariable Long id) {
        try {
            AnnonceDTO annonce = annonceService.getAnnonceById(id);
            return ResponseEntity.ok(annonce);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnnonceDTO> updateAnnonce(
            @PathVariable Long id,
            @RequestBody UpdateAnnonceRequest request) {
        try {
            AnnonceDTO annonce = annonceService.updateAnnonce(id, request);
            return ResponseEntity.ok(annonce);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnnonce(@PathVariable Long id) {
        try {
            annonceService.deleteAnnonce(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<Page<AnnonceDTO>> searchAnnonces(
            @RequestParam(required = false) String categorie,
            @RequestParam(required = false) String localisation,
            @RequestParam(required = false) Double prixMin,
            @RequestParam(required = false) Double prixMax,
            @RequestParam(required = false) String motCle,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setCategorie(categorie);
        searchRequest.setLocalisation(localisation);
        searchRequest.setPrixMin(prixMin);
        searchRequest.setPrixMax(prixMax);
        searchRequest.setMotCle(motCle);
        searchRequest.setPage(page);
        searchRequest.setSize(size);
        searchRequest.setSortBy(sortBy);
        searchRequest.setSortDir(sortDir);

        Page<AnnonceDTO> annonces = annonceService.searchAnnonces(searchRequest);
        return ResponseEntity.ok(annonces);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<String>> getCategories() {
        List<String> categories = annonceService.getCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AnnonceDTO>> getAnnoncesByUser(@PathVariable Long userId) {
        List<AnnonceDTO> annonces = annonceService.getAnnoncesByUser(userId);
        return ResponseEntity.ok(annonces);
    }
}
