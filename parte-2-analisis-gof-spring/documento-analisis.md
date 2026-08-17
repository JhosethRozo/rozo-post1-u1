# Post-contenido Unidad 1: Patrones de Diseño en Spring Framework

## Portada

**Autor:** Jhoseth Rozo  
**Código:** rozo-post1-u1  
**Curso:** Patrones de Diseño de Software  
**Unidad:** 1 — Fundamentos de Patrones de Diseño y Buenas Prácticas  
**Fecha:** Agosto 2026  

---

## Introducción

El presente documento realiza un análisis profundo de tres patrones de diseño Gang of Four (GoF) identificados en el código fuente del framework Spring Boot, clasificándolos por categoría (Creacional, Estructural y de Comportamiento) y conectando su implementación con los principios SOLID. Spring Framework representa un ecosistema maduro donde los patrones de diseño se aplican de manera sistemática para resolver problemas complejos de desarrollo empresarial. La investigación que sigue examina cómo Spring utiliza patrones específicos no solo como herramientas técnicas, sino como expresiones concretas de principios de arquitectura de software de alto nivel.

El objetivo de este análisis es demostrar que el reconocimiento de patrones GoF en código real, y la comprensión de su conexión con SOLID, es fundamental para diseñar sistemas de software escalables, mantenibles y flexibles. Se seleccionaron tres patrones de categorías distintas que resuelven desafíos centrales en el diseño de un contenedor IoC (Inversion of Control) y un framework AOP (Aspect-Oriented Programming).

---

## Análisis de Patrón 1: Singleton (Categoría Creacional)

### Identificación y Ubicación

El patrón **Singleton** pertenece a la categoría **Creacional** del catálogo GoF. Su propósito general es garantizar que una clase tenga una única instancia en toda la aplicación y proporcionar un punto de acceso global a esa instancia. En Spring Framework, este patrón es fundamental porque el contenedor IoC por defecto crea beans en alcance singleton, es decir, crea una única instancia de cada bean que se reutiliza en toda la aplicación.

La clase responsable de esta implementación es `org.springframework.beans.factory.support.DefaultSingletonBeanRegistry`, ubicada en el módulo **spring-core**.

### Problema Específico que Resuelve

En un framework como Spring Boot que necesita gestionar cientos o miles de beans de aplicación, es crítico asegurar que ciertos objetos — como bases de datos, configuraciones, servicios transversales — existan en una única instancia durante la vida útil de la aplicación. Crear múltiples instancias de estos objetos sería ineficiente (consumiendo memoria innecesaria) y problemático (causando inconsistencias de estado). El patrón Singleton resuelve este problema almacenando cada bean singleton en un registro thread-safe (utiliza un `ConcurrentHashMap`) y reutilizando la misma instancia en todas las invocaciones posteriores.

### Evidencia de Código

El fragmento de código fuente de `DefaultSingletonBeanRegistry` muestra cómo Spring implementa el patrón:

```java
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
    // Registro thread-safe de singletons
    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);
    
    public synchronized Object getSingleton(String beanName) {
        return this.singletonObjects.get(beanName);
    }
    
    protected synchronized void addSingleton(String beanName, Object singletonObject) {
        this.singletonObjects.put(beanName, singletonObject);
    }
}
```

Este código demuestra que:
1. Existe un mapa centralizado (`singletonObjects`) que almacena todas las instancias singleton
2. El acceso al mapa está sincronizado para evitar condiciones de carrera en entornos multihilo
3. Cada bean identificado por su nombre (`beanName`) se recupera del mapa en lugar de ser recreado

### Conexión con Principios SOLID

Este patrón refuerza principalmente el principio **Single Responsibility Principle (SRP)**. La clase `DefaultSingletonBeanRegistry` tiene una única responsabilidad: gestionar el registro y recuperación de beans singleton. No gestiona la creación de estos beans (eso lo hace el `BeanFactory`), ni su configuración, ni su inyección de dependencias — solo su almacenamiento y recuperación centralizada. Esta separación clara de responsabilidades hace que el código sea más testeable y mantenible.

Adicionalmente, el patrón refuerza el **Dependency Inversion Principle (DIP)**, porque el resto del framework depende de la abstracción `SingletonBeanRegistry` (una interfaz) en lugar de depender directamente de `DefaultSingletonBeanRegistry` (la implementación concreta). Esto permite reemplazar la implementación del registro sin afectar el resto del framework.

