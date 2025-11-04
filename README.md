# 🍰 Pastelería Mil Sabores — Aplicación Android

**Asignatura:** Desarrollo de Aplicaciones Móviles  
**Institución:** Duoc UC  
**Carrera:** Ingeniería en Informática  
**Autores:** Cristóbal Pérez y equipo  
**Profesor guía:** [Nombre del profesor si corresponde]  
**Año:** 2025

---

## 📱 Descripción general

**Pastelería Mil Sabores** es una aplicación móvil nativa para Android desarrollada con **Kotlin** y **Jetpack Compose**, que permite a los usuarios registrarse, iniciar sesión y acceder a un catálogo interactivo de productos de repostería.  
Además, integra un sistema de beneficios por usuario, manejo de sesiones persistentes y la opción de actualizar la foto de perfil mediante cámara o galería.

El objetivo del proyecto es simular una tienda real de pastelería digital, enfocada en la experiencia de usuario, la navegación intuitiva y la correcta aplicación de los principios de arquitectura moderna en Android.

---

## 🧩 Características principales

- **Registro e inicio de sesión** con almacenamiento de usuarios en archivo local JSON.
- **Manejo de sesión persistente** mediante **DataStore Preferences**.
- **Pantalla de Perfil** con edición de foto desde **cámara o galería**.
- **Catálogo de productos** dinámico (lista de pasteles cargados desde `assets/database/Pasteles.json`).
- **Sistema de descuentos y beneficios:**
    - 50% para adultos mayores (50+)
    - 10% de descuento permanente con código `FELICES50`
    - Regalo de cumpleaños para usuarios con correo institucional `@duoc.cl`
- **Diseño moderno y responsivo** utilizando **Material 3 (Material You)**.
- **Navegación con BottomBar y DrawerMenu** entre secciones:
    - Inicio
    - Productos
    - Carrito
    - Nosotros
    - Contáctanos
    - Perfil

---

## 🧱 Tecnologías utilizadas

| Tecnología / Librería | Uso principal |
|------------------------|----------------|
| **Kotlin** | Lenguaje base del proyecto |
| **Jetpack Compose** | UI declarativa moderna |
| **Material 3** | Componentes visuales y estilo |
| **Navigation Compose** | Navegación entre pantallas |
| **DataStore Preferences** | Manejo de sesión de usuario |
| **Kotlinx Serialization / Gson** | Lectura y escritura de JSON |
| **Coil Compose** | Carga de imágenes |
| **CameraX** | Acceso a cámara del dispositivo |
| **Gradle KTS** | Sistema de compilación y dependencias |

---

## 🧠 Arquitectura del proyecto

El proyecto sigue una **estructura modular y limpia** orientada a MVVM:

