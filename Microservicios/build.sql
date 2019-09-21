-- Create a new database
DROP SCHEMA public CASCADE;
CREATE SCHEMA public;

-- En este punto debe iniciarse cualquiera de los servicios, para que la BD se cree a partir de los mapeos

-- Alter sequences
alter sequence tcl01_gasolinera_seq restart with 1;
alter sequence tcl02_ubicacion_seq restart with 1;
alter sequence tcl03_insignia_gasolinera_seq restart with 1;
alter sequence tme01_sensor_seq restart with 1;
alter sequence tme02_medicion_seq restart with 1;
alter sequence tme03_estado_sensor_seq restart with 1;
alter sequence tme04_estado_medicion_seq restart with 1;
alter sequence tus01_usuario_seq restart with 1;
alter sequence tus02_direccion_usuario_seq restart with 1;
alter sequence tus03_automovil_seq restart with 1;
alter sequence tus04_reporte_seq restart with 1;
alter sequence tus05_insignia_usuario_seq restart with 1;
alter sequence tus06_estado_reporte_seq restart with 1;
alter sequence tus07_login_seq restart with 1;
alter sequence tus08_estado_usuario_seq restart with 1;
alter sequence tus10_trama_seq restart with 1;

-- Catalogos

-- Insignia Usuario
insert into tus05_insignia_usuario(id_insignia_usuario, criterio, descripcion, url_imagen) values(nextval('tus05_insignia_usuario_seq'),'Poniendo mi granito de arena', 'El usuario ha realizado su primer medición', 'http://aws.com/usuario/1');
insert into tus05_insignia_usuario(id_insignia_usuario, criterio, descripcion, url_imagen) values(nextval('tus05_insignia_usuario_seq'),'Usuario de Diez', 'El usuario ha alcanzado las 10 mediciones', 'http://aws.com/usuario/2');
insert into tus05_insignia_usuario(id_insignia_usuario, criterio, descripcion, url_imagen) values(nextval('tus05_insignia_usuario_seq'),'Cien y contando', 'El usuario ha alcanzado las 100 mediciones', 'http://aws.com/usuario/3');
insert into tus05_insignia_usuario(id_insignia_usuario, criterio, descripcion, url_imagen) values(nextval('tus05_insignia_usuario_seq'),'Gasolicólicos Anónimos Miembro Oficial', 'El usuario ha alcanzado las 1000 mediciones', 'http://aws.com/usuario/4');

-- Insignia Gasolinera
insert into tcl03_insignia_gasolinera(id_insignia_gasolinera, criterio, descripcion, url) values(nextval('tcl03_insignia_gasolinera_seq'), 'Gasolinera reluciente', 'La gasolinera ha sido indicada como un lugar limpio', 'http://aws.com/gasolinera/1' );
insert into tcl03_insignia_gasolinera(id_insignia_gasolinera, criterio, descripcion, url) values(nextval('tcl03_insignia_gasolinera_seq'), 'Gasolinera veloz', 'La gasolinera ha obtenido record de velocidad atendiendo a sus clientes', 'http://aws.com/gasolinera/2' );
insert into tcl03_insignia_gasolinera(id_insignia_gasolinera, criterio, descripcion, url) values(nextval('tcl03_insignia_gasolinera_seq'), 'Gasolinera de la familia', 'Toda tu familia frecuenta esta gasolinera', 'http://aws.com/gasolinera/3' );
insert into tcl03_insignia_gasolinera(id_insignia_gasolinera, criterio, descripcion, url) values(nextval('tcl03_insignia_gasolinera_seq'), 'Gasolinera recomendada', 'Los clientes han recomendado esta gasolinera', 'http://aws.com/gasolinera/4' );

-- Estado reporte
insert into tus06_estado_reporte(id_estado, codigo, descripcion) values(nextval('tus06_estado_reporte_seq'), 'Creado', 'El reporte ha sido creado pero no ha sido atendido por un administrador');
insert into tus06_estado_reporte(id_estado, codigo, descripcion) values(nextval('tus06_estado_reporte_seq'), 'En Revisión', 'El reporte se encuentra siendo revisado por un administrador');
insert into tus06_estado_reporte(id_estado, codigo, descripcion) values(nextval('tus06_estado_reporte_seq'), 'Liberado', 'El reporte ha sido liberado por administrador al realizar las acciones pertinentes');

