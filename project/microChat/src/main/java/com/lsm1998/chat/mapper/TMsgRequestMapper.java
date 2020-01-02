package com.lsm1998.chat.mapper;

import com.lsm1998.chat.domain.TMsgRequest;
import com.lsm1998.chat.domain.TMsgRequestExample;
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

public interface TMsgRequestMapper {
    @SelectProvider(type=TMsgRequestSqlProvider.class, method="countByExample")
    long countByExample(TMsgRequestExample example);

    @DeleteProvider(type=TMsgRequestSqlProvider.class, method="deleteByExample")
    int deleteByExample(TMsgRequestExample example);

    @Delete({
        "delete from t_msg_request",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into t_msg_request (send_id, accept_id, ",
        "create_time)",
        "values (#{sendId,jdbcType=BIGINT}, #{acceptId,jdbcType=BIGINT}, ",
        "#{createTime,jdbcType=TIMESTAMP})"
    })
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insert(TMsgRequest record);

    @InsertProvider(type=TMsgRequestSqlProvider.class, method="insertSelective")
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insertSelective(TMsgRequest record);

    @SelectProvider(type=TMsgRequestSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="send_id", property="sendId", jdbcType=JdbcType.BIGINT),
        @Result(column="accept_id", property="acceptId", jdbcType=JdbcType.BIGINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<TMsgRequest> selectByExample(TMsgRequestExample example);

    @Select({
        "select",
        "id, send_id, accept_id, create_time",
        "from t_msg_request",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="send_id", property="sendId", jdbcType=JdbcType.BIGINT),
        @Result(column="accept_id", property="acceptId", jdbcType=JdbcType.BIGINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP)
    })
    TMsgRequest selectByPrimaryKey(Long id);

    @UpdateProvider(type=TMsgRequestSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") TMsgRequest record, @Param("example") TMsgRequestExample example);

    @UpdateProvider(type=TMsgRequestSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") TMsgRequest record, @Param("example") TMsgRequestExample example);

    @UpdateProvider(type=TMsgRequestSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(TMsgRequest record);

    @Update({
        "update t_msg_request",
        "set send_id = #{sendId,jdbcType=BIGINT},",
          "accept_id = #{acceptId,jdbcType=BIGINT},",
          "create_time = #{createTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(TMsgRequest record);
}