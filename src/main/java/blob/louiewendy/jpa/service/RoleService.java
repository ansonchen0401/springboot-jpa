package blob.louiewendy.jpa.service;

import blob.louiewendy.jpa.bean.Role;

import java.util.List;

/**
 * @author louie
 * @date created in 2018-6-4 13:37
 */
public interface RoleService {
    /**
     * save role
     * @param role
     * @return
     */
    Role save(Role role);

    /**
     * find all
     * @return
     */
    List<Role> findAll();
}
