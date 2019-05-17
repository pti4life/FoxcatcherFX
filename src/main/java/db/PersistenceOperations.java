package db;

import com.google.inject.Guice;
import com.google.inject.Injector;
import guice.PersistenceModule;
import org.unideb.Gamer;

import java.util.List;

public class PersistOperations {

    private static Injector injector = Guice.createInjector(new PersistenceModule("test"));

    /**
     * A perzisztencia műveletek végrehajtására
     * szolgálaó {@code GamerDao} osztály egy objektuma.
     */
    private static GamerDao gmd=injector.getInstance(GamerDao.class);




    public void PersistGamer(Gamer gamer, int actualScore) {
        actualScore = gamer.getScore()+1;
        gamer.setScore(actualScore);
        gmd.update(gamer);
    }

    /**
     * Az adatbázisban lévő össze játékos lekérése.
     * @return {@code Gamer} objektumok.
     */
    public List<Gamer> getAllGamers() {
        return gmd.findAll();
    }


}
