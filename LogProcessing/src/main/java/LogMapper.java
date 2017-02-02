import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class LogMapper extends Mapper<LongWritable, Text, Text, LogWritable> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] splits = value.toString().split("\t");
        Text key2 = new Text(splits[1]);
        LogWritable value2 = new LogWritable(splits[6], splits[7], splits[8], splits[9]);
        context.write(key2, value2);
    }
}