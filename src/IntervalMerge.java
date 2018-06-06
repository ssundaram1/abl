import java.util.*;

class Interval {
    int start;
    int end;
    public Interval(int start, int end) {
        this.start = start;
        this.end = end;
    }
    @Override
    public String toString(){
        return "["+start+", "+end+" ]";
    }
}

class myComparator implements Comparator<Interval> {
    @Override
    public int compare(Interval i1, Interval i2) {
        if (i1.start == i2.start) {
            return 0;
        } else {
            return i1.start < i2.start? -1: 1;
        }
    }
}
//A: [1,5], [10,14], [16,18]
// B: [2,6], [8,10], [11,20]
//
// output [1,6], [8, 20]
public class IntervalMerge {
    public List<Interval> mergeList(List<Interval> l1, List<Interval> l2) {
        if (l1 == null || l1.size()  == 0) {
            return l2;
        } else if (l2 == null || l2.size() == 0) {
            return l1;
        }

        Collections.sort(l1, new myComparator());
        Collections.sort(l2, new myComparator());

        List<Interval> result = new ArrayList<>();
        int ix1 = 0;
        int ix2 = 0;
        // Get the first interval
        Interval prev = null;
        if (l1.get(0).start < l2.get(0).start) {
            prev = l1.get(0);
            ix1 ++;
        } else {
            prev = l2.get(0);
            ix2 ++;
        }
        // Move two pointers to merge lists
        while (ix1 < l1.size() || ix2 < l2.size()) {
            if (ix2 == l2.size() || (ix1 < l1.size() && l1.get(ix1).start < l2.get(ix2).start)) {
                // merge prev with ix1
                if (prev.end < l1.get(ix1).start) {
                    result.add(prev);
                    prev = l1.get(ix1);
                } else {
                    prev.end = Math.max(prev.end, l1.get(ix1).end);
                }
                ix1 ++;
            } else {
                // merge prev with ix2
                if (prev.end < l2.get(ix2).start) {
                    result.add(prev);
                    prev = l2.get(ix2);
                } else {
                    prev.end = Math.max(prev.end, l2.get(ix2).end);
                }
                ix2 ++;
            }
        }
        result.add(prev);
        return result;
    }

    private static List<Interval> mergeIntvl(List<Interval> intervals1, List<Interval> intervals2){
        Collections.sort(intervals1, new myComparator());
        Collections.sort(intervals2, new myComparator());
        int idx1 =0,idx2=0;
        Interval prev = intervals1.get(idx1);
        List<Interval> result = new ArrayList<>();
        if(intervals2.get(idx1).start < intervals1.get(idx2).start){
            prev = intervals1.get(idx1);
            idx2++;
        }else{
            idx1++;
        }

        while(idx1 < intervals1.size() && idx2 < intervals2.size()){
            Interval current = null;
            if(intervals1.get(idx1).start < intervals2.get(idx2).start){
                current = intervals1.get(idx1);
                if(prev.end >= current.start){
                    prev.end = Math.max(prev.end,current.end );
                }else{
                    result.add(prev);
                    prev = current;
                }
                idx1++;
            }else{
                current = intervals2.get(idx2);
                if(current.start <= prev.end){
                    prev.end = Math.max(prev.end,current.end );
                }else{
                    result.add(prev);
                    prev = current;
                }
                idx2++;
            }

        }
        result.add(prev);

        return result;

    }

//    You are given two lists of intervals, A and B.
//
//    In A, the intervals are sorted by their starting points. None of the intervals within A overlap.
//
//            Likewise, in B, the intervals are sorted by their starting points. None of the intervals within B overlap.
//
//    Return the intervals that overlap between the two lists.
//
//            Example:
//
//    A: {[0,4], [7,12]}
//    B: {[1,3], [5,8], [9,11]}
//    Return:
//
//    {[1,3], [7,8], [9,11]}
    private static List<Interval> mergeIntersetingIntvl(List<Interval> intervals1, List<Interval> intervals2){
        intervals1.sort(new myComparator());
        intervals2.sort(new myComparator());
        int idx1 =0 ,idx2 =0;
        Interval prev =null;
        List<Interval> result = new ArrayList<>();
        if(intervals1.get(idx1).start < intervals2.get(idx2).start){
            prev = intervals1.get(idx1);
            idx1++;
        }else{
            prev = intervals2.get(idx2);
            idx2++;
        }
        while(idx1 < intervals1.size() || idx2 < intervals2.size()){
            Interval current = null;
            if(idx2 == intervals2.size() || (idx1 < intervals1.size() && intervals1.get(idx1).start < intervals2.get(idx2).start)){
                current = intervals1.get(idx1);
                if(current.start <= prev.end){
                    prev.start = Math.max(current.start, prev.start);
                    prev.end = Math.min(current.end, prev.end);
                }else{
                    result.add(prev);
                    prev = current;
                }
                idx1++;
            }else{
                current = intervals2.get(idx2);
                if(current.start <= prev.end){
                    prev.start = Math.max(current.start, prev.start);
                    prev.end = Math.min(current.end, prev.end);
                }else{
                    result.add(prev);
                    prev = current;
                }
                idx2++;
            }

        }
        result.add(prev);
        return result;
    }



//    We are given the following labels and intervals. Merge the labels that contain overlapping intervals:
//
//    A: 1, 3
//    B: 2, 4
//    C: 7,8
//
//    The result should be:
//            [A, B], [C]





