Movie-Recommender-System:
--------------

Simple movie recommendatnion system implementation with Java based in Hadoop ecosystem

Current model:
--------------
- Collaborative recommendations using item-to-item similarity mappings

Reference:
----------
- "Item-Based Collaborative Filtering Recommendation Algorithms", Badrul Sarwar, George Karypis, Joseph Konstan, and John Riedl, ACM 1-58113-348-0/01/0005 2001



Getting started:
----------------

To run with input small data:
First, started hadoop ecosystem.
Then run:

```Java
cd MovieRecommenderSystem
hdfs dfs -mkdir /input
hdfs dfs -put input/* /input  
hdfs dfs -rm -r /dataDividedByUser
hdfs dfs -rm -r /AverageRating
hdfs dfs -rm -r /coOccurrenceMatrix
hdfs dfs -rm -r /Normalize
hdfs dfs -rm -r /Multiplication
hdfs dfs -rm -r /Sum
cd src/main/java/
hadoop com.sun.tools.javac.Main *.java
jar cf recommender.jar *.class

hadoop jar recommender.jar Driver /input /dataDividedByUser /coOccurrenceMatrix /Normalize /Multiplication /Sum /AverageRating 10001 7

#args0: original dataset

#args1: output directory for DividerByUser job

#args2: output directory for coOccurrenceMatrixBuilder job

#args3: output directory for Normalize job

#args4: output directory for Multiplication job

#args5: output directory for Sum job

#args6: output directory for AverageRating job

#args7: movieIDStartIndex

#args8: totalNumberOfMovie
```

Notes:
------
Used one MapReduce job to calculate user’s own average rating value and interpose into original rating matrix, enhanced the credibility of the model.
