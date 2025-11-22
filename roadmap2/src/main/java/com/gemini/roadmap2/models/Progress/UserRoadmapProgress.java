package com.gemini.roadmap2.models.Progress;

import com.gemini.roadmap2.models.User;
import com.gemini.roadmap2.models.Roadmap.Roadmap;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserRoadmapProgress {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    

    @ManyToOne
    @JoinColumn(name = "roadmap_id")
    private Roadmap roadmap;

    private int substepsCompleted;
    private int totalSubsteps;
    @Override
    public String toString() {
        return "UserRoadmapProgress [id=" + id + ", user=" + user + ", roadmap=" + roadmap + ", substepsCompleted="
                + substepsCompleted + ", totalSubsteps=" + totalSubsteps + "]";
    }

}
