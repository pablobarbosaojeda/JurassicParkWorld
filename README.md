# Jurassic Park Management System

Este proyecto es un sistema de gestión del parque temático Jurassic Park, diseñado como una aplicación web que permite a la administración gestionar la población de dinosaurios y realizar el seguimiento de los visitantes en el parque.

# URL backend repository:https://github.com/pablobarbosaojeda/JurassicParkWorld.git
# URL frotend repository: https://github.com/pablobarbosaojeda/juurassic-frontend.git

## Funcionalidades del Proyecto

1. **Gestión de Dinosaurios**:
   - Añadir o eliminar dinosaurios en el parque.
   - Asignar dinosaurios a recintos específicos (paddocks).
   - Alimentar a los dinosaurios.
   - Transferir dinosaurios entre recintos, asegurando que solo se mezclen dinosaurios del mismo tipo (herbívoros o carnívoros).

2. **Gestión de Recintos**:
   - Crear, modificar o eliminar recintos en el parque.
   - Ver detalles sobre qué dinosaurios están en cada recinto.

3. **Seguimiento de Visitantes**:
   - Monitorear el número de visitantes en el parque en tiempo real.
   - Registrar ingresos acumulados a partir de las entradas.

4. **Estado del Parque**:
   - Abrir y cerrar el parque para visitantes.

## Tecnologías Utilizadas

- **Frontend**: ReactJS
- **Backend**: Java (Spring Boot)
- **Base de Datos**: PostgreSQL

## Requisitos Previos

Asegúrate de tener instalados los siguientes programas:

- Node.js (v14 o superior)
- Java JDK 17 o superior
- Maven
- PostgreSQL
- Docker (opcional para base de datos)

## Cómo Ejecutar el Proyecto

### 1. Configuración de la Base de Datos

* Inicia el servicio de PostgreSQL:

  sudo service postgresql start

* Crea la base de datos de Jurassic Park:

  sudo -u postgres -i
  createdb jurassicparkmanagement

### 2. Configuración del Backend

* Asegúrate de que el archivo `application.properties` del backend contiene la siguiente configuración, añadiendo la contraseña correspondiente al usuario `postgres` en tu máquina:

 spring.datasource.password=<tu_contraseña_postgres>

 
### 3. Ejecutar el Backend

* Desde la carpeta `JurassicParkWorld-master` del backend, inicia el servidor backend:
  
./mvnw spring-boot:run

### 4. Ejecutar el Frontend

* Desde la carpeta `juurassic-frontend-master/front_end` del frontend, instala las dependencias e inicia el servidor del frontend:

npm install
npm start

### 5. Verificar la Base de Datos

* (Opcional) Verifica desde la terminal que la base de datos está funcionando correctamente:

sudo -u postgres -i
psql jurassicparkmanagement

## Autores

- Pablo Barbosa
- Daniel Sousa
- Adrián Puyo
