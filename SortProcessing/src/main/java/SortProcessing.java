import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.net.URI;

public class SortProcessing extends Configured implements Tool {

    private static final String IN_FILE = "hdfs://localhost:9000/user/lasting/input/sort";
    private static final String OUT_FILE = "hdfs://localhost:9000/user/lasting/output/sort";

    public static void main(String[] args) throws Exception{
        System.exit(ToolRunner.run(new Configuration(), new SortProcessing(), args));
    }

    public static class MyWritable implements Writable, WritableComparable<MyWritable>{
        long firstNum;
        long secondNum;

        public MyWritable(){

        }

        public MyWritable(long firstNum, long secondNum){
            this.firstNum = firstNum;
            this.secondNum = secondNum;
        }

        @Override
        public int compareTo(MyWritable o) {
            long min = firstNum - o.firstNum;
            if (min != 0){
                return (int)min;
            } else {
                return (int)(secondNum - o.secondNum);
            }
        }

        @Override
        public void write(DataOutput dataOutput) throws IOException {
            dataOutput.writeLong(firstNum);
            dataOutput.writeLong(secondNum);
        }

        @Override
        public void readFields(DataInput dataInput) throws IOException {
            firstNum = dataInput.readLong();
            secondNum = dataInput.readLong();
        }
    }

    public static class MyMapper extends Mapper<Object, Text, MyWritable, LongWritable>{
        @Override
        protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String[] splits = value.toString().split("\t");
            long firstNum = Long.parseLong(splits[0]);
            long secondNum = Long.parseLong(splits[1]);
            MyWritable mw = new MyWritable(firstNum, secondNum);
            context.write(mw, new LongWritable(secondNum));
        }
    }

    public static class MyReducer extends Reducer<MyWritable, LongWritable, LongWritable, LongWritable>{
        @Override
        protected void reduce(MyWritable key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {
            context.write(new LongWritable(key.firstNum), new LongWritable(key.secondNum));
        }
    }

    @Override
    public int run(String[] strings) throws Exception {
        FileSystem fs = FileSystem.get(new URI(IN_FILE), getConf());

        Path path = new Path(OUT_FILE);
        if (fs.exists(path)) {
            fs.delete(path, true);
        }

        Job job = new Job(getConf(), "SortProcessing");
        FileInputFormat.setInputPaths(job, new Path(IN_FILE));

        job.setMapperClass(MyMapper.class);
        job.setMapOutputKeyClass(MyWritable.class);
        job.setMapOutputValueClass(LongWritable.class);
        job.setReducerClass(MyReducer.class);
        job.setOutputKeyClass(LongWritable.class);
        job.setOutputValueClass(LongWritable.class);

        FileOutputFormat.setOutputPath(job, new Path(OUT_FILE));

        boolean result = job.waitForCompletion(true);
        if (result) {
            System.out.println("success");
            System.exit(0);
        } else {
            System.out.println("fail");
            System.exit(1);
        }

        return 0;
    }
}