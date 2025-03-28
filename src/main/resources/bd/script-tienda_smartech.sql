use test;
drop database if exists tienda_smartech;
create database if not exists tienda_smartech;
use tienda_smartech;

create table usuarios(
	id int primary key auto_increment,
    documento enum('DNI','RUC','CE','PASAPORTE'),
    numero varchar(12) unique,
    rol enum('ADMIN','CLIENTE','USUARIO','INVITADO'),
    nombres varchar(50),
    apellidos varchar(50),
    direccion varchar(50),
    telefono varchar(12),
    email varchar(50) unique not null,
    password varchar(255) not null,
    estado boolean default 1,
    verificado boolean default 0,
    nacimiento date,
    registro datetime,
    actualiza datetime
);

create table galerias(
	id int primary key auto_increment,
    url varchar(255) unique
);

create table categorias(
	id int primary key auto_increment,
    nombre varchar(50) unique,
    galeria int,
    foreign key (galeria) references galerias(id)
);

create table marcas(
	id int primary key auto_increment,
    nombre varchar(50) unique,
    galeria int,
    foreign key (galeria) references galerias(id)
);

create table productos(
	id int primary key auto_increment,
    sku varchar(50) unique,
    nombre varchar(255) unique,
    descripcion text,
    slogan varchar(100),
    marca int,
    categoria int,
    precio decimal(6,2),
    descuento int,
    stock int,
    estado boolean default 1,
    registro datetime,
    actualiza datetime,
    constraint chk_descuento check(descuento<=100),
    foreign key (marca) references marcas(id),
    foreign key (categoria) references categorias(id)
);

create table fotos(
	id int primary key auto_increment,
    galeria int,
    producto int,
    foreign key (galeria) references galerias(id),
    foreign key (producto) references productos(id),
    unique (galeria, producto)
);

create table videos(
	id int primary key auto_increment,
    galeria int,
    producto int,
    foreign key (galeria) references galerias(id),
    foreign key (producto) references productos(id),
    unique (galeria, producto)
);

create table especificaciones(
	id int primary key auto_increment,
    nombre varchar(50),
    descripcion varchar(50),
    producto int,
    foreign key (producto) references productos(id),
    unique (nombre, producto)
);

create table carritos(
	id int primary key auto_increment,
    usuario int unique,
    registro datetime,
    actualiza datetime,
    foreign key (usuario) references usuarios(id)
);

create table carrito_detalles(
	id int primary key auto_increment,
    carrito int,
    producto int,
    cantidad int,
    foreign key (carrito) references carritos(id),
    foreign key (producto) references productos(id)
);

create table direcciones(
	id int primary key auto_increment,
    via enum('AVENIDA','CALLE','JIRON','URB','PASAJE'),
    nombre varchar(100),
    numero varchar(10),
    referencia varchar(100),
    distrito varchar(50),
    provincia varchar(50),
    departamento varchar(50),
    codigo_postal int,
    registro datetime,
    actualiza datetime
);

create table consignatarios(
	id int primary key auto_increment,
    documento enum('DNI','RUC','CE','PASAPORTE'),
    numero varchar(20),
    nombres varchar(100),
    celular varchar(20),
    email varchar(50),
    registro datetime,
    actualiza datetime
);

create table domicilios(
	id int primary key auto_increment,
    consignatario int,
    direccion int,
    usuario int,
    registro datetime,
    actualiza datetime,
    foreign key (consignatario) references consignatarios(id),
    foreign key (direccion) references direcciones(id),
    foreign key (usuario) references usuarios(id)
);

create table oficinas(
	id int primary key auto_increment,
    usuario int, 
    direccion int,
    nombre varchar(50),
    celular varchar(20),
    hora_inicio time,
    hora_fin time,
    registro datetime,
    actualiza datetime,
    foreign key (usuario) references Usuarios(id),
    foreign key (direccion) references Direcciones (id)
);

create table pedidos(
	id int primary key auto_increment,
    numero varchar(50),
    estado enum('GENERADO','APROBADO','ENVIADO','ENTREGADO','DEVUELTO','CANCELADO'),
    usuario int,
    entrega enum('DELIVERY','RETIRO'),
    consignatario int,
    direccion int,
    metodo_pago enum('TARJETA','MONEDA_DIGITAL','PAGO_EFECTIVO','CONTRA_ENTREGA'),
    precio_envio decimal(6,2),
    precio_cupon decimal(6,2),
    total decimal(6,2),
    igv decimal(6,2),
    comentarios varchar(255),
    fecha_entrega datetime,
    registro datetime,
    actualiza datetime,
    foreign key (usuario) references usuarios(id),
    foreign key (consignatario) references consignatarios(id),
    foreign key (direccion) references direcciones(id)
);

create table pedido_detalles(
	id int primary key auto_increment,
    pedido int,
    producto int,
    cantidad int,
    precio decimal(6,2),
    foreign key (pedido) references pedidos(id),
    foreign key (producto) references productos(id)
);

create table favoritos(
	id int primary key auto_increment,
    usuario int,
    producto int,
    registro datetime,
    foreign key (usuario) references Usuarios(id),
    foreign key (producto) references Productos(id)
);

DELIMITER $$
CREATE TRIGGER after_insert_usuarios
AFTER INSERT ON usuarios
FOR EACH ROW
BEGIN
    INSERT INTO carritos (usuario, registro, actualiza)
    VALUES (NEW.id, now(), now());
