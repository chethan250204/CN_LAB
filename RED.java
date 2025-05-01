import java.util.*;

public class RED {
    private double minThreshold;
    private double maxThreshold;
    private double maxDropProbability;
    private int currentQueue;
    private int queueSize;

    // Constructor
    public RED(double minThreshold, double maxThreshold, double maxDropProbability, int queueSize) {
        this.minThreshold = minThreshold;
        this.maxThreshold = maxThreshold;
        this.maxDropProbability = maxDropProbability;
        this.queueSize = queueSize;
        this.currentQueue = 0; // Initialize the current queue to 0
    }

    // Enqueue a packet
    public boolean enqueuePacket() {
        if (currentQueue >= queueSize) {
            System.out.println("Packet dropped (queue full)");
            return false;
        }

        double dropProbability = calculateDropProbability();
        if (dropProbability > 0 && shouldDrop(dropProbability)) {
            System.out.println("Packet dropped (RED)");
            return false;
        }

        currentQueue++;
        System.out.println("Packet enqueued. Current queue size: " + currentQueue);
        return true;
    }

    // Calculate drop probability
    private double calculateDropProbability() {
        if (currentQueue < minThreshold) {
            return 0.0;
        } else if (currentQueue >= maxThreshold) {
            return 1.0;
        } else {
            return maxDropProbability * (currentQueue - minThreshold) / (maxThreshold - minThreshold);
        }
    }

    // Determine if a packet should be dropped
    private boolean shouldDrop(double probability) {
        Random random = new Random();
        return random.nextDouble() < probability;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter min threshold, max threshold, max drop probability, queue size in order:");
        double minTh = scanner.nextDouble();
        double maxTh = scanner.nextDouble();
        double maxProb = scanner.nextDouble();
        int size = scanner.nextInt();

        RED red = new RED(minTh, maxTh, maxProb, size);

        // Simulate packet arrivals
        for (int i = 0; i < 10; i++) {
            red.enqueuePacket();
        }
    }
}
