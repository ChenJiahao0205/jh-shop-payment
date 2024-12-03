package pers.jhshop.payment.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
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
 * 支付回调表
 * </p>
 *
 * @author ChenJiahao(wutiao)
 * @since 2024-12-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("payment_callbacks")
@ApiModel(value = "Callbacks对象", description = "支付回调表")
public class Callbacks extends Model<Callbacks> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "回调记录ID，主键")
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "支付ID，外键关联支付表")
    @TableField("PAYMENT_ID")
    private Integer paymentId;

    @ApiModelProperty(value = "支付回调的原始数据")
    @TableField("CALLBACK_DATA")
    private String callbackData;

    @ApiModelProperty(value = "回调处理状态（0=未处理，1=已处理）未处理表示回调尚未处理，已处理表示支付结果已经被成功记录并且订单状态已更新。")
    @TableField("CALLBACK_STATUS")
    private Boolean callbackStatus;

    @ApiModelProperty(value = "回调时间")
    @TableField("CREATED_AT")
    private LocalDateTime createdAt;

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
