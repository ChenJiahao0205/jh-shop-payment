package pers.jhshop.payment.model.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 支付表
 * </p>
 *
 * @author ChenJiahao(wutiao)
 * @since 2024-12-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("payments")
@ApiModel(value = "Payments对象", description = "支付表")
public class Payments extends Model<Payments> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "支付记录ID，主键")
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "订单ID，外键关联订单表")
    @TableField("ORDER_ID")
    private Integer orderId;

    @ApiModelProperty(value = "支付方式（如支付宝，微信支付等）")
    @TableField("PAYMENT_METHOD")
    private String paymentMethod;

    @ApiModelProperty(value = "支付状态（0=未支付，1=已支付）")
    @TableField("PAYMENT_STATUS")
    private Boolean paymentStatus;

    @ApiModelProperty(value = "支付金额")
    @TableField("AMOUNT")
    private BigDecimal amount;

    @ApiModelProperty(value = "支付交易流水号")
    @TableField("TRANSACTION_NO")
    private String transactionNo;

    @ApiModelProperty(value = "支付时间")
    @TableField("PAYMENT_TIME")
    private LocalDateTime paymentTime;

    @ApiModelProperty(value = "创建时间")
    @TableField("CREATED_AT")
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "更新时间")
    @TableField("UPDATED_AT")
    private LocalDateTime updatedAt;

    @ApiModelProperty(value = "退款状态（0=未退款，1=已退款）")
    @TableField("REFUND_STATUS")
    private Boolean refundStatus;

    @ApiModelProperty(value = "退款时间（如果有退款）")
    @TableField("REFUND_TIME")
    private LocalDateTime refundTime;

    @ApiModelProperty(value = "描述")
    @TableField("DESCRIPTION")
    private String description;

    @ApiModelProperty(value = "生效标志(TRUE-生效, FALSE-失效)")
    @TableField("VALID_FLAG")
    private Boolean validFlag;

    @ApiModelProperty(value = "创建时间")
    @TableField("CREATE_TIME")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("UPDATE_TIME")
    private LocalDateTime updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
