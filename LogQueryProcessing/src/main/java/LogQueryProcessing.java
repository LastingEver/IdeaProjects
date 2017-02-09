import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

public class LogQueryProcessing {
    private static final String TABLE_NAME = "log";
    private static final String FAMILY_NAME = "cf";

    private static Configuration conf = HBaseConfiguration.create();

    public static void main(String[] args) throws IOException {
        scan(TABLE_NAME, "13600217502");
        System.out.println();
        scanPeriod(TABLE_NAME, "136");
    }

    public static void scan(String tableName, String mobileNum) throws IOException {
        HTable ht = new HTable(conf, tableName);
        Scan s = new Scan();
        s.setStartRow(Bytes.toBytes(mobileNum + ":/"));
        s.setStopRow(Bytes.toBytes(mobileNum + "::"));
        ResultScanner rs = ht.getScanner(s);
        for (Result result : rs){
            System.out.println(result);
        }
    }

    public static void scanPeriod(String tableName, String period) throws IOException {
        HTable ht = new HTable(conf, tableName);
        Scan s = new Scan();
        s.setStartRow(Bytes.toBytes(period + "/"));
        s.setStopRow(Bytes.toBytes(period + ":"));
        s.setMaxVersions(1);
        ResultScanner rs = ht.getScanner(s);
        for (Result result : rs){
            System.out.println(result);
        }
    }
}
