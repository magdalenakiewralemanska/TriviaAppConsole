package pl.sda.trivia.repository;

import org.hibernate.cfg.NotYetImplementedException;
import pl.sda.trivia.entity.QuizComplete;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;


public class QuizCompleteRepositoryJpa {
    private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("trivia");

    private EntityManager getEntityManager(){
        return factory.createEntityManager();
    }

    public void save (QuizComplete quizComplete){
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(quizComplete);
        em.getTransaction().commit();
        em.close();
    }

    public List<QuizComplete> findAll(){
        EntityManager em = getEntityManager();
        List<QuizComplete> list = em.createQuery("from QuizComplete q", QuizComplete.class).getResultList();
        em.close();
        return list;
    }
}
