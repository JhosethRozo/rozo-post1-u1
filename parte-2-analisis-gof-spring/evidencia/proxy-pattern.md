/**
 * Archivo: parte-2-analisis-gof-spring/evidencia/proxy-pattern.md
 * 
 * PATRÓN: PROXY
 * CATEGORÍA: Estructural
 * UBICACIÓN EN SPRING: org.springframework.aop.framework.ProxyFactoryBean
 *                     org.springframework.aop.framework.JdkDynamicAopProxy
 * 
 * Fragmento de código fuente de Spring Framework:
 * 
 * La clase JdkDynamicAopProxy implementa el patrón Proxy utilizando reflexión de Java
 * para interceptar llamadas a métodos de beans Spring y aplicar aspectos (AOP).
 * 
 * public class JdkDynamicAopProxy implements AopProxy, InvocationHandler, Serializable {
 *     private static final Log logger = LogFactory.getLog(JdkDynamicAopProxy.class);
 *     private final AdvisedSupport advised;
 *     private boolean equalsDefined;
 *     private boolean hashCodeDefined;
 * 
 *     public JdkDynamicAopProxy(AdvisedSupport config) throws AopConfigException {
 *         Assert.notNull(config, "AdvisedSupport must not be null");
 *         this.advised = config;
 *     }
 * 
 *     @Override
 *     public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
 *         // Obtener la cadena de interceptores
 *         List<Object> chain = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass);
 *         
 *         // Crear la invocación del método con los interceptores
 *         MethodInvocation invocation = new ReflectiveMethodInvocation(proxy, target, method, args, targetClass, chain);
 *         
 *         // Proceder con la invocación, permitiendo que los interceptores ejecuten lógica antes/después
 *         return invocation.proceed();
 *     }
 * }
 * 
 * FUENTE: Spring Framework GitHub (spring-aop module)
 * Ubicación exacta: spring-aop/src/main/java/org/springframework/aop/framework/JdkDynamicAopProxy.java
 */
