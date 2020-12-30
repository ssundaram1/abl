import java.util.LinkedList;
import java.util.Queue;

public class ProdCon {
    static class PC{
        Queue<Integer> q;
        boolean isProd = true;
        int capacity;


        public PC(){
            q = new LinkedList<>();
        }

        public PC(int capacity){
            q = new LinkedList<>();
            this.capacity = capacity;
        }

        public synchronized void produceCap(int val){
            while(q.size() == capacity){
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(" producing val:"+ val);
            q.add(val);
            notify();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public synchronized void consumeCap(){
            while(q.isEmpty()){
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            int val = q.remove();
            System.out.println(" consuming val:"+ val);
            notify();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public synchronized void produce(int val){
            if(!isProd){
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(" producing val:"+ val);
            q.add(val);
            this.isProd = false;
            notify();
        }

        public synchronized int consume(){
            if(isProd){
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            int val = q.remove();
            this.isProd = true;
            notify();
            System.out.println(" consuming val:"+ val);
            return val;
        }

    }


    public static void main(String[] args){
        PC pc = new PC(2);
        Thread producer = new Thread( () ->{
            for(int i=1; i<=10; i++){
                pc.produce(i);
            }
        });

        Thread consumer = new Thread(() ->{
            for(int i=1; i<=10; i++){
                pc.consume();
            }
        });
        consumer.start();
        producer.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread producer1 = new Thread( () ->{
            for(int i=1; i<=10; i++){
                pc.produceCap(i);
            }
        });

        Thread consumer1 = new Thread(() ->{
            for(int i=1; i<=10; i++){
                pc.consumeCap();
            }
        });
        consumer1.start();
        producer1.start();

    }

}
