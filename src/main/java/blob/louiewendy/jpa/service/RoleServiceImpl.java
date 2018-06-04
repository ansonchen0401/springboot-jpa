package blob.louiewendy.jpa.service;

import blob.louiewendy.jpa.bean.Role;
import blob.louiewendy.jpa.dao.RoleRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author louie
 * @date created in 2018-6-4 13:41
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleRepository roleRepository;

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}
