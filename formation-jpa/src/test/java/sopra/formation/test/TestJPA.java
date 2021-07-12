package sopra.formation.test;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import sopra.formation.Application;
import sopra.formation.model.Evaluation;

public class TestJPA {

	public static void main(String[] args) {
		EntityManagerFactory emf = Application.getInstance().getEntityManagerFactory();

		Evaluation eval = new Evaluation(12, 15, "mon commentaires"); // new - transient => en mémoire

		EntityManager em = null;
		EntityTransaction tx = null;

		try {
			em = emf.createEntityManager();

			tx = em.getTransaction();
			tx.begin();

			em.persist(eval); // managed => en mémoire et une ligne en bdd qui lui correspond (connecté)

			eval.setTechnique(18); // dirty checking => mise à jour automatique uniquement sur les entitées managed

			tx.commit(); // em.flush() implicite => génération des ordres SQL à envoyer à la BDD
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

		eval.setTechnique(8); // detached => en mémoire et une ligne en bdd qui lui correspond mais non
								// connecté

		// 2ème EntityManager

		try {
			em = emf.createEntityManager();

			tx = em.getTransaction();
			tx.begin();

			Evaluation evalCopy = em.merge(eval); // managed : evalCopy - detached : eval
			eval.setComportemental(13); // ignorer par JPA

			evalCopy.setComportemental(17);

			em.remove(evalCopy); // removed : entité managed marqué "à supprimer"

			TypedQuery<Object[]> query = em.createQuery(
					"select eval.commentaires, eval.comportemental, eval from Evaluation eval", Object[].class);

			List<Object[]> rows = query.getResultList();

			for (Object[] row : rows) {
				String comm = (String) row[0];
				Integer comp = (Integer) row[1];
				Evaluation evala = (Evaluation) row[2];
			}

//			TypedQuery<Evaluation> queryBis = em.createQuery("select e from Evaluation e where e.stagiaire.prenom = :prenom", Evaluation.class);
//			TypedQuery<Evaluation> queryBis = em.createQuery("select e from Evaluation e join e.stagiaire s where s.prenom = :prenom", Evaluation.class);
//			TypedQuery<Evaluation> queryBis = em.createQuery("select s.evaluation from Stagiaire s where s.prenom = :prenom", Evaluation.class);
			TypedQuery<Evaluation> queryBis = em.createQuery(
					"select e from Stagiaire s join s.evaluation e where s.prenom = :prenom", Evaluation.class);

			queryBis.setParameter("prenom", "Léa");

			List<Evaluation> evaluations = queryBis.getResultList();

			TypedQuery<Evaluation> queryFil = em.createQuery(
					"select e from Filiere f inner join f.stagiaires s join s.evaluation e where f.promotion = :fil",
					Evaluation.class);
			queryFil.setParameter("fil", "TPT");

			tx.commit(); // em.flush() implicite => génération des ordres SQL à envoyer à la BDD
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

		emf.close();

	}

}
