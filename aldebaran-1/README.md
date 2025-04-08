# Información del Proyecto Aldebaran

## 1. Arquitectura

El proyecto parece seguir una arquitectura en capas. La presencia de `WebMvcConfig`, servicios (como `GamesService`) y entidades sugiere una estructura con capas distintas para la presentación (web), la lógica de negocio (servicios) y el acceso a datos (entidades).

## 2. Versión de Java

El proyecto utiliza Java 11 y Springboot 2, según se especifica en el archivo `pom.xml`.

## 3. Base de Datos

El proyecto utiliza MySQL. La URL de conexión en `application.properties` (`spring.datasource.url=jdbc:mysql://loteria.mongibello.tech:3306/aldebaran?zeroDateTimeBehavior=CONVERT_TO_NULL&useSSL=false&allowPublicKeyRetrieval=true&useCompression=true`) lo confirma. La versión del conector MySQL es 8.0.33, según el `pom.xml`.

## 4. Servicios Externos

El proyecto depende de varios servicios externos, como se indica en las URLs en `application.properties`:

*   Core Frontend: `http://loteria.mongibello.tech:6001/frontend/`
*   Core Backend: `http://loteria.mongibello.tech:6001/backend/`
*   Payments Frontend: `http://loteria.mongibello.tech:6004/frontend/`
*   Games Frontend: `http://loteria.mongibello.tech:6002/frontend/`
*   Games Backend: `http://loteria.mongibello.tech:6002/backend/`
*   CMS: `http://loteria.mongibello.tech:6005/cms/`
*   Reports Frontend: `http://loteria.mongibello.tech:6006/frontend/`
*   Reports Backend: `http://loteria.mongibello.tech:6006/backend/`
*   Sportsbook JS: `https://sb2wsdk-altenar2-stage.biahosted.com/altenarWSDK.js`
*   LIW JS: `https://services.testnisite.com:8103/RNGWidgetContent/magicwin/?partner=colombiabet593`
*   MTPU URL: `https://loteria.mongibello.tech/in/` y `https://loteria.mongibello.tech/launcher/service/launcher/alboran`
*   GeoLite2 para geolocalización: `https://download.maxmind.com/app/geoip_download?edition_id=GeoLite2-Country&license_key=\${geolite.password}&suffix=tar.gz`
*   Google reCAPTCHA: `https://www.google.com/recaptcha/api.js?render=6Lcw1XAoAAAAAGjf57FMa0WroxgMMgBK3DwUZbWA`

## 5. Seguridad (Autenticación/Autorización)

*   El proyecto utiliza interceptores para la verificación de roles, como se ve en `WebMvcConfig`. El `RoleCheckInterceptor` se agrega al registro de interceptores, lo que sugiere que los roles se utilizan para la autorización.
*   El código utiliza un `cryptoService` para generar hashes, que se incluyen en las solicitudes a los servicios backend. Esto probablemente proporciona algún nivel de autenticación o verificación de integridad. Las propiedades `secret.shared.*` en `application.properties` se utilizan como secretos para generar estos hashes.

## Puntos Adicionales Relevantes

*   **Caching:** La anotación `@Cacheable` en `GamesService` indica que se utiliza el mecanismo de almacenamiento en caché de Spring para almacenar en caché los resultados de los métodos `topGamesRounds`, `topGamesWin` y `categoriesOrder`.
*   **Comunicación HTTP:** El proyecto utiliza un objeto `http` (probablemente un cliente HTTP personalizado) para realizar solicitudes a servicios externos.
*   **Configuración:** El archivo `application.properties` contiene numerosos parámetros de configuración, incluidas las URL para servicios externos, claves de API y credenciales de la base de datos.
*   **Feature Flags:** Las propiedades `client.gsNewLauncher` y `client.gsNewLauncher1` sugieren el uso de *feature flags* para habilitar o deshabilitar ciertos lanzadores de juegos.
*   **Configuración de Correo:** El proyecto tiene configuraciones de correo electrónico, probablemente para enviar correos electrónicos (por ejemplo, para formularios de contacto o notificaciones de excepciones).
*   **Geolocalización:** El proyecto utiliza GeoLite2 para fines de geolocalización, que podría utilizarse para adaptar el contenido o aplicar restricciones regionales.
*   **Compresión:** El proyecto habilita la compresión para las respuestas HTTP.
*   **HTTP/2:** El proyecto habilita HTTP/2.

## Dependencias (pom.xml)

*   `spring-boot-starter-web`: Para construir aplicaciones web con Spring MVC.
*   `spring-boot-starter-thymeleaf`: Para usar Thymeleaf como el motor de plantillas.
*   `spring-boot-starter-data-jpa`: Para usar Spring Data JPA para interactuar con la base de datos.
*   `spring-boot-starter-cache`: Para habilitar el soporte de caché de Spring.
*   `mysql-connector-java`: Driver MySQL Connector/J para conectar a bases de datos MySQL.
*   `spring-boot-starter-test`: Para escribir pruebas unitarias y de integración.
*   `spring-boot-devtools`: Proporciona características en tiempo de desarrollo, como reinicios automáticos de la aplicación y recarga en vivo.
*   `lombok`: Librería Lombok para reducir el código repetitivo.
*   `spring-boot-starter-mail`: Para enviar correos electrónicos.
*   `geoip2`: Librería MaxMind GeoIP2 para geolocalización.
*   `spring-boot-starter-actuator`: Para monitorear y administrar la aplicación.
*   `httpclient`: Librería Apache HttpClient para realizar solicitudes HTTP.
*   `commons-io`: Librería Apache Commons IO para funciones de utilidad.
*   `json`: Librería JSON para manejar datos JSON.