END$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER after_insert_pedido_detalles
AFTER INSERT ON pedido_detalles
FOR EACH ROW
BEGIN
    -- Declarar la variable para identificar al usuario
    DECLARE user_id INT;

    -- Obtener el ID del usuario asociado al pedido insertado
    SELECT usuario INTO user_id
    FROM pedidos
    WHERE id = NEW.pedido;

    -- Eliminar las filas del carrito_detalles únicamente del usuario correspondiente
    DELETE FROM carrito_detalles
    WHERE producto = NEW.producto
      AND carrito IN (
          SELECT id
          FROM carritos
          WHERE usuario = user_id
      );
END$$
DELIMITER ;

insert into galerias(url) values
('apple-celular-1.png'),('apple-celular-2.png'),('apple-celular-3.png'),('apple-celular-4.png'),
('samsung-celular-1.png'),('samsung-celular-2.png'),('samsung-celular-3.png'),('samsung-celular-4.png'),
('xiaomi-celular-1.png'),('xiaomi-celular-2.png'),('xiaomi-celular-3.png'),('xiaomi-celular-4.png'),
('huawei-celular-1.png'),('huawei-celular-2.png'),('huawei-celular-3.png'),('huawei-celular-4.png'),
('lenovo-laptop-1.png'),('lenovo-laptop-2.png'),('lenovo-laptop-3.png'),('lenovo-laptop-4.png'),
('asus-laptop-1.png'),('asus-laptop-2.png'),('asus-laptop-3.png'),('asus-laptop-4.png'),
('hp-laptop-1.png'),('hp-laptop-2.png'),('hp-laptop-3.png'),('hp-laptop-4.png'),
('apple-laptop-1.png'),('apple-laptop-2.png'),('apple-laptop-3.png'),('apple-laptop-4.png'),
('samsung-televisor-1.png'),('samsung-televisor-2.png'),('samsung-televisor-3.png'),('samsung-televisor-4.png'),
('lg-televisor-1.png'),('lg-televisor-2.png'),('lg-televisor-3.png'),('lg-televisor-4.png'),
('tcl-televisor-1.png'),('tcl-televisor-2.png'),('tcl-televisor-3.png'),('tcl-televisor-4.png'),
('hisense-televisor-1.png'),('hisense-televisor-2.png'),('hisense-televisor-3.png'),('hisense-televisor-4.png');

insert into categorias (nombre, galeria) values
('Celulares', 1),
('Laptop', 2),
('Televisor', 3),
('Altavoces', 4),
('Teclados', 5),
('Audifonos', 6),
('Mouse', 7),
('Monitores', 8),
('SmartWatch', 9),
('SmartHome', 10),
('Climatizacion', 11),
('Impresoras', 12),
('Consolas', 13),
('Sillas', 14),
('Accesorios', 15),
('Deporte', 16),
('Electrodomesticos', 17),
('Lavadoras', 18),
('Refrigeradoras', 19),
('Cocinas', 20);


insert into marcas (nombre, galeria) values
('Apple', 1),
('Samsung', 2),
('Xiaomi', 3),
('Huawei', 4),
('LG', 5),
('Honnor', 6),
('Lenovo', 7),
('Asus', 8),
('HP', 9),
('Dell', 10),
('Acer', 11),
('Amazon', 12),
('Hyundai', 13),
('TCL', 14),
('Hisense', 15),
('JBL', 16),
('Philips', 17),
('Panasonic', 18),
('Bose', 19),
('Logitech', 20),
('Razer', 21),
('Redragon', 22),
('Antryx', 23);

