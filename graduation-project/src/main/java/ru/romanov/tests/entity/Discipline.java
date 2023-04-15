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
@Table(name = "discipline")
public class Discipline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "discipline_name")
    private String disciplineName;

    @Column(name = "semestr_number")
    private String semesterNumbers;

    @Column(name = "list_of_competence")
    private String listOfCompetence;

    @Column(name = "block_name")
    private String blockName;

    @Column(name = "part_name")
    private String partName;

    @Column(name = "type_of_dicsipline")
    private String typeOfDiscipline;
    @Column(name = "id_study_direction")
    private Long idStudyDirection;
    @Column(name = "id_competence")
    private Long idCompetence;

    @Override
    public String toString() {
        return "Discipline{" +
                "disciplineName='" + disciplineName + '\'' +
                '}';
    }

    @OneToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "id_study_direction", referencedColumnName = "id", insertable = false, updatable = false)
    private StudyDirection studyDirection;

    public Discipline() {

    }
}
