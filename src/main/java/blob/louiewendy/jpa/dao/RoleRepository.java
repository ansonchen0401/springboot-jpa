package blob.louiewendy.jpa.dao;

import blob.louiewendy.jpa.bean.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author louie
 * @date created in 2018-6-4 13:42
 */
@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    /**
     * find by id
     * @param id
     * @return
     */
    Role findById(String id);
}
