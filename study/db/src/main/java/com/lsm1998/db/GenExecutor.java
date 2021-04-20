/**
 * 作者：刘时明
 * 时间：2021/4/13
 */
package com.lsm1998.db;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class GenExecutor
{
    static ThreadLocal<Connection> conn;

    @Test
    public void start() throws IOException, ClassNotFoundException, InterruptedException, ExecutionException, SQLException
    {
        var gen = new GenExecutor();
        gen.run(10000000, 1000);
    }

    public GenExecutor() throws IOException, ClassNotFoundException
    {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = ThreadLocal.withInitial(() ->
        {
            try
            {
                return getConnection();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
            return null;
        });
    }

    public static Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection("jdbc:mysql://119.29.117.244:3306/some", "root", "fuck123");
    }

    private static void execute(String sql) throws SQLException
    {
        conn.get().createStatement().execute(sql);
    }

    public static Stream<Pair<Integer, Integer>> batch(int start, int end, int batch)
    {
        return IntStream.iterate(0, x -> x + 1)
                .limit((long) (Math.ceil((end - start) / batch) + 1))
                .mapToObj(i -> Pair.of(start + i * batch, Math.min(start + (i + 1) * batch, end)));
    }


    public void run(int num, int bucket) throws IOException, ClassNotFoundException, SQLException, ExecutionException, InterruptedException
    {
        var pool = Executors.newFixedThreadPool(10);
        Stream.iterate(0, x -> x + 1)
                .limit(10)
                .map(i -> pool.submit(new Worker(i, num / 10, bucket)))
                .collect(Collectors.toList())
                .forEach(future ->
                {
                    System.out.println(future);
                    try
                    {
                        future.get();
                    } catch (InterruptedException | ExecutionException e)
                    {
                        e.printStackTrace();
                    }
                });
    }

    static class Worker implements Callable<Object>
    {
        static AtomicInteger counter = new AtomicInteger(0);
        private final int id;
        private final int num;
        private final int bucket;

        public Worker(int id, int num, int bucket)
        {
            this.id = id;
            this.num = num;
            this.bucket = bucket;
        }

        @Override
        public Object call() throws IOException, ClassNotFoundException
        {
            System.out.format("run worker %d\n", id);

            // 10 threads
            var buckets = this.num / this.bucket;

            // 1 user -> 10 post
            var totalUser = this.num / 10;
            var userBuckets = buckets / 10;

            for (int j = 0; j < userBuckets; j++)
            {
                RowGen rowGen = new RowGen();
                try
                {
                    // create users
                    var sql = rowGen.genUserBatch(this.bucket);
                    // create posts
                    // 1000 users -> 100,000 posts
                    var userStart = this.id * totalUser + j * 1000;
                    var userEnd = userStart + 1000;
                    for (int i = 0; i < 10; i++)
                    {
                        var postSql = rowGen.getBatchPost(1000, userStart, userEnd);
                        execute(postSql);
                    }
                    execute(sql);
                    System.out.format("finished %d/%d\n", counter.incrementAndGet(), buckets);
                } catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }
}
