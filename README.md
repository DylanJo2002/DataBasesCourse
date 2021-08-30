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

###BASE DE DATOS: ESTRUCTURA

El nombre de la base de datos es <agenda>. El nombre de la tabla de contactos es <contacto>. Asegúrese que usted cuente con esta estructura.
<p align="center">
  <img src="https://github.com/DylanJo2002/DataBasesCourse/blob/main/Imagenes/Agenda/Estructura%20tabla%20contacto.PNG" width="1000" title="hover text">    
</p>
El código SQL para crear la tabla es el siguiente:
<p align="center">
  <img src="https://github.com/DylanJo2002/DataBasesCourse/blob/main/Imagenes/Agenda/create%20table%20contacto.PNG" width="1000" title="hover text">    
</p>  
     
### VALIDACIONES DE DATOS: 
El software valida los datos incluso antes de insertarlos en la base de datos. Este le informa si usted ingresó un dato inválido. Estos son los rangos aceptados (Incluyen       los valores mínimos y máximos):
      
Variable     | Minimo        | Máximo
------------ | ------------- | -------------
Nombre | 1 carácter | 100 carácteres
Apellido | 1 carácter | 100 carácteres
Edad | 0 | 120
Género | No aplica | No aplica
Teléfono | 1 carácter | 10 carácteres
Email | 1 carácter | 100 carácteres
      
### POSIBLES ERRORES
Si existen errores en la conectividad con PostgreSQL, por favor revise si su servicio está levantado y que usted cuente con la estructura.
      
# Sobre mí
1. Nombre: Dilan Joel Bergaño Caicedo
2. Código: 1925763-2711
3. Email: dilan.bergano@correounivalle.edu.co
