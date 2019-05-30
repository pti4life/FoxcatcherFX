package db;

import java.util.List;
import com.google.inject.persist.Transactional;
import org.unideb.GameMembers;
import org.unideb.Gamer;


/**
 * Segédosztály a perzisztencia műveletek elvégzéséhez.
 */

public class GameMembersDao extends GenericJpaDao<GameMembers> {

    public GameMembersDao() {
        super(GameMembers.class);
    }



}