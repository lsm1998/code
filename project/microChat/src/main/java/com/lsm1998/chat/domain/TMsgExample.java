package com.lsm1998.chat.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Generated;

@Generated("t_msg")
public class TMsgExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Integer offset;

    public TMsgExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getOffset() {
        return offset;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andSendIdIsNull() {
            addCriterion("send_id is null");
            return (Criteria) this;
        }

        public Criteria andSendIdIsNotNull() {
            addCriterion("send_id is not null");
            return (Criteria) this;
        }

        public Criteria andSendIdEqualTo(Long value) {
            addCriterion("send_id =", value, "sendId");
            return (Criteria) this;
        }

        public Criteria andSendIdNotEqualTo(Long value) {
            addCriterion("send_id <>", value, "sendId");
            return (Criteria) this;
        }

        public Criteria andSendIdGreaterThan(Long value) {
            addCriterion("send_id >", value, "sendId");
            return (Criteria) this;
        }

        public Criteria andSendIdGreaterThanOrEqualTo(Long value) {
            addCriterion("send_id >=", value, "sendId");
            return (Criteria) this;
        }

        public Criteria andSendIdLessThan(Long value) {
            addCriterion("send_id <", value, "sendId");
            return (Criteria) this;
        }

        public Criteria andSendIdLessThanOrEqualTo(Long value) {
            addCriterion("send_id <=", value, "sendId");
            return (Criteria) this;
        }

        public Criteria andSendIdIn(List<Long> values) {
            addCriterion("send_id in", values, "sendId");
            return (Criteria) this;
        }

        public Criteria andSendIdNotIn(List<Long> values) {
            addCriterion("send_id not in", values, "sendId");
            return (Criteria) this;
        }

        public Criteria andSendIdBetween(Long value1, Long value2) {
            addCriterion("send_id between", value1, value2, "sendId");
            return (Criteria) this;
        }

        public Criteria andSendIdNotBetween(Long value1, Long value2) {
            addCriterion("send_id not between", value1, value2, "sendId");
            return (Criteria) this;
        }

        public Criteria andAcceptIdIsNull() {
            addCriterion("accept_id is null");
            return (Criteria) this;
        }

        public Criteria andAcceptIdIsNotNull() {
            addCriterion("accept_id is not null");
            return (Criteria) this;
        }

        public Criteria andAcceptIdEqualTo(Long value) {
            addCriterion("accept_id =", value, "acceptId");
            return (Criteria) this;
        }

        public Criteria andAcceptIdNotEqualTo(Long value) {
            addCriterion("accept_id <>", value, "acceptId");
            return (Criteria) this;
        }

        public Criteria andAcceptIdGreaterThan(Long value) {
            addCriterion("accept_id >", value, "acceptId");
            return (Criteria) this;
        }

        public Criteria andAcceptIdGreaterThanOrEqualTo(Long value) {
            addCriterion("accept_id >=", value, "acceptId");
            return (Criteria) this;
        }

        public Criteria andAcceptIdLessThan(Long value) {
            addCriterion("accept_id <", value, "acceptId");
            return (Criteria) this;
        }

        public Criteria andAcceptIdLessThanOrEqualTo(Long value) {
            addCriterion("accept_id <=", value, "acceptId");
            return (Criteria) this;
        }

        public Criteria andAcceptIdIn(List<Long> values) {
            addCriterion("accept_id in", values, "acceptId");
            return (Criteria) this;
        }

        public Criteria andAcceptIdNotIn(List<Long> values) {
            addCriterion("accept_id not in", values, "acceptId");
            return (Criteria) this;
        }

        public Criteria andAcceptIdBetween(Long value1, Long value2) {
            addCriterion("accept_id between", value1, value2, "acceptId");
            return (Criteria) this;
        }

        public Criteria andAcceptIdNotBetween(Long value1, Long value2) {
            addCriterion("accept_id not between", value1, value2, "acceptId");
            return (Criteria) this;
        }

        public Criteria andMsgIsNull() {
            addCriterion("msg is null");
            return (Criteria) this;
        }

        public Criteria andMsgIsNotNull() {
            addCriterion("msg is not null");
            return (Criteria) this;
        }

        public Criteria andMsgEqualTo(String value) {
            addCriterion("msg =", value, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgNotEqualTo(String value) {
            addCriterion("msg <>", value, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgGreaterThan(String value) {
            addCriterion("msg >", value, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgGreaterThanOrEqualTo(String value) {
            addCriterion("msg >=", value, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgLessThan(String value) {
            addCriterion("msg <", value, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgLessThanOrEqualTo(String value) {
            addCriterion("msg <=", value, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgLike(String value) {
            addCriterion("msg like", value, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgNotLike(String value) {
            addCriterion("msg not like", value, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgIn(List<String> values) {
            addCriterion("msg in", values, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgNotIn(List<String> values) {
            addCriterion("msg not in", values, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgBetween(String value1, String value2) {
            addCriterion("msg between", value1, value2, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgNotBetween(String value1, String value2) {
            addCriterion("msg not between", value1, value2, "msg");
            return (Criteria) this;
        }

        public Criteria andSignFlagIsNull() {
            addCriterion("sign_flag is null");
            return (Criteria) this;
        }

        public Criteria andSignFlagIsNotNull() {
            addCriterion("sign_flag is not null");
            return (Criteria) this;
        }

        public Criteria andSignFlagEqualTo(Integer value) {
            addCriterion("sign_flag =", value, "signFlag");
            return (Criteria) this;
        }

        public Criteria andSignFlagNotEqualTo(Integer value) {
            addCriterion("sign_flag <>", value, "signFlag");
            return (Criteria) this;
        }

        public Criteria andSignFlagGreaterThan(Integer value) {
            addCriterion("sign_flag >", value, "signFlag");
            return (Criteria) this;
        }

        public Criteria andSignFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("sign_flag >=", value, "signFlag");
            return (Criteria) this;
        }

        public Criteria andSignFlagLessThan(Integer value) {
            addCriterion("sign_flag <", value, "signFlag");
            return (Criteria) this;
        }

        public Criteria andSignFlagLessThanOrEqualTo(Integer value) {
            addCriterion("sign_flag <=", value, "signFlag");
            return (Criteria) this;
        }

        public Criteria andSignFlagIn(List<Integer> values) {
            addCriterion("sign_flag in", values, "signFlag");
            return (Criteria) this;
        }

        public Criteria andSignFlagNotIn(List<Integer> values) {
            addCriterion("sign_flag not in", values, "signFlag");
            return (Criteria) this;
        }

        public Criteria andSignFlagBetween(Integer value1, Integer value2) {
            addCriterion("sign_flag between", value1, value2, "signFlag");
            return (Criteria) this;
        }

        public Criteria andSignFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("sign_flag not between", value1, value2, "signFlag");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}