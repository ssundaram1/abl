import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class StreamPractise {

    public static void main(String[] args){
        List<Integer> list = Arrays.asList(1,2,3);
        Predicate<Integer> predicate = i -> i % 2 == 0;
        //Stream.of(list).filter(i => predicate.test(i))
    }
}
