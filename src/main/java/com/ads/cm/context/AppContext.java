package com.ads.cm.context;

import com.ads.cm.annotation.Component;
import com.ads.cm.annotation.Consumer;
import com.ads.cm.annotation.OnEvent;
import com.ads.cm.annotation.Service;
import com.ads.cm.container.ContainerWrapper;
import com.ads.cm.domain.consumer.ConsumerMethodHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * Author: cyc
 * Date: 12-3-18
 * Time: 下午11:01
 * Description: 注入Consumer Bean到容器中
 */
public class AppContext implements ApplicationContextAware, ApplicationListener {
    private final Logger logger = LoggerFactory.getLogger(AppContext.class);
    private ContainerWrapper containerWrapper;
    private ApplicationContext applicationContext;
    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ContextRefreshedEvent) {
            injectSpringToContainer();
        }
    }


    public void injectSpringToContainer() {
        Map<String, Object> consumers = applicationContext.getBeansWithAnnotation(Consumer.class);
        Map<String, Object> components = applicationContext.getBeansWithAnnotation(Component.class);
        Map<String, Object> services = applicationContext.getBeansWithAnnotation(Service.class);

        //注册consumer
        if (null != consumers) {
            for (Object consumer : consumers.values()) {
                Consumer consumerAnnotation = consumer.getClass().getAnnotation(Consumer.class);
                containerWrapper.registerConsumer(consumerAnnotation.value(), consumer.getClass().getName());
                containerWrapper.registerOriginal(consumer.getClass().getName(), consumer);
            }
        }

        //注册OnEvent
        if (null != components) {
            for (Object component : components.values()) {
                //判断component对象中是否有使用@OnEvent注释的方法
                Method[] methods = component.getClass().getDeclaredMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(OnEvent.class)) {
                        OnEvent onEvent = method.getAnnotation(OnEvent.class);
                        containerWrapper.registerOriginal(component.getClass().getName(), component);

                        ConsumerMethodHolder consumerMethodHolder = new ConsumerMethodHolder(component.getClass().getName(), method);
                        containerWrapper.registerOnEventConsumer(onEvent.value(), consumerMethodHolder);
                    }
                }
            }
        }

        //注册OnEvent
        if (null != services) {
            for (Object service : services.values()) {
                //判断Service对象中是否有使用@OnEvent注释的方法
                Method[] methods = service.getClass().getDeclaredMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(OnEvent.class)) {
                        OnEvent onEvent = method.getAnnotation(OnEvent.class);
                        containerWrapper.registerOriginal(service.getClass().getName(), service);

                        ConsumerMethodHolder consumerMethodHolder = new ConsumerMethodHolder(service.getClass().getName(), method);
                        containerWrapper.registerOnEventConsumer(onEvent.value(), consumerMethodHolder);
                    }
                }
            }
        }
    }

    public void setContainerWrapper(ContainerWrapper containerWrapper) {
        this.containerWrapper = containerWrapper;
    }
}