-- Categoría: Celulares
INSERT INTO productos (sku, nombre, descripcion, slogan, marca, categoria, precio, descuento, stock, estado, registro, actualiza) VALUES
('SKU001', 'iPhone 13 Pro Max de Apple con pantalla Super Retina XDR y más de 50 caracteres', 'El Samsung Galaxy S21 Ultra 5G ofrece una experiencia increíble con su pantalla Dynamic AMOLED 2X de 6.8 pulgadas, procesador Exynos 2100, 12GB de RAM y 256GB de almacenamiento. Este dispositivo cuenta con una cámara trasera cuádruple de 108MP, 10MP, 10MP y 12MP para fotos y videos de calidad profesional. Su batería de 5000mAh te permite disfrutar de tu teléfono durante todo el día.', 'Descubre el poder de la conectividad 5G con Samsung.', 1, 1, 1199.99, 40, 50, 1, NOW(), NOW()),
('SKU002', 'Samsung Galaxy S21 Ultra 5G con características avanzadas y más de 50 caracteres', 'El iPhone 13 Pro Max ofrece una pantalla Super Retina XDR de 6.7 pulgadas, procesador A15 Bionic, 6GB de RAM y 512GB de almacenamiento. Este dispositivo cuenta con un sistema de cámara trasera triple de 12MP con tecnología ProRAW para capturar fotos y videos impresionantes. Su batería de larga duración te permite disfrutar de tu iPhone durante todo el día.', 'Potencia y elegancia en la palma de tu mano.', 2, 1, 1399.50, 50, 30, 1, NOW(), NOW()),
('SKU003', 'Xiaomi Mi 11 Ultra con pantalla AMOLED y cámara avanzada', 'El Xiaomi Mi 11 Ultra cuenta con una pantalla AMOLED de 6.81 pulgadas, procesador Snapdragon 888, 12GB de RAM y 256GB de almacenamiento. Este dispositivo cuenta con una cámara trasera triple de 50MP, 48MP y 48MP para fotos y videos de alta calidad. Su batería de 5000mAh con carga rápida de 67W te permite cargar tu teléfono en poco tiempo.', 'Innovación y rendimiento al alcance de todos.', 3, 1, 999.99, 30, 70, 1, NOW(), NOW()),
('SKU004', 'Huawei P50 Pro con pantalla OLED y carga rápida', 'El OnePlus 9 Pro ofrece una pantalla Fluid AMOLED de 6.7 pulgadas, procesador Snapdragon 888, 8GB de RAM y 128GB de almacenamiento. Este dispositivo cuenta con una cámara trasera cuádruple de 48MP, 50MP, 8MP y 2MP para capturar fotos y videos de alta calidad. Su batería de 4500mAh con carga rápida de 65W asegura que puedas cargar tu teléfono rápidamente.', 'La mejor experiencia de carga rápida y rendimiento.', 4, 1, 899.75, 45, 40, 1, NOW(), NOW()),
('SKU005', 'Google Pixel 6 Pro con pantalla P-OLED y Android puro', 'El Google Pixel 6 Pro ofrece una pantalla P-OLED de 6.71 pulgadas, procesador Google Tensor, 12GB de RAM y 256GB de almacenamiento. Este dispositivo cuenta con una cámara trasera triple de 50MP, 12MP y 48MP para capturar fotos y videos impresionantes. Su batería de 5000mAh con carga rápida de 30W te permite disfrutar de tu teléfono durante todo el día.', 'El mejor de Google en un solo dispositivo.', 5, 1, 1099.25, 40, 20, 1, NOW(), NOW()),
('SKU006', 'Sony Xperia 1 III con pantalla OLED 4K y características avanzadas', 'El Sony Xperia 1 III ofrece una pantalla OLED 4K de 6.5 pulgadas, procesador Snapdragon 888, 12GB de RAM y 256GB de almacenamiento. Este dispositivo cuenta con una cámara trasera triple de 12MP con tecnología ZEISS para fotos y videos de calidad profesional. Su batería de 4500mAh con carga rápida de 30W asegura que puedas disfrutar de tu teléfono durante todo el día.', 'Calidad y rendimiento en cada detalle.', 6, 1, 1199.99, 60, 35, 1, NOW(), NOW()),
('SKU007', 'Samsung Galaxy Z Fold3 5G con pantalla plegable y más de 50 caracteres', 'El Samsung Galaxy Z Fold3 5G ofrece una pantalla plegable Dynamic AMOLED 2X de 7.6 pulgadas y una pantalla externa Super AMOLED de 6.2 pulgadas, procesador Snapdragon 888, 12GB de RAM y 512GB de almacenamiento. Este dispositivo cuenta con una cámara trasera triple de 12MP para capturar fotos y videos de alta calidad. Su batería de 4400mAh con carga rápida de 25W te permite disfrutar de tu teléfono durante todo el día.', 'La innovación de la pantalla plegable en tus manos.', 1, 1, 1799.50, 35, 25, 1, NOW(), NOW()),
('SKU008', 'OnePlus 9 Pro con pantalla Fluid AMOLED y carga rápida', 'El Huawei P50 Pro ofrece una pantalla OLED de 6.6 pulgadas, procesador Kirin 9000, 8GB de RAM y 256GB de almacenamiento. Este dispositivo cuenta con una cámara trasera cuádruple de 50MP, 64MP, 13MP y 40MP para capturar fotos y videos impresionantes. Su batería de 4360mAh con carga rápida de 66W asegura que puedas cargar tu teléfono rápidamente.', 'Rendimiento y calidad en un diseño elegante.', 2, 1, 999.75, 50, 30, 1, NOW(), NOW()),
('SKU009', 'Oppo Find X3 Pro con pantalla AMOLED y rendimiento avanzado', 'El Oppo Find X3 Pro ofrece una pantalla AMOLED de 6.7 pulgadas, procesador Snapdragon 888, 12GB de RAM y 256GB de almacenamiento. Este dispositivo cuenta con una cámara trasera cuádruple de 50MP, 50MP, 13MP y 3MP para capturar fotos y videos de alta calidad. Su batería de 4500mAh con carga rápida de 65W te permite cargar tu teléfono en poco tiempo.', 'Innovación y estilo en un solo dispositivo.', 3, 1, 1299.25, 65, 20, 1, NOW(), NOW()),
('SKU010', 'Motorola Edge 20 Pro con pantalla OLED y cámara avanzada', 'El Motorola Edge 20 Pro ofrece una pantalla OLED de 6.7 pulgadas, procesador Snapdragon 870, 12GB de RAM y 256GB de almacenamiento. Este dispositivo cuenta con una cámara trasera triple de 108MP, 16MP y 8MP para capturar fotos y videos impresionantes. Su batería de 4500mAh con carga rápida de 30W asegura que puedas disfrutar de tu teléfono durante todo el día.', 'Diseñado para los amantes de la fotografía.', 4, 1, 899.99, 30, 15, 1, NOW(), NOW());

