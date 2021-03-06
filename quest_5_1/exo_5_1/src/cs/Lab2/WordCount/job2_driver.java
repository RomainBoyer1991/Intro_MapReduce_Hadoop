package cs.Lab2.WordCount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
 
/**
 * WordCountsInDocuments counts the total number of words in each document and
 * produces data with the relative and total number of words for each document.
 */
public class job2_driver extends Configured implements Tool {
 
    // where to put the data in hdfs when we're done
    private static final String OUTPUT_PATH = "2-word-counts";
 
    // where to read the data from.
    private static final String INPUT_PATH = "1-word-freq";
 
    public int run(String[] args) throws Exception {
 
        Configuration conf = getConf();
        Job job = new Job(conf, "Words Counts");
 
        job.setJarByClass(job2_driver.class);
        job.setMapperClass(job2_mapper.class);
        job.setReducerClass(job2_reducer.class);
 
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
 
        FileInputFormat.addInputPath(job, new Path(INPUT_PATH));
        FileOutputFormat.setOutputPath(job, new Path(OUTPUT_PATH));
 
        return job.waitForCompletion(true) ? 0 : 1;
    }
 
    public static void main(String[] args) throws Exception {
        int res = ToolRunner.run(new Configuration(), new job2_driver(), args);
        System.exit(res);
    }
}