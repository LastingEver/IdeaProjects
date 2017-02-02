import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class LogReducer extends Reducer<Text, LogWritable, Text, LogWritable> {
    @Override
    protected void reduce(Text key, Iterable<LogWritable> values, Context context) throws IOException, InterruptedException {
        long upPackNum = 0L;
        long downPackNum = 0L;
        long upPayLoad = 0L;
        long downPayLoad = 0L;

        for (LogWritable value : values) {
            upPackNum += value.upPackNum;
            downPackNum += value.downPackNum;
            upPayLoad += value.upPayLoad;
            downPayLoad += value.downPayLoad;
        }

        LogWritable value2 = new LogWritable(upPackNum + "", downPackNum + "", upPayLoad + "", downPayLoad + "");
        context.write(key, value2);
    }
}