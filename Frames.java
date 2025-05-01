import java.util.*;


// Define the Frame class
class Frame {
    public int sequenceNumber;
    public String data;

    // Constructor
    public Frame(int sequenceNumber, String data) {
        this.sequenceNumber = sequenceNumber;
        this.data = data;
    }
    @Override
    public String toString() {
        return "Frame{sequenceNumber=" + sequenceNumber + ", data='" + data + "'}";
    }
}

// Main class
public class BubbleSortFrames {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of frames: ");
        int n = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        Frame[] frames= new Frame[n];
        // Input frame details
        for (int i = 0; i < n; i++) {
            System.out.print("Enter sequence number for frame " + (i + 1) + ": ");
            int sequenceNumber = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            System.out.print("Enter data for frame " + (i + 1) + ": ");
            String data = scanner.nextLine();

             frames[i]=new Frame(sequenceNumber,data);
        }

        System.out.println("\nFrames before sorting:");
        for (int i=0;i<n;i++) {
            System.out.println(frames[i].toString());
        }

        // Sort frames using Bubble Sort
        bubbleSort(frames,n);

        System.out.println("\nFrames after sorting by sequence number:");
        for (int i=0;i<n;i++) {
            System.out.println(frames[i].toString());
        }

        scanner.close();
    }

    // Bubble Sort implementation for frames
    private static void bubbleSort(Frame[] frames,int n) {
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (frames[j].sequenceNumber> frames[j+1].sequenceNumber) {
                    // Swap frames[j] and frames[j + 1]
                    Frame temp = frames[j];
                    frames[j]=frames[j+1];
                    frames[j+1]=temp;
                }
            }
        }
    }
}
