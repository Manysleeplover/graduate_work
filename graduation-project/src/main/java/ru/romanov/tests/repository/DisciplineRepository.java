package ru.romanov.tests.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.romanov.tests.entity.Discipline;

public interface DisciplineRepository extends JpaRepository<Discipline, Long> {
}
