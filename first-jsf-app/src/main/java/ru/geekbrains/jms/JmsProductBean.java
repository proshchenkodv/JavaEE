package ru.geekbrains.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.geekbrains.service.ProductRepr;
import ru.geekbrains.service.ProductService;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "java:jboss/exported/jms/queue/productQueue"),
        @ActivationConfigProperty(propertyName = "messageSelector", propertyValue = "action='create'"),
})
public class JmsProductBean implements MessageListener {

    private static final Logger logger= LoggerFactory.getLogger(JmsProductBean.class);

    @EJB
    private ProductService productService;

    @Override
    public void onMessage(Message message) {
        logger.info("New JMS Message");

        if(message instanceof ObjectMessage){
            ObjectMessage om=(ObjectMessage) message;
            try{
                ProductRepr productRepr=(ProductRepr) om.getObject();
                productService.insert(productRepr);
            }
            catch (JMSException e){
                logger.error("", e);
            }
        }
    }
}
