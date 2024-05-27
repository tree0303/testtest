package jp.skywill.personal_dev.model;

public class UserScoreDTO {
    private String userName;
    private Integer scoreNum;
    private String scoreTime;

    // Constructor
    public UserScoreDTO(String userName, Integer scoreNum, String scoreTime) {
        this.userName = userName;
        this.scoreNum = scoreNum;
        this.scoreTime = scoreTime;
    }

    // Getters and Setters
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getScoreNum() {
        return scoreNum;
    }

    public void setScoreNum(Integer scoreNum) {
        this.scoreNum = scoreNum;
    }

    public String getScoreTime() {
        return scoreTime;
    }

    public void setScoreTime(String scoreTime) {
        this.scoreTime = scoreTime;
    }
}
