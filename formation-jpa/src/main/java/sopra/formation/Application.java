package sopra.formation;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import sopra.formation.repository.IEvaluationRepository;
import sopra.formation.repository.IFiliereRepository;
import sopra.formation.repository.IMatiereRepository;
import sopra.formation.repository.IOrdinateurRepository;
import sopra.formation.repository.IOrdinateurWithEmbeddedIdRepository;
import sopra.formation.repository.IPersonneRepository;
import sopra.formation.repository.ISalleRepository;
import sopra.formation.repository.IUERepository;
import sopra.formation.repository.jpa.EvaluationRepositoryJpa;
import sopra.formation.repository.jpa.FiliereRepositoryJpa;
import sopra.formation.repository.jpa.MatiereRepositoryJpa;
import sopra.formation.repository.jpa.OrdinateurRepositoryJpa;
import sopra.formation.repository.jpa.OrdinateurWithEmbeddedIdRepositoryJpa;
import sopra.formation.repository.jpa.PersonneRepositoryJpa;
import sopra.formation.repository.jpa.SalleRepositoryJpa;
import sopra.formation.repository.jpa.UERepositoryJpa;

public class Application {
	private static Application instance = null;

	private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("formation-jpa");

	private final IEvaluationRepository evaluationRepo = new EvaluationRepositoryJpa();
	private final IFiliereRepository filiereRepo = new FiliereRepositoryJpa();
	private final IMatiereRepository matiereRepo = new MatiereRepositoryJpa();
	private final IPersonneRepository personneRepo = new PersonneRepositoryJpa();
	private final ISalleRepository salleRepo = new SalleRepositoryJpa();
	private final IUERepository ueRepo = new UERepositoryJpa();
	private final IOrdinateurRepository ordinateurRepo = new OrdinateurRepositoryJpa();
	private final IOrdinateurWithEmbeddedIdRepository ordinateurWithEmbeddedIdRepo = new OrdinateurWithEmbeddedIdRepositoryJpa();

	private Application() {
	}

	public static Application getInstance() {
		if (instance == null) {
			instance = new Application();
		}

		return instance;
	}

	public EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	public IEvaluationRepository getEvaluationRepo() {
		return evaluationRepo;
	}

	public IFiliereRepository getFiliereRepo() {
		return filiereRepo;
	}

	public IMatiereRepository getMatiereRepo() {
		return matiereRepo;
	}

	public IPersonneRepository getPersonneRepo() {
		return personneRepo;
	}

	public ISalleRepository getSalleRepo() {
		return salleRepo;
	}

	public IUERepository getUeRepo() {
		return ueRepo;
	}

	public IOrdinateurRepository getOrdinateurRepo() {
		return ordinateurRepo;
	}

	public IOrdinateurWithEmbeddedIdRepository getOrdinateurWithEmbeddedIdRepo() {
		return ordinateurWithEmbeddedIdRepo;
	}

}
