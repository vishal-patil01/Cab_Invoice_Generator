public class InvoiceService {
    private static double MINIMUM_COST_PER_KILOMETER;
    private static int COST_PER_TIME;
    private static double MINIMUM_FARE;
    private RideRepository rideRepository;

    enum Category{
        PREMIUM(15,2,20), NORMAL(10,1,5);
        private double minCostPerK, minimumFare;
        private int cpt;
        Category(double minCostPerK, int cpt, double minimumFare) {
            this.minCostPerK=minCostPerK;
            this.cpt= cpt;
            this.minimumFare = minimumFare;
        }
    }

    public InvoiceService() {
        this.rideRepository = new RideRepository();
    }

    public double calculateFare(double distance, int time, Category category) {
        setValue(category);
        double totalFare = distance * MINIMUM_COST_PER_KILOMETER + time * COST_PER_TIME;
        return Math.max(totalFare, MINIMUM_FARE);
    }

    public InvoiceSummary calculateFare(Ride[] rides) {
        double totalFare = 0.0;
        for(Ride ride : rides) {
            totalFare += this.calculateFare(ride.distance, ride.time, ride.category);
        }
        return new InvoiceSummary(rides.length, totalFare);
    }

    private void setValue(Category category) {
            MINIMUM_COST_PER_KILOMETER = category.minCostPerK;
            COST_PER_TIME = category.cpt;
            MINIMUM_FARE = category.minimumFare;
    }

    public void addRides(String userId, Ride[] rides) {
        rideRepository.addRides(userId, rides);
    }

    public InvoiceSummary getInvoiceSummary(String userId) {
        return this.calculateFare(rideRepository.getRides(userId));
    }
}
