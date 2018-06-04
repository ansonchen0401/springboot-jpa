package blob.louiewendy.jpa.dao;

import blob.louiewendy.jpa.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author louie
 * @date created in 2018-6-4 11:40
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    /**
     * find user by id
     * @param id
     * @return
     */
    User findById(String id);
}
