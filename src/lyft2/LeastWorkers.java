package lyft2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.TreeSet;

public class LeastWorkers {

    private int numOfJobs = 0;

    private static class Interval{
        int startTime;
        int endTime;
        int workerIndex;
        public Interval(int startTime, int endTime){
            this.startTime = startTime;
            this.endTime = endTime;
        }

    }
    public static void main(String[] args){
        LeastWorkers leastWorkers = new LeastWorkers();
        List<String> inputLine = leastWorkers.readFile();
        leastWorkers.numOfJobs = Integer.parseInt(inputLine.get(0));
        List<Interval> intervals = leastWorkers.parseIntervals(leastWorkers, inputLine);
        Collections.sort(intervals, (a,b) -> Integer.compare(a.startTime, b.startTime));
        PriorityQueue<Interval> pq = new PriorityQueue<Interval>((a,b)-> a.endTime - b.endTime);
        int workerIdx =0;
        List<String> result = new ArrayList<>();
        int i =0;

        for(Interval interval: intervals){
            if(pq.isEmpty()){
                interval.workerIndex = workerIdx;
                pq.add(interval);
                result.add("J"+i++ +" "+ "W"+workerIdx++);
                continue;
            }
            //scope to optimize here since we read all the intervals having
            TreeSet<Interval> sortedWorkers = new TreeSet<>((a,b) -> Integer.compare(a.workerIndex, b.workerIndex));
            while(!pq.isEmpty() && pq.peek().endTime < interval.startTime){
                sortedWorkers.add(pq.poll());
            }
            if(sortedWorkers.size() >1){
                // multiple workers  have end times lesser than current interval start time
                interval.workerIndex = sortedWorkers.first().workerIndex;
                sortedWorkers.remove(sortedWorkers.first());
                //add other workers back
                pq.addAll(sortedWorkers);
                pq.add(interval);
                result.add("J"+i++ +" "+ "W"+interval.workerIndex);

            }else if (sortedWorkers.size() == 1){
                interval.workerIndex = sortedWorkers.first().workerIndex;
                pq.add(interval);
                result.add("J"+i++ +" "+ "W"+interval.workerIndex);

            }else{
                //empty meaning none of them have end times lesser than current interval start time
                interval.workerIndex = workerIdx;
                pq.add(interval);
                result.add("J"+i +" "+ "W"+workerIdx++);
            }
            i++;
        }
        System.out.println(workerIdx);
        for(String str: result){
            System.out.println(str);
        }
    }



    private List<Interval>  parseIntervals(LeastWorkers leastWorkers, List<String> inputLine) {
        List<Interval> intervals = new ArrayList<>();
        for(int i=1; i< inputLine.size(); i++){
            String[] inputStr = inputLine.get(i).split(" ");
            //The natural way to represent the time is as an integer t in the range 0 <= t < 24 * 60.
            String startTimeStr = inputStr[0];
            int startTimeInMutes = 60 * Integer.parseInt(startTimeStr.substring(0, 2));
            startTimeInMutes += Integer.parseInt(startTimeStr.substring(2));
            int endTime = startTimeInMutes +Integer.parseInt(inputStr[1]);
            Interval interval = new Interval(startTimeInMutes, endTime);
            intervals.add(interval);
        }
        return intervals;
    }

    private List<String> readFile() {
        List<String> input = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        System.out.println("Printing the file passed in:");
        while(sc.hasNextLine()) input.add(sc.nextLine());
        return input;
    }
}
