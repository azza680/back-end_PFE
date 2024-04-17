package com.boky.PFE.entite;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor

public class Annonce {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String type_d_hebergement;
    private int nb_voyageur;
    private int nb_chamber;
    private int nb_lits;
    private int nb_salles;
    private String equipement;
    private String equipement_specail;
    private String image ;
    private String titre ;
    private String description;
    private String mode_de_confirmation;
    private String frais_de_service;
    private boolean reduction_semaine;
    private boolean reduction_mois;
    private float prix;
    private String pays;
    private String etat;
    private String libelle_de_voie;
    private String code_postale;
    private String heure_depart;
    private String heure_arriver;

    public String getType_d_hebergement() {
        return type_d_hebergement;
    }

    public void setType_d_hebergement(String type_d_hebergement) {
        this.type_d_hebergement = type_d_hebergement;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public int getNb_chamber() {
        return nb_chamber;
    }

    public int getNb_voyageur() {
        return nb_voyageur;
    }

    public void setNb_voyageur(int nb_voyageur) {
        this.nb_voyageur = nb_voyageur;
    }

    public void setNb_chamber(int nb_chamber) {
        this.nb_chamber = nb_chamber;
    }

    public int getNb_lits() {
        return nb_lits;
    }

    public void setNb_lits(int nb_lits) {
        this.nb_lits = nb_lits;
    }

    public int getNb_salles() {
        return nb_salles;
    }

    public void setNb_salles(int nb_salles) {
        this.nb_salles = nb_salles;
    }

    public String getEquipement() {
        return equipement;
    }

    public void setEquipement(String equipement) {
        this.equipement = equipement;
    }

    public String getEquipement_specail() {
        return equipement_specail;
    }

    public void setEquipement_specail(String equipement_specail) {
        this.equipement_specail = equipement_specail;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMode_de_confirmation() {
        return mode_de_confirmation;
    }

    public void setMode_de_confirmation(String mode_de_confirmation) {
        this.mode_de_confirmation = mode_de_confirmation;
    }

    public String getFrais_de_service() {
        return frais_de_service;
    }

    public void setFrais_de_service(String frais_de_service) {
        this.frais_de_service = frais_de_service;
    }

    public boolean isReduction_semaine() {
        return reduction_semaine;
    }

    public void setReduction_semaine(boolean reduction_semaine) {
        this.reduction_semaine = reduction_semaine;
    }

    public boolean isReduction_mois() {
        return reduction_mois;
    }

    public void setReduction_mois(boolean reduction_mois) {
        this.reduction_mois = reduction_mois;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getLibelle_de_voie() {
        return libelle_de_voie;
    }

    public void setLibelle_de_voie(String libelle_de_voie) {
        this.libelle_de_voie = libelle_de_voie;
    }

    public String getCode_postale() {
        return code_postale;
    }

    public void setCode_postale(String code_postale) {
        this.code_postale = code_postale;
    }

    public String getHeure_depart() {
        return heure_depart;
    }

    public void setHeure_depart(String heure_depart) {
        this.heure_depart = heure_depart;
    }

    public String getHeure_arriver() {
        return heure_arriver;
    }

    public void setHeure_arriver(String heure_arriver) {
        this.heure_arriver = heure_arriver;
    }
}