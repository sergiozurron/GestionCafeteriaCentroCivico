# Proyecto de Modelado Software: Proyecto de Modelado Software: Centro cívico y cafetería

Este repositorio contiene el código fuente correspondiente al proyecto de Modelado de Software.

El objetivo del proyecto es desarrollar una aplicación Java de manejo de datos.

- [Proyecto de Modelado Software: Biblioteca FDI](#proyecto-de-modelado-software-biblioteca-fdi)
  - [Cómo aportar al repositorio](#cómo-aportar-al-repositorio)
    - [Descarga y setup](#descarga-y-setup)
    - [Workflow](#workflow)
  - [Requisitos de las entregas](#requisitos-de-las-entregas)
    - [Primera entrega](#primera-entrega)
    - [Segunda entrega](#segunda-entrega)
    - [Tercera entrega](#tercera-entrega)

## Cómo aportar al repositorio

En esta sección se describen las tecnologías que usa el proyecto, cómo realizar una copia local del repositorio para empezar a realizar cambios, y qué pasos se deben seguir para finalizar estos cambios.

### Descarga y setup

Para trabajar sobre el proyecto es necesario tener 3 tecnologías instaladas:

1. La primera de ellas es [**Git**](https://git-scm.com/), para el control de versiones e interacción con GitHub. Para comprobar que la tienes instalada ve a la terminal de tu ordenador (Windows, Mac o Linux) y teclea:

    ```bash
    git -v
    ```

    Si devuelve un error indicando que no existe la instrucción git, descarga e instala la última versión según [su página de descargas](https://git-scm.com/downloads). Al acabar, vuelve a introducir el comando, que ahora debería devolver el número de versión instalada.
    Si nunca antes has trabajado con git o GitHub te recomiendo aprender lo antes posible con cualquiera de los muchos tutoriales que existen, porque es esencial cada vez que quieras escribir algo para el proyecto.

2. Debemos trabajar con al menos la **versión 11 de Java**. Para comprobar qué versión tienes instalada ejecuta este comando en la terminal:

    ```bash
    java -version
    ```

    Mira qué número devuelve. A mí por ejemplo me devuelve: "openjdk version "21.0.8" 2025-07-15", es decir, la versión 21.0.8. La tuya debe ser 11.0.0 o más. Si fuese menor, ve a la [página de descargas de Java](https://www.java.com/es/download/) y actualiza la versión que tienes.

3. Por último es necesario tener [**Apache Maven**](https://maven.apache.org/index.html), nos proporciona una versión uniforme del proyecto y elimina la necesidad de descargar el .jar y configurar el proyecto en cada ordenador cada vez que queramos usar una nueva librería, además de métodos útiles para manejar los tests. Para comprobar qué versión tienes instalada ejecuta este comando en la terminal:

    ```bash
    mvn --v
    ```

    Si recibís un error indicando que no existe la instrucción "mvn", debes instalar mvn según las instrucciones de su página web. Después, vuelve a correr el comando, que ahora debería devolverte el número de instalación. Si nunca has usado Maven antes, te recomiendo leer [este artículo](https://www.digitalocean.com/community/tutorials/maven-commands-options-cheat-sheet) que describe para qué sirven los comandos principales.

Para disponer de una copia local del repositorio en tu ordenador, existen 2 métodos posibles:

1. En el botón en verde "code" de la página del repositorio, haz click a "descargar zip". Descomprime el zip.

2. En la terminal de tu ordenador escribe:

    ```bash
    git clone https://github.com/inestrivino/Biblioteca-FDI.git
    ```

Una vez tengas la carpeta descargada, abre una terminal en su localización, y ejecuta el siguiente comando para instalar las dependencias del proyecto (de momento JUnit para la ejecución de tests, y JaCoCo para analizar la covertura del código):

```bash
mvn install
```

Deberías recibir un mensaje indicando "Build Success". En ese momento el proyecto estará operativo y todas las dependencias necesarias instaladas. Siéntete libre de hacer `mvn clean` después. Si no entiendes qué son estos comandos, leéte [este artículo](https://www.digitalocean.com/community/tutorials/maven-commands-options-cheat-sheet).

### Workflow

El profesor quiere que cada entrega se encuentre en una rama diferente. Por ello podréis comprobar que actualmente existen 4 ramas:

1. Main
2. Entrega 1 (solo en el repositorio de modelo)
3. Entrega 2
4. Entrega 3

La rama main no debe ser tocada y solo contiene de manera útil este README explicativo sobre cómo trabajar con el proyecto.
Vamos a trabajar con la metodología [Feature Branch](https://git-scm.com/book/ms/v2/Git-Branching-Branching-Workflows). Es decir, cada vez que añadamos una nueva funcionalidad o refactorización al proyecto, crearemos una rama dedicada a ello. Además es recomendable realizar una revisión del código antes de mergear.

Digamos que decido crear por ejemplo la pantalla de inicio de nuestra aplicación:

1. Desde dentro de la carpeta del proyecto, me aseguro de estar en la rama correspondiente a la entrega del proyecto en la que estoy trabajando (primera, segunda o tercera).

2. Ejecuto los siguientes comandos para asegurarme de que mi versión local está al día:

```bash
git fetch
git pull
```

3. Creo una nueva rama para la pantalla de inicio de nuestra aplicación.Viajo a ella.

```bash
git checkout -b pantalla-inicio
```

4. Hago mis cambios de manera normal desde mi nueva rama.

5. Una vez termino de trabajar envío los cambios a GitHub para que se vean públicamente:

```bash
git push origin pantalla-inicio
```

6. Desde GitHub, en la página del repositorio, doy click a "Compare and Pull Request". Escribo un título descriptivo y una descripción de los cambios realizados si quiero. **Me aseguro de que la rama "base" o "target" es la de la entrega, no Main**.

7. Hago click en "Send Pull Request".

A través del Pull Request mis compañeros de equipo pueden revisar el código y pedirme modificaciones (si detectan algún problema) o mergear con la rama deseada, considerando esa funcionalidad como terminada e integrada completamente.
Recordad que es importante ir escribiendo tests JUnit por cada método que escribimos.

## Requisitos de las entregas

### Primera entrega

La primera entrega consiste solo en la Especificación de Requisitos Software.

### Segunda entrega

La segunda entrega es la primera versión de la aplicación.

Debe cumplir las siguientes específicaciones:

- 6 entidades
- 2 entidades con 2 subclases
- 1:N
- M:N sin atributos
- M:N con atributos
- Funciones CRUD
- Queries

Debe hacer uso de los siguientes patrones de diseño:

- Service to worker
- Transfer object
- Transfer object assembler
- Application service
- Data Access Object
- Alguna query

### Tercera entrega

La tercera entrega del proyecto es la versión final de nuestra aplicación.

Debe cumpliir las mismas especificacines que la segunda entrega, y además incluir una función polimórfica.

Debe añadir el uso de los siguientes patrones de diseño:

- Business object
- Domain store
