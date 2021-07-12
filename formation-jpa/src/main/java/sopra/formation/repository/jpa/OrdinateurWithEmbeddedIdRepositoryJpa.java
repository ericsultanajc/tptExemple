package sopra.formation.repository.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import sopra.formation.Application;
import sopra.formation.model.OrdinateurId;
import sopra.formation.model.OrdinateurWithEmbeddedId;
import sopra.formation.repository.IOrdinateurWithEmbeddedIdRepository;

public class OrdinateurWithEmbeddedIdRepositoryJpa implements IOrdinateurWithEmbeddedIdRepository {

	@Override
	public List<OrdinateurWithEmbeddedId> findAll() {
		List<OrdinateurWithEmbeddedId> ordinateurs = new ArrayList<OrdinateurWithEmbeddedId>();
		
		EntityManager em = null;
		EntityTransaction tx = null;

		try {
			em = Application.getInstance().getEntityManagerFactory().createEntityManager();

			tx = em.getTransaction();
			tx.begin();

			TypedQuery<OrdinateurWithEmbeddedId> query = em.createQuery("from OrdinateurWithEmbeddedId", OrdinateurWithEmbeddedId.class);
			
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
	public OrdinateurWithEmbeddedId findById(OrdinateurId id) {
		OrdinateurWithEmbeddedId ordinateur = null;
		
		EntityManager em = null;
		EntityTransaction tx = null;

		try {
			em = Application.getInstance().getEntityManagerFactory().createEntityManager();

			tx = em.getTransaction();
			tx.begin();

			ordinateur = em.find(OrdinateurWithEmbeddedId.class, id);
			
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
	public OrdinateurWithEmbeddedId save(OrdinateurWithEmbeddedId obj) {
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
	public void delete(OrdinateurWithEmbeddedId obj) {
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