-- Estado Medicion
insert into tme04_estado_medicion(id_estado, codigo, descripcion) values(nextval('tme04_estado_medicion_seq'), 'Registrada', 'La medición ha sido recibida a partir de la app móvil');
insert into tme04_estado_medicion(id_estado, codigo, descripcion) values(nextval('tme04_estado_medicion_seq'), 'Clasificada', 'La medición ha sido utilizada por el algoritmo clasificatorio');
insert into tme04_estado_medicion(id_estado, codigo, descripcion) values(nextval('tme04_estado_medicion_seq'), 'Reportada', 'La medición ha sido descartada pues su sensor correspondiente has sido reportado');

-- Estado Sensor
insert into tme03_estado_sensor(id_estado, codigo, descripcion) values(nextval('tme03_estado_sensor_seq'), 'Registrado', 'El sensor a sido vendido a un usuario');
insert into tme03_estado_sensor(id_estado, codigo, descripcion) values(nextval('tme03_estado_sensor_seq'), 'Verificado', 'El usuario ha verificado su sensor');
insert into tme03_estado_sensor(id_estado, codigo, descripcion) values(nextval('tme03_estado_sensor_seq'), 'Reportado', 'El sensor ha sido reportaod y no podrá envíar nuevas mediciones');

-- Estado Usuario
insert into tus08_estado_usuario(id_estado, codigo, descripcion) values(nextval('tus08_estado_usuario_seq'), 'Registrado', 'El usuario se ha registrado dentro del sistema');
insert into tus08_estado_usuario(id_estado, codigo, descripcion) values(nextval('tus08_estado_usuario_seq'), 'Cuenta Básica', 'El usuario ha verificado su cuenta, por lo que tendrá todas las funcionalidades');
insert into tus08_estado_usuario(id_estado, codigo, descripcion) values(nextval('tus08_estado_usuario_seq'), 'Cuenta Premium', 'El usuario ha alcanzado un nivel premium dentro de la app, por lo que tendrá nuevas funcionalidades');
insert into tus08_estado_usuario(id_estado, codigo, descripcion) values(nextval('tus08_estado_usuario_seq'), 'Bloqueado', 'El usuario ha sido bloqueado por lo que no podrá acceder a las funcionalidades de la app');

-- Usuarios
insert into tus01_usuario(id_usuario, nombre, apellido_paterno, apellido_materno, genero, tipo_usuario, id_estado, puerto)
values(nextval('tus01_usuario_seq'), 'Juan Daniel', 'Castillo', 'Reyes', true, 1,2,8080);
insert into tus01_usuario(id_usuario, nombre, apellido_paterno, apellido_materno, genero, tipo_usuario, id_estado, puerto)
values(nextval('tus01_usuario_seq'), 'Elioth', 'Monroy', 'Martos', true, 2,3,8080);
insert into tus01_usuario(id_usuario, nombre, apellido_paterno, apellido_materno, genero, tipo_usuario, id_estado, puerto)
values(nextval('tus01_usuario_seq'), 'Javier Said', 'Naranjo', 'Miranda', true, 1,2,8080);
insert into tus01_usuario(id_usuario, nombre, apellido_paterno, apellido_materno, genero, tipo_usuario, id_estado, puerto)
values(nextval('tus01_usuario_seq'), 'Alma', 'García', 'Romero', true, 2,2,8080);
insert into tus01_usuario(id_usuario, nombre, apellido_paterno, apellido_materno, genero, tipo_usuario, id_estado, puerto)
values(nextval('tus01_usuario_seq'), 'Karina', 'Resendiz', 'Miranda', true, 1,2,8080);

