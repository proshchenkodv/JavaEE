import ru.geekbrains.service.ProductService;
import ru.geekbrains.service.ProductServiceWs;

import java.net.MalformedURLException;
import java.net.URL;

public class Main {
    public static void main(String[] args) throws MalformedURLException {
        URL url = new URL("http://localhost:8080/first-jsf-app/ProductService/ProductServiceImpl?wsdl");
        ProductService productService = new ProductService(url);

        ProductServiceWs port = productService.getProductServiceImplPort();

        port.findAll().forEach(prod -> System.out.println( prod.getId() + " : " + prod.getName() +"-" + prod.getDescription()));

        System.out.println(port.findByIdWs(2L).getName());

        System.out.println(port.findByName("Apple iPad").getName());

        port.findByCategoryId(1L).forEach(prod -> System.out.println(prod.getName() +"-" + prod.getDescription()));




    }
}
