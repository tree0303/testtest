package jp.skywill.personal_dev.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jp.skywill.personal_dev.model.User;
import jp.skywill.personal_dev.repository.UserRerpsitory;

@Service
public class UserService {
    @Autowired
    private UserRerpsitory userRerpsitory;

    @Transactional
    public boolean insertuser(User user) {
        userRerpsitory.save(user);
        return true;
    }

    @Transactional
    public User findByNameAndPass(String name, String pass) {
        return userRerpsitory.findByNameAndPass(name, pass);
    }

    @Transactional
    public boolean existsByNameAndPass(String name, String pass){
        return userRerpsitory.existsByNameAndPass(name, pass);
    }

    @Transactional
    public Optional<User> findById(Integer userId) {
        return userRerpsitory.findById(userId);
    }

    @Transactional
    public Integer findid(String name, String pass){
        return userRerpsitory.findid(name, pass);
    }

    @Transactional
    public int updateById(Integer id, String name, Integer age, String gender) {
        return userRerpsitory.updateById(id, name, age, gender);
    }
}
