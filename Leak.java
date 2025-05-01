import java.util.*;
    class leaky{
    void Leaky(int n,int[] packets,int bucketsize,int outputrate){
        int bucketcontent=0;
        for(int i=0;i<n;i++){
            int incoming=packets[i];
            
            if(incoming+bucketcontent<bucketsize){
                bucketcontent +=incoming;
                System.out.println("packet of size"+ incoming+"added to bucket");
            }
            else{
                System.out.println("packet dropped bucket full!");
            }
            if(bucketcontent>0){
                int sent=min(bucketcontent,outputrate);
                bucketcontent -=sent;
                System.out.println("packets sent: "+sent);
            }
        }
        while(bucketcontent>0){
            int sent=min(bucketcontent,outputrate);

            bucketcontent -=sent;
            System.out.println("packets sent: "+sent);
        }
    }
    public int min(int a,int b){
        return (a<b)?a:b;
    }
}
public class Main{
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        System.out.println("enter the Number of packets: ");
        int n=sc.nextInt();
        int[] packets=new int[n];
        System.out.println("enter the sizes of packets :");
        for(int i=0;i<n;i++){
            packets[i]=sc.nextInt();
        }
        System.out.println("enter the bucket size: ");
        int p=sc.nextInt();
        System.out.println("enter the outputrate: ");
        int q=sc.nextInt();
        leaky l=new leaky();
        l.Leaky(n,packets,p,q);
    }
}
