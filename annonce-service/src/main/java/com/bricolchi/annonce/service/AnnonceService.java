package com.bricolchi.annonce.service;

import com.bricolchi.annonce.dto.*;
import com.bricolchi.annonce.entity.Annonce;
import com.bricolchi.annonce.entity.Categorie;
import com.bricolchi.annonce.repository.AnnonceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
@Transactional
public class AnnonceService {

    private final AnnonceRepository annonceRepository;

    public Page<AnnonceDTO> getAllAnnonces(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return annonceRepository.findActiveAnnonces(pageable)
                .map(this::convertToDTO);
    }

    public AnnonceDTO createAnnonce(CreateAnnonceRequest request) {
        Annonce annonce = new Annonce();
        annonce.setTitre(request.getTitre());
        annonce.setDescription(request.getDescription());
        annonce.setCategorie(request.getCategorie());
        annonce.setPrix(request.getPrix());
        annonce.setLocalisation(request.getLocalisation());
        annonce.setAdresse(request.getAdresse());
        annonce.setUserId(request.getUserId());
        annonce.setUserName(request.getUserName());
        annonce.setUserEmail(request.getUserEmail());
        annonce.setUserPhone(request.getUserPhone());
        annonce.setImages(request.getImages());
        annonce.setTags(request.getTags());
        annonce.setLatitude(request.getLatitude());
        annonce.setLongitude(request.getLongitude());

        Annonce savedAnnonce = annonceRepository.save(annonce);
        return convertToDTO(savedAnnonce);
    }

    public AnnonceDTO getAnnonceById(Long id) {
        Annonce annonce = annonceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Annonce non trouvée avec l'ID: " + id));
        return convertToDTO(annonce);
    }

    public AnnonceDTO updateAnnonce(Long id, UpdateAnnonceRequest request) {
        Annonce annonce = annonceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Annonce non trouvée avec l'ID: " + id));

        if (request.getTitre() != null) annonce.setTitre(request.getTitre());
        if (request.getDescription() != null) annonce.setDescription(request.getDescription());
        if (request.getCategorie() != null) annonce.setCategorie(request.getCategorie());
        if (request.getPrix() != null) annonce.setPrix(request.getPrix());
        if (request.getLocalisation() != null) annonce.setLocalisation(request.getLocalisation());
        if (request.getAdresse() != null) annonce.setAdresse(request.getAdresse());
        if (request.getImages() != null) annonce.setImages(request.getImages());
        if (request.getTags() != null) annonce.setTags(request.getTags());
        if (request.getLatitude() != null) annonce.setLatitude(request.getLatitude());
        if (request.getLongitude() != null) annonce.setLongitude(request.getLongitude());
        if (request.getDisponible() != null) annonce.setDisponible(request.getDisponible());

        Annonce updatedAnnonce = annonceRepository.save(annonce);
        return convertToDTO(updatedAnnonce);
    }

    public void deleteAnnonce(Long id) {
        Annonce annonce = annonceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Annonce non trouvée avec l'ID: " + id));
        annonce.setStatus(Annonce.StatusAnnonce.ARCHIVED);
        annonceRepository.save(annonce);
    }

    public Page<AnnonceDTO> searchAnnonces(SearchRequest searchRequest) {
        Sort sort = searchRequest.getSortDir().equalsIgnoreCase("desc") ?
                Sort.by(searchRequest.getSortBy()).descending() :
                Sort.by(searchRequest.getSortBy()).ascending();

        Pageable pageable = PageRequest.of(searchRequest.getPage(), searchRequest.getSize(), sort);

        return annonceRepository.searchAnnonces(
                searchRequest.getCategorie(),
                searchRequest.getLocalisation(),
                searchRequest.getPrixMin(),
                searchRequest.getPrixMax(),
                searchRequest.getMotCle(),
                pageable
        ).map(this::convertToDTO);
    }

    public List<String> getCategories() {
        return Arrays.stream(Categorie.values())
                .map(Categorie::getDisplayName)
                .collect(Collectors.toList());
    }

    public List<AnnonceDTO> getAnnoncesByUser(Long userId) {
        return annonceRepository.findByUserId(userId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private AnnonceDTO convertToDTO(Annonce annonce) {
        AnnonceDTO dto = new AnnonceDTO();
        dto.setId(annonce.getId());
        dto.setTitre(annonce.getTitre());
        dto.setDescription(annonce.getDescription());
        dto.setCategorie(annonce.getCategorie());
        dto.setPrix(annonce.getPrix());
        dto.setLocalisation(annonce.getLocalisation());
        dto.setAdresse(annonce.getAdresse());
        dto.setUserId(annonce.getUserId());
        dto.setUserName(annonce.getUserName());
        dto.setUserEmail(annonce.getUserEmail());
        dto.setUserPhone(annonce.getUserPhone());
        dto.setStatus(annonce.getStatus().name());
        dto.setImages(annonce.getImages());
        dto.setTags(annonce.getTags());
        dto.setCreatedAt(annonce.getCreatedAt());
        dto.setUpdatedAt(annonce.getUpdatedAt());
        dto.setLatitude(annonce.getLatitude());
        dto.setLongitude(annonce.getLongitude());
        dto.setDisponible(annonce.getDisponible());
        return dto;
    }
}
