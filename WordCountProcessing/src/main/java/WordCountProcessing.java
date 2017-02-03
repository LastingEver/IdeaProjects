import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;
import java.net.URI;

public class WordCountProcessing extends Configured implements Tool {

    private static final String IN_FILE = "hdfs://localhost:9000/user/lasting/input/WordCount";
    private static final String OUT_FILE = "hdfs://localhost:9000/user/lasting/output/WordCount";

    public static void main(String[] args) throws Exception{
        System.exit(ToolRunner.run(new Configuration(), new WordCountProcessing(), args));
    }

    public static class MyMapper extends Mapper<Object, Text, Text, IntWritable>{
        @Override
        protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String[] splits = value.toString().replaceAll("[^a-z^A-Z^0-9]", " ").replaceAll("\\s+", " ").trim().toLowerCase().split(" ");
            for (String s : splits){
                context.write(new Text(s), new IntWritable(1));
            }
        }
    }

    public static class MyReducer extends Reducer<Text, IntWritable, Text, IntWritable>{
        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable value : values){
                sum += value.get();
            }
            context.write(key, new IntWritable(sum));
        }
    }

    @Override
    public int run(String[] strings) throws Exception {
        FileSystem fs = FileSystem.get(new URI(IN_FILE), getConf());

        Path path = new Path(OUT_FILE);
        if (fs.exists(path)) {
            fs.delete(path, true);
        }

        Job job = new Job(getConf(), "WordCountProcessing");
        FileInputFormat.setInputPaths(job, new Path(IN_FILE));

        job.setMapperClass(MyMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        job.setCombinerClass(MyReducer.class);
        job.setReducerClass(MyReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

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