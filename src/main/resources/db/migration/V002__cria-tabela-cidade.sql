CREATE TABLE cidade (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  nome_cidade varchar(60) NOT NULL,
  nome_estado varchar(80) not null,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;