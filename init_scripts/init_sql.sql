--Таблица с направлениями подготовки
create table if not exists STUDY_DIRECTION
(
    id bigserial,
	primary key (id),
    study_direction_code VARCHAR(16) NOT NULL,
    study_directions_name text,
    level_of_training varchar(32)
);

--Таблица с компетенциями
create table if not exists COMPETENCE
(
    id bigserial, 
    competence_list text,
	primary key (id),
	id_study_direction bigint,
    foreign key (id_study_direction) references STUDY_DIRECTION(id)
);

--Таблица с Дисципплинами, отличия в которых идут по направлению подготовки, т.е. Может быть две математикис разными компетенциями
create table if not exists Discipline
(
    id bigserial, 
    discipline_name VARCHAR(16) ,
    semestr_number VARCHAR(16) NOT NULL,
    list_of_competence varchar(128),
    block_name varchar(128),
    part_name varchar(128),
    type_of_dicsipline varchar(128),
    id_competence bigint,
    id_study_direction bigint,
    primary key (id),
    foreign key (id_competence) references COMPETENCE(id),
    foreign key (id_study_direction) references STUDY_DIRECTION(id)
);

INSERT INTO public.study_direction(study_direction_code, study_directions_name, level_of_training)
	VALUES ('09.03.01','Информатика и вычислительная техника', 'Бакалавриат');
INSERT INTO public.study_direction(study_direction_code, study_directions_name, level_of_training)
	VALUES ('09.03.02','Информационные системы и технологии', 'Бакалавриат');
INSERT INTO public.study_direction(study_direction_code, study_directions_name, level_of_training)
	VALUES ('09.03.03','Прикладная информатика', 'Бакалавриат');
INSERT INTO public.study_direction(study_direction_code, study_directions_name, level_of_training)
	VALUES ('09.03.04','Программная инженерия', 'Бакалавриат');
INSERT INTO public.study_direction(study_direction_code, study_directions_name, level_of_training)
	VALUES ('12.03.01','Приборостроение', 'Бакалавриат');
INSERT INTO public.study_direction(study_direction_code, study_directions_name, level_of_training)
	VALUES ('15.03.01','Машиностроение', 'Бакалавриат');
INSERT INTO public.study_direction(study_direction_code, study_directions_name, level_of_training)
	VALUES ('15.03.02','Технологические машины и оборудование', 'Бакалавриат');
INSERT INTO public.study_direction(study_direction_code, study_directions_name, level_of_training)
	VALUES ('15.03.04','Автоматизация технологических процессов и производств', 'Бакалавриат');	
INSERT INTO public.study_direction(study_direction_code, study_directions_name, level_of_training)
	VALUES ('15.03.05','Конструкторско-технологическое обеспечение машиностроительных производств', 'Бакалавриат');
INSERT INTO public.study_direction(study_direction_code, study_directions_name, level_of_training)
	VALUES ('15.03.06','Мехатроника и Робототехника', 'Бакалавриат');
INSERT INTO public.study_direction(study_direction_code, study_directions_name, level_of_training)
	VALUES ('15.05.01','Проектирование технологических машин и комплексов', 'Бакалавриат');
INSERT INTO public.study_direction(study_direction_code, study_directions_name, level_of_training)
	VALUES ('20.03.01','Техносферная безопасность', 'Бакалавриат');
INSERT INTO public.study_direction(study_direction_code, study_directions_name, level_of_training)
	VALUES ('22.03.01','Материаловедение и технология материалов', 'Бакалавриат');
INSERT INTO public.study_direction(study_direction_code, study_directions_name, level_of_training)
	VALUES ('27.03.01','Стандартизация и метрология', 'Бакалавриат');
INSERT INTO public.study_direction(study_direction_code, study_directions_name, level_of_training)
	VALUES ('27.03.02','Управление качеством', 'Бакалавриат');
