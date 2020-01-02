package com.lsm1998.chat.mapper;

import com.lsm1998.chat.domain.TUser;
import com.lsm1998.chat.domain.TUserExample;
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

public interface TUserMapper {
    @SelectProvider(type=TUserSqlProvider.class, method="countByExample")
    long countByExample(TUserExample example);

    @DeleteProvider(type=TUserSqlProvider.class, method="deleteByExample")
    int deleteByExample(TUserExample example);

    @Delete({
        "delete from t_user",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into t_user (username, password, ",
        "face_image, face_image_big, ",
        "nickname, qrcode, ",
        "cid, create_time)",
        "values (#{username,jdbcType=VARCHAR}, #{password,jdbcType=VARCHAR}, ",
        "#{faceImage,jdbcType=VARCHAR}, #{faceImageBig,jdbcType=VARCHAR}, ",
        "#{nickname,jdbcType=VARCHAR}, #{qrcode,jdbcType=VARCHAR}, ",
        "#{cid,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP})"
    })
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insert(TUser record);

    @InsertProvider(type=TUserSqlProvider.class, method="insertSelective")
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insertSelective(TUser record);

    @SelectProvider(type=TUserSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="username", property="username", jdbcType=JdbcType.VARCHAR),
        @Result(column="password", property="password", jdbcType=JdbcType.VARCHAR),
        @Result(column="face_image", property="faceImage", jdbcType=JdbcType.VARCHAR),
        @Result(column="face_image_big", property="faceImageBig", jdbcType=JdbcType.VARCHAR),
        @Result(column="nickname", property="nickname", jdbcType=JdbcType.VARCHAR),
        @Result(column="qrcode", property="qrcode", jdbcType=JdbcType.VARCHAR),
        @Result(column="cid", property="cid", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<TUser> selectByExample(TUserExample example);

    @Select({
        "select",
        "id, username, password, face_image, face_image_big, nickname, qrcode, cid, create_time",
        "from t_user",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="username", property="username", jdbcType=JdbcType.VARCHAR),
        @Result(column="password", property="password", jdbcType=JdbcType.VARCHAR),
        @Result(column="face_image", property="faceImage", jdbcType=JdbcType.VARCHAR),
        @Result(column="face_image_big", property="faceImageBig", jdbcType=JdbcType.VARCHAR),
        @Result(column="nickname", property="nickname", jdbcType=JdbcType.VARCHAR),
        @Result(column="qrcode", property="qrcode", jdbcType=JdbcType.VARCHAR),
        @Result(column="cid", property="cid", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP)
    })
    TUser selectByPrimaryKey(Long id);

    @UpdateProvider(type=TUserSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") TUser record, @Param("example") TUserExample example);

    @UpdateProvider(type=TUserSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") TUser record, @Param("example") TUserExample example);

    @UpdateProvider(type=TUserSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(TUser record);

    @Update({
        "update t_user",
        "set username = #{username,jdbcType=VARCHAR},",
          "password = #{password,jdbcType=VARCHAR},",
          "face_image = #{faceImage,jdbcType=VARCHAR},",
          "face_image_big = #{faceImageBig,jdbcType=VARCHAR},",
          "nickname = #{nickname,jdbcType=VARCHAR},",
          "qrcode = #{qrcode,jdbcType=VARCHAR},",
          "cid = #{cid,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(TUser record);
}