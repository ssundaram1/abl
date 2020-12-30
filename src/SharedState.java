import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;

public class SharedState {

    static boolean isEven = false;
    static int counter =0;

    public synchronized void incCounter(boolean isEven){
        while(isEven != this.isEven){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        counter++;
        System.out.println(" Printing when called by "+ (isEven? " Even ": " Odd ") +" thread " + counter);
        this.isEven = !this.isEven;
        notifyAll();
    }

    static class Foo{
        int order =1;
        public synchronized void first(){
            System.out.println(" Printing from first");
           order++;
            notifyAll();
        }

        public synchronized void second(){
            while(order != 2){
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            order++;
            System.out.println("Calling from second");
            notifyAll();
        }

        public synchronized void third(){
            while(order != 3){
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            counter++;
            System.out.println("Calling from third");
            notifyAll();
        }
    }

    static class FizzBuzz{
        int cnt =1;
        int max;
        FizzTypes type = FizzTypes.None;
        enum FizzTypes{
            Fizz,
            Buzz,
            FizzBuzz,
            None
        }
         public FizzBuzz(int n){
            this.max = n;
         }
        public synchronized void allowFizzBuzz(FizzTypes fizzTypes){
           while(this.type != fizzTypes){
               try {
                   wait();
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
           switch (fizzTypes){
               case FizzBuzz:
                   System.out.println("FizzBuzz");
                   break;
               case Fizz:
                   System.out.println("Fizz");
                   break;
               case Buzz:
                   System.out.println("Buzz");
                   break;
               default:
                   System.out.println("None");
                   break;
           }
           cnt++;
           if(cnt % 3 ==0 && cnt % 5 == 0){
               type = FizzTypes.FizzBuzz;
           }else if(cnt % 3 ==0){
               type = FizzTypes.Fizz;
           }else if(cnt % 5 ==0){
               type = FizzTypes.Buzz;
           }else{
               type = FizzTypes.None;
           }
           notifyAll();
        }
    }


    static class Account implements Comparable{
        private int id;
        private int balance;
        private static int globalId;
        private final Lock lock = new ReentrantLock();

        public Account(int balance){
            this.id = ++globalId;
            this.balance = balance;
        }

        public void transferV2(Account to, int amount){
            while(true){
                try{
                    if(this.lock.tryLock()){
                        try{
                            if(to.lock.tryLock()){
                                to.balance+=amount;
                                this.balance-=amount;
                                break;
                            }
                        }finally{
                            to.lock.unlock();
                        }
                    }
                }finally{
                    this.lock.unlock();
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


        }


        public void transfer(Account to, int amount){
            Account first = this.compareTo(to) <0 ? this: to;
            Account second = this.compareTo(to) >0 ? this: to;

            synchronized (this){
                synchronized (to){
                    to.balance+=amount;
                    this.balance-=amount;
                }
            }

        }

        public static void transferAmount(Account from, Account to, int amount){
            //new Thread(()-> {from.transfer(to, amount);}).start();
            new Thread(() -> {from.transferV2(to, amount);});
        }

        @Override
        public int compareTo(Object o) {
            Account that = (Account) o;
            return Integer.compare(this.id, that.id);
        }
    }


    static class EvenPrint implements Runnable{
        SharedState sharedState;

        public EvenPrint(SharedState sharedState){
            this.sharedState= sharedState;
        }
        @Override
        public void run(){
           for(int i =2; i<=100; i+=2){
               sharedState.incCounter(true);
           }
        }
    }

    static class OddPrint implements Runnable{
        SharedState sharedState;

        public OddPrint(SharedState sharedState){
            this.sharedState= sharedState;
        }
        @Override
        public void run(){
            for(int i =1; i<=100; i+=2){
                sharedState.incCounter(false);
            }
        }
    }


    public static void main(String[] args){
//        SharedState sharedState = new SharedState();
//        Thread oddThread = new Thread(new OddPrint(sharedState));
//        Thread evenThread = new Thread(new EvenPrint(sharedState));
//        oddThread.start();
//        evenThread.start();

//        Account acc1 = new Account(100);
//        Account acc2 = new Account(10);
//        System.out.println(" account transfer ");
//        Account.transferAmount(acc2, acc1, 10);
//        Account.transferAmount(acc1, acc2, 10);
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("Acc1 balance: "+acc1.balance);
//        System.out.println("Acc2 balance: "+acc2.balance);
//        Foo foo = new Foo();
//        Thread t1 = new Thread(() ->{
//            foo.third();
//        });
//        Thread t2 = new Thread(() ->{
//            foo.second();
//        });
//        Thread t3 = new Thread(() ->{
//            foo.first();
//        });
//        t1.start();
//        t2.start();
//        t3.start();
        FizzBuzz fizzBuzz = new FizzBuzz(15);
        Thread t1= new Thread(()->{
            for(int i=1; i<=15; i++){
                if(i % 3 ==0){
                    fizzBuzz.allowFizzBuzz(FizzBuzz.FizzTypes.Fizz);
                }
            }
        });
        Thread t2= new Thread(()->{
            for(int i=1; i<=15; i++){
                if(i % 5 ==0){
                    fizzBuzz.allowFizzBuzz(FizzBuzz.FizzTypes.Buzz);
                }
            }
        });
        Thread t3= new Thread(()->{
            for(int i=1; i<=15; i++){
                if(i % 3 ==0 && i % 5 ==0){
                    fizzBuzz.allowFizzBuzz(FizzBuzz.FizzTypes.FizzBuzz);
                }
            }
        });

        Thread t4= new Thread(()->{
            for(int i=1; i<=15; i++){
                if(i % 3 !=0  && i % 5 !=0){
                    fizzBuzz.allowFizzBuzz(FizzBuzz.FizzTypes.None);
                }
            }
        });
        t1.start();
        t2.start();
        t3.start();
        t4.start();

    }


}
