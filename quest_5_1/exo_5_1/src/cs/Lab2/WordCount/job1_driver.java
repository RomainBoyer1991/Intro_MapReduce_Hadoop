package cs.Lab2.WordCount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;


/*
 * WordCount creates the index of the words in documents,
 * mapping each of them to their frequency
 */

public class job1_driver extends Configured implements Tool {
	// where to put the data in hdfs whn we're done
	private static final String OUTPUT_PATH = "1-word-freq";
	
	// where to read the data from
	private static final String INPUT_PATH = "input";
	
	public int run(String[] args) throws Exception {
		
		Configuration conf = getConf();
		Job job = new Job(conf, "Word Frequence in Document");
		
		job.setJarByClass(job1_driver.class);
		job.setMapperClass(job1_mapper.class);
		job.setReducerClass(job1_reducer.class);
		job.setCombinerClass(job1_reducer.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		FileInputFormat.addInputPath(job,  new Path(INPUT_PATH));
		FileOutputFormat.setOutputPath(job, new Path(OUTPUT_PATH));
		
		return job.waitForCompletion(true) ? 0 : 1;
	}

	public static void main(String[] args) throws Exception {
		int res = ToolRunner.run(new Configuration(), new job1_driver(), args);
		System.exit(res);
	}
	
}