-- Login
insert into tus07_login(id_login,email,"password",id_usuario) values(nextval('tus07_login_seq'), 'castilloreyesjuan@gmail.com', 'alma123', 1);
insert into tus07_login(id_login,email,"password",id_usuario) values(nextval('tus07_login_seq'), 'eliothmonroy@gmail.com', 'laptop123', 2);
insert into tus07_login(id_login,email,"password",id_usuario) values(nextval('tus07_login_seq'), 'saidwassem@gmail.com', 'karina123', 3);
insert into tus07_login(id_login,email,"password",id_usuario) values(nextval('tus07_login_seq'), 'alma1im19@gmail.com', 'juan123', 4);
insert into tus07_login(id_login,email,"password",id_usuario) values(nextval('tus07_login_seq'), 'karinarmjuan@gmail.com', 'said123', 5);

-- Direccion

-- Automovil
insert into tus03_automovil(id_automovil, marca, nombre, modelo, capacidad_litros, id_usuario) 
values(nextval('tus03_automovil_seq'),'Volkswagen','Polo','Starline Std',40,1);
insert into tus03_automovil(id_automovil, marca, nombre, modelo, capacidad_litros, id_usuario) 
values(nextval('tus03_automovil_seq'),'Ford','Mustang','2019  Coupe', 50,1);
insert into tus03_automovil(id_automovil, marca, nombre, modelo, capacidad_litros, id_usuario) 
values(nextval('tus03_automovil_seq'),'Chevrolet','Camaro','2019 RS B AT',55,2);
insert into tus03_automovil(id_automovil, marca, nombre, modelo, capacidad_litros, id_usuario) 
values(nextval('tus03_automovil_seq'),'Audi','A5','2019',40,3);
insert into tus03_automovil(id_automovil, marca, nombre, modelo, capacidad_litros, id_usuario) 
values(nextval('tus03_automovil_seq'),'Volkswagen','Beetle','2018',40,4);

-- Sensor
insert into tme01_sensor(id_sensor,tx_device,id_estado,id_usuario) values(nextval('tme01_sensor_seq'), 'CMDX_001', 1, 1);
insert into tme01_sensor(id_sensor,tx_device,id_estado,id_usuario) values(nextval('tme01_sensor_seq'), 'CMDX_002', 2, 1);
insert into tme01_sensor(id_sensor,tx_device,id_estado,id_usuario) values(nextval('tme01_sensor_seq'), 'CMDX_003', 1, 2);
insert into tme01_sensor(id_sensor,tx_device,id_estado,id_usuario) values(nextval('tme01_sensor_seq'), 'CMDX_004', 3, 2);
insert into tme01_sensor(id_sensor,tx_device,id_estado,id_usuario) values(nextval('tme01_sensor_seq'), 'CMDX_005', 1, 3);
insert into tme01_sensor(id_sensor,tx_device,id_estado,id_usuario) values(nextval('tme01_sensor_seq'), 'CMDX_006', 1, 4);

-- Automovil Sensor
insert into tus09_automovil_sensor(id_sensor, id_automovil, activo) values(1, 1, 1);
insert into tus09_automovil_sensor(id_sensor, id_automovil, activo) values(2, 2, 1);
insert into tus09_automovil_sensor(id_sensor, id_automovil, activo) values(3, 3, 1);
insert into tus09_automovil_sensor(id_sensor, id_automovil, activo) values(5, 4, 1);
insert into tus09_automovil_sensor(id_sensor, id_automovil, activo) values(6, 5, 1);

-- Asociar Insignia a un Usuario
insert into tus01_usuario_tus05_insignia_usuario(insignia_id_insignia_usuario, usuario_id_usuario) values(1,1);
insert into tus01_usuario_tus05_insignia_usuario(insignia_id_insignia_usuario, usuario_id_usuario) values(2,1);
insert into tus01_usuario_tus05_insignia_usuario(insignia_id_insignia_usuario, usuario_id_usuario) values(3,1);
insert into tus01_usuario_tus05_insignia_usuario(insignia_id_insignia_usuario, usuario_id_usuario) values(4,2);
insert into tus01_usuario_tus05_insignia_usuario(insignia_id_insignia_usuario, usuario_id_usuario) values(1,2);
insert into tus01_usuario_tus05_insignia_usuario(insignia_id_insignia_usuario, usuario_id_usuario) values(1,3);
insert into tus01_usuario_tus05_insignia_usuario(insignia_id_insignia_usuario, usuario_id_usuario) values(4,4);

