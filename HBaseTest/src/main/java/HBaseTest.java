import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class HBaseTest {
    private Configuration conf = null;
    private HBaseAdmin hba = null;

    @Before
    public void before() throws IOException {
        conf = HBaseConfiguration.create();
        hba = new HBaseAdmin(conf);
    }

    @After
    public void after() throws IOException {
        hba.close();
    }

    @Test
    public void createTest() throws IOException {
        TableName tn = TableName.valueOf("test");

        HTableDescriptor htd = new HTableDescriptor(tn);

        htd.addFamily(new HColumnDescriptor(Bytes.toBytes("cols1")));

        if (hba.tableExists(tn)){
            hba.disableTable(tn);
            hba.deleteTable(tn);
        }

        hba.createTable(htd);

        System.out.println("success");

        putRecord("test", "row1", "cols1", "1", "x1");
        getRecord("test", "row1");
        scan("test");
    }

    public void putRecord(String tableName, String row, String columnFamily, String column, String value) throws IOException {
        HTable ht = new HTable(conf, tableName);
        Put p = new Put(Bytes.toBytes(row));
        p.add(Bytes.toBytes(columnFamily), Bytes.toBytes(column), Bytes.toBytes(value));
        ht.put(p);
    }

    public void getRecord(String tableName, String row) throws IOException {
        HTable ht = new HTable(conf, tableName);
        Get g = new Get(Bytes.toBytes(row));
        Result result = ht.get(g);
        System.out.println(result);
    }

    public void scan(String tableName) throws IOException {
        HTable ht = new HTable(conf, tableName);
        Scan s = new Scan();
        ResultScanner rs = ht.getScanner(s);
        for (Result result : rs){
            System.out.println(result);
        }
    }
}
