# Práctica: Desarrollo de Microservicio con Spring WebFlux y Arquitectura Hexagonal

La prueba consta de un microservicio que accede a una Base de Datos MongoDB usando las características de una arquitectura hexagonal.

### Requisitos

1. Java v-17
2. Maven v-3.x.x
3. MongoDB v-7.0 

### Pasos para instalar

**1. Clonar la aplicación**

```bash
git clone https://github.com/MR-MICHIFUS/ms-manage-client.git
```

**2. Compilar y empaquetar la aplicación**

```bash
mvn clean install
```

**3. Ejecutar la aplicación**

```bash
mvn spring-boot:run
```

La aplicación se ejecutará en <http://localhost:8080>.

### Explore Rest APIs

Se puede observar los endpoints de la aplicación en la siguiente ruta:
<http://localhost:8080/doc/webjars/swagger-ui/index.html>