# OT230-server
ONG-java


> ### **Mentor:**
|       **Nombre**        |              **GitHub**               |
|  :-------------------   | :-----------------------------------  |
| Francisco Mastucci      | <https://github.com/franmastucci>     |

> ### Participantes
|       **Nombre**        |              **GitHub**               |
|  :---                   | :---                                  |
| Cristian Aguirre        | <https://github.com/Cristianaaguirre> |
| Franco Tonanes          | <https://github.com/FrancoTonanes>    |
| Carlos Obando Casiano   | <https://github.com/caobandoc>        |
| Facundo D'alessio       | <https://github.com/FacuDalessio>     |
| Mariano Martinez Rinaudo| <https://github.com/MartinezRinaudo>  |
| Adrian Olij             | <https://github.com/AdrianOlij>       |

----------------------------

### Herramientas utilizadas
- 👉 Java y Spring Boot
- 👉 Librería Spring Security
- 👉 Tecnología REST
- 👉 Servicios de Amazon AWS.
- 👉 Servicios de SendGrid.
- 👉 Paginación.
- 👉 Uso Data Transfer Object.
- 👉 Testing (JUnit y Mockito).
- 👉 Documentación Swagger.

---------------------------

### Para ejecutar la API 👇🏻
* Descargar el proyecto o clonarlo.
* Abra la consola y vaya a la carpeta del proyecto.
* Ejecute estos comandos: ``` mvn clean install ```  y luego ``` mvn spring-boot:run ``` .
* Abra su navegador e ingrese a http://localhost:8080/swagger-ui/index.html#/ .
* Puede ver la documentación de la API e interactuar con los endpoints.

---------------------------

> ### **Usuarios precargados en la API**
> ##### *Usuarios Administradores*

|   **Mail**      |    **Nombre**     |  **Password** |
| :---:           | :---:             | :---:         |
|`admin1@test.com`| Patricia	Brett   | 12345678      |
|`admin2@test.com`| Ronald	Kathleen  | 12345678      |
|`admin3@test.com`| Carlos	Brandi    | 12345678      |
|`admin4@test.com`| Nathaniel	Craig   | 12345678      |
|`admin5@test.com`| Caitlin	Katrina   | 12345678      |
|`admin6@test.com`| Juan	Peralta     | 12345678      |
|`admin7@test.com`| Thanos Smith      | 12345678      |
|`admin8@test.com`| Will	Doll        | 12345678      |

> ##### *Usuarios Standard*

|    **Mail**     |    **Nombre**     |  **Password** |
| :---:           | :---:             | :---:         |
| `user1@test.com`| Patricia	Brett   | 12345678      |
| `user2@test.com`| Ronald	Kathleen  | 12345678      |
| `user3@test.com`| Carlos	Brandi    | 12345678      |
| `user4@test.com`| Nathaniel	Craig   | 12345678      |
| `user5@test.com`| Caitlin	Katrina   | 12345678      |
| `user6@test.com`| Juan	Peralta     | 12345678      |
| `user7@test.com`| Thanos	Smith     | 12345678      |
| `user8@test.com`| Will	Doll        | 12345678      |

---------------------------

## Autenticación de Usuarios
Después de registrarse e iniciar sesión, obtiene un token, requerido para acceder a los demás paths. Una vez que pasan 10 horas el token queda desactualizado o vencido, lo que obliga a que el usuario vuelva a generarlo mediante un nuevo login.

### POST (registro)
	http://localhost:8080/auth/register

Ejemplo:

    {
        "firstName": "Facundo",
        "lastName": "Dalessio",
        "email": "facu@mail.com",
        "password": "password123",
        "photo": "url.com/facu.jpg"
    }

### POST (login)
	http://localhost:8080/auth/login

Ejemplo:

    {   
        "email": "facu@mail.com",
        "password": "password123"     
    }


Para acceder a cualquier path que requiera rol ("ROLE_USER" o "ROLE_ADMIN"), una vez logeado, se debe ingresar el token.

---------------------------

##Categories

### POST (creación de entidad)
	http://localhost:8080/categories

Ejemplo:

    {
        "name": "categoriaName",       
        "description": "descipción de la categoria",
        "image": "url.com/image.jpg"
    }

