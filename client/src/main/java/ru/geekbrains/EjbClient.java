package ru.geekbrains;

import ru.geekbrains.service.ProductServiceRemote;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class EjbClient {

    public static void main(String[] args) throws IOException, NamingException, ExecutionException, InterruptedException {
        Context context = createInitialContext();

        ProductServiceRemote service = (ProductServiceRemote) context.lookup("ejb:/first-jsf-app/ProductServiceImpl!ru.geekbrains.ProductServiceRemote");
        service.findAll().forEach(todo-> System.out.println(todo.getId()+ ": " + todo.getName()) );

//        Future<ProductRepr> future=service.findByIdAsync(1L);
//        System.out.println(future.get());
    }

    public static Context createInitialContext() throws IOException, NamingException {
        final Properties env = new Properties();
        env.load(EjbClient.class
                .getClassLoader()
                .getResourceAsStream("wildfly-jndi.properties"));
        return new InitialContext(env);
    }
}
