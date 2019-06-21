import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @作者：刘时明
 * @时间：2019/6/17-10:30
 * @作用：
 */
@Data
public class ClinicOrder
{
    private Long id;

    /*
     * 机构id
     */
    
    public Long orgId;

    /*
     * 营业点id
     */
    
    public Long agencyId;

    /*
     * 科室id
     */
    
    public Long fkDepartmentId;

    /*
     * 医生id
     */
    
    public Long fkDoctorId;

    /*
     * 病人id
     */
    
    public Long fkOutpatientId;

    /*
     * 客户端支付结果信息
     */
    
    public String payResultInfo;

    /*
     * 订单编号
     */
    
    public String orderNumber;

    /*
     * 支付宝或微信返回的订单号
     */
    
    public String tradeNo;

    /*
     * 支付宝或微信商家订单号
     */
    
    public String outTradeNo;

    /*
     * 优惠金额
     */
    
    public BigDecimal discountedPrice;

    /*
     * 实收金额
     */
    
    public BigDecimal paidAmount;

    /*
     * 医保实收(统筹)金额
     */
    
    public BigDecimal medicalInsuranceAmount;

    /*
     * 医保刷卡金额
     */
    
    public BigDecimal medicareCardAmount;

    /*
     * 现金退款总额(包含：现金、银联卡、医保刷卡的退款)
     */
    
    public BigDecimal cashBackAmount;

    /*
     * 医保(统筹)退款总额
     */
    
    public BigDecimal medicareBackAmount;

    /*
     * 支付状态 1未支付 2支付成功 3支付失败 4支付中
     */
    
    public Integer isPay;

    /*
     * 状态 1正常 2已作废 3重开票 4待退款 5部分退款 6已退款
     */
    
    public Integer orderStatus;

    /*
     * 状态 1正常 2删除
     */
    
    public Integer status;

    /*
     * 其他支付方式（0 未知1 现金 2 银行卡3 微信 4 支付宝 6.账户付款 7.医保 32.会员卡）
     */
    
    public Integer payType;

    /*
     * 费用类型 1西药2中成药3中草药 4其他
     */
    
    public Integer extraCharge;

    /*
     * 收据号
     */
    
    public String documentNo;

    /*
     * 发票信息
     */
    
    public String invoice;

    /*
     * 收据信息
     */
    public String receipt;

    /*
     * 1处方订单 2挂号订单 3零售订单 4附加费 5上门服务 6追加费用
     */
    public Integer orderType;

    /*
     * 判断该订单 是不是医保收费的订单 1=是 2=否
     */
    public Integer isMedicareOrder;

    /*
     * 上一次作废的订单编号
     */
    public String lastInvalidOrderNumber;

    /*
     * 备注
     */
    public String remark;

    /*
     * 收费人id
     */
    
    public Long creatorId;

    /*
     * 收费人名字
     */
    public String creatorName;

    /*
     * 订单添加时间
     */
    public String createTime;

    /*
     * 订单修改时间
     */
    public String upStringTime;

    /*
     * 其他支付方式支付金额
     */
    public BigDecimal otherPaycount;

    /*
     * 账户支出金额
     */
    public BigDecimal accountPaycount;

    /*
     * 支付前账户余额
     */
    public BigDecimal accountBalance;

    /*
     * 订购人id
     */
    public Long subscriberId;

    /*
     * 挂号
     */
    public Long fkRegisterId;

    /*
     * （外键）预约id
     */
    public Long fkOnlineRegisterId;

    /*
     * 支付人ID
     */
    public Long fkPaypeopleId;

    /*
     * 支付时间
     */
    public String paidTime;

    /*
     * 订单锁定人id
     */
    public Long fkLockUserId;

    /*
     * 订单锁定时间
     */
    public String lockTime;

    /*
     * 退款医生
     */
    public Long fkBackDoctorId;

    /*
     * 退费支付方式（0 未知1 现金 2 银行卡3 微信 4 支付宝 6.账户付款）
     */
    public Integer payBackType;

    /*
     * 退款时间
     */
    public String backTime;

    /*
     * 微信是否可见 1是可以
     */
    public Integer wxShowStatus;

    /*
     * 已退款总金额(单位：分)
     */
    public BigDecimal totalBackAmount;

    /*
     * 订单总金额
     */
    public BigDecimal totalAmount;

    /*
     * 支付形式（0其他 1 刷卡 2 扫码）
     */
    
    public Integer payMethod;

    /*
     * 退款原因
     */
    public String refundReason;

    public Long checkId;
    
    public String refusalReason;//拒绝原因

    public String attachmentAddress;//图片附件地址
    
    public String verifyTime;//审核时间
    
    public String verifyName;//审核人

    public Long verifyId;//审核人id

    private Integer submitTollCollector;//是否发送收银 0否 1是

    private String trueName;//患者名字
    
    private String phone;//手机号
    
    private String outpatientCid;//身份证
    
    private Integer createType;//'创建类型（1普通档案、2临时档案）',

    
    private String commonName;//通用名
    
    private String factoryName;//生产企业
    
    private String packageSpecification;//如果是药品是包装规格，如果是医疗器械是 医疗器械 规格
    
    private Integer count;//数量
    
    public Long commonUnit;//常规单位/销售单位/进货单位
    
    private Integer commonRetailPrice;//如果是药品是 常规零售价，如果是医疗器械是 销售的价格
    
    private String endTime;//时间尾
    
    private String beginTime;//时间头
    
    private List<ClinicOrder>  order;
    
    private ClinicOrder pagelIst;

    private String outpatientPhone;
    
    private String code;
    
    private String name;
    
    private Integer type;
    
    private BigDecimal price;
    
    private BigDecimal amount;
    
    private BigDecimal turnMoney;
    
    private String goodsName;
}