    //Given [1,3],[2,6],[8,10],[15,18],
    //return [1,6],[8,10],[15,18].
    private static List<Interval> mergeIntvl(List<Interval> intervals){
        Collections.sort(intervals,  (o1,o2)-> Integer.compare(o1.start, o2.start) );
//        int start = intervals.get(0).start;
//        int end = intervals.get(0).end;
        Interval prev = intervals.get(0);

        List<Interval> mergedIntervals = new ArrayList<>();
        for(Interval current : intervals){
            if(current.start <= prev.end){
                prev.end = Math.max(prev.end,current.end); // Overlapping intervals, move the end if needed
            }else{
                // Disjoint intervals, add the previous one and reset bounds
                mergedIntervals.add(new Interval(prev.start, prev.end));
                prev = current;
            }
        }
        mergedIntervals.add(new Interval(prev.start, prev.end));
        return mergedIntervals;

    }

    public boolean canAttendMeetings(Interval[] intervals) {
        if (intervals == null)
            return false;

        // Sort the intervals by start time
        Arrays.sort(intervals, new Comparator<Interval>() {
            public int compare(Interval a, Interval b) { return a.start - b.start; }
        });

        for (int i = 1; i < intervals.length; i++)
            if (intervals[i].start < intervals[i - 1].end)
                return false;

        return true;
    }


    //Example 2:
//    Given [1,2],[3,5],[6,7],[8,10],[12,16], insert and merge [4,9] in as [1,2],[3,10],[12,16].
//
//    This is because the new interval [4,9] overlaps with [3,5],[6,7],[8,10].

    private static List<Interval> insertInt(List<Interval> intervals, Interval newInt) {
        int i =0;
        List<Interval> result = new ArrayList<>();
        //find entry point
        while(i < intervals.size() && intervals.get(i).end <  newInt.start){
            result.add(intervals.get(i++));
        }
        //merge intervals
        while(i < intervals.size() && newInt.end >= intervals.get(i).start ){
            newInt.start = Math.min(intervals.get(i).start, newInt.start);
            newInt.end = Math.max(intervals.get(i).end, newInt.end);
            i++;
        }
        result.add(newInt);
        while (i < intervals.size()) result.add(intervals.get(i++));
        return result;

    }
    private static List<Interval> insert(List<Interval> intervals, Interval newInterval) {
        List<Interval> result = new LinkedList<>();
        int i = 0;
        // add all the intervals ending before newInterval starts
        while (i < intervals.size() && intervals.get(i).end < newInterval.start)
            result.add(intervals.get(i++));
        // merge all overlapping intervals to one considering newInterval
        while (i < intervals.size() && intervals.get(i).start <= newInterval.end) {
            newInterval = new Interval( // we could mutate newInterval here also
                    Math.min(newInterval.start, intervals.get(i).start),
                    Math.max(newInterval.end, intervals.get(i).end));
            i++;
        }
        result.add(newInterval); // add the union of intervals we got
        // add all the rest
        while (i < intervals.size()) result.add(intervals.get(i++));
        return result;
    }

