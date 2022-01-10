import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class AmazonProcessingTest {
    /**
     * Creates the shared buffers the go between each node as well as executes each thread at runtime
     * @param args Standard parameter
     */
    public static void main(String[] args){

        // Shared buffers between WebServer and ShippingCenter
        Buffer webToShippingCenter1 = new Buffer();
        Buffer webToShippingCenter2 = new Buffer();

        // Shared buffers between ShippingCenter and Section
        Buffer shippingCenterToSection1 = new Buffer();
        Buffer shippingCenterToSection2 = new Buffer();
        Buffer shippingCenterToSection3 = new Buffer();
        Buffer shippingCenterToSection4 = new Buffer();

        // Shared buffers between Section and ShippingDock
        Buffer sectionToShippingDock1 = new Buffer();
        Buffer sectionToShippingDock2 = new Buffer();

        // Shared buffers between ShippingDock and DeliveryTruck
        Buffer dockToTruck1 = new Buffer();
        Buffer dockToTruck2 = new Buffer();
        Buffer dockToTruck3 = new Buffer();
        Buffer dockToTruck4 = new Buffer();

        ExecutorService executorService = Executors.newCachedThreadPool();

        // Starts WebServer to ShippingCenter thread
        executorService.execute(new WebServer(webToShippingCenter1, webToShippingCenter2));

        // Starts ShippingCenter to Section thread
        executorService.execute(new ShippingCenter(webToShippingCenter1, shippingCenterToSection1, shippingCenterToSection2, "1"));
        executorService.execute(new ShippingCenter(webToShippingCenter2, shippingCenterToSection3, shippingCenterToSection4, "2"));

        // Starts Section to ShippingDock thread
        executorService.execute(new Section(shippingCenterToSection1, sectionToShippingDock1, "1"));
        executorService.execute(new Section(shippingCenterToSection2, sectionToShippingDock1, "2"));
        executorService.execute(new Section(shippingCenterToSection3, sectionToShippingDock2, "3"));
        executorService.execute(new Section(shippingCenterToSection4, sectionToShippingDock2, "4"));

        // Starts ShippingDock to DeliveryTruck thread
        executorService.execute(new ShippingDock(sectionToShippingDock1, dockToTruck1, dockToTruck2));
        executorService.execute(new ShippingDock(sectionToShippingDock2, dockToTruck3, dockToTruck4));

        // Starts DeliveryTruck to Screen thread
        executorService.execute(new DeliveryTruck(dockToTruck1, "1"));
        executorService.execute(new DeliveryTruck(dockToTruck2, "2"));
        executorService.execute(new DeliveryTruck(dockToTruck3, "3"));
        executorService.execute(new DeliveryTruck(dockToTruck4, "4"));

        executorService.shutdown();
    }
}
