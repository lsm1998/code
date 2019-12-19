package com.oo.mapper;

import com.oo.domain.Msg;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 作者：刘时明
 * 日期：2018/10/8
 * 时间：12:16
 * 说明：
 */
@Transactional
public interface MsgMapper
{
    @Insert("Insert into msg(myid,friendid,content,flag) values(#{myId},#{friendId},#{content},#{flag})")
    int saveMsg(Msg msg);

    @Select("select * from msg where flag=0 and friendid=#{acc}")
    List<Msg> getMsgByAcc(long acc);

    @Select("select count(myid) from msg where flag=0 and friendid=#{acc}")
    int getFriendNum(long acc);

    @Update("update msg set flag=1 where friendid=#{acc}")
    int readMsg(long acc);
}
