package sopra.formation.test;

import java.text.SimpleDateFormat;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import sopra.formation.Application;
import sopra.formation.model.Adresse;
import sopra.formation.model.Civilite;
import sopra.formation.model.Dispositif;
import sopra.formation.model.Evaluation;
import sopra.formation.model.Filiere;
import sopra.formation.model.Formateur;
import sopra.formation.model.Matiere;
import sopra.formation.model.NiveauEtude;
import sopra.formation.model.Salle;
import sopra.formation.model.Stagiaire;
import sopra.formation.model.UE;

public class TestJPAData {

	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				
		EntityManager em = null;
		EntityTransaction tx = null;

		try {
			em = Application.getInstance().getEntityManagerFactory().createEntityManager();

			tx = em.getTransaction();
			tx.begin();

			Evaluation evalLea = new Evaluation(15, 13, "super délégué");

			em.persist(evalLea);
			
			

			Stagiaire lea = new Stagiaire("lea.dumont@gmail.com"); // new
			lea.setCivilite(Civilite.MLLE);
			lea.setNom("DUMONT");
			lea.setPrenom("Léa");
			lea.setTelephone("0606060606");
			lea.setDtNaissance(sdf.parse("25/12/1995"));
			lea.setNiveauEtude(NiveauEtude.BAC_8);

			Adresse adrLea = new Adresse();

			adrLea.setRue("5 avenue villemejan");
			adrLea.setComplement("Résidence Diderot");
			adrLea.setCodePostal("33600");
			adrLea.setVille("PESSAC");
			
			lea.setAdresse(adrLea);
			
			
			em.persist(lea);
					
			lea.setEvaluation(evalLea);

			Stagiaire manon = new Stagiaire("serain.manon@yahoo.com");
			manon.setCivilite(Civilite.MME);
			manon.setNom("SERAIN");
			manon.setPrenom("Manon");
			manon.setTelephone("0645457845");
			manon.setDtNaissance(sdf.parse("01/01/1996"));
			manon.setNiveauEtude(NiveauEtude.BAC_5);

			manon.setAdresse(new Adresse("21 avenue du Colonel Pierre Bourgoin", "", "33000", "BORDEAUX"));

			Evaluation evalManon = new Evaluation(11, 19, "Très bon relationnel");
			em.persist(evalManon);
			
			manon.setEvaluation(evalManon);
			
			em.persist(manon);

			Salle wim = new Salle("San Fransisco", 15, true);

			wim.setAdr(new Adresse("86 avenue JFK", "1er étage", "33700", "Mérignac"));

			em.persist(wim);

			Formateur eric = new Formateur("e.sultan@ajc-ingenierie.fr");
			eric.setCivilite(Civilite.M);
			eric.setNom("SULTAN");
			eric.setPrenom("Eric");
			eric.setTelephone("0645104506");
			eric.setAdresse("4 rue de Corono", "", "33160", "Saint-Médard-en-Jalles");
			eric.setReferent(true);
			eric.setExperience(20);

			em.persist(eric);

			Filiere tpt = new Filiere("TPT");
			tpt.setIntitule("JAVA SPRING ANGULAR");
			tpt.setDtDebut(sdf.parse("02/06/2021"));
			tpt.setDuree(57);
			tpt.setDispositif(Dispositif.POEI);
			tpt.setReferent(eric);

			em.persist(tpt);
			
			lea.setFiliere(tpt);
			manon.setFiliere(tpt);
		
			Matiere unix = new Matiere("UNIX", 1);

			em.persist(unix);
			
			UE ueUnix = new UE(4738, 1, 1);
			ueUnix.setFiliere(tpt);
			ueUnix.setMatiere(unix);
			ueUnix.setFormateur(eric);
			ueUnix.setSalle(wim);
			
			em.persist(ueUnix);

			Matiere algo = new Matiere("ALGO EN JAVA", 3);

			em.persist(algo);
			
			UE ueAlgo = new UE(3326, 3, 2);
			ueAlgo.setFiliere(tpt);
			ueAlgo.setMatiere(algo);
			ueAlgo.setFormateur(eric);
			ueAlgo.setSalle(wim);
			
			em.persist(ueAlgo);
			
			Matiere uml = new Matiere("UML", 1);

			em.persist(uml);
			
			UE ueUml = new UE(2369, 1, 3);
			ueUml.setFiliere(tpt);
			ueUml.setMatiere(uml);
			ueUml.setFormateur(eric);
			ueUml.setSalle(wim);

			em.persist(ueUml);

			Matiere javaObjet = new Matiere("JAVA OBJET", 3);
			
			em.persist(javaObjet);

			UE ueJavaObjet = new UE(3542, 2, 4);
			ueJavaObjet.setFiliere(tpt);
			ueJavaObjet.setMatiere(javaObjet);
			ueJavaObjet.setFormateur(eric);
			ueJavaObjet.setSalle(wim);
						
			em.persist(ueJavaObjet);
			
			eric.getCompetences().add(unix);
			eric.getCompetences().add(algo);
			eric.getCompetences().add(uml);
			eric.getCompetences().add(javaObjet);
			
			
			
			
			tx.commit();  
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
		} finally {
			if (em != null) {
				em.close();
			}
		}

		Application.getInstance().getEntityManagerFactory().close();

	}

}
