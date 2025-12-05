package com.gemini.roadmap2.models.Progress;

import com.gemini.roadmap2.models.User;
import com.gemini.roadmap2.models.Roadmap.RoadmapSubstep;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "user_substep_progress", uniqueConstraints = @UniqueConstraint(columnNames = { "user_id",
        "roadmap_substep_id" })

)
public class UserSubstepProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "substep_id")
    private RoadmapSubstep roadmapSubstep;

    private boolean completed = false;

}
