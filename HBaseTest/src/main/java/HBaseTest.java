import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class HBaseTest {
    private final Logger logger = Logger.getLogger(HBaseTest.class);

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
        htd.addFamily(new HColumnDescriptor(Bytes.toBytes("cols2")));
        htd.addFamily(new HColumnDescriptor(Bytes.toBytes("cols3")));
        htd.addFamily(new HColumnDescriptor(Bytes.toBytes("cols4")));

        if (hba.tableExists(tn)){
            hba.disableTable(tn);
            hba.deleteTable(tn);
        }

        hba.createTable(htd);

        logger.info("success");
    }
}
