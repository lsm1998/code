package com.oo.mapper;

import com.oo.domain.User;
import com.oo.mapper.provider.UserProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * 作者：刘时明
 * 日期：2018/9/25
 * 时间：18:18
 * 说明：User数据库操作类
 */
@Transactional
public interface UserMapper
{
    @Select("select * from user where accNumber=#{accNumber} and passWord=#{passWord}")
    User login(User user);

    @Insert("Insert into user(nickName,accNumber,passWord,sex,age,birthDay,autoGraph,head_img) values(#{nickName},#{accNumber},#{passWord},#{sex},#{age},#{birthDay},#{autoGraph},#{head_img})")
    int saveUser(User user);

    @Select("select * from user where accNumber=#{accNumber}")
    User getUserByAccNumber(long accNumber);

    @Select("SELECT * FROM `user` WHERE accnumber in (SELECT friendsid FROM friends WHERE myid=#{accNumber})")
    List<User> getMyFriend(long accNumber);

    @Select("SELECT COUNT(id) FROM `user`")
    long getAccNumber();

    @Select("SELECT * FROM `user` WHERE accNumber in (SELECT Friendsid FROM friends WHERE myid=#{acc} and Groupid in (SELECT id FROM `group` WHERE Groupname=#{groupName}))")
    List<User> getFriendByAccAndGroupName(@Param("acc") long acc, @Param("groupName") String groupName);

    @Update("update user set head_img=#{head_img},nickName=#{nickName},sex=#{sex},passWord=#{passWord},birthDay=#{birthDay},autoGraph=#{autoGraph},port=#{port},ipAddr=#{ipAddr},flag=#{flag} where accNumber=#{accNumber}")
    int updateUser(User user);

    @Update("update user set flag=#{flag} where accNumber=#{acc}")
    int changeFlag(@Param("acc") long acc, @Param("flag") byte flag);

    @SelectProvider(type = UserProvider.class, method = "selectByMap")
    List<User> findUser(Map<String, String> map);

    @Select("select flag,nickName,ipAddr,port,accNumber,head_img from user")
    List<User> getAll();
}
