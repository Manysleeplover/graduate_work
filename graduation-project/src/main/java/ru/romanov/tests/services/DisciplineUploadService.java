package ru.romanov.tests.services;

import org.springframework.stereotype.Service;
import ru.romanov.tests.entity.Discipline;
import ru.romanov.tests.entity.StudyDirection;
import ru.romanov.tests.repository.DisciplineRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class DisciplineUploadService {

    private final DisciplineRepository disciplineRepository;

    public DisciplineUploadService(DisciplineRepository disciplineRepository) {
        this.disciplineRepository = disciplineRepository;
    }

    public List<Discipline> uploadDiscipline(Discipline discipline) {
        disciplineRepository.save(discipline);
        return disciplineRepository.findAll();
    }
}
