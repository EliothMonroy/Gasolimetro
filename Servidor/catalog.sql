--- tus08_estado_usuario
DELETE FROM public.tus08_estado_usuario;
ALTER SEQUENCE tus08_estado_usuario_seq RESTART WITH 1; 

INSERT INTO public.tus08_estado_usuario(id_estado, codigo, descripcion)VALUES(nextval('tus08_estado_usuario_seq'), 1, 'Usuario registrado');
INSERT INTO public.tus08_estado_usuario(id_estado, codigo, descripcion)VALUES(nextval('tus08_estado_usuario_seq'),2, 'Usuario verificado');
INSERT INTO public.tus08_estado_usuario(id_estado, codigo, descripcion)VALUES(nextval('tus08_estado_usuario_seq'),3, 'Usuario destacado');

 --- tme03_estado_sensor
DELETE FROM public.tme03_estado_sensor; 
ALTER SEQUENCE tme03_estado_sensor_seq RESTART WITH 1; 

INSERT INTO public.tme03_estado_sensor (id_estado, codigo, descripcion) VALUES(nextval('tme03_estado_sensor_seq'), 1, 'Sensor envíado');
INSERT INTO public.tme03_estado_sensor (id_estado, codigo, descripcion) VALUES(nextval('tme03_estado_sensor_seq'), 2, 'Sensor activado');
INSERT INTO public.tme03_estado_sensor (id_estado, codigo, descripcion) VALUES(nextval('tme03_estado_sensor_seq'), 3, 'Sensor reportado');

--- tme04_estado_medicion
DELETE FROM public.tme04_estado_medicion;
ALTER SEQUENCE tme04_estado_medicion_seq RESTART WITH 1; 

INSERT INTO public.tme04_estado_medicion (id_estado, codigo, descripcion) VALUES(nextval('tme04_estado_medicion_seq'), 1, 'Medición recibida');
INSERT INTO public.tme04_estado_medicion (id_estado, codigo, descripcion) VALUES(nextval('tme04_estado_medicion_seq'), 2, 'Medición usada por el algoritmo de clasificación');

--- tus06_estado_reporte
DELETE FROM public.tus06_estado_reporte;
ALTER SEQUENCE tus06_estado_reporte_seq RESTART WITH 1;

INSERT INTO public.tus06_estado_reporte (id_estado, codigo, descripcion) VALUES(nextval('tus06_estado_reporte_seq'), 1, 'Creado');
INSERT INTO public.tus06_estado_reporte (id_estado, codigo, descripcion) VALUES(nextval('tus06_estado_reporte_seq'), 2, 'En atención');
INSERT INTO public.tus06_estado_reporte (id_estado, codigo, descripcion) VALUES(nextval('tus06_estado_reporte_seq'), 3, 'Liberado');
