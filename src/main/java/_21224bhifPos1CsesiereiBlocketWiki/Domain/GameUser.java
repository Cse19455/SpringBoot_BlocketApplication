package _21224bhifPos1CsesiereiBlocketWiki.Domain;

import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.lang.model.element.Name;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/*
 * @Author : [Philipp.cserich@gmail.com]
 * User Model Class
 */

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
@Entity
@Table(name = "Users")
public class GameUser extends AbstractPersistable<Long> {
    private String nanoId;
    private LocalDateTime created_at;

    @OneToMany(targetEntity = Surname.class,fetch = FetchType.EAGER)
    private List<Surname> surnames;

    private String firstname,name,username;
    private LocalDate birthDate;
    private String token;



    //@ElementCollection
    //private List<Permission> permissions;

}
