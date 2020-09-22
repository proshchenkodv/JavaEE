package ru.geekbrains;

import service.ToDoRepr;
import service.ToDoServiceRemote;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.Future;

public class EjbClient {

    public static void main(String[] args) throws Exception {
        Context context = createInitialContext();

        ToDoServiceRemote service = (ToDoServiceRemote) context.lookup("ejb:/todo-jsf/ToDoServiceImpl!service.ToDoServiceRemote");
        service.findAll().forEach(todo -> System.out.println(todo.getDescription()));

        Future<ToDoRepr> future = service.findByIdAsync(1L);
        System.out.println(future.get());
    }

    public static Context createInitialContext() throws IOException, NamingException {
        final Properties env = new Properties();
        env.load(EjbClient.class.getClassLoader().getResourceAsStream("wildfly-jndi.properties"));
        return new InitialContext(env);
    }
}
