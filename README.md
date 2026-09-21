<div align="center">

# 🕵️ Engañón

**Juego de deducción y engaño para Android, con vocabulario andaluz**

![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![SQLite](https://img.shields.io/badge/SQLite-003B57?style=for-the-badge&logo=sqlite&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white)

</div>

<br>

Un grupo de amigos, un solo móvil y una palabra secreta que todos conocen... menos el Engañón. Él solo tiene una pista, y le toca disimular para que no lo pillen.

## 🎯 Sobre el proyecto

Engañón es una app nativa que adapta la mecánica de los juegos de impostor a un ambiente cercano: las palabras del juego son expresiones y modismos andaluces ("coscar", "quillo", "enritar"...), y las pistas obligan a los jugadores a pensar en el significado y no solo en la palabra.

Proyecto desarrollado como Trabajo Fin de Grado (TFG) del ciclo de Desarrollo de Aplicaciones Multiplataforma, pensado para jugarse en reuniones con amigos y familia.

## 📥 Descarga

Puedes probar la app sin compilar nada:

<div align="center">

[![Descargar APK](https://img.shields.io/badge/%F0%9F%93%A5%20Descargar-APK-3DDC84?style=for-the-badge)](https://github.com/jmcarrionh/Enganon_App_Android/releases/latest)

[Ver todas las versiones](https://github.com/jmcarrionh/Enganon_App_Android/releases)

</div>

1. Descarga el archivo `.apk`.
2. Ábrelo en tu móvil Android (7.0 o superior).
3. Si el sistema lo pide, permite la instalación de aplicaciones de origen desconocido.

## ⚙️ Tecnologías

| Área | Tecnología |
|---|---|
| Lenguaje | Java |
| Plataforma | Android nativo (minSdk 24, targetSdk 36) |
| Build | Gradle (Kotlin DSL) y Android Gradle Plugin 8.13.2 |
| Interfaz | XML, Material Components, ConstraintLayout, CardView |
| Navegación | Activities, Fragments y Navigation Component |
| Datos | SQLite con base de datos precargada desde `assets/` |
| Extras | Animaciones, gestos táctiles, `CountDownTimer`, intents implícitos |

## 🧩 Qué incluye

- Partidas de 3 o más jugadores con roles configurables: **Participante**, **Engañón** y **Encarni**.
- Palabra y pista aleatorias desde un banco de 100 términos, sin necesidad de conexión.
- Revelado del rol en privado deslizando la pantalla, con avatares aleatorios.
- Debate cronometrado, votación y revelado dramático del resultado.
- Guía del juego integrada y buzón de sugerencias por correo.

## 🧠 Retos técnicos

- **Estado de la partida entre pantallas:** jugadores, roles y palabra viajan entre Activities mediante objetos `Serializable`, y las eliminaciones de cada ronda se reflejan en las siguientes.
- **Temporizador compartido:** el tiempo del debate continúa durante las rondas de votación pasando la hora de fin entre pantallas.
- **Base de datos precargada:** la app copia `tfg.db` desde `assets/` al primer arranque y la consulta con `SQLiteOpenHelper`.

## 🛠️ Compilar el proyecto

1. Clona el repositorio: `git clone https://github.com/jmcarrionh/Enganon_App_Android.git`
2. Ábrelo en Android Studio (JDK 17) y espera a la sincronización de Gradle.
3. Ejecútalo en un dispositivo o emulador con Android 7.0 (API 24) o superior.

---

Desarrollado por **Juan Miguel Carrión Hidalgo** · [GitHub](https://github.com/jmcarrionh)
