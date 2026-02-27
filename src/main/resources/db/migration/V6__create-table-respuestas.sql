CREATE TABLE respuestas
(
    id             BIGINT   NOT NULL AUTO_INCREMENT,
    mensaje        TEXT     NOT NULL,
    fecha_creacion DATETIME NOT NULL,
    topico_id      BIGINT   NOT NULL,
    usuario_id     BIGINT   NOT NULL,
    solucion       TINYINT  NOT NULL,

    PRIMARY KEY (id),
    CONSTRAINT fk_topico_id FOREIGN KEY (topico_id) REFERENCES topicos (id),
    CONSTRAINT fk_usuario_id FOREIGN KEY (usuario_id) REFERENCES usuarios (id)
);