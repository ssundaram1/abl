/*
 * You are encouraged to solve this task according to the task description, using any language you may know.
 * You are given a collection of ABC blocks. Just like the ones you had when you were a kid. The sample blocks are:
 *
 * (
 * (B, O),
 * (D, O),
 * (G, T),
 * (B, R)
 )
 * The goal of this task is to write a function that takes a string and a collection of blocks and determine whether you can spell the word with the blocks or not.
 * The rule is simple: Once a letter on a block is used that block cannot be used again
 * Assume each block contains at least one letter from the word
 * Example word that can be spelled: Boo, Dog, Too, Boot
 * Example word that cannot be spelled: Good
 */
import java.util.*;
public class Blocks {

    static class Block{
        Set<Character> letters;
        public Block(Set<Character> letters){
            this.letters = letters;
        }
    }

    public static boolean canSpell(String word, List<Block> inputBlock) {
        return helper(word, inputBlock, 0, new boolean[inputBlock.size()], new StringBuilder());
    }

    // used: {4},
    // blockMap: {
    //     "B": [1, 4],
    //     "O": [1, 2],
    //     "G": [3],
    //     "T": [3],
    //     "R": [4]
    // }

    public static boolean helper(String word, List<Block> inputBlock, int level, boolean[] used, StringBuilder sb){
        if(sb.toString().equals(word)){
            return true;
        }
        if(level == inputBlock.size()){
            return false;
        }
        boolean canBuild = false;
        for(int i=0; i< inputBlock.size(); i++){
            if(!used[i] && inputBlock.get(i).letters.contains(Character.valueOf(word.charAt(level)))) {
                    sb.append(word.charAt(level));
                    used[i] = true;
                    canBuild = helper(word, inputBlock, level+1, used, sb);
                    if(canBuild){
                        return true;
                    }
                    sb.setLength(sb.length() -1);
                    used[i] = false;
            }
        }
        return canBuild;

    }

    public static void main(String[] args) {
        List<Block> blocks = Arrays.asList(new Block[] {
                new Block(new HashSet(Arrays.asList(new Character[] { 'B', 'O' }))),
                new Block(new HashSet(Arrays.asList(new Character[] { 'D', 'O' }))),
                new Block(new HashSet(Arrays.asList(new Character[] { 'G', 'T' }))),
                new Block(new HashSet(Arrays.asList(new Character[] { 'B', 'R' })))
        });
        System.out.println("DOG: " + Blocks.canSpell("DOG", blocks));
         System.out.println("BOOT: " + Blocks.canSpell("BOOT", blocks));
         System.out.println("GOOD: " + Blocks.canSpell("GOOD", blocks));

        System.out.println("OOGB: " + Blocks.canSpell("OOGB", blocks));
        System.out.println("OOGBR: " + Blocks.canSpell("OOGBR", blocks));


    }
}
