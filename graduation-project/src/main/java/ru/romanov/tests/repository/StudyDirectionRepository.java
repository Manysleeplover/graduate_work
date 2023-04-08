package ru.romanov.tests.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.romanov.tests.entity.StudyDirection;

import java.util.List;

public interface StudyDirectionRepository extends JpaRepository<StudyDirection, Long> {
    @Query(value = "SELECT * " +
            " FROM public.study_direction " +
            " where level_of_training = :levelOfTraining ", nativeQuery = true)
    List<StudyDirection> findStudyDirectionByLevelOfTraining(@Param("levelOfTraining") String levelOfTraining);

    List<StudyDirection> findStudyDirectionByStudyDirectionCode(String studyDirection);
}
