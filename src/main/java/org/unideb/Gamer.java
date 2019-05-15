package org.unideb;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Egy játékost reprezentál.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Gamer {


    @GeneratedValue
    @Id
    private long id;


    /**
     * A játékos felhasználóneve.
     */
    @Column(nullable = false, unique = true)
    private String name;


    /**
     * A játékos pontszáma.
     */
    @Column(nullable =false)
    private int score=0;


}


