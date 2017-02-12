import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class LogCleanProcessing extends Configured implements Tool {
    private static final String IN_FILE = "hdfs://localhost:9000/user/lasting/input/clean.log";
    private static final String OUT_FILE = "hdfs://localhost:9000/user/lasting/output/clean";

    public static void main(String[] args) throws Exception {
        System.exit(ToolRunner.run(new Configuration(), new LogCleanProcessing(), args));
    }

    @Override
    public int run(String[] strings) throws Exception {
        FileSystem fs = FileSystem.get(new URI(IN_FILE), getConf());

        Path path = new Path(OUT_FILE);
        if (fs.exists(path)) {
            fs.delete(path, true);
        }

        Job job = new Job(getConf(), "LogCleanProcessing");
        FileInputFormat.setInputPaths(job, new Path(IN_FILE));

        job.setMapperClass(MyMapper.class);
        job.setMapOutputKeyClass(LongWritable.class);
        job.setMapOutputValueClass(Text.class);
        job.setReducerClass(MyReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

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

    public static class LogParser{
        public String[] parseAll(String s) throws ParseException {
            String ip = s.split("- -")[0].trim();
            String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new SimpleDateFormat("d/MMM/yyyy:HH:mm:ss", Locale.ENGLISH).parse(s.substring(s.indexOf("[") + 1, s.lastIndexOf("+0800]")).trim()));
            String url = s.substring(s.indexOf("\"") + 1, s.lastIndexOf("\""));
            String status = s.substring(s.lastIndexOf("\"") + 1).trim().split(" ")[0];
            String traffic = s.substring(s.lastIndexOf("\"") + 1).trim().split(" ")[1];

            return new String[]{ip, time, url, status, traffic};
        }
    }

    public static class MyMapper extends Mapper<LongWritable, Text, LongWritable, Text>{
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] parses = new String[]{};
            try {
                parses = new LogParser().parseAll(value.toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (parses[2].startsWith("GET /static/") || parses[2].startsWith("GET /uc_server")){
                return;
            }

            if (parses[2].startsWith("GET /")){
                parses[2] = parses[2].substring("GET /".length());
            } else if (parses[2].startsWith("POST /")){
                parses[2] = parses[2].substring("POST /".length());
            }

            if (parses[2].endsWith(" HTTP/1.1")){
                parses[2] = parses[2].substring(0, parses[2].length() - " HTTP/1.1".length());
            }

            context.write(key, new Text(parses[0] + "\t" + parses[1] + "\t" + parses[2]));
        }
    }

    public static class MyReducer extends Reducer<LongWritable, Text, Text, NullWritable>{
        @Override
        protected void reduce(LongWritable key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            for (Text value : values){
                context.write(value, NullWritable.get());
            }
        }
    }
}
