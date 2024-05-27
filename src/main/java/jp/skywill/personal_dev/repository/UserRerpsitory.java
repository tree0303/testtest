package jp.skywill.personal_dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jp.skywill.personal_dev.model.User;
import java.util.Optional;

@Repository
public interface UserRerpsitory extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    @Query(value = "select * from user_master where name = ?1 and pass = ?2 and is_deleted=false", nativeQuery = true)
    User findByNameAndPass(String name, String pass);

    @Query(value = "SELECT EXISTS (SELECT 1 FROM user_master WHERE name = ?1 AND pass = ?2 and is_deleted=false)", nativeQuery = true)
    boolean existsByNameAndPass(String name, String pass);

    @Query(value = "select id from user_master where name = ?1 and pass = ?2 and is_deleted=false", nativeQuery = true)
    Integer findid(String name, String pass);

    @Query(value = "select * from user_master where id = ?1 and is_deleted=false", nativeQuery = true)
    Optional<User> findById(Integer id);

    @Modifying
    @Query(nativeQuery = true, value = "update user_master set name = ?2, age = ?3, gender = ?4 WHERE id = ?1")
    public int updateById(Integer id, String name, Integer age, String gender);
}
