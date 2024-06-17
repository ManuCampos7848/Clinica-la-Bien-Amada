Este proyecto cuenta con algunas funcionalidades principales de las clínicas y hospitales tales como:
-	Crear una cuenta.
-	Agendar citas
-	Ver los detalles de una cita agendada - Paciente
-	Ver los detalles de una atención (una cita realizada) - Paciente
-	Buscar citas y atenciones
-	Modificar datos personales
-	Eliminar cuenta
-	Crear PQRS
-	Chatear con el administrador para dar solución a las PQRS
-	Atender citas
-	Ver los detalles de una cita agendada - Medico
-	Ver los detalles de una atención (una cita realizada) – Medico
-	Agendar días libres
-	Etc.

Algunas características de este proyecto son:
-	Se realizó con el framework Angular conocido para creación de aplicaciones web de una sola página (SPA, por sus siglas en inglés)
-	Las interfaces graficas se realizaron con la ayuda de la herramienta de desarrollo Bootstrap
-	Se utilizaron Guards como middleware que se ejecutan antes de cargar una ruta específica en la aplicación. La principal función es determinar si el usuario tiene permisos suficientes para acceder a esa ruta en particular 
-	Se utilizaron Interceptors principalmente para inspeccionar y modificar las peticiones HTTP que la aplicación envía al servidor, así como las respuestas que recibe; permite realizar diversas operaciones como modificar encabezados HTTP, manipular el cuerpo de la solicitud, establecer tokens de autenticación o autorización, y manejar errores.
- Para la parte del backend se uso Servlet Filter la cual su función es interceptar y procesar solicitudes y respuestas HTTP antes de que lleguen a un servlet o después de que se generen. Por ejemplo: Restringir métodos según el rol del usuario, como permitir solo a pacientes crear citas y solo a administradores cerrar PQRS.
-	Se usaron tokens garantiza la validez y seguridad de los datos con la ayuda de JSON Web Tokens (JWT). 

Dentro la carpeta “Imagenes de su Funcionamiento” se encuentran algunas imágenes del proyecto en ejecución mostrando algunas funcionalidades tanto del paciente como del médico.

___________________________ English _____________________

This project has some main functionalities of the clinics and hospitals such as:

-	Create an account.
-	Schedule appointments
- View details of a scheduled appointment - Patient
- View the details of a care (an appointment made) - Patient
- Search for appointments and attention
- Modify personal data
-	Delete account
- Create PQRS
- Chat with the administrator to resolve PQRS
- Attend appointments
- View the details of a scheduled appointment - Medical
- See the details of a care (an appointment made) – Doctor
- Schedule free days
-	Etc.

Some features of this project are:

- It was made with the Angular framework known for creating single page web applications (SPA)
- The graphical interfaces were made with the help of the Bootstrap development tool
- Guards were used as middleware that are executed before loading a specific route in the application. The main function is to determine if the user has sufficient permissions to access that particular route
- Interceptors were mainly used to inspect and modify the HTTP requests that the application sends to the server, as well as the responses it receives; allows you to perform various operations such as modifying HTTP headers, manipulating the request body, setting authentication or authorization tokens, and handling errors.
- For the backend part, Servlet Filter was used, whose function is to intercept and process HTTP requests and responses before they reach a servlet or after they are generated. For example: Restrict methods based on the user's role, such as allowing only patients to create appointments and only allowing administrators to close PQRS.
- Tokens were used to ensure the validity and security of the data with the help of JSON Web Tokens (JWT).

Within the “Images of its Operation” folder there are some images of the project in progress showing some functionalities of both the patient and the doctor.
