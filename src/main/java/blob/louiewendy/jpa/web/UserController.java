package blob.louiewendy.jpa.web;

import blob.louiewendy.jpa.bean.User;
import blob.louiewendy.jpa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author louie
 * @date created in 2018-6-4 11:57
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * save user
     * @param user
     * @return
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public User save(User user){
        return userService.saveUser(user);
    }

    @RequestMapping(value = "/get/{id}")
    public User getUser(@PathVariable String id){
        return userService.findById(id);
    }

    @RequestMapping(value = "/get")
    public List<User> findAll(){
        return userService.findAll();
    }

    @RequestMapping(value = "/role")
    public User setRole(String userId,String roleId){
        return userService.setUserRole(userId, roleId);
    }

    @RequestMapping(value = "/get/page/{pageNow}")
    public Page<User> findPageable(@PathVariable Integer pageNow){
        return userService.findPageable(pageNow);
    }
}
