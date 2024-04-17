package com.boky.PFE.restController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.boky.PFE.entite.Utilisateur;
import com.boky.PFE.repository.UtilisateurRepository;

import com.boky.PFE.service.EmailService;
import com.boky.PFE.service.UtilisateurService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/Utilisateur")
public class UtilisateurRestController {
    @Autowired
    UtilisateurRepository utilisateurRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    @PostMapping("/register")
    ResponseEntity<?> AjouterUtilisateur (@RequestBody Utilisateur utilisateur)
    {
        return utilisateurService.AjouterUtilisateur(utilisateur);
    }
    @Autowired
    UtilisateurService utilisateurService;
    @Autowired
    EmailService emailService;
    @RequestMapping(method = RequestMethod.GET)
    public List<Utilisateur> AfficherUtilisateur()
    {
        return utilisateurService.AfficherUtilisateur();
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE )

    public void SupprimerUtilisateur(@PathVariable("id") Long id){
        utilisateurService.SupprimerUtilisateur(id);

    }
    @PostMapping("/Login")
    public ResponseEntity<Map<String, Object>> loginUtilisateur(@RequestBody Utilisateur utilisateur) {
        System.out.println("in login-utilisateur"+utilisateur);
        HashMap<String, Object> response = new HashMap<>();

        Utilisateur userFromDB = utilisateurRepository.findUtilisateurByEmail(utilisateur.getEmail());
        System.out.println("userFromDB+utilisateur"+userFromDB);
        if (userFromDB == null) {
            response.put("message", "Utilisateur not found !");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } else {
            boolean compare = this.bCryptPasswordEncoder.matches(utilisateur.getMdp(), userFromDB.getMdp());
            System.out.println("compare"+compare);
            if (!compare) {
                response.put("message", "Utilisateur not found !");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }else
            {
                String token = Jwts.builder()
                        .claim("data", userFromDB)
                        .signWith(SignatureAlgorithm.HS256, "SECRET")
                        .compact();
                response.put("token", token);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }

        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Utilisateur ModifierUtilisateur(@RequestBody Utilisateur utilisateur, @PathVariable("id") Long id) {
        Utilisateur newUtilisateur = null;
        if (utilisateurRepository.findById(id).isPresent()) { //ken user deja mawjoud
            Utilisateur utilisateur1 = utilisateurRepository.findById(id).get();
            var utilisateurId = utilisateur.getId();
            var nom = utilisateur.getNom();
            var prenom = utilisateur.getPrenom();
            var email = utilisateur.getEmail();
            var Date_N = utilisateur.getDate_de_naissance();
            var tel = utilisateur.getTelephone();
            var adresse = utilisateur.getAdresse();
            var mdp = utilisateur.getMdp();
            var role = utilisateur.getRole();
            utilisateur1.setId(utilisateurId);
            utilisateur1.setNom(nom);
            utilisateur1.setPrenom(prenom);
            utilisateur1.setEmail(email);
            utilisateur1.setDate_de_naissance(Date_N);
            utilisateur1.setTelephone(tel);
            utilisateur1.setAdresse(adresse);
            utilisateur1.setMdp(mdp);
            utilisateur1.setRole(role);


//mta3 yjih mail fih l etat
            utilisateur.setMdp(this.bCryptPasswordEncoder.encode(utilisateur1.getMdp()));
            if (utilisateur.isEtat() != utilisateur1.isEtat()) {
                //ternary expression
                String etat = utilisateur1.isEtat() ? "Bloqué" : "Accepté";
                emailService.SendSimpleMessage(utilisateur1.getEmail(), "L'etat de votre compte", "votre compte a été " + etat);
            }
            utilisateur1.setEtat(utilisateur.isEtat());
            newUtilisateur = utilisateurRepository.save(utilisateur1);
        }
        return newUtilisateur;
    }
    @RequestMapping(value = "/{id}" , method = RequestMethod.GET)
    public Optional<Utilisateur> getUtilisateurById(@PathVariable("id") long id){

        Optional<Utilisateur> utilisateur = utilisateurService.getUtilisateurById(id);
        return utilisateur;
    }
    @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<?> confirmUserAccount(@RequestParam("token")String confirmationToken) {
        return utilisateurService.ConfirmationEmail(confirmationToken);
    }
    @PostMapping("/{userId}/photo")
    public ResponseEntity<?> uploadProfilePhoto(@PathVariable Long userId, @RequestParam("photo") MultipartFile photo) {
        // Recherche de l'utilisateur par son ID
        Optional<Utilisateur> optionalUtilisateur = utilisateurRepository.findById(userId);
        if (optionalUtilisateur.isPresent()) {
            Utilisateur utilisateur = optionalUtilisateur.get();
            try {
                // Convertir la photo en tableau de bytes
                byte[] photoBytes = photo.getBytes();
                // Stocker la photo dans l'attribut correspondant de l'entité Utilisateur
                utilisateur.setPhoto(photoBytes);
                // Enregistrer les modifications dans la base de données
                utilisateurRepository.save(utilisateur);
                return ResponseEntity.ok().build();
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{userId}/photo")
    public ResponseEntity<?> updateProfilePhoto(@PathVariable Long userId, @RequestParam("photo") MultipartFile photo) {
        // Recherche de l'utilisateur par son ID
        Optional<Utilisateur> optionalUtilisateur = utilisateurRepository.findById(userId);
        if (optionalUtilisateur.isPresent()) {
            Utilisateur utilisateur = optionalUtilisateur.get();
            try {
                // Convertir la photo en tableau de bytes
                byte[] photoBytes = photo.getBytes();
                // Mettre à jour la photo dans l'attribut correspondant de l'entité Utilisateur
                utilisateur.setPhoto(photoBytes);
                // Enregistrer les modifications dans la base de données
                utilisateurRepository.save(utilisateur);
                return ResponseEntity.ok().build();
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }

    }
}
