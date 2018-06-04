package blob.louiewendy.jpa.bean;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author louie
 * @date created in 2018-6-4 11:30
 */
@Entity
@Table(name = "b_id_role")
@Setter
@Getter
public class Role implements Serializable {
    @Id
    @GenericGenerator(name = "idGenerator",strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    private String id;
    private String name;
}
