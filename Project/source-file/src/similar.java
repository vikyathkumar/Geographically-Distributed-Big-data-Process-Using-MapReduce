import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class similar {
	  public static void main(String[] args) throws Exception {
	    String splitBy = ",";
	    BufferedReader br = new BufferedReader(new FileReader("/home/user/Desktop/Cybercrime/Cyber/Cybercrime.csv"));
	    String line;
	    while((line = br.readLine()) != null) {
	      String[] b = line.split(splitBy);
	      String[] c = line.split(splitBy);
	      String[] d = line.split(splitBy);
	     // String[] e = line.split(splitBy);
	     // System.out.println(b[1]);
	     // System.out.println(c[2]);
	      System.out.println(b[2]+"\t"+c[3]+"\t"+d[4]+"\t");
	    }
	    br.close();
	  }
	}
