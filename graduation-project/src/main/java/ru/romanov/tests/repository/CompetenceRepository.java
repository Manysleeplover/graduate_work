package ru.romanov.tests.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.romanov.tests.entity.Competence;

public interface CompetenceRepository extends JpaRepository<Competence, Long> {
    Competence findCompetenceById(long id);

    Competence findCompetenceByIdStudyDirection(long id);

}
