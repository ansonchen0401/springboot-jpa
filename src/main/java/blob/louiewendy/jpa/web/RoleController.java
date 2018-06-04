package blob.louiewendy.jpa.web;

import blob.louiewendy.jpa.bean.Role;
import blob.louiewendy.jpa.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author louie
 * @date created in 2018-6-4 13:37
 */
@RestController
@RequestMapping(value = "/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public Role save(Role role){
        return roleService.save(role);
    }

    @RequestMapping(value = "/get")
    public List<Role> findAll(){
        return roleService.findAll();
    }
}
