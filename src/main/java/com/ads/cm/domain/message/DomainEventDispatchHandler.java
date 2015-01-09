package com.ads.cm.domain.message;

import com.ads.cm.container.ContainerWrapper;
import com.ads.cm.domain.consumer.ConsumerMethodHolder;
import com.ads.cm.event.disuptor.EventDisruptor;
import com.ads.cm.model.*;
import com.ads.cm.util.http.HttpUtils;
import com.ads.cm.util.log.LogInstance;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.jboss.netty.channel.MessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: cyc
 * Date: 12-3-13
 * Time: 下午8:57
 * Description: to write something
 */
public class DomainEventDispatchHandler implements DomainEventHandler<EventDisruptor> {
    private final Logger logger = LoggerFactory.getLogger(DomainEventDispatchHandler.class);

    private ConsumerMethodHolder consumerMethodHolder;
    private ContainerWrapper containerWrapper;

    public DomainEventDispatchHandler(ConsumerMethodHolder consumerMethodHolder, ContainerWrapper containerWrapper) {
        super();
        this.consumerMethodHolder = consumerMethodHolder;
        this.containerWrapper = containerWrapper;
    }

    @Override
    public void onEvent(EventDisruptor event, final boolean endOfBatch) throws Exception {
        try {
            Method method = consumerMethodHolder.getMethod();
            Class[] pTypes = method.getParameterTypes();
            if (pTypes.length == 0) {
                logger.warn("consumer:{} method must have one parameter.", consumerMethodHolder);
                throw new Exception("method must have one parameter.");
            }
            Object parameter = event.getDomainMessage().getEventSource();
            if (parameter == null) {
                logger.error("the publisher method with @Send need return type.parameter:{},method:{}", pTypes, method);
                return;
            }

            Object[] parameters = new Object[pTypes.length];
            int i = 0;
            for (Class pType : pTypes) {
                if (pType.isAssignableFrom(parameter.getClass())) {
                    parameters[i] = parameter;
                } else {
                    if (!pType.isPrimitive())
                        parameters[i] = pType.newInstance();
                    else
                        parameters[i] = defaultValues.get(pType);
                }
                i++;
            }
            Object o = containerWrapper.lookupOriginal(consumerMethodHolder.getClassName());
            Object eventResult = method.invoke(o, parameters);

            if (method.getReturnType() != Void.class) {
                event.getDomainMessage().setEventResult(eventResult);
            }
        } catch (Exception e) {
            logger.error("method:{} with @onEvent error:{} ", consumerMethodHolder.getClassName(), ExceptionUtils.getStackTrace(e));   //产生异常直接返回，结束线程，防止线程阻塞。

            HashMap<String, Object> response = new HashMap<String, Object>();
            ClientRequestModel model = null;

            Object o = event.getDomainMessage().getEventSource();

            Class c = o.getClass();

            String name = c.getName();


            if (name.equals("com.ads.cm.model.RegisterModel")) {
                response.put("resultCode", "100");
                response.put("errorCode", "100");//无效的AppKey

                model = (RegisterModel) o;
                LogInstance.registerLogger.debug("client:{} method:{} with @onEvent error:{}",new Object[]{model.getModelIp(),consumerMethodHolder.getClassName(), ExceptionUtils.getStackTrace(e)});
            }

            if (name.equals("com.ads.cm.model.GetTasksModel")) {
                response.put("resultCode", "100");
                response.put("errorCode", "101");//无可执行任务

                model = (GetTasksModel) o;
                LogInstance.getTaskLogger.debug("client:{} method:{} with @onEvent error:{}",new Object[]{model.getModelIp(),consumerMethodHolder.getClassName(), ExceptionUtils.getStackTrace(e)});
            }

            if (name.equals("com.ads.cm.model.ReportTaskStatusModel")) {
                response.put("linkId", "");
                response.put("resultCode", "200");

                model = (ReportTaskStatusModel) o;
                LogInstance.reportTaskLogger.debug("client:{} method:{} with @onEvent error:{}",new Object[]{model.getModelIp(),consumerMethodHolder.getClassName(), ExceptionUtils.getStackTrace(e)});
            }

            if (name.equals("com.ads.cm.model.UpdateTaskStatusModel")) {
                response.put("resultCode", "200");

                model = (UpdateTaskStatusModel) o;
                LogInstance.updateTaskLogger.debug("client:{} method:{} with @onEvent error:{}",new Object[]{model.getModelIp(),consumerMethodHolder.getClassName(), ExceptionUtils.getStackTrace(e)});
            }

            if (name.equals("com.ads.cm.model.LoadManagerModel")) {
                response.put("resultCode", "100");
                response.put("errorCode","");

                model = (LoadManagerModel) o;

                LogInstance.loadManagerLogger.debug("client:{} method:{} with @onEvent error:{}",new Object[]{model.getModelIp(),consumerMethodHolder.getClassName(), ExceptionUtils.getStackTrace(e)});
            }


            HttpUtils.response(model, response);

//            MessageEvent messageEvent = (MessageEvent) model.getProperty(model.MESSAGE_EVENT_KEY);
//            messageEvent.getChannel().close();
//            logger.error("Finished close channel ! ");

            return;
        }
    }

    private final static Map<Class<?>, Object> defaultValues = new HashMap<Class<?>, Object>();

    static {
        defaultValues.put(String.class, "");
        defaultValues.put(Integer.class, 0);
        defaultValues.put(int.class, 0);
        defaultValues.put(Long.class, 0L);
        defaultValues.put(long.class, 0L);
        defaultValues.put(Character.class, '\0');
        defaultValues.put(char.class, '\0');
        // etc
    }
}
