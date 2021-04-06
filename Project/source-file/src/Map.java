import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;


public class Map extends MapReduceBase implements
	Mapper<Text, Text, Text, IntWritable>{
	
	String returnValidString(String parseString)
	{
		String subString="";
		String match = "]";
		for(int index=42;index<parseString.length();index++)
		{
			if(parseString.charAt(index) !=match.charAt(0) )
			{
				subString += parseString.charAt(index);
			}
			else
				break;
		}
		return subString;
		
	}

	@Override
	public void map(Text key, Text value, OutputCollector<Text, IntWritable> output,
			Reporter reporter) throws IOException {
		// TODO Auto-generated method stub
		String splitword=key.toString();
		String[] part=splitword.split(",");
		int humidity  = 1;

			Double h  = Double.parseDouble(part[2]);
			 humidity =   h.intValue();
		
			output.collect(new Text( Integer.toString(humidity)), new IntWritable(1));
		


	}


}


