package pers.jhshop.payment.model.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 支付表新增Req
 * </p>
 *
 * @author ChenJiahao(wutiao)
 * @since 2024-12-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "PaymentsCreateReq", description = "支付表新增Req")
public class PaymentsCreateReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单ID，外键关联订单表")
    private Integer orderId;

    @ApiModelProperty(value = "支付方式（如支付宝，微信支付等）")
    private String paymentMethod;

    @ApiModelProperty(value = "支付状态（0=未支付，1=已支付）")
    private Boolean paymentStatus;

    @ApiModelProperty(value = "支付金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "支付交易流水号")
    private String transactionNo;

    @ApiModelProperty(value = "支付时间")
    private LocalDateTime paymentTime;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updatedAt;

    @ApiModelProperty(value = "退款状态（0=未退款，1=已退款）")
    private Boolean refundStatus;

    @ApiModelProperty(value = "退款时间（如果有退款）")
    private LocalDateTime refundTime;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "生效标志(TRUE-生效, FALSE-失效)")
    private Boolean validFlag;
}