-- Categoría: laptops
INSERT INTO productos (sku, nombre, descripcion, slogan, marca, categoria, precio, descuento, stock, estado, registro, actualiza) VALUES
('SKU011', 'Lenovo ThinkPad X1 Carbon de octava generación con construcción robusta', 'Esta laptop cuenta con la última tecnología de procesadores Intel Core i9, una pantalla OLED 4K de 15.6 pulgadas y una pantalla secundaria ScreenPad Plus de 14 pulgadas, ideal para multitareas y aumentar tu productividad. Además, tiene un diseño elegante y sofisticado que atraerá todas las miradas. La capacidad de almacenamiento es de 1TB SSD y 32GB de RAM para un rendimiento óptimo.', 'Experimenta el futuro con esta innovadora laptop de doble pantalla.', 7, 2, 2999.99, 30, 15, 1, NOW(), NOW()),
('SKU012', 'Laptop ASUS ZenBook Pro Duo con pantalla táctil dual y más de 50 caracteres', 'La Dell XPS 15 ofrece una pantalla Full HD InfinityEdge de 15.6 pulgadas, procesador Intel Core i7 de décima generación, 16GB de RAM y 512GB SSD. Este dispositivo está diseñado para ofrecer un rendimiento superior y una experiencia visual impresionante. La tecnología de audio Waves Nx 3D proporciona un sonido inmersivo.', 'Rendimiento inigualable en un diseño compacto y elegante.', 8, 2, 2499.50, 40, 20, 1, NOW(), NOW()),
('SKU013', 'HP Spectre x360 con diseño convertible y pantalla táctil 4K UHD', 'La nueva MacBook Pro de Apple cuenta con el revolucionario chip M1 que ofrece un rendimiento extraordinario y una eficiencia energética inigualable. Con 32GB de RAM y 1TB de almacenamiento SSD, esta laptop está diseñada para manejar las tareas más exigentes. La pantalla Retina de 16 pulgadas proporciona una calidad de imagen asombrosa y un color preciso.', 'Potencia y rendimiento en su máxima expresión.', 9, 2, 3499.00, 50, 10, 1, NOW(), NOW()),
('SKU014', 'MacBook Pro de Apple de 16 pulgadas con chip M1 y alto rendimiento', 'La HP Spectre x360 es una laptop convertible que ofrece una pantalla táctil 4K UHD de 15.6 pulgadas, procesador Intel Core i7, 16GB de RAM y 1TB SSD. Su diseño versátil permite usarla como laptop o tablet, y su batería de larga duración asegura que puedas trabajar todo el día sin interrupciones. Además, incluye un lápiz óptico para una experiencia de usuario mejorada.', 'Versatilidad y potencia en tus manos.', 1, 2, 2199.75, 35, 25, 1, NOW(), NOW()),
('SKU015', 'Dell XPS 15 con pantalla InfinityEdge y características avanzadas', 'El Lenovo ThinkPad X1 Carbon de octava generación es una laptop ultradelgada y ligera con un diseño robusto. Está equipada con un procesador Intel Core i7, 16GB de RAM y 512GB SSD. Su pantalla Full HD de 14 pulgadas proporciona una excelente calidad de imagen. Además, cuenta con tecnología de seguridad avanzada como reconocimiento facial y lector de huellas dactilares.', 'La combinación perfecta de rendimiento y seguridad.', 11, 2, 1899.25, 45, 30, 1, NOW(), NOW()),
('SKU016', 'Microsoft Surface Laptop 4 con pantalla táctil PixelSense y alto rendimiento', 'La Microsoft Surface Laptop 4 ofrece una pantalla táctil PixelSense de 15 pulgadas, procesador AMD Ryzen 7, 16GB de RAM y 512GB SSD. Esta laptop está diseñada para ofrecer un rendimiento impresionante y una experiencia de usuario fluida. Su diseño ligero y elegante la hace perfecta para trabajar sobre la marcha.', 'Potencia y portabilidad en un diseño elegante.', 12, 2, 1999.99, 50, 20, 1, NOW(), NOW()),
('SKU017', 'Acer Predator Helios 300 con características avanzadas para gaming', 'La Acer Predator Helios 300 es una laptop para gaming que cuenta con una pantalla Full HD de 15.6 pulgadas, procesador Intel Core i7, 16GB de RAM y 1TB SSD. Está equipada con una tarjeta gráfica NVIDIA GeForce RTX 3070 que proporciona un rendimiento gráfico excepcional. Además, su sistema de refrigeración avanzado asegura que puedas jugar durante horas sin problemas de sobrecalentamiento.', 'Juego sin límites con la máxima potencia.', 7, 2, 1799.50, 60, 15, 1, NOW(), NOW()),
('SKU018', 'Razer Blade 15 Advanced con pantalla OLED 4K y diseño delgado', 'La Razer Blade 15 Advanced ofrece una pantalla OLED 4K de 15.6 pulgadas, procesador Intel Core i9, 32GB de RAM y 1TB SSD. Esta laptop está diseñada para gamers y profesionales que buscan el máximo rendimiento. Su diseño delgado y elegante la hace ideal para llevarla a cualquier lugar.', 'Rendimiento extremo en un diseño compacto.', 8, 2, 2999.75, 70, 10, 1, NOW(), NOW()),
('SKU019', 'MSI GS66 Stealth con características avanzadas y diseño discreto', 'La MSI GS66 Stealth es una laptop de alto rendimiento diseñada para gaming y trabajo profesional. Cuenta con una pantalla Full HD de 15.6 pulgadas, procesador Intel Core i7, 16GB de RAM y 1TB SSD. Su tarjeta gráfica NVIDIA GeForce RTX 3060 asegura un rendimiento gráfico excepcional. Además, su diseño discreto y elegante la hace ideal para cualquier entorno.', 'El equilibrio perfecto entre potencia y estilo.', 9, 2, 2499.25, 55, 20, 1, NOW(), NOW()),
('SKU020', 'Gigabyte Aero 15 OLED con pantalla 4K UHD y rendimiento profesional', 'La Gigabyte Aero 15 OLED ofrece una pantalla 4K UHD de 15.6 pulgadas, procesador Intel Core i9, 32GB de RAM y 1TB SSD. Esta laptop está diseñada para profesionales creativos que necesitan el máximo rendimiento y precisión en el color. Su diseño elegante y portátil la hace perfecta para trabajar desde cualquier lugar.', 'La mejor herramienta para profesionales creativos.', 10, 2, 3399.99, 65, 5, 1, NOW(), NOW());

