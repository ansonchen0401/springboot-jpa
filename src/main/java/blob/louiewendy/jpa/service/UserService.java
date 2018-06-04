package blob.louiewendy.jpa.service;

import blob.louiewendy.jpa.bean.User;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author louie
 * @date created in 2018-6-4 11:41
 */
public interface UserService {
    /**
     * save user
     * @param user
     * @return
     */
    User saveUser(User user);
    /**
     * find user by id
     * @param id
     * @return
     */
    User findById(String id);

    /**
     * find all
     * @return
     */
    List<User> findAll();

    /**
     * 分页查询
     * @param pageNow
     * @return
     */
    Page<User> findPageable(int pageNow);

    /**
     *
     * set user role
     * @param userId
     * @param roleId
     * @return
     */
    User setUserRole(String userId,String roleId);
}
