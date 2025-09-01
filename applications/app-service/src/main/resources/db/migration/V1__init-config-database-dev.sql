-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS solicitudes;
USE solicitudes;

-- Tabla estados
CREATE TABLE IF NOT EXISTS estados (
    id_estado BIGINT AUTO_INCREMENT PRIMARY KEY,  -- Cambio a BIGINT para coincidir con dominio
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255),
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    UNIQUE KEY uk_estados_nombre (nombre)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla tipo_prestamo
CREATE TABLE IF NOT EXISTS tipo_prestamo (
    id_tipo_prestamo BIGINT AUTO_INCREMENT PRIMARY KEY,  -- Cambio a BIGINT para coincidir con dominio
    nombre VARCHAR(100) NOT NULL,
    monto_minimo DECIMAL(15,2) NOT NULL,
    monto_maximo DECIMAL(15,2) NOT NULL,
    tasa_interes DECIMAL(5,4) NOT NULL,
    validacion_automatica BOOLEAN NOT NULL DEFAULT FALSE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    UNIQUE KEY uk_tipo_prestamo_nombre (nombre),
    CONSTRAINT chk_monto_maximo_minimo CHECK (monto_maximo > monto_minimo),
    CONSTRAINT chk_monto_minimo_positivo CHECK (monto_minimo > 0),
    CONSTRAINT chk_tasa_interes_positiva CHECK (tasa_interes > 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla solicitud
CREATE TABLE IF NOT EXISTS solicitud (
    id_solicitud BIGINT AUTO_INCREMENT PRIMARY KEY,  -- Cambio a BIGINT para coincidir con dominio
    monto DECIMAL(15,2) NOT NULL,
    plazo INT NOT NULL,
    email VARCHAR(255) NOT NULL,  -- Aumento tamaño para mayor flexibilidad
    documento_identidad VARCHAR(255) NOT NULL,
    id_estado BIGINT NOT NULL,  -- Cambio a BIGINT
    id_tipo_prestamo BIGINT NOT NULL,  -- Cambio a BIGINT
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    INDEX idx_solicitud_email (email),
    INDEX idx_solicitud_estado (id_estado),
    INDEX idx_solicitud_tipo_prestamo (id_tipo_prestamo),
    INDEX idx_solicitud_fecha_creacion (fecha_creacion),

    CONSTRAINT fk_estado FOREIGN KEY (id_estado) REFERENCES estados(id_estado)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_tipo_prestamo FOREIGN KEY (id_tipo_prestamo) REFERENCES tipo_prestamo(id_tipo_prestamo)
        ON DELETE RESTRICT ON UPDATE CASCADE,

    CONSTRAINT chk_monto_positivo CHECK (monto > 0),
    CONSTRAINT chk_plazo_positivo CHECK (plazo > 0),
    CONSTRAINT chk_email_format CHECK (email REGEXP '^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$')
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Insertar datos iniciales para estados
INSERT IGNORE INTO estados (id_estado, nombre, descripcion) VALUES
(1, 'PENDIENTE_DE_REVISION', 'Solicitud pendiente de revisión'),
(2, 'EN_REVISION', 'Solicitud en proceso de revisión'),
(3, 'APROBADA', 'Solicitud aprobada'),
(4, 'RECHAZADA', 'Solicitud rechazada'),
(5, 'CANCELADA', 'Solicitud cancelada por el usuario');

-- Insertar datos iniciales para tipos de préstamo
INSERT IGNORE INTO tipo_prestamo (id_tipo_prestamo, nombre, monto_minimo, monto_maximo, tasa_interes, validacion_automatica) VALUES
(1, 'PERSONAL', 1000000.00, 50000000.00, 0.1250, true),
(2, 'HIPOTECARIO', 20000000.00, 500000000.00, 0.0890, false),
(3, 'VEHICULAR', 5000000.00, 150000000.00, 0.1150, true),
(4, 'EDUCATIVO', 2000000.00, 100000000.00, 0.0950, true),
(5, 'COMERCIAL', 10000000.00, 1000000000.00, 0.1580, false);