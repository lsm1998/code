/**
 * 作者：刘时明
 * 时间：2021/4/13
 */
package com.lsm1998.db;

import com.lsm1998.util.file.MyFiles;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class BookLoader
{
    public void anotherLoad() throws URISyntaxException, IOException, InterruptedException, ExecutionException
    {
        LinkedBlockingQueue<Resource<Chapter>> queue = IntStream.range(1, 121)
                .mapToObj(this::getChapterResource)
                .collect(LinkedBlockingQueue::new, AbstractQueue::add, AbstractQueue::addAll);

        var client = ThreadLocal.withInitial(() ->
                HttpClient.newBuilder()
                        .version(HttpClient.Version.HTTP_2)
                        .build()
        );

        var pool = Executors.newFixedThreadPool(10);

        AtomicInteger counter = new AtomicInteger(0);

        var joins = new Future[10];
        for (int i = 0; i < 10; i++)
        {
            var future = pool.submit(() ->
            {
                while (true)
                {
                    var resource = queue.poll();
                    if (resource == null)
                    {
                        break;
                    }

                    try
                    {
                        var req = createRequest(resource);
                        var resp = client.get().send(
                                req,
                                HttpResponse.BodyHandlers.ofString(Charset.forName("gbk")));

                        resource.setData(Chapter.fromBodyText(resp.body()));
                        resource.save();
                        System.out.format("finised %d/%d\n", counter.incrementAndGet(), 120);
                        Thread.sleep(1000);
                    } catch (HttpTimeoutException e)
                    {
                        System.out.println("timeout retry.");
                        queue.add(resource);
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            });
            joins[i] = future;
        }

        for (int i = 0; i < joins.length; i++)
        {
            joins[i].get();
        }
    }


    public void load() throws URISyntaxException, IOException, InterruptedException
    {
        LinkedList<Resource<Chapter>> queue = IntStream.range(1, 121)
                .mapToObj(this::getChapterResource)
                .collect(LinkedList::new, LinkedList::add, LinkedList::addAll);

        var client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofMillis(2000))
                .build();

        while (queue.size() > 0)
        {
            var resource = queue.pop();
            var req = createRequest(resource);
            var resp = client.send(
                    req,
                    HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

            resource.setData(Chapter.fromBodyText(resp.body()));
            resource.save();
        }
    }


    private HttpRequest createRequest(Resource<Chapter> r) throws URISyntaxException
    {
        return HttpRequest
                .newBuilder()
                .timeout(Duration.ofMillis(2000))
                .uri(r.getURI())
                .GET()
                .build();
    }

    private Resource<Chapter> getChapterResource(int x)
    {
        return new Resource<>(
                String.format("http://www.purepen.com/hlm/%03d.htm", x),
                x);
    }

    public static class Sentence
    {
        String text;
        int chapterId;
        int offset;

        public String getText()
        {
            return text;
        }

        public int getChapterId()
        {
            return chapterId;
        }
    }

    public ArrayList<Sentence> sentences() throws IOException, ClassNotFoundException
    {
        var arr = new ArrayList<Sentence>();
        for (int i = 1; i <= 120; i++)
        {
            var file = MyFiles.getFileByResources(String.format("novel/%03d.txt", i));
            var fin = new ObjectInputStream(new FileInputStream(file));
            var chapter = (Chapter) fin.readObject();
            var sens = chapter.content.split("。");

            for (int j = 0; j < sens.length; j++)
            {
                var sentence = new Sentence();
                sentence.chapterId = i;
                sentence.text = sens[j].replaceAll("\n", "");
                arr.add(sentence);
            }
        }
        return arr;
    }

    @Test
    public void testLoad() throws IOException, ClassNotFoundException
    {
        ArrayList<Sentence> list = sentences();
        list.forEach(System.out::println);
    }

    @Test
    public void testAnotherLoad() throws URISyntaxException, IOException, InterruptedException, ExecutionException
    {
        anotherLoad();
    }
}
