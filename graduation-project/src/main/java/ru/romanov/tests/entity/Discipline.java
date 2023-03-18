package ru.romanov.tests.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.time.LocalDate;

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

    @Column(name = "semester_number")
    private String semesterNumbers;
    @Column(name = "part_of_discipline")
    private LocalDate partOfDiscipline;

    @Column(name = "kind_of_discipline")
    private LocalDate kindOf;

    @OneToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "id_study_direction", referencedColumnName = "id", insertable = false, updatable = false)
    private StudyDirection studyDirection;

    public Discipline() {

    }
}
