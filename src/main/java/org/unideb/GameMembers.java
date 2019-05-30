package org.unideb;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class GameMembers {

    @GeneratedValue
    @Id
    private long id;

    @OneToOne
    private Gamer gamerWithFox;

    private int scoreOfFox;

    @OneToOne
    private Gamer gamerWithDog;

    private int scoreOfDog;


    private String state;

}
