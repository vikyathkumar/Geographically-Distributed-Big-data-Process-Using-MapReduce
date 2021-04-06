import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.KeyValueTextInputFormat;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.TextOutputFormat;



public class Project {
	
	public static class Map extends MapReduceBase implements Mapper<Text, Text, Text, IntWritable> {
	    public void map(Text key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
	        String splitword = key.toString();
	        String[] part = splitword.split(",");
	        
	        output.collect(new Text( part[6]), new IntWritable(1));    
	    }
	}

	public static class Red extends MapReduceBase implements Reducer<Text, IntWritable, Text, IntWritable> {
	    public void reduce(Text key, Iterator<IntWritable> values, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
	        int count = 0;
	        while (values.hasNext()) {
	            values.next();
	            ++count;
	        }
	        
	        System.out.println(key + "   " + count);
	        output.collect(key, new IntWritable(count));
	        
	           }
	}

	public static void main(String[] args) throws IOException {
        JobClient client = new JobClient();
        JobConf conf = new JobConf(Project.class);  
        
        FileInputFormat.addInputPath((JobConf)conf, new Path(args[0]));
        FileOutputFormat.setOutputPath((JobConf)conf, new Path(args[1]));

        
        conf.setJobName("combiner_partitioner");
        conf.setMapperClass(Map.class);
        conf.setReducerClass(Red.class);
        conf.setInputFormat(KeyValueTextInputFormat.class);
        conf.setOutputFormat(TextOutputFormat.class);
        conf.setOutputKeyClass(Text.class);
        conf.setOutputValueClass(IntWritable.class);
        conf.setNumReduceTasks(1);
        client.setConf((Configuration)conf);
        try {
            JobClient.runJob((JobConf)conf);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

public static class Comparator2 implements WritableComparable<Comparator2>{
	long first;
	double second;
	
	public Comparator2(){}
	
	public Comparator2(long first, double second){
		this.first = first;
		this.second = second;
	}

	public void write(DataOutput out) throws IOException {
		out.writeLong(first);
		out.writeDouble(second);
	}

	public void readFields(DataInput in) throws IOException {
		this.first = in.readLong();
		this.second = in.readDouble();
	}

	public int compareTo(Comparator2 o) {
		int comp = (int)(this.first - o.first);
		if(comp != 0){
			return comp;
		}
		return (int)(o.second - this.second);
	}
	
}

public static class Comparator1 implements WritableComparable<Comparator1> {
	long first;
	long second;

	public Comparator1() {
	}

	public Comparator1(long first, long second) {
		this.first = first;
		this.second = second;
	}

	public void write(DataOutput out) throws IOException {
		out.writeLong(first);
		out.writeLong(second);
	}

	public void readFields(DataInput in) throws IOException {
		this.first = in.readLong();
		this.second = in.readLong();
	}

	public int compareTo(Comparator1 o) {
		int comp = (int) (this.first - o.first);
		if (comp != 0) {
			return comp;
		}
		return (int) (this.second - o.second);
	}
}
}

