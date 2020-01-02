package com.lsm1998.chat.mapper;

import com.lsm1998.chat.domain.TMsg;
import com.lsm1998.chat.domain.TMsgExample;
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

public interface TMsgMapper {
    @SelectProvider(type=TMsgSqlProvider.class, method="countByExample")
    long countByExample(TMsgExample example);

    @DeleteProvider(type=TMsgSqlProvider.class, method="deleteByExample")
    int deleteByExample(TMsgExample example);

    @Delete({
        "delete from t_msg",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into t_msg (send_id, accept_id, ",
        "msg, sign_flag, create_time)",
        "values (#{sendId,jdbcType=BIGINT}, #{acceptId,jdbcType=BIGINT}, ",
        "#{msg,jdbcType=VARCHAR}, #{signFlag,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP})"
    })
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insert(TMsg record);

    @InsertProvider(type=TMsgSqlProvider.class, method="insertSelective")
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insertSelective(TMsg record);

    @SelectProvider(type=TMsgSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="send_id", property="sendId", jdbcType=JdbcType.BIGINT),
        @Result(column="accept_id", property="acceptId", jdbcType=JdbcType.BIGINT),
        @Result(column="msg", property="msg", jdbcType=JdbcType.VARCHAR),
        @Result(column="sign_flag", property="signFlag", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<TMsg> selectByExample(TMsgExample example);

    @Select({
        "select",
        "id, send_id, accept_id, msg, sign_flag, create_time",
        "from t_msg",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="send_id", property="sendId", jdbcType=JdbcType.BIGINT),
        @Result(column="accept_id", property="acceptId", jdbcType=JdbcType.BIGINT),
        @Result(column="msg", property="msg", jdbcType=JdbcType.VARCHAR),
        @Result(column="sign_flag", property="signFlag", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP)
    })
    TMsg selectByPrimaryKey(Long id);

    @UpdateProvider(type=TMsgSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") TMsg record, @Param("example") TMsgExample example);

    @UpdateProvider(type=TMsgSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") TMsg record, @Param("example") TMsgExample example);

    @UpdateProvider(type=TMsgSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(TMsg record);

    @Update({
        "update t_msg",
        "set send_id = #{sendId,jdbcType=BIGINT},",
          "accept_id = #{acceptId,jdbcType=BIGINT},",
          "msg = #{msg,jdbcType=VARCHAR},",
          "sign_flag = #{signFlag,jdbcType=INTEGER},",
          "create_time = #{createTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(TMsg record);
}