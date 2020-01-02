package com.lsm1998.chat.mapper;

import com.lsm1998.chat.domain.TUser;
import com.lsm1998.chat.domain.TUserExample.Criteria;
import com.lsm1998.chat.domain.TUserExample.Criterion;
import com.lsm1998.chat.domain.TUserExample;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;

public class TUserSqlProvider {

    public String countByExample(TUserExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("t_user");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String deleteByExample(TUserExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("t_user");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String insertSelective(TUser record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("t_user");
        
        if (record.getUsername() != null) {
            sql.VALUES("username", "#{username,jdbcType=VARCHAR}");
        }
        
        if (record.getPassword() != null) {
            sql.VALUES("password", "#{password,jdbcType=VARCHAR}");
        }
        
        if (record.getFaceImage() != null) {
            sql.VALUES("face_image", "#{faceImage,jdbcType=VARCHAR}");
        }
        
        if (record.getFaceImageBig() != null) {
            sql.VALUES("face_image_big", "#{faceImageBig,jdbcType=VARCHAR}");
        }
        
        if (record.getNickname() != null) {
            sql.VALUES("nickname", "#{nickname,jdbcType=VARCHAR}");
        }
        
        if (record.getQrcode() != null) {
            sql.VALUES("qrcode", "#{qrcode,jdbcType=VARCHAR}");
        }
        
        if (record.getCid() != null) {
            sql.VALUES("cid", "#{cid,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateTime() != null) {
            sql.VALUES("create_time", "#{createTime,jdbcType=TIMESTAMP}");
        }
        
        return sql.toString();
    }

    public String selectByExample(TUserExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("id");
        } else {
            sql.SELECT("id");
        }
        sql.SELECT("username");
        sql.SELECT("password");
        sql.SELECT("face_image");
        sql.SELECT("face_image_big");
        sql.SELECT("nickname");
        sql.SELECT("qrcode");
        sql.SELECT("cid");
        sql.SELECT("create_time");
        sql.FROM("t_user");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    public String updateByExampleSelective(Map<String, Object> parameter) {
        TUser record = (TUser) parameter.get("record");
        TUserExample example = (TUserExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("t_user");
        
        if (record.getId() != null) {
            sql.SET("id = #{record.id,jdbcType=BIGINT}");
        }
        
        if (record.getUsername() != null) {
            sql.SET("username = #{record.username,jdbcType=VARCHAR}");
        }
        
        if (record.getPassword() != null) {
            sql.SET("password = #{record.password,jdbcType=VARCHAR}");
        }
        
        if (record.getFaceImage() != null) {
            sql.SET("face_image = #{record.faceImage,jdbcType=VARCHAR}");
        }
        
        if (record.getFaceImageBig() != null) {
            sql.SET("face_image_big = #{record.faceImageBig,jdbcType=VARCHAR}");
        }
        
        if (record.getNickname() != null) {
            sql.SET("nickname = #{record.nickname,jdbcType=VARCHAR}");
        }
        
        if (record.getQrcode() != null) {
            sql.SET("qrcode = #{record.qrcode,jdbcType=VARCHAR}");
        }
        
        if (record.getCid() != null) {
            sql.SET("cid = #{record.cid,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateTime() != null) {
            sql.SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        }
        
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("t_user");
        
        sql.SET("id = #{record.id,jdbcType=BIGINT}");
        sql.SET("username = #{record.username,jdbcType=VARCHAR}");
        sql.SET("password = #{record.password,jdbcType=VARCHAR}");
        sql.SET("face_image = #{record.faceImage,jdbcType=VARCHAR}");
        sql.SET("face_image_big = #{record.faceImageBig,jdbcType=VARCHAR}");
        sql.SET("nickname = #{record.nickname,jdbcType=VARCHAR}");
        sql.SET("qrcode = #{record.qrcode,jdbcType=VARCHAR}");
        sql.SET("cid = #{record.cid,jdbcType=VARCHAR}");
        sql.SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        
        TUserExample example = (TUserExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(TUser record) {
        SQL sql = new SQL();
        sql.UPDATE("t_user");
        
        if (record.getUsername() != null) {
            sql.SET("username = #{username,jdbcType=VARCHAR}");
        }
        
        if (record.getPassword() != null) {
            sql.SET("password = #{password,jdbcType=VARCHAR}");
        }
        
        if (record.getFaceImage() != null) {
            sql.SET("face_image = #{faceImage,jdbcType=VARCHAR}");
        }
        
        if (record.getFaceImageBig() != null) {
            sql.SET("face_image_big = #{faceImageBig,jdbcType=VARCHAR}");
        }
        
        if (record.getNickname() != null) {
            sql.SET("nickname = #{nickname,jdbcType=VARCHAR}");
        }
        
        if (record.getQrcode() != null) {
            sql.SET("qrcode = #{qrcode,jdbcType=VARCHAR}");
        }
        
        if (record.getCid() != null) {
            sql.SET("cid = #{cid,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateTime() != null) {
            sql.SET("create_time = #{createTime,jdbcType=TIMESTAMP}");
        }
        
        sql.WHERE("id = #{id,jdbcType=BIGINT}");
        
        return sql.toString();
    }

    protected void applyWhere(SQL sql, TUserExample example, boolean includeExamplePhrase) {
        if (example == null) {
            return;
        }
        
        String parmPhrase1;
        String parmPhrase1_th;
        String parmPhrase2;
        String parmPhrase2_th;
        String parmPhrase3;
        String parmPhrase3_th;
        if (includeExamplePhrase) {
            parmPhrase1 = "%s #{example.oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{example.oredCriteria[%d].allCriteria[%d].value} and #{example.oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{example.oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{example.oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{example.oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        } else {
            parmPhrase1 = "%s #{oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{oredCriteria[%d].allCriteria[%d].value} and #{oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        }
        
        StringBuilder sb = new StringBuilder();
        List<Criteria> oredCriteria = example.getOredCriteria();
        boolean firstCriteria = true;
        for (int i = 0; i < oredCriteria.size(); i++) {
            Criteria criteria = oredCriteria.get(i);
            if (criteria.isValid()) {
                if (firstCriteria) {
                    firstCriteria = false;
                } else {
                    sb.append(" or ");
                }
                
                sb.append('(');
                List<Criterion> criterions = criteria.getAllCriteria();
                boolean firstCriterion = true;
                for (int j = 0; j < criterions.size(); j++) {
                    Criterion criterion = criterions.get(j);
                    if (firstCriterion) {
                        firstCriterion = false;
                    } else {
                        sb.append(" and ");
                    }
                    
                    if (criterion.isNoValue()) {
                        sb.append(criterion.getCondition());
                    } else if (criterion.isSingleValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase1, criterion.getCondition(), i, j));
                        } else {
                            sb.append(String.format(parmPhrase1_th, criterion.getCondition(), i, j,criterion.getTypeHandler()));
                        }
                    } else if (criterion.isBetweenValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase2, criterion.getCondition(), i, j, i, j));
                        } else {
                            sb.append(String.format(parmPhrase2_th, criterion.getCondition(), i, j, criterion.getTypeHandler(), i, j, criterion.getTypeHandler()));
                        }
                    } else if (criterion.isListValue()) {
                        sb.append(criterion.getCondition());
                        sb.append(" (");
                        List<?> listItems = (List<?>) criterion.getValue();
                        boolean comma = false;
                        for (int k = 0; k < listItems.size(); k++) {
                            if (comma) {
                                sb.append(", ");
                            } else {
                                comma = true;
                            }
                            if (criterion.getTypeHandler() == null) {
                                sb.append(String.format(parmPhrase3, i, j, k));
                            } else {
                                sb.append(String.format(parmPhrase3_th, i, j, k, criterion.getTypeHandler()));
                            }
                        }
                        sb.append(')');
                    }
                }
                sb.append(')');
            }
        }
        
        if (sb.length() > 0) {
            sql.WHERE(sb.toString());
        }
    }
}