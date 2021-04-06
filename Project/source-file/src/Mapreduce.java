import java.io.IOException;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.FirstKeyOnlyFilter;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Job;

public class Mapreduce {

    static class Mapper1 extends TableMapper<ImmutableBytesWritable, IntWritable> {

       
        private static final IntWritable one = new IntWritable(1);

        @Override
        public void map(ImmutableBytesWritable row, Result values, Context context) throws IOException {
           
        	String Data=new String(row.get());
        	String Split_data[]=Data.split(":");
        	switch(Split_data[0])
        	{
        	case "09":Split_data[1]="10";break;
        	case "10":Split_data[1]="11";break;
        	case "11":Split_data[1]="12";break;
        	case "12":Split_data[1]="13";break;
        	case "13":Split_data[1]="14";break;
        	case "14":Split_data[1]="15";break;
        	case "16":Split_data[1]="17";break;
        	case "15":Split_data[1]="16";break;
        	case "17":Split_data[1]="18";break;
        	case "18":Split_data[1]="19";break;
        	}
     
            ImmutableBytesWritable userKey = new ImmutableBytesWritable(Bytes.toBytes(Split_data[0]+" to "+Split_data[1]));
            try {
                context.write(userKey, one);
            } catch (InterruptedException e) {
                throw new IOException(e);
            }
           
            
        }
    }

    public static class Reducer1 extends TableReducer<ImmutableBytesWritable, IntWritable, ImmutableBytesWritable> {

        @SuppressWarnings("deprecation")
		public void reduce(ImmutableBytesWritable key, Iterable<IntWritable> values, Context context)
                throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable val : values) {
                sum += val.get();
            }

            Put put = new Put(key.get());
            put.add(Bytes.toBytes("details"), Bytes.toBytes("total"), Bytes.toBytes(String.valueOf(sum)));
            System.out.println(String.format("stats :   key : %d,  count : %d", Bytes.toInt(key.get()), sum));
            context.write(key, put);
        }
    }
    
    @SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception {
        HBaseConfiguration conf = new HBaseConfiguration();
        Job job = new Job(conf, "");
        job.setJarByClass(Mapreduce.class);
        Scan scan = new Scan();
        String columns = "station";
        scan.addFamily(Bytes.toBytes(columns));
        scan.setFilter(new FirstKeyOnlyFilter());
        TableMapReduceUtil.initTableMapperJob(args[0], scan, Mapper1.class, ImmutableBytesWritable.class,
                IntWritable.class, job);
        TableMapReduceUtil.initTableReducerJob(args[1], Reducer1.class, job);
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}

