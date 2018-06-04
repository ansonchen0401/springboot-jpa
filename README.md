# spring-boot整合spring-data-jpa

---
### 一、创建spring-boot工程

1. 新建spring-boot项目，选择项目类型为gradle project，勾选初始化依赖如下：
![项目依赖][1]

2. 删除resources下application.properties，创建application.yml文件（.yml与.properties比较这里不多说，.yml为spring-boot推荐配置方式），添加数据库和jpa配置：

```
spring:
  datasource:
    url: jdbc:postgresql://127.0.0.1:5432/louie
    username: louie
    password: louie1234
  jpa:
    hibernate:
      ddl-auto: update
    database: postgresql
    show-sql: true
```
3.角色实体
```
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

```
用户实体：
```java
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
```
4.执行Application中的main方法启动spring-boot工程，启动成功后查看数据库，jpa自动创建数据库表：
![数据库表][2]

### 二、角色管理
1、角色服务（RoleService）
```
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
```
2、角色服务实现类（RoleServiceImpl）
```
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
```
3、角色数据仓库(RoleRepository)
```
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
```
4、角色控制器（RoleController）
```
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
```
5、使用postMan等工具创建角色
创建角色：
![新建角色.png-61.8kB][3]

查询角色：
![获取角色.png-68.9kB][4]

### 三、用户管理
1、用户服务（UserService）
```
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
```
2、用户服务实现（UserServiceImpl）
```
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
```
3、用户数据仓库（UserRepository）
```
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
```
4、用户控制器（UserController）
```
package blob.louiewendy.jpa.web;

import blob.louiewendy.jpa.bean.User;
import blob.louiewendy.jpa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
```
### 四、设置用户角色
1、使用postMan访问设置角色服务，为用户指定角色：
![设置角色.png-83.1kB][5]

2、访问查询用户服务：
![获取用户.png-54.8kB][6]
我们在查询用户逻辑中未处理用户角色，但是我们获取到的数据中已包含用户角色数据，这部分工作spring data jpa已帮我们处理。

### 五、分页查询
spring data jpa已内置分页查询功能，使用也是很方便。
1、UserService新增分页查询服务，下面是实现：
```
public Page<User> findPageable(int pageNow) {
        Pageable pageable = new PageRequest(pageNow, 10, Sort.Direction.DESC, "id");
        return userRepository.findAll(pageable);
    }
```
2、控制器添加分页查询控制器：
```
@RequestMapping(value = "/get/page/{pageNow}")
    public Page<User> findPageable(@PathVariable Integer pageNow){
        return userService.findPageable(pageNow);
    }
```
3、访问分页查询服务：
![分页查询.png-59.6kB][7]
响应信息全文：
```
{
    "content": [
        {
            "id": "4028b88163ca18900163ca18bd2d0000",
            "name": "louie",
            "password": "123",
            "position": null,
            "age": 18,
            "birth": "1990-01-01",
            "email": null,
            "roles": [
                {
                    "id": "4028b88163ca3d8b0163ca3e21130000",
                    "name": "普通员工"
                }
            ]
        }
    ],
    "pageable": {
        "sort": {
            "sorted": true,
            "unsorted": false
        },
        "offset": 0,
        "pageSize": 10,
        "pageNumber": 0,
        "unpaged": false,
        "paged": true
    },
    "last": true,
    "totalElements": 1,
    "totalPages": 1,
    "number": 0,
    "size": 10,
    "sort": {
        "sorted": true,
        "unsorted": false
    },
    "first": true,
    "numberOfElements": 1
}
```
> 从分页查询的响应信息可以看出，响应中的content便是查询到的数组，totalElements为总数、totalPages为总页数、number为当前页数、numberOfElements本次获取数量




  [1]: http://static.zybuluo.com/louie-001/2rgltiupr2cbf1rsjq11flyr/%E5%BE%AE%E4%BF%A1%E6%88%AA%E5%9B%BE_20180604102023.png
  [2]: http://static.zybuluo.com/louie-001/30syz35n2cr8effyax2hvl4o/20180604121624.png
  [3]: http://static.zybuluo.com/louie-001/ach1mfw6flbg6nhox8z0f9fd/%E6%96%B0%E5%BB%BA%E8%A7%92%E8%89%B2.png
  [4]: http://static.zybuluo.com/louie-001/irhd794tws47be7w72zl3tth/%E8%8E%B7%E5%8F%96%E8%A7%92%E8%89%B2.png
  [5]: http://static.zybuluo.com/louie-001/7592r2gkvpfh0jvy2y5g0z9f/%E8%AE%BE%E7%BD%AE%E8%A7%92%E8%89%B2.png
  [6]: http://static.zybuluo.com/louie-001/orfuqb9bdu14063lr1if4z9z/%E8%8E%B7%E5%8F%96%E7%94%A8%E6%88%B7.png
  [7]: http://static.zybuluo.com/louie-001/1on10yy7et13gks2romsvlo4/%E5%88%86%E9%A1%B5%E6%9F%A5%E8%AF%A2.png