INSERT INTO public.study_direction(study_direction_code, study_directions_name, level_of_training)
	VALUES ('27.03.04','Управление в технических системах', 'Бакалавриат');
INSERT INTO public.study_direction(study_direction_code, study_directions_name, level_of_training)
	VALUES ('38.03.01','Экономика', 'Бакалавриат');
INSERT INTO public.study_direction(study_direction_code, study_directions_name, level_of_training)
	VALUES ('38.03.02','Менеджмент', 'Бакалавриат');
INSERT INTO public.study_direction(study_direction_code, study_directions_name, level_of_training)
	VALUES ('38.03.03','Информатика и вычислительная техника', 'Бакалавриат');
INSERT INTO public.study_direction(study_direction_code, study_directions_name, level_of_training)
	VALUES ('09.03.01','Управление персоналом', 'Бакалавриат');
	
INSERT INTO public.study_direction(study_direction_code, study_directions_name, level_of_training)
	VALUES ('15.05.01','Проектирование технологических машин и комплексов', 'Специалитет');
	
INSERT INTO public.study_direction(study_direction_code, study_directions_name, level_of_training)
	VALUES ('09.04.01','Информатика и вычислительная техника', 'Магистратура');
INSERT INTO public.study_direction(study_direction_code, study_directions_name, level_of_training)
	VALUES ('09.04.04','Программная инженерия', 'Магистратура');
INSERT INTO public.study_direction(study_direction_code, study_directions_name, level_of_training)
	VALUES ('12.04.01','Приборостроение', 'Магистратура');
INSERT INTO public.study_direction(study_direction_code, study_directions_name, level_of_training)
	VALUES ('15.04.04','Автоматизация технологических процессов и производств', 'Магистратура');
INSERT INTO public.study_direction(study_direction_code, study_directions_name, level_of_training)
	VALUES ('15.04.05','Конструкторско- технологическое обеспечение машино- строительных производств', 'Магистратура');
INSERT INTO public.study_direction(study_direction_code, study_directions_name, level_of_training)
	VALUES ('15.04.06','Мехатроника и робототехника', 'Магистратура');
INSERT INTO public.study_direction(study_direction_code, study_directions_name, level_of_training)
	VALUES ('20.04.01','Техносферная безопасность', 'Магистратура');
INSERT INTO public.study_direction(study_direction_code, study_directions_name, level_of_training)
	VALUES ('27.04.02','Управление качеством', 'Магистратура');
INSERT INTO public.study_direction(study_direction_code, study_directions_name, level_of_training)
	VALUES ('38.04.02','Менеджмент', 'Магистратура');
INSERT INTO public.study_direction(study_direction_code, study_directions_name, level_of_training)
	VALUES ('38.04.03','Управление персоналом', 'Магистратура');
INSERT INTO public.study_direction(study_direction_code, study_directions_name, level_of_training)
	VALUES ('38.04.04','Государственное и муниципальное управление', 'Магистратура');

INSERT INTO public.study_direction(study_direction_code, study_directions_name, level_of_training)
	VALUES ('03.06.01','Физика и астрономия', 'Аспирантура');
INSERT INTO public.study_direction(study_direction_code, study_directions_name, level_of_training)
	VALUES ('09.06.01','Информатика и вычислительная техника', 'Аспирантура');
INSERT INTO public.study_direction(study_direction_code, study_directions_name, level_of_training)
	VALUES ('15.06.01','Машиностроение', 'Аспирантура');
INSERT INTO public.study_direction(study_direction_code, study_directions_name, level_of_training)
	VALUES ('27.06.01','Управление в технических системах', 'Аспирантура');
INSERT INTO public.study_direction(study_direction_code, study_directions_name, level_of_training)
	VALUES ('38.06.01','Экономика', 'Аспирантура');
INSERT INTO public.study_direction(study_direction_code, study_directions_name, level_of_training)
	VALUES ('47.06.01','Философия, этика и религиоведение', 'Аспирантура');

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

