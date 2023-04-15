package ru.romanov.tests.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.romanov.tests.entity.Discipline;

import java.util.List;

public interface DisciplineRepository extends JpaRepository<Discipline, Long> {

    List<Discipline> findDisciplineByIdStudyDirection(Long id);
}