-- En este punto se cargan las Gasolineras desde un XML con: http://localhost:9000/gasolineras/xml

--  Asociar Insignias Gasolinera
insert into tcl03_insignia_gasolinera_tcl01_gasolinera(insignia_id_insignia_gasolinera, gasolinera_id_gasolinera, numero_insignias) values(1,1,35);
insert into tcl03_insignia_gasolinera_tcl01_gasolinera(insignia_id_insignia_gasolinera, gasolinera_id_gasolinera, numero_insignias) values(2,1,56);
insert into tcl03_insignia_gasolinera_tcl01_gasolinera(insignia_id_insignia_gasolinera, gasolinera_id_gasolinera, numero_insignias) values(3,100,44);
insert into tcl03_insignia_gasolinera_tcl01_gasolinera(insignia_id_insignia_gasolinera, gasolinera_id_gasolinera, numero_insignias) values(4,2,90);
insert into tcl03_insignia_gasolinera_tcl01_gasolinera(insignia_id_insignia_gasolinera, gasolinera_id_gasolinera, numero_insignias) values(1,300,129);
insert into tcl03_insignia_gasolinera_tcl01_gasolinera(insignia_id_insignia_gasolinera, gasolinera_id_gasolinera, numero_insignias) values(2,45,340);
insert into tcl03_insignia_gasolinera_tcl01_gasolinera(insignia_id_insignia_gasolinera, gasolinera_id_gasolinera, numero_insignias) values(3,89,75);
insert into tcl03_insignia_gasolinera_tcl01_gasolinera(insignia_id_insignia_gasolinera, gasolinera_id_gasolinera, numero_insignias) values(4,33,13);
insert into tcl03_insignia_gasolinera_tcl01_gasolinera(insignia_id_insignia_gasolinera, gasolinera_id_gasolinera, numero_insignias) values(1,56,7);
insert into tcl03_insignia_gasolinera_tcl01_gasolinera(insignia_id_insignia_gasolinera, gasolinera_id_gasolinera, numero_insignias) values(2,1,56)

-- Mediciones
insert into public.tme02_medicion
(id_medicion, bomba, id_automovil, id_estado, id_gasolinera, id_sensor, litros_ingresados, litros_solicitados, precio_solicitado, precio_total, tm_fin)
values(nextval('tme02_medicion_seq'),1,1,1,1,1,20,21,200,220,'2019-05-07 19:10:25-07');

INSERT INTO public.tme02_medicion
(id_medicion, litros_ingresados, litros_solicitados, precio_total, precio_solicitado, id_automovil, id_sensor, id_estado, id_gasolinera)
VALUES(nextval('tme02_medicion_seq'),  '2019-05-07 19:16:25-07', 20, 23, 260.65, 280.00, 2, 1, 1, 10983);

INSERT INTO public.tme02_medicion
(id_medicion, tm_fin, tm_inicio, litros_ingresados, litros_solicitados, precio_total, precio_solicitado, id_automovil, id_sensor, id_estado, id_gasolinera)
VALUES(nextval('tme02_medicion_seq'),  '2019-05-07 19:22:25-07', 25, 26, 290.65, 295.60, 2, 2, 1, 10983);

-- Deletes
delete from tcl02_ubicacion;
delete from tcl01_gasolinera;
delete from tcl03_insignia_gasolinera;
delete from tme01_sensor;
delete from tme02_medicion;
delete from tme03_estado_sensor;
delete from tme04_estado_medicion;
delete from tus01_usuario;
delete from tus01_usuario_tcl01_gasolinera;
delete from tus02_direccion_usuario;
delete from tus03_automovil;
delete from tus04_reporte;
delete from tus05_insignia_usuario;
delete from tus06_estado_reporte;
delete from tus07_login;
delete from tus08_estado_usuario;
delete from tus09_automovil_sensor;
delete from tus10_trama;

