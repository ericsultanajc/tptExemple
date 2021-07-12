package sopra.formation.repository.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import sopra.formation.Application;
import sopra.formation.model.Ordinateur;
import sopra.formation.model.OrdinateurId;
import sopra.formation.repository.IOrdinateurRepository;

public class OrdinateurRepositoryJpa implements IOrdinateurRepository {

	@Override
	public List<Ordinateur> findAll() {
		List<Ordinateur> ordinateurs = new ArrayList<Ordinateur>();
		
		EntityManager em = null;
		EntityTransaction tx = null;

		try {
			em = Application.getInstance().getEntityManagerFactory().createEntityManager();

			tx = em.getTransaction();
			tx.begin();

			TypedQuery<Ordinateur> query = em.createQuery("from Ordinateur", Ordinateur.class);
			
			ordinateurs = query.getResultList();
			
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
		
		return ordinateurs;
	}

	@Override
	public Ordinateur findById(OrdinateurId id) {
		Ordinateur ordinateur = null;
		
		EntityManager em = null;
		EntityTransaction tx = null;

		try {
			em = Application.getInstance().getEntityManagerFactory().createEntityManager();

			tx = em.getTransaction();
			tx.begin();

			ordinateur = em.find(Ordinateur.class, id);
			
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
		
		return ordinateur;
	}

	@Override
	public Ordinateur save(Ordinateur obj) {
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

	@Override
	public void delete(Ordinateur obj) {
		EntityManager em = null;
		EntityTransaction tx = null;

		try {
			em = Application.getInstance().getEntityManagerFactory().createEntityManager();

			tx = em.getTransaction();
			tx.begin();
			
			obj = em.merge(obj);
			
			em.remove(obj);
		
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

}
