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

public class FactoryAddressProcessing extends Configured implements Tool {

    private static final String IN_FILE = "hdfs://localhost:9000/user/lasting/input/FactoryAddress";
    private static final String OUT_FILE = "hdfs://localhost:9000/user/lasting/output/FactoryAddress";

    public static void main(String[] args) throws Exception{
        System.exit(ToolRunner.run(new Configuration(), new FactoryAddressProcessing(), args));
    }

    public static class MyMapper extends Mapper<Object, Text, Text, Text>{
        @Override
        protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String[] splits = value.toString().split("\t");
            if (splits[0].length() == 1){
                context.write(new Text(splits[0]), new Text("-" + splits[1]));
            } else {
                context.write(new Text(splits[1]), new Text("+" + splits[0]));
            }
        }
    }

    public static class MyReducer extends Reducer<Text, Text, Text, Text>{
        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            ArrayList<Text> factory = new ArrayList<Text>();
            ArrayList<Text> address = new ArrayList<Text>();
            for (Text value : values){
                String s = value.toString();
                if (s.startsWith("-")){
                    address.add(new Text(s.substring(1)));
                } else {
                    factory.add(new Text(s.substring(1)));
                }
            }

            for (int i = 0; i < factory.size(); i++){
                for (int j = 0; j < address.size(); j++){
                    context.write(factory.get(i), address.get(j));
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

        Job job = new Job(getConf(), "FactoryAddressProcessing");
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