-- Drops
drop table tcl02_ubicacion;
drop table tcl01_gasolinera;
drop table tcl03_insignia_gasolinera;
drop table tme01_sensor;
drop table tme02_medicion;
drop table tme03_estado_sensor;
drop table tme04_estado_medicion;
drop table tus01_usuario;
drop table tus01_usuario_tcl01_gasolinera;
drop table tus02_direccion_usuario;
drop table tus03_automovil;
drop table tus04_reporte;
drop table tus05_insignia_usuario;
drop table tus06_estado_reporte;
drop table tus07_login;
drop table tus08_estado_usuario;
drop table tus09_automovil_sensor;
drop table tus10_trama;


-- Secuencias y permisos sobre las secuencias
select c.relname from pg_class c where c.relkind = 'S';
grant all on all sequences in schema public to postgres;

-- Schema y usuario
select schema_name from information_schema.schemata;
select current_user;


--Selects http://localhost:8090/sensores/automovil/2
select count(id_automovil) from tus03_automovil where id_usuario = 1 and nombre = 'Coche Hijo';
select * from tus03_automovil where id_usuario = 15;

select * from tme01_sensor as sensor, tus09_automovil_sensor as axs, tus03_automovil as aut
where sensor.id_sensor = axs.id_sensor and axs.id_automovil = aut.id_automovil and aut.id_automovil = 2; 

select * from tme02_medicion as med where med.id_sensor = 1 and med.id_automovil = 2;

select gasolinera.* from tcl01_gasolinera as gasolinera, tcl02_ubicacion as ubicacion 
where gasolinera.id_gasolinera  = ubicacion.id_gasolinera  
and CAST(ubicacion.latitud as TEXT) like '19.642%'
and CAST(ubicacion.longitud as TEXT) like '-99.110%';

select CAST(longitud as TEXT) from tcl02_ubicacion where id_ubicacion = 8214 or id_ubicacion = 12278;

delete from tcl02_ubicacion where id_gasolinera > 1;
delete from tcl01_gasolinera where id_gasolinera > 1;
alter sequence tcl01_gasolinera_seq restart with 2;
alter sequence tcl02_ubicacion_seq restart with 2;

select count(distinct id_gasolinera) from tcl01_gasolinera ;
select count(distinct latitud) from tcl02_ubicacion;
select count(id_medicion) from tme02_medicion where id_gasolinera = 10983;
select * from tcl01_gasolinera where id_gasolinera = 2;
select count(*) from tus09_automovil_sensor as axs, tme01_sensor as sensor
where axs.id_sensor = sensor.id_sensor and sensor.id_estado = 2 and axs.activo = 1 and sensor.id_usuario = 1;
select count(id_sensor) from tme01_sensor where id_usuario = 1;
select count(id_gasolinera) from tcl01_gasolinera;
select count(id_medicion) from tme02_medicion;

select id_sensor from tme01_sensor where id_estado = 1 and id_usuario = 1;
		
select axs.id_sensor from tus09_automovil_sensor as axs, tme01_sensor as sensor 
where axs.id_sensor = sensor.id_sensor and sensor.id_estado = 2 and axs.activo = 1 and sensor.id_usuario = 1;

select auto.* from tus03_automovil as auto, tus09_automovil_sensor as axs 
where auto.id_automovil = axs.id_automovil and axs.id_sensor = 1;


select * from tcl01_gasolinera where id_gasolinera = 1;

select * from tme02_medicion where id_gasolinera = 787;

ALTER USER postgres WITH PASSWORD 'postgres';

select insignia.* from tcl03_insignia_gasolinera as insignia, tcl03_insignia_gasolinera_tcl01_gasolinera as ixg 
where insignia.id_insignia_gasolinera = ixg.insignia_id_insignia_gasolinera and 
ixg.gasolinera_id_gasolinera = 1;






