package sopra.formation.repository.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import sopra.formation.Application;
import sopra.formation.model.Filiere;
import sopra.formation.model.Salle;
import sopra.formation.repository.ISalleRepository;

public class SalleRepositoryJpa implements ISalleRepository {

	public List<Salle> findAll() {
		List<Salle> salles = new ArrayList<Salle>();

		EntityManager em = null;
		EntityTransaction tx = null;

		try {
			em = Application.getInstance().getEntityManagerFactory().createEntityManager();
			tx = em.getTransaction();
			tx.begin();

			TypedQuery<Salle> query = em.createQuery("select s from Salle s", Salle.class);

			salles = query.getResultList();

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

		return salles;
	}

	public Salle findById(Long id) {
		Salle salle = null;

		EntityManager em = null;
		EntityTransaction tx = null;

		try {
			em = Application.getInstance().getEntityManagerFactory().createEntityManager();
			tx = em.getTransaction();
			tx.begin();

			salle = em.find(Salle.class, id);

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

		return salle;
	}

	public Salle save(Salle obj) {
		EntityManager em = null;
		EntityTransaction tx = null;

		try {
			em = Application.getInstance().getEntityManagerFactory().createEntityManager();
			tx = em.getTransaction();
			tx.begin();

			obj = em.merge(obj);

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

		return obj;
	}

	public void delete(Salle obj) {
		EntityManager em = null;
		EntityTransaction tx = null;

		try {
			em = Application.getInstance().getEntityManagerFactory().createEntityManager();
			tx = em.getTransaction();
			tx.begin();

			em.remove(em.merge(obj));

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
	}

	@Override
	public List<Salle> findAllByFiliere(Filiere filiere) {
		List<Salle> salles = new ArrayList<Salle>();

		EntityManager em = null;
		EntityTransaction tx = null;

		try {
			em = Application.getInstance().getEntityManagerFactory().createEntityManager();
			tx = em.getTransaction();
			tx.begin();

//			TypedQuery<Salle> query = em.createQuery("select s from Salle s join s.ues ue join ue.filiere f where f = :filiere", Salle.class);
			
//			TypedQuery<Salle> query = em.createQuery("select s from Salle s join s.ues ue where ue.filiere = :filiere", Salle.class);
			
			TypedQuery<Salle> query = em.createQuery("select ue.salle from UE ue where ue.filiere = :filiere", Salle.class);

			query.setParameter("filiere", filiere);
			
			salles = query.getResultList();

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

		return salles;
	}

	@Override
	public List<Salle> findAllByVille(String ville) {
		List<Salle> salles = new ArrayList<Salle>();

		EntityManager em = null;
		EntityTransaction tx = null;

		try {
			em = Application.getInstance().getEntityManagerFactory().createEntityManager();
			tx = em.getTransaction();
			tx.begin();

			TypedQuery<Salle> query = em.createQuery("select s from Salle s where s.adr.ville = :ville", Salle.class);

			query.setParameter("ville", ville);
			
			salles = query.getResultList();

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

		return salles;
	}
}
