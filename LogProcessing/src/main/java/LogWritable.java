import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class LogWritable implements Writable {
    public long upPackNum;
    public long downPackNum;
    public long upPayLoad;
    public long downPayLoad;

    public LogWritable() {
    }

    LogWritable(String upn, String dpn, String upl, String dpl) {
        upPackNum = Long.parseLong(upn);
        downPackNum = Long.parseLong(dpn);
        upPayLoad = Long.parseLong(upl);
        downPayLoad = Long.parseLong(dpl);
    }

    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeLong(upPackNum);
        dataOutput.writeLong(downPackNum);
        dataOutput.writeLong(upPayLoad);
        dataOutput.writeLong(downPayLoad);
    }

    public void readFields(DataInput dataInput) throws IOException {
        upPackNum = dataInput.readLong();
        downPackNum = dataInput.readLong();
        upPayLoad = dataInput.readLong();
        downPayLoad = dataInput.readLong();
    }

    @Override
    public String toString() {
        return upPackNum + "\t" + downPackNum + "\t" + upPayLoad + "\t" + downPayLoad;
    }
}