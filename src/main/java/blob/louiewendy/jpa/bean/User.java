package blob.louiewendy.jpa.bean;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author louie
 * @date created in 2018-6-4 10:57
 */
@Entity
@Table(name = "b_id_user")
@Getter
@Setter
public class User implements Serializable {
    @Id
    @GenericGenerator(name = "idGenerator",strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    private String id;
    private String name;
    private String password;
    private String position;
    private Integer age;
    private String birth;
    private String email;
    @ManyToMany
    private List<Role> roles;
}
