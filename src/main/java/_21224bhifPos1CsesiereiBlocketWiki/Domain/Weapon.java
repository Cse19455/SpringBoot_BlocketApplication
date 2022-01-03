package _21224bhifPos1CsesiereiBlocketWiki.Domain;

import lombok.*;

import javax.persistence.*;
import java.util.function.Predicate;

/*
 * @Author : Cse19455@spengergasse.at
 *
 * Weapon Model Class
 */

@Entity
@Data
@NoArgsConstructor @AllArgsConstructor

@Table(name = "Weapons")

public class Weapon extends Item {

    private String description;
    private int damage;

    @Embedded
    private WeaponClass classification;

}
