/**
 * 作者：刘时明
 * 时间：2021/4/13
 */
package com.lsm1998.db;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class Resource<T>
{
    String uri;
    int id;

    public T getData()
    {
        return data;
    }

    public void setData(T data)
    {
        this.data = data;
    }

    T data;

    CompletableFuture<HttpResponse<String>> future;

    public CompletableFuture<HttpResponse<String>> getFuture()
    {
        return future;
    }

    public void setFuture(CompletableFuture<HttpResponse<String>> future)
    {
        this.future = future;
    }

    public Resource(String uri, int id)
    {
        this.uri = uri;
        this.id = id;
    }

    public URI getURI() throws URISyntaxException
    {
        return new URI(this.uri);
    }


    public void save() throws IOException
    {
        var file = new File("./data/novel/" + this.id + ".txt");
        try (var objectOutStream = new ObjectOutputStream(new FileOutputStream(file)))
        {
            objectOutStream.writeObject(this.data);
            objectOutStream.flush();
        }
    }
}
