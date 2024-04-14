CREATE TABLE IF NOT EXISTS Usuarios (nombreUsuario VARCHAR(50) primary key, password VARCHAR(50) NOT NULL);
CREATE TABLE IF NOT EXISTS Cuentas (
    cuentaID int primary key auto_increment,
    nombreUsuario VARCHAR(50) NOT NULL,
    nombreCuenta VARCHAR(50) NOT NULL,
    saldo DECIMAL(12, 2) NOT NULL,
    FOREIGN KEY (nombreUsuario) REFERENCES Usuarios(nombreUsuario)
    );
CREATE TABLE IF NOT EXISTS Movimientos (
    movimientoID int primary key auto_increment,
    cuentaID int,
    movimientoTipo VARCHAR(20) NOT NULL,
    movimientoNombre VARCHAR(50) NOT NULL,
    movimientoDesc VARCHAR(200) NOT NULL,
    movimientoMonto DECIMAL(12, 2) NOT NULL,
    movimientoFecha DATE NOT NULL,
    movimientoRegistro TIMESTAMP NOT NULL,
    FOREIGN KEY (cuentaID) REFERENCES Cuentas(cuentaID)
    );