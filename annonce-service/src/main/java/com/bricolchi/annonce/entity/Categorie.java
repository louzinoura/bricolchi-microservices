package com.bricolchi.annonce.entity;

public enum Categorie {
    PLOMBERIE("Plomberie"),
    ELECTRICITE("Électricité"),
    MENUISERIE("Menuiserie"),
    PEINTURE("Peinture"),
    JARDINAGE("Jardinage"),
    NETTOYAGE("Nettoyage"),
    REPARATION("Réparation"),
    INFORMATIQUE("Informatique"),
    AUTRE("Autre");

    private final String displayName;

    Categorie(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}