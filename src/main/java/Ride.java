public class Ride {
    public InvoiceService.Category category;
    public double distance;
    public int time;

    public Ride(double distance, int time) {
        this.distance = distance;
        this.time = time;
    }

    public Ride(double distance, int time, InvoiceService.Category category) {
        this.distance = distance;
        this.time = time;
        this.category = category;
    }
}
