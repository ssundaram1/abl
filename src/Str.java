import java.util.*;

/**
 * Created by ssundaram on 5/12/18.
 */
public class Str {

    public static Map<String,List<Integer>> wordDistance(String[] words) {
       Map<String, List<Integer>> map = new HashMap<String, List<Integer>>();
        for(int i = 0; i < words.length; i++) {
            String w = words[i];
            if(map.containsKey(w)) {
                map.get(w).add(i);
            } else {
                List<Integer> list = new ArrayList<Integer>();
                list.add(i);
                map.put(w, list);
            }
        }
        return map;
    }
    public static int shortestWordDistance(String[] words, String word1, String word2) {
        long dist = Integer.MAX_VALUE, i1 = -1, i2 = -1;
        boolean same = word1.equals(word2);
        for (int i=0; i<words.length; i++) {
            if (words[i].equals(word1)) {
                if (same) {
                    i1 = i2;
                    i2 = i;
                } else {
                    i1 = i;
                }
            } else if (words[i].equals(word2)) {
                i2 = i;
            }
            if(i1 != 1 && i2 != -1){
                dist = Math.min(dist, Math.abs(i1 - i2));
            }
        }
        return (int) dist;
    }


    public static int shortest(String word1, String word2, Map<String, List<Integer>> map) {
        List<Integer> list1 = map.get(word1);
        List<Integer> list2 = map.get(word2);
        int ret = Integer.MAX_VALUE;
        for(int i = 0, j = 0; i < list1.size() && j < list2.size(); ) {
            int index1 = list1.get(i), index2 = list2.get(j);
            if(index1 < index2) {
                ret = Math.min(ret, index2 - index1);
                i++;
            } else {
                ret = Math.min(ret, index1 - index2);
                j++;
            }
        }
        return ret;
    }

    private static class WordNode{
        String word;
        int numSteps;

        public WordNode(String word, int numSteps){
            this.word = word;
            this.numSteps = numSteps;
        }
    }

    public static int ladderLength(String beginWord, String endWord, Set<String> wordDict) {
        LinkedList<WordNode> queue = new LinkedList<WordNode>();
        queue.add(new WordNode(beginWord, 1));

        wordDict.add(endWord);

        while(!queue.isEmpty()){
            WordNode top = queue.remove();
            String word = top.word;

            if(word.equals(endWord)){
                return top.numSteps;
            }

            char[] arr = word.toCharArray();
            for(int i=0; i<arr.length; i++){
                for(char c='a'; c<='z'; c++){
                    char temp = arr[i];
                    if(arr[i]!=c){
                        arr[i]=c;
                    }

                    String newWord = new String(arr);
                    if(wordDict.contains(newWord)){
                        queue.add(new WordNode(newWord, top.numSteps+1));
                        wordDict.remove(newWord);
                    }

                    arr[i]=temp;
                }
            }
        }

        return 0;
    }

//    Input: str = "GEEKSFORGEEKS"
//    n = 3
//    Output: GSGSEKFREKEOE
//    Explanation: Let us write input string in Zig-Zag fashion
//    in 3 rows.
//      Now concatenate the two rows and ignore spaces
//    in every row. We get "GSGSEKFREKEOE"

//    G               S          G             S
//        E       K       F    R    E       K
//            E             O          E

    private static String zigZag(String s, int row){
        List<StringBuilder> levelToStr = new ArrayList<>(row);
        int i =0;
        for(int r =0; r < row; r++){
            levelToStr.add(new StringBuilder());
        }

        while(i < s.length()){
            for(int j =0; j< row && i< s.length(); j++){
                StringBuilder sb = levelToStr.get(j);
                sb.append(s.charAt(i++));
            }
            for(int k = row - 2 ; k > 0 && i < s.length() ; k --){
                StringBuilder sb =levelToStr.get(k);
                sb.append(s.charAt(i++));
            }
        }
        StringBuilder result =new StringBuilder();
        for(StringBuilder sb: levelToStr){
            result.append(sb);
        }

        return result.toString();
    }

    public static String reverseWords(String s) {
        if (s == null) return null;

        char[] a = s.toCharArray();
        int n = a.length;

        // step 1. reverse the whole string
        reverse(a, 0, n - 1);
        // step 2. reverse each word
        reverseWords(a, n);
        // step 3. clean up spaces
        return cleanSpaces(a, n);
    }

    static void reverseWords(char[] a, int n) {
        int i = 0, j = 0;

        while (i < n) {
            while (i < j || (i < n && a[i] == ' ')) i++; // skip spaces
            while (j < i || (j < n && a[j] != ' ')) j++; // skip non spaces
            reverse(a, i, j - 1);                      // reverse the word
        }
    }

