package com.lsm1998.chat.mapper;

import com.lsm1998.chat.domain.TFriends;
import com.lsm1998.chat.domain.TFriendsExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface TFriendsMapper {
    @SelectProvider(type=TFriendsSqlProvider.class, method="countByExample")
    long countByExample(TFriendsExample example);

    @DeleteProvider(type=TFriendsSqlProvider.class, method="deleteByExample")
    int deleteByExample(TFriendsExample example);

    @Delete({
        "delete from t_friends",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into t_friends (user_id, friend_id, ",
        "create_time)",
        "values (#{userId,jdbcType=BIGINT}, #{friendId,jdbcType=BIGINT}, ",
        "#{createTime,jdbcType=TIMESTAMP})"
    })
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insert(TFriends record);

    @InsertProvider(type=TFriendsSqlProvider.class, method="insertSelective")
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insertSelective(TFriends record);

    @SelectProvider(type=TFriendsSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.BIGINT),
        @Result(column="friend_id", property="friendId", jdbcType=JdbcType.BIGINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<TFriends> selectByExample(TFriendsExample example);

    @Select({
        "select",
        "id, user_id, friend_id, create_time",
        "from t_friends",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.BIGINT),
        @Result(column="friend_id", property="friendId", jdbcType=JdbcType.BIGINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP)
    })
    TFriends selectByPrimaryKey(Long id);

    @UpdateProvider(type=TFriendsSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") TFriends record, @Param("example") TFriendsExample example);

    @UpdateProvider(type=TFriendsSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") TFriends record, @Param("example") TFriendsExample example);

    @UpdateProvider(type=TFriendsSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(TFriends record);

    @Update({
        "update t_friends",
        "set user_id = #{userId,jdbcType=BIGINT},",
          "friend_id = #{friendId,jdbcType=BIGINT},",
          "create_time = #{createTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(TFriends record);
}