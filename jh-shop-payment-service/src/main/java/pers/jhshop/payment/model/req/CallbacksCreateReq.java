package pers.jhshop.payment.model.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 支付回调表新增Req
 * </p>
 *
 * @author ChenJiahao(wutiao)
 * @since 2024-12-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "CallbacksCreateReq", description = "支付回调表新增Req")
public class CallbacksCreateReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "支付ID，外键关联支付表")
    private Integer paymentId;

    @ApiModelProperty(value = "支付回调的原始数据")
    private String callbackData;

    @ApiModelProperty(value = "回调处理状态（0=未处理，1=已处理）未处理表示回调尚未处理，已处理表示支付结果已经被成功记录并且订单状态已更新。")
    private Boolean callbackStatus;

    @ApiModelProperty(value = "回调时间")
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "生效标志(TRUE-生效, FALSE-失效)")
    private Boolean validFlag;
}
