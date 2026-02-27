CREATE TABLE usuarios
(
    id                 BIGINT       NOT NULL AUTO_INCREMENT,
    nombre             VARCHAR(100) NOT NULL,
    correo_electronico VARCHAR(50)  NOT NULL,
    contrasena         VARCHAR(256) NOT NULL,

    PRIMARY KEY (id)
);