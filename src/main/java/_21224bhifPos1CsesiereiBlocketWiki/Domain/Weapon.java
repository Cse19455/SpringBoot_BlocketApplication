package _21224bhifPos1CsesiereiBlocketWiki.Domain;

import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.function.Predicate;

/*
 * @Author : [Philipp.cserich@gmail.com]
 *
 * Weapon Model Class
 */

@Entity
@Data
@NoArgsConstructor @AllArgsConstructor
@Table(name = "Weapons")

public class Weapon extends Item
{
    public Weapon(String name,int size) {super(name,size);}
    private String description;
    private int damage;

    @Embedded
    private WeaponClass classification;

}
