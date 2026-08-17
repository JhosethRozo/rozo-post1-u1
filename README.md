# Post-contenido — Unidad 1: Fundamentos de Patrones de Diseño y Buenas Prácticas

## Descripción
Repositorio del post-contenido de la Unidad 1 de Patrones de Diseño de Software — Sexto Semestre. Contiene dos partes: refactorización SOLID de un God Object (parte-1-refactorizacion-solid/) y análisis de patrones GoF en Spring Framework (parte-2-analisis-gof-spring/).

---

## Parte 1 — Refactorización SOLID

### Análisis de Violaciones SOLID

La clase `OrderProcessor` viola múltiples principios SOLID simultáneamente. El siguiente análisis detalla cada violación identificada:

| Principio | Método/Sección afectada | Descripción de la violación |
|-----------|-------------------------|-----------------------------|
| **SRP** | `calculateTotal()`, `applyDiscount()`, `saveOrder()`, `sendEmail()`, `printReport()` | La clase concentra cinco responsabilidades distintas: cálculo de precios, aplicación de descuentos, persistencia en base de datos, notificación por correo y generación de reportes. Una única clase no debería ser responsable de todos estos aspectos del negocio. |
| **OCP** | `applyDiscount(String customerType)` | El método utiliza condicionales if/else para determinar el descuento según el tipo de cliente (VIP, REGULAR). Agregar un nuevo tipo de cliente requiere modificar el código existente, violando el principio Abierto/Cerrado. La clase está cerrada a extensión sin modificación. |
| **DIP** | Toda la clase | `OrderProcessor` no depende de abstracciones sino de implementaciones concretas. Los métodos están acoplados directamente a la lógica de persistencia, notificación y reportes. No hay inyección de dependencias ni interfaces que desacopen estas responsabilidades, causando alto acoplamiento interno. |

### Ejecución
Para compilar y ejecutar la Parte 1:
```bash
cd parte-1-refactorizacion-solid
mvn compile
mvn exec:java -Dexec.mainClass="com.patrones.u1.Main"
```

---

## Parte 2 — Análisis de Patrones GoF en Spring

*Sección completada en siguiente checkpoint*

## Herramientas utilizadas
- Java 17 JDK
- Apache Maven 3.8+
- VS Code con Extension Pack for Java
- Git / GitHub