-- Categoría: Televisor
INSERT INTO productos (sku, nombre, descripcion, slogan, marca, categoria, precio, descuento, stock, estado, registro, actualiza) VALUES
('SKU021', 'Televisor Ultra HD de 50 pulgadas con tecnología avanzada', 'Este televisor ofrece una calidad de imagen impresionante con una resolución Ultra HD de 4K. Disfruta de colores vivos y detalles nítidos en cada escena, acompañado de un sonido envolvente que te sumergirá en la experiencia audiovisual. Además, cuenta con múltiples puertos para conectar tus dispositivos favoritos y acceder a tus aplicaciones de streaming preferidas.', 'Innovación en cada detalle y una experiencia visual inigualable.', 2, 3, 2100.00, 45, 150, 1, NOW(), NOW()),
('SKU022', 'Televisor LED de 55 pulgadas con inteligencia artificial', 'Este televisor LED de 55 pulgadas incorpora inteligencia artificial para mejorar tu experiencia de visualización. Con funciones inteligentes, podrás controlar el televisor con comandos de voz y acceder a una variedad de aplicaciones y servicios. La calidad de imagen es excepcional, brindando colores brillantes y contrastes profundos.', 'Controla tu entretenimiento con solo tu voz.', 5, 3, 2300.00, 50, 120, 1, NOW(), NOW()),
('SKU023', 'Televisor OLED de 65 pulgadas con HDR', 'Sumérgete en una experiencia cinematográfica con este televisor OLED de 65 pulgadas. La tecnología HDR ofrece un rango dinámico más amplio, proporcionando una calidad de imagen superior con negros profundos y colores vibrantes. Ideal para ver películas, series y deportes en la mejor calidad posible.', 'Vive cada momento con una claridad impresionante.', 14, 3, 2500.00, 55, 130, 1, NOW(), NOW()),
('SKU024', 'Televisor QLED de 75 pulgadas con resolución 8K', 'Este televisor QLED de 75 pulgadas redefine la calidad de imagen con su resolución 8K. Disfruta de una claridad y detalle nunca antes vistos, con colores precisos y un brillo excepcional. Perfecto para grandes espacios y para aquellos que buscan lo mejor en tecnología de entretenimiento.', 'La mejor calidad de imagen para los más exigentes.', 15, 3, 2800.00, 60, 110, 1, NOW(), NOW()),
('SKU025', 'Televisor Smart TV de 43 pulgadas con 4K UHD', 'Un televisor inteligente de 43 pulgadas que combina elegancia y funcionalidad. Con resolución 4K UHD, ofrece imágenes nítidas y colores vibrantes. Accede a tus aplicaciones de streaming favoritas y navega por Internet directamente desde tu televisor.', 'La combinación perfecta de estilo y tecnología.', 2, 3, 2200.00, 50, 140, 1, NOW(), NOW()),
('SKU026', 'Televisor Curvo de 55 pulgadas con tecnología QLED', 'Este televisor curvo de 55 pulgadas con tecnología QLED te ofrece una experiencia de visualización envolvente. La curvatura de la pantalla te sumerge en cada escena, mientras que la tecnología QLED proporciona colores brillantes y precisos.', 'Una experiencia envolvente en cada ángulo.', 5, 3, 2400.00, 55, 160, 1, NOW(), NOW()),
('SKU027', 'Televisor 3D de 60 pulgadas con resolución 4K', 'Disfruta de una experiencia visual tridimensional con este televisor 3D de 60 pulgadas. La resolución 4K garantiza una calidad de imagen impresionante, mientras que la función 3D te permite ver tus películas y programas favoritos con una nueva perspectiva.', 'Experimenta el entretenimiento en tres dimensiones.', 14, 3, 2700.00, 40, 180, 1, NOW(), NOW()),
('SKU028', 'Televisor LED de 70 pulgadas con Smart TV', 'Un televisor LED de 70 pulgadas que ofrece una gran calidad de imagen y funcionalidad de Smart TV. Conéctate a Internet, accede a tus aplicaciones de streaming favoritas y disfruta de una experiencia de entretenimiento completa desde la comodidad de tu hogar.', 'Tu centro de entretenimiento en una sola pantalla.', 15, 3, 2900.00, 60, 200, 1, NOW(), NOW()),
('SKU029', 'Televisor UHD de 50 pulgadas con tecnología Quantum Dot', 'Este televisor UHD de 50 pulgadas con tecnología Quantum Dot proporciona colores más vivos y realistas. La resolución 4K UHD te permite ver tus contenidos favoritos con una claridad impresionante, mientras que las funciones inteligentes te brindan acceso a una variedad de aplicaciones y servicios.', 'Colores más reales para una experiencia visual sin igual.', 2, 3, 2100.00, 45, 120, 1, NOW(), NOW()),
('SKU030', 'Televisor 4K de 65 pulgadas con sonido Dolby Atmos', 'Este televisor de 65 pulgadas ofrece una calidad de imagen 4K y un sonido envolvente Dolby Atmos para una experiencia audiovisual completa. Disfruta de una imagen nítida y un sonido impresionante que te sumergirá en cada escena.', 'Calidad de imagen y sonido de cine en tu hogar.', 5, 3, 2500.00, 50, 150, 1, NOW(), NOW());