### PUT (actualización de entidad)

	http://localhost:8080/categories/{id}

Ejemplo:

Path:

    http://localhost:8080/categories/1


Body:

    {   
        "name": "cambioDeNombreCategoria",       
        "description": "descipción de la categoria",
        "image": "url.com/image.jpg"
    }

### DELETE (eliminación de entidad)

	http://localhost:8080/categories/{id}

Ejemplo:

    http://localhost:8080/categories/1

### GET (consulta de una entidad por id)

	http://localhost:8080/categories/{id}

Ejemplo:

    http://localhost:8080/categories/1

### GET (listado de entidades paginadas)

	http://localhost:8080/categories/?page={param}

Ejemplo:
  http://localhost:8080/categories/1
---------------------------

## 4. Testimonial

### POST (creación de entidad)
	http://localhost:8080/testimonials

Ejemplo:

    {
        "name": "Facundo Dalessio",
        "image": "url.com/image.jpg",
        "content": "I love this ONG"
    }

### PUT (actualización de entidad)

	http://localhost:8080/testimonials/{id}

Ejemplo:

Path:

    http://localhost:8080/testimonials/1


Body:

    {   
        "name": "Facundo Dalessio",
        "image": "url.com/anotherImage.jpg",
        "content": "I love this ONG"
    }

### DELETE (eliminación de entidad)

	http://localhost:8080/testimonials/{id}

Ejemplo:

    http://localhost:8080/testimonials/1

### GET (lista de entidades paginadas)

	http://localhost:8080/testimonials?page={param}

Ejemplo:

    http://localhost:8080/testimonials/1

---------------------------

## 5. Slide

### POST (creación de entidad)
	http://localhost:8080/slides

Ejemplo:

    {
        "imageUrl": "url.com/image.jpg",
        "text": "Welcome to the web side!",
        "sort": 1,
        "organizationId": 1
    }

### PUT (actualización de entidad)

	http://localhost:8080/slides/{id}

Ejemplo:

Path:

    http://localhost:8080/slides/1

Body:

    {   
      "imageUrl": "url.com/anotherImage.jpg",
      "text": "GoodBye to the web side!",
      "order": 3,
      "oragizationId": 1
    }

### DELETE (eliminación de entidad)

	http://localhost:8080/slides/{id}

Ejemplo:

    http://localhost:8080/slides/1

### GET (consulta de una entidad por id)

	http://localhost:8080/slides/{id}

Ejemplo:

    http://localhost:8080/slides/1

### GET (lista de entidades paginadas)

	http://localhost:8080/slides?page?{param}

Ejemplo:

    http://localhost:8080/slides/1
    
### GET (lista de slides por organizatoin)

	http://localhost:8080/slides/organization/{id}

Ejemplo:
     
	 http://localhost:8080/slides/oraganization/1
   
 ---------------------------
 
    ## 6. Organization

### POST (creación de entidad)
	http://localhost:8080/organization

Ejemplo:

    {
        "name": "Somos más",
        "image": "url.com/image.jpg",
        "address": "av. italia n° 899",
        "phone": "3795552288",
        "email": "someemail@gmail.com",
        "welcomeText": "hello we are Somosmas",
        "aboutUsText": "we are Somosmas fundation",
        "instagramUrl": "InstaSomosmas",
        "facebookUrl": "faceSomosmas",
        "linkedInUrl": "linkdeSomosmas"
    }

### PUT (actualización de entidad)

	http://localhost:8080/organization/public/{id}

Ejemplo:

Path:

    http://localhost:8080/organization/public/1


Body:

    {
        "name": "Somos menos",
        "image": "url.com/image.jpg",
        "address": "av. italia n° 999",
        "phone": "3795552288",
        "email": "somosMasEmail@gmail.com",
        "instagramUrl": "InstaSomosmas",
        "facebookUrl": "faceSomosmas",
        "linkedInUrl": "linkdeSomosmas",
        "welcomeText": "hello we are Somosmas",
        "aboutUsText": "we are Somosmas fundation"
    }



### GET (listar organization)

	http://localhost:8080/organization/public

---------------------------
    
 ## 7. News

### POST (creación de entidad)
	http://localhost:8080/news

