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

## Parte 2 — Análisis de Patrones GoF en Spring Framework

### Patrones Identificados y Analizados

| # | Patrón | Categoría | Clase en Spring | Módulo |
|---|--------|-----------|-----------------|--------|
| 1 | Singleton | Creacional | `DefaultSingletonBeanRegistry` | spring-core |
| 2 | Proxy | Estructural | `JdkDynamicAopProxy` | spring-aop |
| 3 | Observer/Event | Comportamiento | `ApplicationEventPublisher`, `ApplicationListener` | spring-context |

### Análisis Detallado

El documento completo de análisis se encuentra en: **`parte-2-analisis-gof-spring/documento-analisis.md`**

Cada patrón analizado incluye:
- Identificación de la categoría GoF y ubicación exacta en Spring Framework
- Descripción del problema específico que resuelve en el contexto de Spring Boot
- Evidencia de código fuente con explicaciones técnicas
- Conexión explícita con los principios SOLID (SRP, OCP, DIP, LSP, ISP)
- Análisis del impacto en la arquitectura y mantenibilidad del framework

### Evidencia de Código

La carpeta `parte-2-analisis-gof-spring/evidencia/` contiene fragmentos de código fuente de Spring Framework para cada patrón:
- `singleton-pattern.md` — Fragmentos de `DefaultSingletonBeanRegistry`
- `proxy-pattern.md` — Fragmentos de `JdkDynamicAopProxy`
- `observer-pattern.md` — Fragmentos de `ApplicationEvent`, `ApplicationListener`, `ApplicationEventPublisher`

---

## Conclusiones

Este proyecto demuestra la aplicación práctica de patrones GoF y principios SOLID en dos contextos complementarios:

1. **Refactorización SOLID (Parte 1)**: El análisis y refactorización del God Object `OrderProcessor` ilustra cómo los principios SOLID permiten separar responsabilidades, facilitar extensibilidad y reducir acoplamiento en código existente. La transición de una clase monolítica a un conjunto de clases cohesivas con inyección de dependencias mejora significativamente la mantenibilidad y testabilidad.

2. **Patrones GoF en Spring (Parte 2)**: El reconocimiento de patrones reales en un framework maduro como Spring demuestra que los patrones no son abstracciones académicas sino herramientas concretas utilizadas sistemáticamente para resolver desafíos arquitectónicos. Cada patrón (Singleton, Proxy, Observer) refuerza explícitamente los principios SOLID en su contexto específico.

La integración de SOLID y GoF proporciona un lenguaje común para comunicar decisiones de arquitectura y un conjunto de prácticas comprobadas para diseñar sistemas escalables y mantenibles. El desarrollo profesional depende de la capacidad de reconocer estas estructuras, entender sus ventajas y limitaciones, y aplicarlas apropiadamente a nuevos desafíos.

---

## Herramientas Utilizadas

| Herramienta | Versión | Propósito |
|-------------|---------|----------|
| Java JDK | 17 | Compilación y ejecución |
| Apache Maven | 3.8+ | Gestión de dependencias y build |
| VS Code | Última | Editor de código |
| Git / GitHub | 2.x | Control de versiones |
| Spring Framework | 6.x | Framework de investigación (Parte 2) |

---

## Commits Realizados

| Commit | Descripción |
|--------|------------|
| `feat: inicializar proyecto Maven parte-1-refactorizacion-solid` | Creación de estructura Maven |
| `refactor(SRP): separar responsabilidades de OrderProcessor` | Aplicación de Single Responsibility Principle |
| `refactor(OCP): extraer DiscountStrategy con implementaciones` | Aplicación de Open/Closed Principle con Strategy Pattern |
| `refactor(DIP): inyectar dependencias por constructor` | Aplicación de Dependency Inversion Principle |
| `feat: crear estructura base de la parte 2` | Creación de estructura de análisis GoF |
| `docs: agregar analisis de patrones GoF en Spring` | Documento de análisis completo de patrones |

---

## Cómo Clonar y Ejecutar

```bash
# Clonar repositorio
git clone https://github.com/JhosethRozo/rozo-post1-u1.git
cd rozo-post1-u1

# Ejecutar Parte 1 (Refactorización SOLID)
cd parte-1-refactorizacion-solid
mvn compile
mvn exec:java -Dexec.mainClass="com.patrones.u1.Main"

# Ver Parte 2 (Análisis GoF)
cd ../parte-2-analisis-gof-spring
cat documento-analisis.md
```

---

**Entrega:** Repositorio público único con ambas partes, commits descriptivos y documentación completa.
