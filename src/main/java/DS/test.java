package DS;

import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;

class Person{
    int personId, startTime, endTime;
    Person(int personId, int startTime, int endTime){
        this.personId = personId;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
public class test {
    //34. Given an encoded string in form of "ab[cd]{2}def"
    //You have to return decoded string "abcdcddef"
//    Example 1:
//    Input: "ab[cd]{2}"
//    Output: "abcdcd"
//    Example 2:
//    Input: "def[ab[cd]{2}]{3}ghi"
//    Output: "defabcdcdabcdcdabcdcdghi"
    public static void main(String[] args) {
        System.out.println(decodeString("3[a2[b]]a"));
//        System.out.println(decodeString("3[a2[z]]b"));
    }

    private static String decodeString(String s) {

        Stack<String> stringStack = new Stack<>();
        Stack<Integer> countStack = new Stack<>();
        int count = 0;
        StringBuilder sb = new StringBuilder();
        int i=0, n = s.length();

        while(i < n){
            char ch = s.charAt(i);
            if(Character.isDigit(ch)){
                count = count * 10 + ch - '0';
            }else if (ch == '['){
                countStack.add(count);
                stringStack.add(sb.toString());
                sb = new StringBuilder();
                count = 0;
            } else if(Character.isLetter(ch)){
                sb.append(ch);
            } else {
               String prev =  stringStack.pop();
               int cnt = countStack.pop();
               String curr = prev + sb.toString().repeat(cnt);
               sb = new StringBuilder(curr);
            }
            i++;
        }
        while(!stringStack.isEmpty()){
            sb.insert(0, stringStack.pop());
        }
        return sb.toString();
    }


}