Ejemplo:

    {
        "name": "news name",
        "content": "news content",
        "image": "url.com/image.jpg",
        "categoryId": 1
    }

### PUT (actualización de entidad)

	http://localhost:8080/news/{id}

Ejemplo:

Path:

    http://localhost:8080/news/1

Body:

    {
        "name": "news name change",
        "content": "news content change",
        "image": "url.com/image.jpg",
        "categoryId": 1
    }

### DELETE (eliminación de entidad)

	http://localhost:8080/news/{id}

Ejemplo:

    http://localhost:8080/news/1

### GET (consulta de una entidad por id)

	http://localhost:8080/news/{id}

Ejemplo:

    http://localhost:8080/news/1

### GET (lista de entidades paginadas)

	http://localhost:8080/news?page={param}

Ejemplo:

    http://localhost:8080/news/1
### GET (buscar coments por news id)

    http://localhost:8080/{id}/coments
    
 Ejemplo:
 
    http://localhost:8080/1/coments
---------------------------

## 8. Member

### POST (creación de entidad)
	http://localhost:8080/members

Ejemplo:

    {
        "name": "Facundo Dalessio",
        "facebook": "facebookFacu",
        "instagram": "instagramFacu",
        "linkedIn": "linkedinFacu",
        "image": "ImageUrlFacu",
        "description": "Hello my name is Facundo"
    }

### PUT (actualización de entidad)

	http://localhost:8080/members/{id}

Ejemplo:

Path:

    http://localhost:8080/members/1

Body:

    {
        "name": "Facundo Dalessio",
        "facebook": "facebookFacu",
        "instagram": "instagramFacu",
        "linkedIn": "linkedinFacu",
        "image": "ImageUrlFacu",
        "description": "Hello my name is Facundo Dalessio"
    }

### DELETE (eliminación de entidad)

	http://localhost:8080/members/{id}

Ejemplo:

    http://localhost:8080/members/1

### GET (lista de entidades paginadas)

	http://localhost:8080/members/get-all?page={param}

Ejemplo:

    http://localhost:8080/members/get-all/1

---------------------------

## 9. Comment

### POST (creación de entidad)
	http://localhost:8080/comments

Ejemplo:

    {
        "body": "i love the ONG!",
        "newsId": 3,
        "userId": 7
    }

### PUT (actualización de entidad)

	http://localhost:8080/comments/{id}


Ejemplo:

Path:

    http://localhost:8080/comments/1

Body:

    {
        "body": "i like the ONG!",
        "newsId": 3,
        "userId": 8
    }

### DELETE (eliminación de entidad)

	http://localhost:8080/comments/{id}

Ejemplo:

    http://localhost:8080/comments/1

### GET (list comments)

	http://localhost:8080/comments

Ejemplo:

    http://localhost:8080/comments

---------------------------

## 10. Activity

### POST (creación de entidad)
	http://localhost:8080/activities

Ejemplo:

    {
        "name": "new activity name",
        "content": "here is the content of the activity",
        "image": "url.com/image.jpg"
    }

### PUT (actualización de entidad)

	http://localhost:8080/activities/{id}

Ejemplo:

Path:

    http://localhost:8080/activities/1

Body:

    {
        "name": "name modifed",
        "content": "here is the content of the activity",
        "image": "url.com/image.jpg"
    }

------------------------------

## 11. Contact

### POST (creación de entidad)
	http://localhost:8080/contacts

Ejemplo:

    {
        "name": "Agustin",
        "email": "agustin@mail.com",
        "phone": "1145386540",
        "message": "this ONG is fantastic"
    }


### GET (lista de entidades)

	http://localhost:8080/contacts

Ejemplo:

    http://localhost:8080/contacts

---------------------------

## 12. User
### PATCH (Update user)

   http://localhost:8080/users/{id}
   
 Ejemplo:
 
 Path:
      http://localhost:8080/users/1
 Body:
    {
      "firstName": "Facundo",
      "lastName": "Dalessio",
      "password": "password123",
      "photo": "photo"
    }

### DELETE (eliminación de entidad)

	http://localhost:8080/users/{id}

Ejemplo:

    http://localhost:8080/users/1

### GET (lista de entidades)

	http://localhost:8080/users

Ejemplo:

    http://localhost:8080/users
 -----------------------------------------------------------------------
