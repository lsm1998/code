package com.oo.mapper;

import com.oo.domain.Friends;
import com.oo.domain.Group;
import com.oo.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 作者：刘时明
 * 日期：2018/9/26
 * 时间：14:02
 * 说明：
 */
@Transactional
public interface FriendsMapper
{
    @Insert("Insert into Friends(myId,FriendsId,groupId) values(#{myId},#{FriendsId},#{groupId})")
    int saveFriends(Friends friends);

    @Select("SELECT * FROM friends WHERE myid=#{myid} and Groupid =(SELECT id FROM `group` WHERE groupname = #{group} and accNumber=#{myid})")
    List<Friends> getFriendsByMyAccAndGroupName(@Param("myid") int myid, @Param("group") String group);

    @Select("select * from user where accnumber in(SELECT FriendsId FROM Friends WHERE myid=#{accnumber})")
    List<User> getFriendsByMyAcc(long accnumber);

    @Update("update friends set groupId=#{groupId} where myId=#{myId} and friendsId=#{friendsId}")
    int changGroupid(@Param("myId") long myId, @Param("friendsId") long friendsId, @Param("groupId") int groupId);

    @Select("select * from friends where myId=#{myId} and friendsId=#{friendsId}")
    Friends isFriends(@Param("myId") long myId, @Param("friendsId") long friendsId);
}
