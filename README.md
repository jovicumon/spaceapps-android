# 🚀 SpaceApps – Aplicación Android (M07 Endterm)

![Banner](https://user-images.githubusercontent.com/placeholder/splash_rocket.gif)

Aplicación Android desarrollada como parte del **ENDTERM del módulo M07** de La Salle.  
Construida con **Kotlin + Jetpack Compose**, consulta la API oficial de SpaceX y permite visualizar información detallada de sus cohetes, tanto online como offline.

---

## ✨ Características principales

### 🛰 Pantalla Splash animada
- GIF animado del cohete al iniciar la app.
- Duración controlada antes de navegación.

### 🔐 Login seguro
- Validación del formulario (email + contraseña).
- Usuario correcto:
  - **Email:** `admin@lasalle.es`
  - **Password:** `admin1234`
- Snackbar de error cuando las credenciales son incorrectas.

### 🚀 Listado de Cohetes (API SpaceX)
- Carga real desde la API pública SpaceX.
- Persistencia local con **Room** para funcionar offline.
- Estados UI controlados:
  - ✳ **Loading**
  - ✔ **Success**
  - ⚠ **Error**
  - ⭕ **Empty**
- Filtro:
  - 🔍 Búsqueda por nombre
  - 🟢 Mostrar solo cohetes activos

### 🧠 Persistencia con Room
La app guarda los cohetes en la BD local:
- id  
- name  
- active  
- description  
- first_flight  
- success_rate_pct  
- wikipedia  

### 📄 Pantalla de Detalle
Incluye:
- Nombre  
- Estado (activo / retirado)  
- Primera fecha de vuelo  
- Porcentaje de éxito  
- Descripción completa  
- Botón **Abrir en Wikipedia** 🌍  

### 🌐 Manejo de errores y estados offline
- Si no hay conexión → mensaje + botón **Reintentar**
- Si ya hay datos guardados → se muestran aunque no haya internet
- BD actualizada cada vez que la app arranca correctamente

---

## 🛠 Tecnologías utilizadas

| Tecnología | Uso |
|-----------|-----|
| **Kotlin** | Lenguaje principal |
| **Jetpack Compose** | Interfaz moderna declarativa |
| **Navigation Compose** | Navegación entre pantallas |
| **Retrofit** | Cliente HTTP para SpaceX API |
| **Gson** | Parseo JSON |
| **Room** | Base de datos local |
| **Coroutines** | Concurrencia, IO y manejo async |
| **Coil** | Carga de imágenes y GIFs |
| **Material 3** | Componentes de interfaz modernos |

---

## 📦 Arquitectura aplicada

- **MVVM (Model - ViewModel - View)**
- **Repository pattern**
- **StateFlow para estados de UI**
- **Sealed classes** para representar estados
- **One Source Of Truth** con Room como base de datos
  
---

  ## ▶ Instalación y ejecución

1. Clona el repositorio:
```bash
git clone https://github.com/jovicumon/spaceapps-android.git

