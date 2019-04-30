package org.unideb;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Gamer {

    @GeneratedValue
    @Id
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int score=0;


}