---

## Análisis de Patrón 2: Proxy (Categoría Estructural)

### Identificación y Ubicación

El patrón **Proxy** pertenece a la categoría **Estructural** del catálogo GoF. Su propósito es proporcionar un sustituto (proxy) para otro objeto, permitiendo controlar el acceso al objeto original. En Spring Framework, este patrón es esencial para implementar AOP (Aspect-Oriented Programming), permitiendo ejecutar lógica adicional (como transacciones, seguridad, logging) alrededor de los métodos de los beans sin modificar su código original.

La clase responsable es `org.springframework.aop.framework.JdkDynamicAopProxy`, ubicada en el módulo **spring-aop**. También está `ProxyFactoryBean` que orquesta la creación de proxies.

### Problema Específico que Resuelve

Un framework empresarial como Spring necesita aplicar comportamientos transversales (cross-cutting concerns) a múltiples clases sin que estas clases conocan la existencia de estos comportamientos. Por ejemplo, cada método que accede a la base de datos debería abrirse en una transacción automáticamente, cada invocación debería ser registrada en logs, y cada acceso debería verificar permisos de seguridad. Replicar este código en cada método violaría el principio DRY y generaría acoplamiento innecesario.

El patrón Proxy resuelve esto creando un objeto proxy que envuelve el objeto original. Cuando se invoca un método en el proxy, primero ejecuta la lógica transversal (advice), luego invoca el método original (join point), y finalmente ejecuta más lógica transversal. El cliente no es consciente de que está usando un proxy.

### Evidencia de Código

El fragmento de código de `JdkDynamicAopProxy` muestra cómo se implementa esto:

```java
public class JdkDynamicAopProxy implements AopProxy, InvocationHandler, Serializable {
    private final AdvisedSupport advised;
    
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) 
            throws Throwable {
        // Obtener la cadena de interceptadores que se aplicarán
        List<Object> chain = this.advised.getInterceptorsAndDynamicInterceptionAdvice(
            method, targetClass);
        
        // Crear una invocación que ejecutará los interceptadores + el método original
        MethodInvocation invocation = new ReflectiveMethodInvocation(
            proxy, target, method, args, targetClass, chain);
        
        // Proceder ejecutando la cadena de lógica
        return invocation.proceed();
    }
}
```

Este código demuestra que:
1. Cada invocación de método pasa por el método `invoke`, que actúa como punto de control
2. La lógica transversal (advice) se organiza en una cadena (chain of responsibility)
3. El método original (`target`) se invoca después de que la cadena se ha completado
4. El cliente nunca conoce que está utilizando un proxy, ve el mismo objeto con la misma interfaz

### Conexión con Principios SOLID

Este patrón refuerza el **Open/Closed Principle (OCP)**: las clases originales no necesitan ser modificadas para agregar nuevo comportamiento transversal. Es posible crear nuevos aspectos (advice) e inyectarlos dinámicamente sin cambiar el código existente. El sistema está cerrado para modificación pero abierto para extensión a través de nuevos aspectos.

También refuerza **Single Responsibility Principle (SRP)**: la clase original solo se preocupa por su responsabilidad de negocio (calcular, buscar, guardar), mientras que el proxy se preocupa por ejecutar comportamientos transversales. Esta separación hace que el código sea más puro y más fácil de testear.

---

## Análisis de Patrón 3: Observer/Event (Categoría Comportamiento)

### Identificación y Ubicación

El patrón **Observer** (también llamado patrón de Eventos en terminología moderna) pertenece a la categoría **Comportamiento** del catálogo GoF. Su propósito es establecer una relación de uno-a-muchos entre objetos de manera que cuando un objeto cambia de estado, todos sus dependientes son notificados automáticamente.

En Spring Framework, este patrón se implementa mediante el sistema de eventos de aplicación compuesto por:
- `org.springframework.context.ApplicationEvent` — el sujeto (subject) que representa un evento
- `org.springframework.context.ApplicationListener` — los observadores que reaccionan a eventos
- `org.springframework.context.ApplicationEventPublisher` — el mediador que publica eventos

Estas clases están en el módulo **spring-context**.

### Problema Específico que Resuelve

