package anla.lean.mybatis.mybatisspring.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Configuration;

/**
 * @author luoan
 * @version 1.0
 * @date 2020/1/13 16:15
 **/
@Configuration
public class BeanDefinitionRegistryPostProcessorConfiguration implements BeanDefinitionRegistryPostProcessor {

    /**
     * BeanDefinitionRegistryPostProcessor 所需方法
     * @param registry
     * @throws BeansException
     */
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        System.out.println("BeanDefinitionRegistryPostProcessorConfiguration registry is " + registry.getClass());
    }

    /**
     * BeanFactoryPostProcessor 方法
     * @param beanFactory
     * @throws BeansException
     */
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("BeanDefinitionRegistryPostProcessorConfiguration registry is " + beanFactory.getClass());
    }
}
