package ru.romanov.tests.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@DynamicUpdate
@Builder
@AllArgsConstructor
@Table(name = "study_direction")
public class StudyDirection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "study_direction_code")
    private String studyDirectionCode;
    @Column(name = "education_year")
    private LocalDate idStudyDirection;



    public StudyDirection() {

    }
}