    public static int minMeetingRooms(Interval[] intervals) {
        if (intervals == null || intervals.length == 0)
            return 0;

        // Sort the intervals by start time
        Arrays.sort(intervals, new Comparator<Interval>() {
            public int compare(Interval a, Interval b) { return a.start - b.start; }
        });

        // Use a min heap to track the minimum end time of merged intervals
        PriorityQueue<Interval> heap = new PriorityQueue<Interval>(intervals.length, new Comparator<Interval>() {
            public int compare(Interval a, Interval b) { return a.end - b.end; }
        });

        // start with the first meeting, put it to a meeting room
        heap.offer(intervals[0]);

        for (int i = 1; i < intervals.length; i++) {
            // get the meeting room that finishes earliest
            Interval interval = heap.poll();

            if (intervals[i].start >= interval.end) {
                // if the current meeting starts right after
                // there's no need for a new room, merge the interval
                interval.end = intervals[i].end;
            } else {
                // otherwise, this meeting needs a new room
                heap.offer(intervals[i]);
            }

            // don't forget to put the meeting room back
            heap.offer(interval);
        }

        return heap.size();
    }




    public static void main(String[] args) {
        IntervalMerge myObj = new IntervalMerge();

        List<Interval> l1 = new ArrayList<>();
        l1.add(new Interval(1, 5));
        l1.add(new Interval(10, 14));
        l1.add(new Interval(16, 18));
        l1.add(new Interval(20, 24));
        l1.add(new Interval(30, 38));
        List<Interval> l2 = new ArrayList<>();
        l2.add(new Interval(2, 6));
        l2.add(new Interval(8, 10));
        l2.add(new Interval(11, 20));

        List<Interval> result = myObj.mergeList(l1, l2);
        for (Interval i1: result) {
            System.out.println(i1.start + ", " + i1.end);
        }

        //Given [1,3],[2,6],[8,10],[15,18],
        //return [1,6],[8,10],[15,18].
        l1 = new ArrayList<>();
        l1.add(new Interval(1, 3));
        l1.add(new Interval(2, 6));
        l1.add(new Interval(8, 10));
        l1.add(new Interval(15, 18));
        System.out.println("merged intervals for single list: "+mergeIntvl(l1) );;

        //    A: {[0,4], [7,12]}
        //    B: {[1,3], [5,8], [9,11]}
        //    Return:
        //
        //    {[1,3], [7,8], [9,11]}
        l1 = new ArrayList<>();
        l1.add(new Interval(0, 4));
        l1.add(new Interval(7, 12));
        l2 = new ArrayList<>();
        l2.add(new Interval(1, 3));
        l2.add(new Interval(5, 8));
        l2.add(new Interval(9, 11));
        System.out.println("merged intersecting intervals for single list: "+mergeIntersetingIntvl(l1, l2) );

        //    Given [1,2],[3,5],[6,7],[8,10],[12,16], insert and merge [4,9] in as [1,2],[3,10],[12,16].
        l1 = Arrays.asList(new Interval(1,2),new Interval(3,5), new Interval(6,7), new Interval(8,10), new Interval(12,16));
        System.out.println(" Insert Intervals:" + insertInt(l1, new Interval(4,9)));

    }

}
