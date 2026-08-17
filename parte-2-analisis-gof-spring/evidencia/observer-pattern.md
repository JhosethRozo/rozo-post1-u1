/**
 * Archivo: parte-2-analisis-gof-spring/evidencia/observer-pattern.md
 * 
 * PATRÓN: OBSERVER (Event)
 * CATEGORÍA: Comportamiento
 * UBICACIÓN EN SPRING: org.springframework.context.ApplicationEvent
 *                      org.springframework.context.ApplicationListener
 *                      org.springframework.context.ApplicationEventPublisher
 * 
 * Fragmento de código fuente de Spring Framework:
 * 
 * El patrón Observer en Spring se implementa mediante el mecanismo de eventos de aplicación.
 * ApplicationEventPublisher actúa como el sujeto (subject) que publica eventos,
 * y ApplicationListener actúa como el observador que recibe notificaciones.
 * 
 * // Interfaz base para eventos
 * public abstract class ApplicationEvent extends EventObject {
 *     private final long timestamp = System.currentTimeMillis();
 *     
 *     public ApplicationEvent(Object source) {
 *         super(source);
 *     }
 *     
 *     public final long getTimestamp() {
 *         return this.timestamp;
 *     }
 * }
 * 
 * // Interfaz para observadores
 * @FunctionalInterface
 * public interface ApplicationListener<E extends ApplicationEvent> extends EventListener {
 *     void onApplicationEvent(E event);
 * }
 * 
 * // Publicador de eventos
 * public interface ApplicationEventPublisher {
 *     default void publishEvent(ApplicationEvent event) {
 *         publishEvent((Object) event);
 *     }
 *     
 *     void publishEvent(Object event);
 * }
 * 
 * FUENTE: Spring Framework GitHub (spring-context module)
 * Ubicación exacta: 
 *   - spring-context/src/main/java/org/springframework/context/ApplicationEvent.java
 *   - spring-context/src/main/java/org/springframework/context/ApplicationListener.java
 *   - spring-context/src/main/java/org/springframework/context/ApplicationEventPublisher.java
 */
