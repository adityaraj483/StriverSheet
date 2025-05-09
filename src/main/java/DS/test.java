package DS;


import java.util.ArrayList;
import java.util.List;

public class test {
    public static void main(String[] args) {

    }

}

class readBuffer{
    int read(char[] buff, int n, Reader4 obj){
        int index = 0;

        while(index < n){
            char[] localBuf = new char[4];
            int bufCount = obj.read(localBuf);

            if(bufCount == 0)
                return index;

            int i = 0;
            while(index < n && i < bufCount) {
                buff[index] = localBuf[i];
                index++;
                i++;
            }
        }
        return index+1;
    }
}