-- Categoría: Altavoces
INSERT INTO productos (sku, nombre, descripcion, slogan, marca, categoria, precio, descuento, stock, estado, registro, actualiza) VALUES
('SKU031', 'Bose SoundLink Revolve+ Altavoz Bluetooth 360 Grados', 'Altavoz Bluetooth portátil con sonido de 360 grados.', 'Música en movimiento.', 15, 4, 299.99, 55, 50, 1, NOW(), NOW()),
('SKU032', 'JBL Charge 5 Altavoz Bluetooth Resistente al Agua', 'Altavoz portátil con batería de larga duración.', 'Sonido potente y claro.', 16, 4, 199.99, 60, 40, 1, NOW(), NOW()),
('SKU033', 'Sony SRS-XB43 Altavoz Bluetooth Extra Bass', 'Altavoz con graves profundos y luces LED.', 'Fiesta en cualquier lugar.', 3, 4, 249.99, 65, 30, 1, NOW(), NOW()),
('SKU034', 'Ultimate Ears BOOM 3 Altavoz Bluetooth Impermeable', 'Altavoz robusto y resistente al agua.', 'Sonido equilibrado.', 7, 4, 179.99, 70, 25, 1, NOW(), NOW()),
('SKU035', 'Amazon Echo (4ta Generación) con Alexa', 'Altavoz inteligente con asistente de voz.', 'Tu asistente personal.', 14, 4, 99.99, 75, 20, 1, NOW(), NOW()),
('SKU036', 'Bang & Olufsen Beosound A1 2nd Gen Altavoz Bluetooth', 'Altavoz premium con sonido de alta calidad.', 'Elegancia portátil.', 7, 4, 299.99, 80, 15, 1, NOW(), NOW()),
('SKU037', 'Sonos Move Altavoz Inteligente para Interior y Exterior', 'Altavoz versátil con batería recargable.', 'Sonido en cualquier lugar.', 7, 4, 399.99, 85, 10, 1, NOW(), NOW()),
('SKU038', 'Marshall Kilburn II Altavoz Bluetooth Retro', 'Altavoz con diseño clásico y sonido potente.', 'Estilo y calidad.', 7, 4, 299.99, 90, 20, 1, NOW(), NOW()),
('SKU039', 'Harman Kardon Onyx Studio 6 Altavoz Bluetooth', 'Altavoz con diseño elegante y sonido premium.', 'Experiencia auditiva.', 7, 4, 249.99, 95, 30, 1, NOW(), NOW()),
('SKU040', 'LG XBOOM Go PL7 Altavoz Bluetooth con Graves Profundos', 'Altavoz portátil con excelente calidad de sonido.', 'Diversión sin cables.', 5, 4, 149.99, 99, 25, 1, NOW(), NOW());

-- Categoría: Teclados
INSERT INTO productos (sku, nombre, descripcion, slogan, marca, categoria, precio, descuento, stock, estado, registro, actualiza) VALUES
('SKU041', 'Logitech MX Keys Teclado Inalámbrico Iluminado', 'Teclado ergonómico con retroiluminación.', 'Confort y precisión.', 7, 5, 99.99, 55, 50, 1, NOW(), NOW()),
('SKU042', 'Razer BlackWidow Elite Teclado Mecánico Gaming', 'Teclado mecánico con switches personalizados.', 'Juega al máximo.', 7, 5, 149.99, 60, 40, 1, NOW(), NOW()),
('SKU043', 'Corsair K95 RGB Platinum Teclado Mecánico', 'Teclado con retroiluminación RGB y teclas macro.', 'Personalización total.', 7, 5, 199.99, 65, 30, 1, NOW(), NOW()),
('SKU044', 'Microsoft Surface Keyboard Teclado Inalámbrico', 'Teclado minimalista y elegante.', 'Diseño y funcionalidad.', 4, 5, 129.99, 70, 25, 1, NOW(), NOW()),
('SKU045', 'Apple Magic Keyboard con Teclado Numérico', 'Teclado inalámbrico con batería recargable.', 'Simplicidad y estilo.', 1, 5, 149.99, 75, 20, 1, NOW(), NOW()),
('SKU046', 'SteelSeries Apex Pro Teclado Mecánico con OLED Smart Display', 'Teclado con tecnología avanzada y pantalla OLED.', 'Rendimiento superior.', 7, 5, 199.99, 80, 15, 1, NOW(), NOW()),
('SKU047', 'HP Omen Sequencer Teclado Gaming', 'Teclado mecánico con retroiluminación RGB.', 'Juega con ventaja.', 9, 5, 129.99, 85, 10, 1, NOW(), NOW()),
('SKU048', 'Asus ROG Strix Scope Teclado Mecánico Gaming', 'Teclado con switches Cherry MX y RGB.', 'Precisión y estilo.', 8, 5, 179.99, 90, 20, 1, NOW(), NOW()),
('SKU049', 'Alienware Pro Gaming Keyboard AW768 Teclado Mecánico', 'Teclado con retroiluminación RGB y teclas programables.', 'Control total.', 7, 5, 149.99, 95, 30, 1, NOW(), NOW()),
('SKU050', 'Dell KM717 Premier Teclado y Mouse Inalámbricos', 'Combo de teclado y mouse con conectividad Bluetooth.', 'Confort y eficiencia.', 10, 5, 99.99, 99, 25, 1, NOW(), NOW());

