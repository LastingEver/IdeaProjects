import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
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
import java.util.ArrayList;

public class ChildParentProcessing extends Configured implements Tool {

    private static final String IN_FILE = "hdfs://localhost:9000/user/lasting/input/ChildParent.txt";
    private static final String OUT_FILE = "hdfs://localhost:9000/user/lasting/output/ChildParent";

    public static void main(String[] args) throws Exception{
        System.exit(ToolRunner.run(new Configuration(), new ChildParentProcessing(), args));
    }

    public static class MyMapper extends Mapper<Object, Text, Text, Text>{
        @Override
        protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String child = value.toString().split(" ")[0];
            String parent = value.toString().split(" ")[1];
            context.write(new Text(child), new Text("-" + parent));
            context.write(new Text(parent), new Text("+" + child));
        }
    }

    public static class MyReducer extends Reducer<Text, Text, Text, Text>{
        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            ArrayList<Text> grandParent = new ArrayList<Text>();
            ArrayList<Text> grandChild = new ArrayList<Text>();
            for (Text value : values){
                String s = value.toString();
                if (s.startsWith("-")){
                    grandParent.add(new Text(s.substring(1)));
                } else {
                    grandChild.add(new Text(s.substring(1)));
                }
            }

            for (int i = 0; i < grandChild.size(); i++){
                for (int j = 0; j < grandParent.size(); j++){
                    context.write(grandChild.get(i), grandParent.get(j));
                }
            }
        }
    }

    @Override
    public int run(String[] strings) throws Exception {
        FileSystem fs = FileSystem.get(new URI(IN_FILE), getConf());

        Path path = new Path(OUT_FILE);
        if (fs.exists(path)) {
            fs.delete(path, true);
        }

        Job job = new Job(getConf(), "ChildParentProcessing");
        FileInputFormat.setInputPaths(job, new Path(IN_FILE));

        job.setMapperClass(MyMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setReducerClass(MyReducer.class);
        job.setOutputKeyClass(Text.class);

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
