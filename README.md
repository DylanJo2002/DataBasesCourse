# DataBasesCourse
Proyectos y trabajos del curso de bases de datos I, en la Universidad del Valle 

## 1. Entrega CRUD agenda - Conectividad Java, PostgreSQL
### Tecnologías usadas 
1. Lenguaje: Java 
2. Vistas: JavaFX con Scene Builder
3. Persistencia: JPA framework
4. Base de datos: PostgreSQL
5. IDE: NetBeans

### Getting Started 

El archivo persistence.xml en Agenda/src/META-INF se encuentra configurado de forma básica para la conectividad con PostgreSQL. Usted debe configurar únicamente la url,
el usuario y la contraseña.

      <property name="javax.persistence.jdbc.url" value="jdbc:postgresql://localhost:<puerto>/agenda"/>
      <property name="javax.persistence.jdbc.user" value=<usuario>/>
      <property name="javax.persistence.jdbc.driver" value="org.postgresql.Driver"/>
      <property name="javax.persistence.jdbc.password" value=<password>/>

La clase principal es Agenda y se encuentra en Agenda/src/Agenda.

### BASE DE DATOS: ESTRUCTURA

El nombre de la base de datos es <agenda>. El nombre de la tabla de contactos es <contacto>. Asegúrese que usted cuente con esta estructura.
![GitHub Logo](/Imagenes/Agenda/Estructura tabla contacto.png)
Format: ![Alt Text](url)
