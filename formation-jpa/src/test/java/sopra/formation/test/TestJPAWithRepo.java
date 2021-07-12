package sopra.formation.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

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
import sopra.formation.repository.IEvaluationRepository;
import sopra.formation.repository.IFiliereRepository;
import sopra.formation.repository.IMatiereRepository;
import sopra.formation.repository.IPersonneRepository;
import sopra.formation.repository.ISalleRepository;
import sopra.formation.repository.IUERepository;

public class TestJPAWithRepo {

	public static void main(String[] args) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		IEvaluationRepository evaluationRepo = Application.getInstance().getEvaluationRepo();
		IFiliereRepository filiereRepo = Application.getInstance().getFiliereRepo();
		IMatiereRepository matiereRepo = Application.getInstance().getMatiereRepo();
		IPersonneRepository personneRepo = Application.getInstance().getPersonneRepo();
		ISalleRepository salleRepo = Application.getInstance().getSalleRepo();
		IUERepository ueRepo = Application.getInstance().getUeRepo();

		Evaluation evalLea = new Evaluation(15, 13, "super délégué");

		evalLea = evaluationRepo.save(evalLea);

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

		lea.setEvaluation(evalLea);
		
		lea = (Stagiaire) personneRepo.save(lea);
		
		Stagiaire manon = new Stagiaire("serain.manon@yahoo.com");
		manon.setCivilite(Civilite.MME);
		manon.setNom("SERAIN");
		manon.setPrenom("Manon");
		manon.setTelephone("0645457845");
		manon.setDtNaissance(sdf.parse("01/01/1996"));
		manon.setNiveauEtude(NiveauEtude.BAC_5);

		manon.setAdresse(new Adresse("21 avenue du Colonel Pierre Bourgoin", "", "33000", "BORDEAUX"));

		manon = (Stagiaire) personneRepo.save(manon);

		Salle wim = new Salle("San Fransisco", 15, true);

		wim.setAdr(new Adresse("86 avenue JFK", "1er étage", "33700", "Mérignac"));

		wim = salleRepo.save(wim);

		Formateur eric = new Formateur("e.sultan@ajc-ingenierie.fr");
		eric.setCivilite(Civilite.M);
		eric.setNom("SULTAN");
		eric.setPrenom("Eric");
		eric.setTelephone("0645104506");
		eric.setAdresse("4 rue de Corono", "", "33160", "Saint-Médard-en-Jalles");
		eric.setReferent(true);
		eric.setExperience(20);

		eric = (Formateur) personneRepo.save(eric);

		Filiere tpt = new Filiere("TPT");
		tpt.setIntitule("JAVA SPRING ANGULAR");
		tpt.setDtDebut(sdf.parse("02/06/2021"));
		tpt.setDuree(57);
		tpt.setDispositif(Dispositif.POEI);
		tpt.setReferent(eric);

		tpt = filiereRepo.save(tpt);
		
		lea.setFiliere(tpt);
		manon.setFiliere(tpt);
		
		lea = (Stagiaire) personneRepo.save(lea);
		manon = (Stagiaire) personneRepo.save(manon);

		Matiere unix = new Matiere("UNIX", 1);

		unix = matiereRepo.save(unix);
		
		eric.addCompetence(unix);
		
		UE ueUnix = new UE(4738, 1, 1);
		ueUnix.setFiliere(tpt);
		ueUnix.setFormateur(eric);
		ueUnix.setMatiere(unix);
		ueUnix.setSalle(wim);
		
		ueUnix = ueRepo.save(ueUnix);

		Matiere algo = new Matiere("ALGO EN JAVA", 3);

		algo = matiereRepo.save(algo);
		
		eric.addCompetence(algo);
		
		UE ueAlgo = new UE(3326, 3, 2);
		ueAlgo.setFiliere(tpt);
		ueAlgo.setFormateur(eric);
		ueAlgo.setMatiere(algo);
		ueAlgo.setSalle(wim);
		
		ueAlgo = ueRepo.save(ueAlgo);
		
		Matiere uml = new Matiere("UML", 1);

		uml = matiereRepo.save(uml);
		
		eric.addCompetence(uml);

		UE ueUml = new UE(2369, 1, 3);
		ueUml.setFiliere(tpt);
		ueUml.setFormateur(eric);
		ueUml.setMatiere(uml);
		ueUml.setSalle(wim);

		ueUml = ueRepo.save(ueUml);

		Matiere javaObjet = new Matiere("JAVA OBJET", 3);
		
		javaObjet = matiereRepo.save(javaObjet);

		eric.addCompetence(javaObjet);
		
		eric = (Formateur) personneRepo.save(eric);

		UE ueJavaObjet = new UE(3542, 2, 4);
		ueJavaObjet.setFiliere(tpt);
		ueJavaObjet.setFormateur(eric);
		ueJavaObjet.setMatiere(javaObjet);
		ueJavaObjet.setSalle(wim);
		
		ueJavaObjet = ueRepo.save(ueJavaObjet);

		Filiere tptFind = filiereRepo.findByIdWithReferent(tpt.getId());
		
		System.out.println(tptFind.getReferent().getNom());
	}

}
