package jp.skywill.personal_dev.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jp.skywill.personal_dev.model.Score;
import jp.skywill.personal_dev.model.UserScoreDTO;
import jp.skywill.personal_dev.repository.ScoreRepository;

@Service
public class ScoreService {
    @Autowired
    private ScoreRepository scoreRepository;

    @Transactional
    public boolean insert(Score score) {
        scoreRepository.save(score);
        return true;
    }
    @Transactional
    public Optional<Score> findById(int id) {
        return scoreRepository.findById(id);
    }
    @Transactional
    public List<Integer> getListEleNum(Integer userId) {
        return scoreRepository.getListEleNumByUserId(userId);
    }

    @Transactional
    public  List<Score> getAllByUserId(Integer userId) {
        return scoreRepository.getAllByUserId(userId);
    }

    @Transactional
    public List<Score> getAllByUserIdAndEleNum(Integer userId, Integer eleNum) {
        return scoreRepository.getAllByUserIdAndEleNum(userId, eleNum);
    }

    @Transactional
    public int delete(Integer id) {
        return scoreRepository.delete(id);
    }



    @Transactional
    public List<UserScoreDTO> getAllScoreOrderByDate() {
        List<Object[]> results = scoreRepository.getAllScoreOrderByDate();
        List<UserScoreDTO> userScoreDTOs = new ArrayList<>();
        for (Object[] result : results) {
            UserScoreDTO dto = new UserScoreDTO((String) result[0], (Integer) result[1], (String) result[2]);
            userScoreDTOs.add(dto);
        }
        return userScoreDTOs;
    }

    @Transactional
    public List<UserScoreDTO> getAllScoreByEleNum(Integer eleNum) {
        List<Object[]> results = scoreRepository.getAllScoreByEleNum(eleNum);
        List<UserScoreDTO> userScoreDTOs = new ArrayList<>();
        for (Object[] result : results) {
            UserScoreDTO dto = new UserScoreDTO((String) result[0], (Integer) result[1], (String) result[2]);
            userScoreDTOs.add(dto);
        }
        return userScoreDTOs;
    }

    @Transactional
    public List<Integer> getAllListEleNum() {
        return scoreRepository.getAllListEleNum();
    }
}
