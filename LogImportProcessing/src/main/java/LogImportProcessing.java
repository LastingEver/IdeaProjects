import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.mapreduce.TableOutputFormat;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogImportProcessing extends Configured implements Tool {
    @Override
    public int run(String[] strings) throws Exception {
        Configuration conf = new Configuration();
        conf.set(TableOutputFormat.OUTPUT_TABLE, "log");
        conf.set("dfs.socket.timeout", "180000");
        Job job = new Job(conf, "LogImportProcessing");
        FileInputFormat.setInputPaths(job, "hdfs://localhost:9000/user/lasting/input/log.dat");

        job.setMapperClass(MyMapper.class);
        job.setMapOutputKeyClass(LongWritable.class);
        job.setMapOutputValueClass(Text.class);
        job.setReducerClass(MyReducer.class);

        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TableOutputFormat.class);

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

    public static class MyMapper extends Mapper<LongWritable, Text, LongWritable, Text>{
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] splits = value.toString().split("\t");
            context.write(key, new Text(splits[1] + ":" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(Long.parseLong(splits[0].trim()))) + "\t" + value.toString()));
        }
    }

    public static class MyReducer extends TableReducer<LongWritable, Text, NullWritable>{
        @Override
        protected void reduce(LongWritable key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            for (Text value : values){
                String[] splits = value.toString().split("\t");

                Put p = new Put(Bytes.toBytes(splits[0]));
                p.add(Bytes.toBytes("cf"), Bytes.toBytes("date"), Bytes.toBytes(splits[1]));
                p.add(Bytes.toBytes("cf"), Bytes.toBytes("msisdn"), Bytes.toBytes(splits[2]));
                p.add(Bytes.toBytes("cf"), Bytes.toBytes("apmac"), Bytes.toBytes(splits[3]));
                p.add(Bytes.toBytes("cf"), Bytes.toBytes("acmac"), Bytes.toBytes(splits[4]));
                p.add(Bytes.toBytes("cf"), Bytes.toBytes("host"), Bytes.toBytes(splits[5]));
                p.add(Bytes.toBytes("cf"), Bytes.toBytes("siteType"), Bytes.toBytes(splits[6]));
                p.add(Bytes.toBytes("cf"), Bytes.toBytes("upPackNum"), Bytes.toBytes(splits[7]));
                p.add(Bytes.toBytes("cf"), Bytes.toBytes("downPackNum"), Bytes.toBytes(splits[8]));
                p.add(Bytes.toBytes("cf"), Bytes.toBytes("upPayLoad"), Bytes.toBytes(splits[9]));
                p.add(Bytes.toBytes("cf"), Bytes.toBytes("downPayLoad"), Bytes.toBytes(splits[10]));
                p.add(Bytes.toBytes("cf"), Bytes.toBytes("httpStatus"), Bytes.toBytes(splits[11]));

                context.write(NullWritable.get(), p);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        System.exit(ToolRunner.run(new Configuration(), new LogImportProcessing(), args));
    }
}