-- Categoría: Audífonos
INSERT INTO productos (sku, nombre, descripcion, slogan, marca, categoria, precio, descuento, stock, estado, registro, actualiza) VALUES
('SKU051', 'Sony WH-1000XM4 Audífonos Inalámbricos con Cancelación de Ruido', 'Audífonos premium con sonido de alta calidad.', 'Experiencia auditiva inmersiva.', 3, 6, 349.99, 55, 50, 1, NOW(), NOW()),
('SKU052', 'Bose QuietComfort 35 II Audífonos Inalámbricos', 'Audífonos con cancelación de ruido activa.', 'Silencio y confort.', 15, 6, 299.99, 60, 40, 1, NOW(), NOW()),
('SKU053', 'Apple AirPods Pro Audífonos Inalámbricos con Cancelación de Ruido', 'Audífonos con diseño ergonómico y sonido de alta calidad.', 'Sonido sin límites.', 1, 6, 249.99, 65, 30, 1, NOW(), NOW()),
('SKU054', 'Samsung Galaxy Buds Pro Audífonos Inalámbricos', 'Audífonos con cancelación de ruido activa y sonido 360.', 'Sonido envolvente.', 2, 6, 199.99, 70, 25, 1, NOW(), NOW()),
('SKU055', 'Jabra Elite 85h Audífonos Inalámbricos', 'Audífonos con cancelación de ruido inteligente.', 'Sonido personalizado.', 7, 6, 249.99, 75, 20, 1, NOW(), NOW()),
('SKU056', 'Sennheiser Momentum 3 Wireless Audífonos', 'Audífonos con diseño elegante y sonido de alta fidelidad.', 'Calidad de sonido superior.', 7, 6, 399.99, 80, 15, 1, NOW(), NOW()),
('SKU057', 'Bang & Olufsen Beoplay H9 3rd Gen Audífonos Inalámbricos', 'Audífonos premium con cancelación de ruido.', 'Elegancia y rendimiento.', 7, 6, 499.99, 85, 10, 1, NOW(), NOW()),
('SKU058', 'AKG N700NC M2 Audífonos Inalámbricos', 'Audífonos con cancelación de ruido adaptativa.', 'Sonido sin interrupciones.', 7, 6, 299.99, 90, 20, 1, NOW(), NOW()),
('SKU059', 'Shure AONIC 50 Audífonos Inalámbricos', 'Audífonos con sonido de estudio y cancelación de ruido.', 'Sonido profesional.', 7, 6, 399.99, 95, 30, 1, NOW(), NOW()),
('SKU060', 'Plantronics BackBeat Go 810 Audífonos Inalámbricos', 'Audífonos con cancelación de ruido y batería de larga duración.', 'Escucha sin límites.', 7, 6, 199.99, 99, 25, 1, NOW(), NOW());

insert into fotos(galeria, producto) values
(1,1),(2,1),(3,1),(4,1),
(5,2),(6,2),(7,2),(8,2),
(9,3),(10,3),(11,3),(12,3),
(13,4),(14,4),(15,4),(16,4),
(17,11),(18,11),(19,11),(20,11),
(21,12),(22,12),(23,12),(24,12),
(25,13),(26,13),(27,13),(28,13),
(29,14),(30,14),(31,14),(32,14),
(33,21),(34,21),(35,21),(36,21),
(37,22),(38,22),(39,22),(40,22),
(41,23),(42,23),(43,23),(44,23),
(45,24),(46,24),(47,24),(48,24);

-- Especificaciones para los productos de celulares (1 al 4)
INSERT INTO especificaciones (nombre, descripcion, producto) VALUES
('Pantalla', '6.8 pulgadas', 1),
('Procesador', 'Exynos 2100', 1),
('RAM', '12GB', 1),
('Almacenamiento', '256GB', 1),
('Cámara', '108MP + 10MP + 10MP + 12MP', 1),
('Batería', '5000mAh', 1),
('Sistema Operativo', 'Android 11', 1),
('Peso', '228g', 1),
('Dimensiones', '165.1 x 75.6 x 8.9 mm', 1),
('Colores', 'Phantom Black, Phantom Silver', 1),

('Pantalla', '6.7 pulgadas', 2),
('Procesador', 'A15 Bionic', 2),
('RAM', '6GB', 2),
('Almacenamiento', '512GB', 2),
('Cámara', '12MP + 12MP + 12MP', 2),
('Batería', '3687mAh', 2),
('Sistema Operativo', 'iOS 15', 2),
('Peso', '238g', 2),
('Dimensiones', '160.8 x 78.1 x 7.7 mm', 2),
('Colores', 'Graphite, Gold, Silver, Sierra Blue', 2),

('Pantalla', '6.81 pulgadas', 3),
('Procesador', 'Snapdragon 888', 3),
('RAM', '12GB', 3),
('Almacenamiento', '256GB', 3),
('Cámara', '50MP + 48MP + 48MP', 3),
('Batería', '5000mAh', 3),
('Sistema Operativo', 'Android 11', 3),
('Peso', '234g', 3),
('Dimensiones', '164.3 x 74.6 x 8.38 mm', 3),
('Colores', 'Ceramic Black, Ceramic White', 3),

('Pantalla', '6.7 pulgadas', 4),
('Procesador', 'Snapdragon 888', 4),
('RAM', '8GB', 4),
('Almacenamiento', '128GB', 4),
('Cámara', '48MP + 50MP + 8MP + 2MP', 4),
('Batería', '4500mAh', 4),
('Sistema Operativo', 'Android 11', 4),
('Peso', '197g', 4),
('Dimensiones', '163.2 x 73.6 x 8.7 mm', 4),
('Colores', 'Morning Mist, Pine Green', 4),

-- Especificaciones para los productos de laptops (11 al 14)
('Pantalla', '15.6 pulgadas', 11),
('Procesador', 'Intel Core i9', 11),
('RAM', '32GB', 11),
('Almacenamiento', '1TB SSD', 11),
('Tarjeta Gráfica', 'NVIDIA GeForce RTX 3070', 11),
('Batería', '99Wh', 11),
('Sistema Operativo', 'Windows 10', 11),
('Peso', '2.4kg', 11),
('Dimensiones', '359.5 x 248.5 x 19.9 mm', 11),
('Colores', 'Celestial Blue', 11),

