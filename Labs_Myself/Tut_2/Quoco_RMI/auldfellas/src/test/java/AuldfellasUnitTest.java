
// Imports
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import service.core.Constants;
import service.core.ClientInfo;
import service.core.Quotation;
import service.core.QuotationService;
import auldfellas.AFQService;
import org.junit.*;
import static org.junit.Assert.assertNotNull;

public class AuldfellasUnitTest {

    // Variable Declarations
    private static Registry registry;

    @BeforeClass
    public static void setup() {

        QuotationService afqService = new AFQService();

        try {

            // Create the RMI registry
            registry = LocateRegistry.createRegistry(1099);

            // Export the stub for the Auldfellas Quotation Service object
            QuotationService quotationService = (QuotationService) UnicastRemoteObject.exportObject(afqService,0);

            // Register and Label the object with the RMI Registry 
            registry.bind(Constants.AULD_FELLAS_SERVICE, quotationService);

        } catch (Exception e) {

            // Error Handling
            System.out.println("Trouble: " + e);

        }
    } 


    /**
     * Tests
    */

    @Test
    public void connectionTest() throws Exception {

        // Look up and retrieve stub of Auldfellas Quotation Service Object
        QuotationService service = (QuotationService) registry.lookup(Constants.AULD_FELLAS_SERVICE);

        // Test whether connection can be made
        assertNotNull(service);
    }

    @Test
    public void generateQuotation() throws Exception {

        // Look up and retrieve stub of Auldfellas Quotation Service Object
        QuotationService service = (QuotationService) registry.lookup(Constants.AULD_FELLAS_SERVICE);

        // Test generation of a quotation from service using test data
        Quotation generated_quotation = service.generateQuotation(new ClientInfo("Niki Collier", ClientInfo.FEMALE, 43, 0, 5, "PQR254/1"));

        // Test whether generation has been created and returned
        assertNotNull(generated_quotation);
        
    }
} 
