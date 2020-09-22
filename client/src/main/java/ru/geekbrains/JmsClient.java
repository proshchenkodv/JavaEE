package ru.geekbrains;

import org.glassfish.jersey.client.HttpUrlConnectorProvider;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.print.attribute.standard.Destination;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Properties;

public class JmsClient {

    public static void main(String[] args) throws Exception {
        Context context = createInitialContext();

        HttpUrlConnectorProvider.ConnectionFactory connectionFactory = (HttpUrlConnectorProvider.ConnectionFactory) context.lookup("jms/RemoteConnectionFactory");
        JMSContext jmsContext = connectionFactory.createContext("jmsGuestUser", "123");

        Destination dest = (Destination) context.lookup("jms/queue/toDoQueue");

        JMSProducer producer = jmsContext.createProducer();

        ObjectMessage message = jmsContext.createObjectMessage(new ToDoRepr(null, "Study JMS again", LocalDate.now(), 1L, null));

        producer.setProperty("action", "create").send(dest, message);
    }

    public static Context createInitialContext() throws IOException, NamingException {
        final Properties env = new Properties();
        env.load(EjbClient.class.getClassLoader().getResourceAsStream("wildfly-jndi.properties"));
        return new InitialContext(env);
    }
}
