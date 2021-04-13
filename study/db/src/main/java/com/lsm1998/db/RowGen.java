/**
 * 作者：刘时明
 * 时间：2021/4/13
 */
package com.lsm1998.db;

import com.github.javafaker.Faker;
import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Locale;
import java.util.function.Supplier;

public class RowGen
{
    Faker faker = new Faker(Locale.CHINA);

    static ArrayList<BookLoader.Sentence> sentences = null;

    {
        try
        {
            sentences = new BookLoader().sentences();
        } catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    private String genBatch(String table, String column, int batchSize, Supplier<String> supplier)
    {
        var batch = new LinkedList<String>();
        for (int j = 0; j < batchSize; j++)
        {
            batch.add('(' + supplier.get() + ')');
        }
        var joined = String.join(",", batch);
        var sql = String.format("insert into %s (%s) values " + joined + ";", table, column);
        return sql;
    }

    public String genUserBatch(int bucketSize)
    {
        var column = "`name`, `uname`, `pwd`, `salt`, `tel`, `address`, `avatar`, `ip`";
        return this.genBatch("user", column, bucketSize, () ->
        {
            var name = faker.name().firstName() + faker.name().lastName();
            var uname = faker.name().username();
            var salt = faker.number().randomNumber(6, false);
            var pwd = faker.number().digits(6);
            pwd = Hashing.sha256().hashString(pwd + salt, Charsets.UTF_8).toString();
            var tel = faker.phoneNumber().phoneNumber();
            var address = faker.address().fullAddress();
            var avatar = faker.avatar().image();
            var ip = faker.internet().ipV4Address();
            int[] ipvals = new int[4];
            String[] parts = ip.split("\\.");
            long ipInt = 0;
            for (int k = 0; k < 4; k++)
            {
                ipInt += ipvals[k] << (24 - (8 * k));
            }


            return String.format("'%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s'",
                    name,
                    uname,
                    pwd,
                    salt,
                    tel,
                    address,
                    avatar,
                    ipInt
            );
        });
    }


    public String getBatchPost(int bucketSize, int userStart, int userEnd)
    {
        var column = "name,user_id,title,info,approve, dislike,state";
        return this.genBatch("post", column, bucketSize, () ->
        {
            var name = faker.name().fullName();
            int userId = (int) (Math.random() * (userEnd - userStart) + userStart);
            var title = faker.medical().medicineName();
            var info = sentences.get((int) (Math.random() * sentences.size())).text;
            var approve = Math.round(Math.random() * 10000);
            var dislike = Math.round(Math.random() * 10000);
            var state = Math.round(Math.random() * 4);
            // var format = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
            title = title.replace("\'", "\\\'");
            if (title.length() > 12)
            {
                title = title.substring(0, 12);
            }
            return String.format("'%s', %s, '%s','%s',%s,%s,%s",
                    name,
                    userId,
                    title,
                    info,
                    approve,
                    dislike,
                    state
            );
        });
    }
}
