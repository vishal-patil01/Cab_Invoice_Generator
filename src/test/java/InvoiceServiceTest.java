import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class InvoiceServiceTest {
    InvoiceService invoiceService = null;
    @Before
    public void setUp() throws Exception {
        invoiceService = new InvoiceService();
    }

    @Test
    public void givenDistanceAndTime_ShouldReturnTotalFare() {
        double distance = 2.0;
        int time = 5;
        double fare = invoiceService.calculateFare(distance, time, InvoiceService.Category.NORMAL);
        Assert.assertEquals(25, fare,0.0);
    }

    @Test
    public void givenLessDistanceAndTime_ShouldReturnMinimumFare() {
        double distance = 0.1;
        int time = 1;
        double fare = invoiceService.calculateFare(distance, time, InvoiceService.Category.NORMAL);
        Assert.assertEquals(5, fare, 0.0);
    }

    @Test
    public void givenMultipleRides_ShouldReturnInvoiceSummary() {
        Ride[] rides = {new Ride(2.0, 5, InvoiceService.Category.NORMAL),
                        new Ride(0.1, 1, InvoiceService.Category.NORMAL),
                        };
        InvoiceSummary invoiceSummary = invoiceService.calculateFare(rides);
        InvoiceSummary expectedInvoiceSummary = new InvoiceSummary(2, 30);
        Assert.assertEquals(expectedInvoiceSummary, invoiceSummary);
    }

    @Test
    public void givenUserIdAndPremiumRides_ShouldReturnInvoiceSummary() {
        String userId = "a@b.com";
        Ride[] rides = {new Ride(2.0, 5, InvoiceService.Category.PREMIUM),
                        new Ride(0.1, 1, InvoiceService.Category.PREMIUM),
        };
        invoiceService.addRides(userId, rides);
        InvoiceSummary summary = invoiceService.getInvoiceSummary(userId);
        InvoiceSummary expectedInvoiceSummary = new InvoiceSummary(2, 60);
        Assert.assertEquals(expectedInvoiceSummary, summary);
    }

    @Test
    public void givenUserIdAndPremiumAndNormalRides_ShouldReturnInvoiceSummary() {
        String userId = "a@b.com";
        Ride[] rides = {new Ride(2.0, 5,InvoiceService.Category.PREMIUM),
                         new Ride(0.1, 1, InvoiceService.Category.NORMAL),
        };
        invoiceService.addRides(userId, rides);
        InvoiceSummary summary = invoiceService.getInvoiceSummary(userId);
        InvoiceSummary expectedInvoiceSummary = new InvoiceSummary(2, 45);
        Assert.assertEquals(expectedInvoiceSummary, summary);

    }
}
