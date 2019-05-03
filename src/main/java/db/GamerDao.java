package db;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.Transactional;
import guice.PersistenceModule;
import org.unideb.Gamer;

import javax.persistence.TypedQuery;

public class GamerDao extends GenericJpaDao<Gamer> {

    public GamerDao() {
        super(Gamer.class);
    }



    @Transactional
    public List<Gamer> findByName(String name) {
        return entityManager.createQuery("SELECT t FROM Gamer t WHERE t.name = :name", Gamer.class)
                .setParameter("name", name).getResultList();
    }

    @Transactional
    @Override
    public List<Gamer> findAll() {
        return entityManager.createQuery("SELECT t FROM Gamer t ORDER BY t.score desc", Gamer.class).getResultList();
    }

}