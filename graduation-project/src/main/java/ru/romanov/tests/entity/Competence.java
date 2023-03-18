package ru.romanov.tests.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;



@Getter
@Setter
@Entity
@DynamicUpdate
@Builder
@AllArgsConstructor
@Table(name = "competence")
public class Competence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "competence_name")
    private String competenceName;
    @Column(name = "id_study_direction")
    private Long idStudyDirection;


    @OneToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "id_study_direction", referencedColumnName = "id", insertable = false, updatable = false)
    private StudyDirection studyDirection;


    public Competence() {

    }
}
