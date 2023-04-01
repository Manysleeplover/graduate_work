package ru.romanov.tests.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.romanov.tests.entity.StudyDirection;

public interface StudyDirectionRepository extends JpaRepository<StudyDirection, Long> {
}
