import java.math.BigInteger;
import java.security.SecureRandom;

public class RSAKeyGenerator {
    public static void main(String[] args) {
        // Step 1: Generate two random prime numbers p and q
        SecureRandom random = new SecureRandom();
        int bitLength = 16; // Bit length of the prime numbers (increase for better security)
        BigInteger p = BigInteger.probablePrime(bitLength, random);
        BigInteger q = BigInteger.probablePrime(bitLength, random);
        
        // Ensure p != q
        while (p.equals(q)) {
            q = BigInteger.probablePrime(bitLength, random);
        }
        
        // Step 2: Calculate n and phi(n)
        BigInteger n = p.multiply(q); // n = p * q
        BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE)); // phi = (p-1)*(q-1)
        
        // Step 3: Choose e such that 1 < e < phi and gcd(phi, e) = 1
        BigInteger e;
        do {
            e = new BigInteger(bitLength, random); // Random e
        } while (e.compareTo(BigInteger.ONE) <= 0 || e.compareTo(phi) >= 0 || !phi.gcd(e).equals(BigInteger.ONE));
        
        // Step 4: Calculate d such that (e * d) % phi = 1
        BigInteger d = e.modInverse(phi);
        
        // Step 5: Display Public and Private Keys
        System.out.println("Generated Prime Numbers:");
        System.out.println("p: " + p);
        System.out.println("q: " + q);
        System.out.println();
        System.out.println("Public Key (e, n): {" + e + ", " + n + "}");
        System.out.println("Private Key (d, n): {" + d + ", " + n + "}");
    }
}
