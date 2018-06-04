package blob.louiewendy.jpa.service;

import blob.louiewendy.jpa.bean.Role;
import blob.louiewendy.jpa.bean.User;
import blob.louiewendy.jpa.dao.RoleRepository;
import blob.louiewendy.jpa.dao.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author louie
 * @date created in 2018-6-4 11:42
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserRepository userRepository;

    @Resource
    private RoleRepository roleRepository;

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Page<User> findPageable(int pageNow) {
        Pageable pageable = new PageRequest(pageNow, 10, Sort.Direction.DESC, "id");
        return userRepository.findAll(pageable);
    }

    @Override
    public User setUserRole(String userId, String roleId) {
        User user = userRepository.findById(userId);
        Role role = roleRepository.findById(roleId);

        user.setRoles(new ArrayList<Role>(){{
            add(role);
        }});
        return saveUser(user);
    }
}
