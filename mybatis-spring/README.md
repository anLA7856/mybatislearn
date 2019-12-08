1. 注解 Lazy， Primary， Condition， DependsOn，Role，Description，
ScopedProxyMode：即返回的bean，是否要代理。

2. AnnotationConfigApplicationContext 的 refresh 方法：
以下在 spring-context 包中：
1. 进入 AbstractApplicationContext 的 refresh 方法。，加了同步锁
    1. 进入 内部方法 prepareRefresh，准备状态，设置 startupDate，active 等。
        1. 执行getEnvironment().validateRequiredProperties();获取某一种环境变量，从而进行参数校验。
        2. 检查 ApplicationListener,即 Set<ApplicationListener<?>> earlyApplicationListeners;
        3. 初始化 this.earlyApplicationEvents，即 Set<ApplicationEvent> earlyApplicationEvents;
    2. 执行 ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();，告诉子类，刷新外部bean工厂。
        1. 执行 refreshBeanFactory，这个方法是 protect 类型空方法，就是让子类实现的。
            1. 进入 GenericApplicationContext 的 refreshBeanFactory
                1. 使用 CAS  ` final AtomicBoolean refreshed` 判断是否已经在处理，有就报错。
                2. 调用 `this.beanFactory.setSerializationId(getId());`，给 beanFactory 设置id，用于反序列化，允许通过此id进行反序列化。（spring-bean）中
                    1. 如果传入的 serializationId优质，将一个 `<serializationId, new WeakReference<>(this)>`，放入 `serializableFactories` 中
                    2. 将 id 设置进 `DefaultListableBeanFactory`  的 `serializationId` 变量
            2. 返回 `getBeanFactory();`，即返回 `GenericApplicationContext` 的 `beanFactory`
    3. 执行 `prepareBeanFactory(beanFactory);`，也就是准备下 `beanFactory`
        1. 设置 `ClassLoader`，设置类加载器，`beanFactory.setBeanClassLoader(getClassLoader());`
        2. 设置 `beanExpressionResolver`，设置 Spring EL表达式解析器。（Spring - Expression 包） https://www.iteye.com/blog/jinnianshilongnian-1418311
        3. 增加 `PropertyEditorRegistrar`， 设置属性编辑器，https://blog.csdn.net/cuichunchi/article/details/90407632，https://blog.csdn.net/fang_qiming/article/details/79881720
        下面开始设置 context 的callback
        4. 增加 `BeanPostProcessor`，设置一些 *Aware 属性， https://blog.csdn.net/andy_zhang2007/article/details/86287786，https://blog.csdn.net/cgsyck/article/details/89317704
        5. 将 `EnvironmentAware.class、ResourceLoaderAware.class、ApplicationEventPublisherAware.class、MessageSourceAware.class、ApplicationContextAware.class` 加入到 忽略检查依赖的set中 `Set<Class<?>> ignoredDependencyInterfaces`
        6. `beanFactory.registerResolvableDependency(BeanFactory.class, beanFactory);`，将 `BeanFactory.class、ResourceLoader.class、ApplicationEventPublisher.class、ApplicationContext.class` 放入 `DefaultListableBeanFactory`的 `Map<Class<?>, Object> resolvableDependencies` 中，用于说明某些类的自动注入实例值。
        7. 添加 `ApplicationListenerDetector` 类型的 `BeanPostProcessor`，  用于对一些实现了 `APplicationListener` 类做处理。https://www.cnblogs.com/hjqstof/p/8998001.html
        8. 判断是否包含 `LoadTimeWeaver`，有则需要 增加 `LoadTimeWeaverAwareProcessor` 类型 `BeanPostProcessor`，设置 临时的 classLoader `beanFactory.setTempClassLoader(new ContextTypeMatchClassLoader(beanFactory.getBeanClassLoader()));`
            `LoadTimeWeaver` 即代码织入，即AOP：https://www.cnblogs.com/wade-luffy/p/6073702.html
            1. `beanFactory.containsBean(LOAD_TIME_WEAVER_BEAN_NAME)` 判断逻辑如下：
            2. 进入 `AbstractBeanFactory`的 `containsBean` 方法
            3. 使用 `transformedBeanName` 获取`beanName`。
                1. 进入 `canonicalName(BeanFactoryUtils.transformedBeanName(name));` 获取name，判断是否返回 beanFactory
                    1. 使用 `BeanFactoryUtils` 的`transformedBeanName`
                    2. 如果 名字不是以 `&` 开头，则直接返回传入name
                    3. 如果以 `&` 开头，则返回工厂名字，即返回该bean 的工厂bean，如果有的话。http://hant.ask.helplib.com/mip/2510839
                        并将它缓存到 BeanFactoryUtils 的 `Map<String, String> transformedBeanNameCache` 中。
            4. 进入 `containsSingleton(beanName)` 判断是否有单例bean名字。
                1. 直接在 在 DefaultSingletonBeanRegistry 的 Map `Map<String, Object> singletonObjects` 中去判断是否有
            5. 再去 `containsBeanDefinition` 判断是否有bean定义名字。
                1. 直接在 `DefaultListableBeanFactory` 的 `Map<String, BeanDefinition> beanDefinitionMap` 判断。
            6. 以上两个判断，有就直接返回判断。
            7. 没有找到，就进入 `AbstractBeanFactory` 的 `getParentBeanFactory` 去查找。
        9. 判断beanFactory中，是否有 `environment`， 没有则使用 `beanFactory.registerSingleton(ENVIRONMENT_BEAN_NAME, getEnvironment());` 填入。
            1. 判断environment时，使用 `beanFactory.containsLocalBean` 判断。
            2. 上述也是通过判断 `(containsSingleton(beanName) || containsBeanDefinition(beanName)`，但是还需要判断是不能是工厂类型：
            完整的语句如上 ： `return ((containsSingleton(beanName) || containsBeanDefinition(beanName)) && (!BeanFactoryUtils.isFactoryDereference(name) || isFactoryBean(beanName)));`
        10. 同样方法会填入 `systemProperties` 和 `systemEnvironment`：
            `beanFactory.registerSingleton(SYSTEM_PROPERTIES_BEAN_NAME, getEnvironment().getSystemProperties());`
            `beanFactory.registerSingleton(SYSTEM_ENVIRONMENT_BEAN_NAME, getEnvironment().getSystemEnvironment());`
    4. 执行 `postProcessBeanFactory(beanFactory);`， 允许 bean 的  `post-processing`，即在 下层bean 工厂的通知机制。 
    5. 执行 `invokeBeanFactoryPostProcessors(beanFactory);`， 将 beanFactory 的 `factory` 类型的bean都执行并返回bean。
        1. 进入 `AbstractApplicationContext` 的 `invokeBeanFactoryPostProcessors`，执行 `PostProcessorRegistrationDelegate.invokeBeanFactoryPostProcessors(beanFactory, getBeanFactoryPostProcessors());`
            1. 进入 `PostProcessorRegistrationDelegate` 的 `invokeBeanFactoryPostProcessors` 
            2. post - processor ：https://www.cnblogs.com/dengpengbo/p/10464892.html
            2. 目的是加入一些配置了的 factory 类型的 post - processor
            3. 其中，会加入一个 解析 `@Configuration` 的功能类。`org.springframework.context.annotation.internalConfigurationAnnotationProcessor` https://www.cnblogs.com/jiaoqq/p/7678037.html
            4. 经过排序，添加后，就需要执行 对应的 `BeanDefinitionRegistryPostProcessors`
            5. 执行对应的 postProcessors
            6. todo 这里以后分析 post-processor细细读。https://www.cnblogs.com/jiaoqq/p/7678037.html
    