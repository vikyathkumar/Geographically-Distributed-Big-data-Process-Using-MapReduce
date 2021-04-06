import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.KeyValueTextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;




public class DriverConf {
	
	
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		
				JobClient client = new JobClient();
				
				//Configurations for Job set in this variable
				JobConf conf = new JobConf(DriverConf.class);
		
//				
			FileInputFormat.setInputPaths(conf, new Path(args[0]));
		 		FileOutputFormat.setOutputPath(conf, new Path(args[1]));
//		 		
				//Name of the Job
				conf.setJobName("NGSensorData");
				
				conf.setMapperClass(Map.class);
//				conf.setCombinerClass(Red.class);
				conf.setReducerClass(Red.class);
				
				
				//Formats of the Data Type of Input and Output
				conf.setInputFormat(KeyValueTextInputFormat.class);
				conf.setOutputFormat(TextOutputFormat.class);
				
				//Data type of Output key and value
				conf.setOutputKeyClass(Text.class);
				conf.setOutputValueClass(IntWritable.class);
//			
				client.setConf(conf);
				
				try{
					//Running the job with Configurations set in the conf
					JobClient.runJob(conf);
				} catch(Exception e){
					e.printStackTrace();
				}
	}

}
