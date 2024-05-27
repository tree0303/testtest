package jp.skywill.personal_dev.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jp.skywill.personal_dev.model.Score;

import java.util.List;




@Repository
public interface ScoreRepository extends JpaRepository<Score, Integer> {
    @Query(nativeQuery = true, value = "select ele_num from score where user_id =:userId and is_deleted=false group by ele_num;")
    public List<Integer> getListEleNumByUserId(Integer userId);
    @Query(nativeQuery = true, value = "select * from score where user_id =:userId and is_deleted=false order by ele_num, time;")
    public List<Score> getAllByUserId(Integer userId);
    @Query(nativeQuery = true, value = "select * from score where user_id =:userId and ele_num=:eleNum and is_deleted=false order by ele_num, time;")
    public List<Score> getAllByUserIdAndEleNum(Integer userId, Integer eleNum);

    @Modifying
    @Query(nativeQuery = true, value = "update score set is_deleted = true WHERE id = ?1")
    public int delete(Integer id);

    @Query(nativeQuery = true, value = "select name, ele_num, time FROM (select * from user_master where is_deleted=false) as user_master join (select * from score where is_deleted=false) as score on user_master.id = score.user_id order by game_date, time limit 10;")
    public List<Object[]> getAllScoreOrderByDate();
    @Query(nativeQuery = true, value = "select name, ele_num, time FROM (select * from user_master where is_deleted=false) as user_master join (select * from score where is_deleted=false) as score on user_master.id = score.user_id where ele_num=:eleNum order by ele_num, time limit 10;")
    public List<Object[]> getAllScoreByEleNum(Integer eleNum);
    @Query(nativeQuery = true, value = "select ele_num from score where is_deleted=false group by ele_num;")
    public List<Integer> getAllListEleNum();
}
