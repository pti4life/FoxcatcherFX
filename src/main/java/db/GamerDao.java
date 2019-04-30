package db;

import java.util.List;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.Transactional;
import guice.PersistenceModule;
import org.unideb.Gamer;

public class GamerDao extends GenericJpaDao<Gamer> {

    public GamerDao() {
        super(Gamer.class);
    }


    @Transactional
    public List<Gamer> listOf(String name, String type) {
        return entityManager.createQuery("SELECT t FROM Gamer t WHERE t.name ="+name+" and t.type="+type, Gamer.class).getResultList();
    }

}