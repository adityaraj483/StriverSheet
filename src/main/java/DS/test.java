package DS;

import javax.swing.event.ListDataListener;
import java.util.*;

public class test {
    public static void main(String[] args) {
        List<String> questions = new ArrayList<>(List.of("MAC VSCODE", "PY AI", "JAVA OS", "PY NW"));
        List<String> volunteers = new ArrayList<>(List.of("PY NW", "AI", "JAVA NW", "JAVA, NW"));
        System.out.println(totalAssignedQuestions(questions, volunteers));
    }

    static int totalAssignedQuestions(List<String> questions, List<String> volunteers){

        int quesCount = questions.size();
        int volCount = volunteers.size();

        List<List<Integer>> adj = new ArrayList<>();
        for(int i=0;i<quesCount;i++){
            adj.add(new ArrayList<>());
        }

        for(int i=0;i<quesCount;i++){
            for(int j=0;j<volCount;j++){

                if(isCommonTags(questions.get(i), volunteers.get(j))){
                    adj.get(i).add(j);
                }
            }
        }

        int[] matched = new int[volCount];
        Arrays.fill(matched, -1);
        int totalCount = 0;
        for(int ques= 0;ques<quesCount;ques++){
            boolean[] vis = new boolean[volCount];
            if(dfs(ques, adj, matched,vis)){
                totalCount++;
            }
        }
        for(int i=0;i<matched.length;i++){

            if(matched[i] == -1)
                System.out.println("Question " + ((char) (i + 'A')) + " not assigned");
            else
                System.out.println("Question " + ((char) (i + 'A')) + ": volunteer " + (matched[i]+1));
        }
        return totalCount;
    }

    static boolean isCommonTags(String str1, String str2){
        Set<String> set = new HashSet<>(Arrays.asList(str1.split(" ")));

        for(String s : str2.split(" ")){
            if(set.contains(s))
                return true;
        }
        return false;
    }

    static boolean dfs(int ques, List<List<Integer>> adj, int[] matched, boolean[] vis){

        for(int vol : adj.get(ques)){

            if(vis[vol])
                continue;
            vis[vol] = true;
            if(matched[vol] == -1 || dfs(matched[vol], adj, matched, vis)){
                matched[vol] = ques;
                return true;
            }
            vis[vol] = false;
        }
        return false;
    }
}






