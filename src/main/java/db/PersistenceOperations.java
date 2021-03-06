package db;

import com.google.inject.Guice;
import com.google.inject.Injector;
import guice.PersistenceModule;
import org.unideb.GameMembers;
import org.unideb.Gamer;

import java.util.List;

public class PersistenceOperations {

    private static Injector injector = Guice.createInjector(new PersistenceModule("test"));

    /**
     * A perzisztencia műveletek végrehajtására
     * szolgálaó {@code GamerDao} osztály egy objektuma.
     */
    private static GamerDao gmd=injector.getInstance(GamerDao.class);

    private static GameMembersDao gamemdao=injector.getInstance(GameMembersDao.class);




    public void updateGamer(Gamer gamer) {
        gmd.update(gamer);
    }

    public void persistGamer(Gamer gamer) {
        gmd.persist(gamer);
    }


    /**
     * Az adatbázisban lévő össze játékos lekérése.
     * @return {@code Gamer} objektumok.
     */
    public List<Gamer> getAllGamers() {
        return gmd.findAll();
    }


    /**
     * A paraméterként kapott nevű játékost adja vissza.
     * @param name String típusú objektum, a játékos neve akit szeretnénk megkeresni.
     * @return {@code Game} objektum.
     */
    public List<Gamer> findByName(String name) {
        return gmd.findByName(name);
    }

    public void persistGameStation(GameMembers gm) {
        gamemdao.persist(gm);
    }


}
