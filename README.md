🚀 SpaceApps – Aplicación Android (M07 Endterm)

Aplicación Android desarrollada por Jovi Cucarella Montell como parte del ENDTERM del módulo M07 en La Salle.

La app conecta con la API oficial de SpaceX, muestra sus cohetes, permite filtrar, navegar por detalles, e incluso funciona offline gracias a Room.
Construida con Kotlin + Jetpack Compose y un diseño limpio, accesible y moderno.

✨ Características principales
🛰 Pantalla Splash animada

GIF animado del cohete en movimiento.

Transición automática tras 2 segundos al login.

🔐 Login validado

Validación de email y contraseña.

Usuario permitido:

admin@lasalle.es

admin1234


Snackbar de error si las credenciales fallan.

Opción: “He olvidado mis datos de acceso”.

🚀 Lista de Cohetes (API SpaceX)

Incluye:

Carga real desde la API pública de SpaceX.

Persistencia local con Room (modo offline real).

Estados UI totalmente controlados:

⏳ Loading (con indicador y mensaje)

✔ Success

⚠ Error (con botón Reintentar)

🔍 Empty (con icono y texto centrado)

Filtros:

Búsqueda por nombre

Mostrar solo cohetes activos

📄 Pantalla de Detalle del Cohete

Muestra:

Imagen grande del cohete

Nombre completo

Estado (activo/retirado)

País de origen

Etapas

Coste por lanzamiento

Porcentaje de éxito

Primera fecha de vuelo

Descripción completa

Botón Wikipedia si existe URL

📡 Funcionamiento Offline

Si no hay red → mensaje + botón Reintentar.

Si hay datos guardados → se muestran aunque no haya conexión.

La BD se actualiza al iniciar la app con conexión.

♿ Accesibilidad mejorada

Todos los elementos relevantes tienen contentDescription.

Mensajes de estados accesibles para lectores de pantalla.

Estilos y posiciones consistentes.

🛠 Tecnologías utilizadas
Tecnología	Uso
Kotlin	Lenguaje principal
Jetpack Compose	UI declarativa
Navigation Compose	Navegación
Retrofit + Gson	Consumo API SpaceX
Room	Base de datos local
Coroutines	Hilos y asincronía
Material 3	Estética moderna
Coil + GIF	Carga de imágenes y animaciones
StateFlow + MVVM	Gestión de estados
🧪 Pruebas UI (Compose UI Test)

Incluye dos pruebas automatizadas:

✔ Login válido navega a la lista

Verifica que introducir:

admin@lasalle.es

admin1234


→ Navega a la lista correctamente.

✔ Error + Reintentar

Simula un error de red:

Aparece mensaje de error

Al pulsar “Reintentar” → la lista se carga correctamente

Ambas pruebas → Passed en verde ✔✔

📦 Arquitectura aplicada

MVVM (Model - ViewModel - View)

Repository Pattern

StateFlow como fuente única de estado

Room como Single Source of Truth

Sealed classes para la UI

▶ Instalación y ejecución

Clona el repositorio:

git clone https://github.com/jovicumon/spaceapps-android.git


Ábrelo con Android Studio.

Ejecuta la app en un emulador o dispositivo físico.

🔐 Firma y release

Keystore creada con alias spaceapps_launch.

Configurada firma del build release desde keystore.properties.

.aab generado correctamente

La keystore NO está subida al repo (cumple requisitos de seguridad).

📱 Ficha de Google Play (simulada)

La ficha completa está documentada en:
👉 PLAY_STORE.md

🔗 Repositorio

Código fuente 100% público y actualizado:

👉 https://github.com/jovicumon/spaceapps-android

👨🏻‍🚀 Autor

Jovi Cucarella Montell
Desarrollador Android en formación 🚀