Un framework empresarial debe permitir que diferentes partes de la aplicación se comuniquen sin acoplarse directamente entre sí. Por ejemplo, cuando se completa un proceso de pago, varias acciones deben ocurrir: guardar en la base de datos, enviar un correo de confirmación, actualizar un tablero de análisis, generar un recibo PDF. Si cada una de estas acciones estuviera acoplada direc al proceso de pago, el código sería frágil: agregar una nueva acción requeriría modificar el código del pago.

El patrón Observer resuelve esto permitiendo que el proceso de pago publique un evento (e.g., `PagoCreadoEvent`) sin conocer quién está escuchando. Cualquier parte de la aplicación puede registrarse como oyente (listener) de este evento sin que el publicador lo sepa. Esto desacopla completamente los componentes.

### Evidencia de Código

El fragmento del sistema de eventos de Spring:

```java
// El sujeto: representa un evento
public abstract class ApplicationEvent extends EventObject {
    private final long timestamp = System.currentTimeMillis();
    
    public ApplicationEvent(Object source) {
        super(source);
    }
}

// El observador: escucha eventos
@FunctionalInterface
public interface ApplicationListener<E extends ApplicationEvent> 
        extends EventListener {
    void onApplicationEvent(E event);
}

// El publicador: notifica a los observadores
public interface ApplicationEventPublisher {
    void publishEvent(Object event);
}
```

Este código muestra que:
1. Los eventos son clases que extienden `ApplicationEvent`, permitiendo crear tipos específicos
2. Los oyentes implementan `ApplicationListener<E>`, vinculándose a un tipo de evento específico
3. El publicador tiene un método simple `publishEvent()` que desacopla completamente la comunicación
4. El framework internamente mantiene un registro de listeners y se encarga de notificarlos

### Conexión con Principios SOLID

Este patrón refuerza principalmente el **Dependency Inversion Principle (DIP)**. En lugar de que los componentes que publican eventos dependan de las clases concretas de los oyentes, todos dependen de abstracciones: `ApplicationEventPublisher` (interfaz) y `ApplicationListener<E>` (interfaz). Esto permite que nuevos oyentes se agreguen sin modificar el código del publicador.

También refuerza el **Single Responsibility Principle (SRP)**: cada listener tiene una única responsabilidad. El listener de correo solo envía correos, el listener de base de datos solo persiste, etc. Cada uno es independiente y puede ser modificado sin afectar a los otros.

El patrón también facilita el **Open/Closed Principle (OCP)**: para agregar nuevos comportamientos cuando ocurre un evento, es posible crear nuevos listeners sin modificar el código existente.

---

## Conclusiones

El análisis de estos tres patrones GoF en Spring Framework demuestra que los patrones no son simplemente construcciones académicas, sino herramientas concretas y sistemáticas utilizadas por frameworks profesionales para resolver desafíos reales de arquitectura de software. 

El patrón Singleton garantiza eficiencia y consistencia en la gestión centralizada de recursos. El patrón Proxy permite agregar comportamiento transversal sin violar la responsabilidad de las clases originales. El patrón Observer desacopla completamente los componentes permitiendo una arquitectura de eventos flexible y escalable.

Cada uno de estos patrones, cuando se implementa correctamente, refuerza explícitamente uno o más principios SOLID. Esta conexión no es coincidencia: los patrones GoF son, en esencia, respuestas estructuradas a violaciones de SOLID. Aprender a reconocer patrones en código existente y entender cómo refuerzan los principios SOLID es la habilidad fundamental que diferencia a un desarrollador que copia soluciones de un arquitecto que entiende profundamente el por qué detrás de cada decisión de diseño.

---

## Referencias

Gang of Four. (1994). *Design Patterns: Elements of Reusable Object-Oriented Software*. Addison-Wesley Professional. ISBN 0-201-63361-2.

Spring Project. (2024). *Spring Framework Documentation — Beans, Scopes, and Lifecycle*. Recuperado de https://docs.spring.io/spring-framework/reference/core/beans/basics.html

Spring Project. (2024). *Spring Framework Documentation — AOP (Aspect Oriented Programming)*. Recuperado de https://docs.spring.io/spring-framework/reference/core/aop.html

Spring Project. (2024). *Spring Framework Documentation — Spring Events*. Recuperado de https://docs.spring.io/spring-framework/reference/core/beans/basics.html#beans-factory-aware

Spring Projects GitHub. (2024). *Spring Framework Source Code*. Recuperado de https://github.com/spring-projects/spring-framework
