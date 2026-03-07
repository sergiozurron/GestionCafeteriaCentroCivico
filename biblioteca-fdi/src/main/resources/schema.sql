USE cafeteria;
DROP TABLE IF EXISTS empleados;
DROP TABLE IF EXISTS bebidas;
DROP TABLE IF EXISTS comidas;
DROP TABLE IF EXISTS ingredientes;
DROP TABLE IF EXISTS linea_pedido;
DROP TABLE IF EXISTS proveedores;
DROP TABLE IF EXISTS productos;
DROP TABLE IF EXISTS pedidos;
DROP TABLE IF EXISTS mesas;
DROP TABLE IF EXISTS salas;
DROP TABLE IF EXISTS terrazas;
DROP TABLE IF EXISTS entradas_recetas;
CREATE TABLE mesas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    numero INT NOT NULL,
    ubicacion VARCHAR(100),
    capacidad INT,
    activo BOOLEAN NOT NULL   
) ENGINE=InnoDB;

CREATE TABLE salas (
    id_mesa INT PRIMARY KEY,
    reservada BOOLEAN NOT NULL,
    privacidad VARCHAR(50),
    FOREIGN KEY (id_mesa) REFERENCES mesas(id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE terrazas (
    id_mesa INT PRIMARY KEY,
    cubierta BOOLEAN NOT NULL,
    suplemento DECIMAL(10,2),
    FOREIGN KEY (id_mesa) REFERENCES mesas(id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE empleados (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    sueldo DECIMAL(10,2),
    donde_atiende VARCHAR(100),
    activo BOOLEAN NOT NULL
) ENGINE=InnoDB;

CREATE TABLE pedidos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATETIME NOT NULL,
    total_factura DECIMAL(10,2),
    estado VARCHAR(50),
    activo BOOLEAN NOT NULL,
    empleado_id INT,
    mesa_id INT,
    FOREIGN KEY (empleado_id) REFERENCES empleados(id),
    FOREIGN KEY (mesa_id) REFERENCES mesas(id)
) ENGINE=InnoDB;

CREATE TABLE proveedores (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    tarifa DECIMAL(10,2),
    tiempo_entrega INT,
    activo BOOLEAN NOT NULL
) ENGINE=InnoDB;

CREATE TABLE ingredientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    precio DECIMAL(10,2),
    activo BOOLEAN NOT NULL,
    proveedor_id INT,
    FOREIGN KEY (proveedor_id) REFERENCES proveedores(id)
) ENGINE=InnoDB;

CREATE TABLE productos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    precio DECIMAL(10,2),
    stock INT,
    activo BOOLEAN NOT NULL
) ENGINE=InnoDB;

CREATE TABLE comidas (
    id INT PRIMARY KEY,
    tipo VARCHAR(50),
    calorias INT,
    tiempo_preparacion INT,
    FOREIGN KEY (id) REFERENCES productos(id)
) ENGINE=InnoDB;

CREATE TABLE bebidas (
    id INT PRIMARY KEY,
    tipo VARCHAR(50),
    tamaño VARCHAR(50),
    FOREIGN KEY (id) REFERENCES productos(id)
) ENGINE=InnoDB;

CREATE TABLE entradas_recetas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    producto_id INT,
    ingrediente_id INT,
    activo BOOLEAN NOT NULL,
    FOREIGN KEY (producto_id) REFERENCES productos(id),
    FOREIGN KEY (ingrediente_id) REFERENCES ingredientes(id)
) ENGINE=InnoDB;

CREATE TABLE linea_pedido (
    id INT AUTO_INCREMENT PRIMARY KEY,
    pedido_id INT,
    producto_id INT,
    cantidad INT NOT NULL,
    activo BOOLEAN NOT NULL,
    FOREIGN KEY (pedido_id) REFERENCES pedidos(id),
    FOREIGN KEY (producto_id) REFERENCES productos(id)
) ENGINE=InnoDB;
