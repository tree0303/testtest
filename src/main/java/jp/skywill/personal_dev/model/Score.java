package jp.skywill.personal_dev.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.SecondaryTable;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "score")
@SecondaryTable(name = "user_master", pkJoinColumns = {@PrimaryKeyJoinColumn(name = "user_id", referencedColumnName = "id")})
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "ele_num")
    private Integer eleNum;

    @Column(name = "time")
    private String time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "game_date", nullable = false, updatable = false)
    private LocalDateTime gameDate;

    @PrePersist
    protected void onCreate() {
        gameDate = LocalDateTime.now();
    }

    Score () {}
}
