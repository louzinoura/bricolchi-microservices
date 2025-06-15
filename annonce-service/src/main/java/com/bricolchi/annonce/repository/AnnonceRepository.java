package com.bricolchi.annonce.repository;

import com.bricolchi.annonce.entity.Annonce;
import com.bricolchi.annonce.entity.Annonce.StatusAnnonce;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AnnonceRepository extends JpaRepository<Annonce, Long> {

    List<Annonce> findByStatus(StatusAnnonce status);

    List<Annonce> findByUserId(Long userId);

    List<Annonce> findByCategorie(String categorie);

    List<Annonce> findByLocalisation(String localisation);

    @Query("SELECT a FROM Annonce a WHERE a.status = 'ACTIVE' AND a.disponible = true")
    Page<Annonce> findActiveAnnonces(Pageable pageable);

    @Query("SELECT a FROM Annonce a WHERE " +
            "(:categorie IS NULL OR a.categorie = :categorie) AND " +
            "(:localisation IS NULL OR LOWER(a.localisation) LIKE LOWER(CONCAT('%', :localisation, '%'))) AND " +
            "(:prixMin IS NULL OR a.prix >= :prixMin) AND " +
            "(:prixMax IS NULL OR a.prix <= :prixMax) AND " +
            "(:motCle IS NULL OR LOWER(a.titre) LIKE LOWER(CONCAT('%', :motCle, '%')) OR LOWER(a.description) LIKE LOWER(CONCAT('%', :motCle, '%'))) AND " +
            "a.status = 'ACTIVE' AND a.disponible = true")
    Page<Annonce> searchAnnonces(@Param("categorie") String categorie,
                                 @Param("localisation") String localisation,
                                 @Param("prixMin") Double prixMin,
                                 @Param("prixMax") Double prixMax,
                                 @Param("motCle") String motCle,
                                 Pageable pageable);

    @Query("SELECT DISTINCT a.categorie FROM Annonce a WHERE a.status = 'ACTIVE'")
    List<String> findDistinctCategories();
}