    // trim leading, trailing and multiple spaces
    static String cleanSpaces(char[] a, int n) {
        int i = 0, j = 0;

        while (j < n) {
            while (j < n && a[j] == ' ') j++;             // skip spaces
            while (j < n && a[j] != ' ') a[i++] = a[j++]; // keep non spaces
            while (j < n && a[j] == ' ') j++;             // skip spaces
            if (j < n) a[i++] = ' ';                      // keep only one space
        }

        return new String(a).substring(0, i);
    }

    // reverse a[] from a[i] to a[j]
    private static void reverse(char[] a, int i, int j) {
        while (i < j) {
            char t = a[i];
            a[i++] = a[j];
            a[j--] = t;
        }
    }


    //dir\n\tsubdir1\n\t\tfile1.ext\n\t\tsubsubdir1\n\tsubdir2\n\t\tsubsubdir2\n\t\t\tfile2.ext

    public static int lengthLongestPath(String input) {
        Deque<Integer> stack = new ArrayDeque<>();
        String[] arr = input.split("\n");
        int maxLen = 0;
        stack.push(0); //dummy null length'
        int level =0;
        for (String s: arr) {
            /*
            numOfTabs is the number of "\t", numOfTabs = 0
            when "\t" is not found, because s.lastIndexOf("\t") returns -1.
            So normally, the first parent "dir" have numOfTabs 0.
            */
            int numOfTabs = s.lastIndexOf("\t") + 1;
            /* Level is defined as numOfTabs + 1.
            For example, in "dir\n\tsubdir1\n\tsubdir2\n\t\tfile.ext",
            dir is level 1, subdir1 and subdir2 are level 2, file.ext is level3
            */
            level = numOfTabs + 1;
            /*
            The following part of code is the case that we want to consider when there are
            several subdirectories in a same level. We want to remove
            the path length of the directory or the file of same level
            that we added during previous step, and calculate
            the path length of current directory or file that we are currently looking at.
            */
            //me - we need to do this when we realize we backed up a level
            //for example we considered dir/subDir1/file and next one is dir/suDir2
            //back up until the level and get that parent
            //for level 1 stack size is 1 which is parent of level 1, for lev 2 its 2 which is peek = parent ...so on
            while (level < stack.size()) {
                stack.poll();
            }
            //+1 for the trailing slash
            //parent + length of str - tabs + backslash
            int curLen = stack.peek() + s.length() - numOfTabs + 1;
            stack.push(curLen);
            if (s.contains(".")) {
                maxLen = Math.max(maxLen, curLen - 1); //Only update the maxLen when a file is discovered,
                // And remove the "/" at the end of file
            }
        }
        return maxLen;
    }

    public static String simplifyPath(String path) {
        Deque<String> stack = new LinkedList<>();
        Set<String> skip = new HashSet<>(Arrays.asList("..",".",""));
        for (String dir : path.split("/")) {
            if (dir.equals("..") && !stack.isEmpty()) stack.pop();
            else if (!skip.contains(dir)) stack.push(dir);
        }
        String res = "";
        for (String dir : stack) res = "/" + dir + res;
        return res.isEmpty() ? "/" : res;
    }

    public static int maxProduct(String[] words) {
        if (words == null || words.length == 0)
            return 0;
        int len = words.length;
        int[] value = new int[len];
        for (int i = 0; i < len; i++) {
            String tmp = words[i];
            value[i] = 0;
            for (int j = 0; j < tmp.length(); j++) {
                //set the bit at position of the character
                value[i] |= 1 << (tmp.charAt(j) - 'a');
            }
        }
        int maxProduct = 0;
        for (int i = 0; i < len; i++)
            for (int j = i + 1; j < len; j++) {
                // value[i] & value[j]) == 0 would mean they cont share any common characters
                if ((value[i] & value[j]) == 0 && (words[i].length() * words[j].length() > maxProduct))
                    maxProduct = words[i].length() * words[j].length();
            }
        return maxProduct;
    }



    public static void main(String[] args){
        Map<String, List<Integer>> map = wordDistance(new String[]{"practice", "makes", "perfect", "coding", "makes","blah","practice"});
        System.out.println("Shortest dist: "+ shortest("makes","practice",map ));
        System.out.println("shortest" + shortestWordDistance(new String[]{"practice", "makes", "perfect", "coding", "makes","blah","practice"}, "makes","makes"));
        System.out.println("word ladder: "+ ladderLength("hit", "cog", new HashSet<>(Arrays.asList("hot","dot","dog","lot","log"))));
        System.out.println(zigZag("GEEKSFORGEEKS", 3));
        System.out.println(reverseWords("the sky is blue"));
        System.out.println(lengthLongestPath("dir\n\tsubdir1\n\t\tfile1.ext\n\t\tsubsubdir1\n\tsubdir2\n\t\tsubsubdir2\n\t\t\tfile2.ext"));
        System.out.println(maxProduct(new String[]{"abcw","baz","foo","bar","xtfn","abcdef"}));
        System.out.println(maxProduct(new String[]{"a","aa","aaa","aaaa"}));
    }
}
