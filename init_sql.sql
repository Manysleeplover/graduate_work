--Таблица с направлениями подготовки
create table if not exists STUDY_DIRECTION
(
    id bigserial,
	primary key (id),
    study_direction_code VARCHAR(16) NOT NULL 
);

--Таблица с компетенциями
create table if not exists COMPETENCE
(
    id bigserial, 
    competence_name VARCHAR(16) NOT NULL,
	primary key (id),
	id_study_direction bigint,
    foreign key (id_study_direction) references STUDY_DIRECTION(id)
);

--Таблица с Дисципплинами, отличия в которых идут по направлению подготовки, т.е. Может быть две математикис разными компетенциями
create table if not exists Discipline
(
    id bigserial, 
    discipline_name VARCHAR(16) NOT NULL,
    semestr_number VARCHAR(16) NOT NULL,
    part_of_discipline VARCHAR(128) NOT NULL, -- Часть ( обязательная, формируемая и т.д.)
    kind_of_discipline VARCHAR(128) NOT NULL, -- Тип (математика, программирование и т.д.)
	id_competence bigint,
	id_study_direction bigint,
	primary key (id),
    foreign key (id_competence) references COMPETENCE(id),
    foreign key (id_study_direction) references STUDY_DIRECTION(id)
);