('Pantalla', '15.6 pulgadas', 12),
('Procesador', 'Intel Core i7', 12),
('RAM', '16GB', 12),
('Almacenamiento', '512GB SSD', 12),
('Tarjeta Gráfica', 'NVIDIA GeForce GTX 1650', 12),
('Batería', '97Wh', 12),
('Sistema Operativo', 'Windows 10', 12),
('Peso', '1.8kg', 12),
('Dimensiones', '344 x 230 x 18 mm', 12),
('Colores', 'Platinum Silver', 12),

('Pantalla', '16 pulgadas', 13),
('Procesador', 'Apple M1', 13),
('RAM', '32GB', 13),
('Almacenamiento', '1TB SSD', 13),
('Tarjeta Gráfica', 'Apple GPU', 13),
('Batería', '100Wh', 13),
('Sistema Operativo', 'macOS', 13),
('Peso', '2.0kg', 13),
('Dimensiones', '355.7 x 243.5 x 16.2 mm', 13),
('Colores', 'Space Gray, Silver', 13),

('Pantalla', '14 pulgadas', 14),
('Procesador', 'AMD Ryzen 7', 14),
('RAM', '16GB', 14),
('Almacenamiento', '512GB SSD', 14),
('Tarjeta Gráfica', 'AMD Radeon Vega 8', 14),
('Batería', '70Wh', 14),
('Sistema Operativo', 'Windows 10', 14),
('Peso', '1.4kg', 14),
('Dimensiones', '320 x 210 x 15.9 mm', 14),
('Colores', 'Nightfall Black', 14),

-- Especificaciones para los productos de televisores (21 al 24)
('Pantalla', '55 pulgadas', 21),
('Resolución', '4K Ultra HD', 21),
('Tipo de Pantalla', 'OLED', 21),
('HDR', 'HDR10+', 21),
('Conectividad', 'Wi-Fi, Bluetooth', 21),
('Puertos', 'HDMI x4, USB x3', 21),
('Sistema Operativo', 'webOS', 21),
('Peso', '18kg', 21),
('Dimensiones', '1228 x 706 x 47 mm', 21),
('Colores', 'Negro', 21),

('Pantalla', '65 pulgadas', 22),
('Resolución', '4K Ultra HD', 22),
('Tipo de Pantalla', 'QLED', 22),
('HDR', 'HDR10+', 22),
('Conectividad', 'Wi-Fi, Bluetooth', 22),
('Puertos', 'HDMI x4, USB x3', 22),
('Sistema Operativo', 'Tizen', 22),
('Peso', '22kg', 22),
('Dimensiones', '1450 x 830 x 55 mm', 22),
('Colores', 'Negro', 22),

('Pantalla', '75 pulgadas', 23),
('Resolución', '8K Ultra HD', 23),
('Tipo de Pantalla', 'QLED', 23),
('HDR', 'HDR10+', 23),
('Conectividad', 'Wi-Fi, Bluetooth', 23),
('Puertos', 'HDMI x4, USB x3', 23),
('Sistema Operativo', 'Tizen', 23),
('Peso', '38kg', 23),
('Dimensiones', '1677 x 958 x 45 mm', 23),
('Colores', 'Negro', 23),

('Pantalla', '85 pulgadas', 24),
('Resolución', '8K Ultra HD', 24),
('Tipo de Pantalla', 'OLED', 24),
('HDR', 'HDR10+', 24),
('Conectividad', 'Wi-Fi, Bluetooth', 24),
('Puertos', 'HDMI x4, USB x3', 24),
('Sistema Operativo', 'Android TV', 24),
('Peso', '44kg', 24),
('Dimensiones', '1893 x 1080 x 50 mm', 24),
('Colores', 'Negro', 24);

insert into usuarios(documento,numero,rol,nombres,apellidos,direccion,telefono,email,password,estado,verificado,nacimiento,registro,actualiza) values
('PASAPORTE','19552011','ADMIN','Steve','Jobs','California','19552011','jobs@gmail.com','$2a$10$g9xqnDDmb/hOgyerlsMsteo5PgJON6Xk.zPV0QKDrW/JDYpulA/0S',1,1,null,now(),now()),
('PASAPORTE','19552025','ADMIN','Bill','Gates','Seattle','19552025','gates@gmail.com','$2a$10$g9xqnDDmb/hOgyerlsMsteo5PgJON6Xk.zPV0QKDrW/JDYpulA/0S',1,1,null,now(),now()),
('DNI','46348500','CLIENTE','Ericson','Cruz','','','ericson4634@gmail.com','$2a$10$g9xqnDDmb/hOgyerlsMsteo5PgJON6Xk.zPV0QKDrW/JDYpulA/0S',1,1,null,now(),now());

select * from usuarios;
select * from direcciones;
select * from consignatarios;
select * from domicilios;
select * from oficinas;
select * from productos;
select * from categorias;
select * from marcas;
select * from fotos;
select * from carritos;
select * from carrito_detalles;
select * from pedidos;
select * from pedido_detalles;
select * from favoritos;

/*

update pedidos
set estado = 'ENVIADO'
where id = 1

update usuarios
set verificado = 1
where id = 3

drop table carritos;
truncate table carritos;
drop table carrito_detalles;
truncate table carrito_detalles;

update carrito_detalles
set producto = 13
where id = 28;

update usuarios
set telefono = '956788951'
where id= 1;

update usuarios
set nacimiento = current_date()
where id=1;

update usuarios
set documento = 'CE'
where id = 1;

*/
