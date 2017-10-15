import java.io.IOException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class AverageRating {
	public static class AverageRatingMapper extends Mapper<LongWritable, Text, Text, Text> {

		// map method
		@Override
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

			//input user,movie,rating
			String[] line = value.toString().trim().split(",");
			context.write(new Text(line[0]), new Text(line[1] + ":" + line[2]));
			//output key:user Value:movie:rating
		}
	}

	public static class AverageRatingReducer extends Reducer<Text, Text, Text, Text> {
        int movieIDStart, movieIDSize;
        @Override
        public void setup(Context context) {
            Configuration conf = context.getConfiguration();
            movieIDStart = conf.getInt("movieIDStart", 10001);
            movieIDSize = conf.getInt("movieIDSize", 7);
        }
		// reduce method
		@Override
		public void reduce(Text key, Iterable<Text> values, Context context)
				throws IOException, InterruptedException {
			//key:user values: a list of movie
			int sum = 0;
			int num = 0;
			Map<Integer, Double> map = new HashMap<Integer, Double>();
			while (values.iterator().hasNext()) {
				String[] movie_rating = values.iterator().next().toString().split(":");
				Integer movie = Integer.parseInt(movie_rating[0]);
				double rating = Double.parseDouble(movie_rating[1]);
				num++;
				sum+=rating;
				map.put(movie, rating);
			}
			double avg = (double)sum/num;
			double rating = avg;

			for (int i = movieIDStart + 0; i < movieIDStart + movieIDSize; i++) {
				if (map.containsKey(i)) {
					rating = map.get(i);
				}
				else {
				    rating = avg;
                }
				String outputValue = i + "," + rating;
				context.write(key, new Text(outputValue));
			}
		}
	}

	public static void main(String[] args) throws Exception {

		Configuration conf = new Configuration();
        String start = args[2];
        String size = args[3];
		conf.set("movieIDStart", start);
		conf.set("movieIDSize", size);

		Job job = Job.getInstance(conf);
		job.setMapperClass(AverageRatingMapper.class);
		job.setReducerClass(AverageRatingReducer.class);

		job.setJarByClass(AverageRating.class);

		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		TextInputFormat.setInputPaths(job, new Path(args[0]));
		TextOutputFormat.setOutputPath(job, new Path(args[1]));

		job.waitForCompletion(true);
	}

}
