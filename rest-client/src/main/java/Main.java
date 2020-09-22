import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.logging.LoggingFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class Main {
    public static void main(String[] args) {
        Client client= ClientBuilder.newClient(new ClientConfig().register(LoggingFeature.class));
        WebTarget webTarget=client.target("http://localhost:8080/first-jsf-app/api").path("products");
        Invocation.Builder invocationBuilder=webTarget.request(MediaType.APPLICATION_JSON);
        Response response=invocationBuilder.get();

        String resp= response.readEntity(String.class);

        System.out.println(response.getStatus());
        System.out.println(resp);
    }
}
