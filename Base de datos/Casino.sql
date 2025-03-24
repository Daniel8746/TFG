-- Elimina la base de datos "casino" si ya existe y crea una nueva
DROP DATABASE IF EXISTS casino;
CREATE DATABASE casino;
USE casino;

-- Elimina la tabla "juego" si ya existe y crea una nueva tabla para almacenar los juegos
DROP TABLE IF EXISTS juego;
CREATE TABLE juego(
    id INTEGER NOT NULL AUTO_INCREMENT,  -- ID único para cada juego
    nombre varchar(100) NOT NULL,        -- Nombre del juego
    tipo varchar(50) NOT NULL,           -- Tipo del juego (puede ser tipo de casino, juego de cartas, etc.)
    reglas TEXT,                         -- Reglas del juego, en formato texto
    PRIMARY KEY (id)                     -- Establece la columna "id" como la clave primaria
);

-- Elimina la tabla "usuario" si ya existe y crea una nueva tabla para almacenar los usuarios
DROP TABLE IF EXISTS usuario;
CREATE TABLE usuario(
    id INTEGER AUTO_INCREMENT,           -- ID único para cada usuario
    nombre varchar(100),                 -- Nombre del usuario
    apellidos varchar(100),              -- Apellidos del usuario
    correo varchar(100) UNIQUE,          -- Correo electrónico único para cada usuario
    contrasenya varchar(255),            -- Contraseña del usuario, debe estar encriptada
    telefono varchar(20) DEFAULT NULL UNIQUE, -- Teléfono del usuario, puede ser NULL y único
    saldo DECIMAL(10, 2) DEFAULT 0.00,   -- Saldo del usuario, comienza en 0.00
    PRIMARY KEY (id)                     -- Establece la columna "id" como la clave primaria
);

-- Elimina la tabla "apuesta" si ya existe y crea una nueva tabla para almacenar las apuestas de los usuarios
DROP TABLE IF EXISTS apuesta;
CREATE TABLE apuesta(
    id INTEGER NOT NULL AUTO_INCREMENT,  -- ID único para cada apuesta
    usuario_id INTEGER NOT NULL,                  -- Relación con la tabla de usuarios (FK)
    juego_id INTEGER NOT NULL,                    -- Relación con la tabla de juegos (FK)
    monto_apostado DECIMAL(10, 2) NOT NULL,  -- Monto apostado en la apuesta
    fecha DATETIME NOT NULL,                           -- Fecha de la apuesta
    resultado varchar(50) NOT NULL,        -- Resultado de la apuesta (ganada/perdida/espera)
    PRIMARY KEY (id),                     -- Establece la columna "id" como la clave primaria
    FOREIGN KEY (usuario_id) REFERENCES usuario(id), -- Relación con la tabla usuario
    FOREIGN KEY (juego_id) REFERENCES juego(id)      -- Relación con la tabla juego
); 