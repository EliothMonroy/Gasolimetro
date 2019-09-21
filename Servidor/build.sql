-- Drop table

-- DROP TABLE public.tus08_estado_usuario

CREATE TABLE public.tus08_estado_usuario (
	id_estado int8 NOT NULL,
	codigo int4 NULL,
	descripcion varchar(255) NULL,
	CONSTRAINT tus08_estado_usuario_pkey PRIMARY KEY (id_estado)
);

-- Drop table

-- DROP TABLE public.tme03_estado_sensor

CREATE TABLE public.tme03_estado_sensor (
	id_estado int8 NOT NULL,
	codigo int4 NULL,
	descripcion varchar(255) NULL,
	CONSTRAINT tme03_estado_sensor_pkey PRIMARY KEY (id_estado)
);
-- Drop table

-- DROP TABLE public.tme04_estado_medicion

CREATE TABLE public.tme04_estado_medicion (
	id_estado int8 NOT NULL,
	codigo int4 NULL,
	descripcion varchar(255) NULL,
	CONSTRAINT tme04_estado_medicion_pkey PRIMARY KEY (id_estado)
);

-- Drop table

-- DROP TABLE public.tus06_estado_reporte

CREATE TABLE public.tus06_estado_reporte (
	id_estado int8 NOT NULL,
	codigo int4 NULL,
	descripcion varchar(255) NULL,
	CONSTRAINT tus06_estado_reporte_pkey PRIMARY KEY (id_estado)
);
