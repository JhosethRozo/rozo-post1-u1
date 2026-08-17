/**
 * Archivo: parte-2-analisis-gof-spring/evidencia/singleton-pattern.md
 * 
 * PATRÓN: SINGLETON
 * CATEGORÍA: Creacional
 * UBICACIÓN EN SPRING: org.springframework.beans.factory.support.DefaultSingletonBeanRegistry
 * 
 * Fragmento de código fuente de Spring Framework (simplificado):
 * 
 * La clase DefaultSingletonBeanRegistry implementa el patrón Singleton para gestionar
 * beans de alcance por defecto en Spring. Cada bean singleton se instancia una única vez
 * y se reutiliza en toda la aplicación.
 * 
 * public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
 *     private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);
 * 
 *     @Override
 *     public void registerSingleton(String beanName, Object singletonObject) {
 *         synchronized (this.singletonObjects) {
 *             Object oldObject = this.singletonObjects.get(beanName);
 *             if (oldObject != null) {
 *                 throw new IllegalStateException("Could not register object [" + singletonObject +
 *                     "] under bean name '" + beanName + "': there is already object [" + oldObject + "] bound");
 *             }
 *             addSingleton(beanName, singletonObject);
 *         }
 *     }
 * 
 *     protected void addSingleton(String beanName, Object singletonObject) {
 *         synchronized (this.singletonObjects) {
 *             this.singletonObjects.put(beanName, singletonObject);
 *         }
 *     }
 * 
 *     @Override
 *     public Object getSingleton(String beanName) {
 *         return this.singletonObjects.get(beanName);
 *     }
 * }
 * 
 * FUENTE: Spring Framework GitHub (spring-core module)
 * Ubicación exacta: spring-core/src/main/java/org/springframework/beans/factory/support/DefaultSingletonBeanRegistry.java
 */
