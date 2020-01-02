/*
 * 作者：刘时明
 * 时间：2019/12/28-18:31
 * 作用：
 */
package jmh;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Collection;

public class HelloJmh
{
    public static void main(String[] args) throws RunnerException, InterruptedException
    {
        Options options=new OptionsBuilder()
                .warmupIterations(2)
                .measurementIterations(2)
                .forks(1)
                .build();
        Collection<RunResult> run = new Runner(options).run();

        Thread.sleep(1000000000);
    }

    @Benchmark
    public void testStringAdd()
    {

    }
}
