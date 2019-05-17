package db;

import java.util.List;
import com.google.inject.persist.Transactional;
import org.unideb.Gamer;



/**
 * Segédosztály a perzisztencia műveletek elvégzéséhez.
 */

public class GamerDao extends GenericJpaDao<Gamer> {

    public GamerDao() {
        super(Gamer.class);
    }


    /**
     * A paraméterként kapott felhasználónév alapján keres az adatbázisban.
     * @param name A játékos felhasználóneve.
     * @return Azok a {@code Gamer} objektumok amiknek a neve megegyezik.
     */
    @Transactional
    public List<Gamer> findByName(String name) {
        return entityManager.createQuery("SELECT t FROM Gamer t WHERE t.name = :name", Gamer.class)
                .setParameter("name", name).getResultList();
    }

    /**
     * Lekéri az adatbázisban található összes {@code Gamer} objektumot pontszám szerint csökkenő sorrendben.
     * @return {@code Gamer} objektumokat tartalmazó lista.
     */
    @Transactional
    @Override
    public List<Gamer> findAll() {
        return entityManager.createQuery("SELECT t FROM Gamer t ORDER BY t.score desc", Gamer.class).getResultList();
    }

}