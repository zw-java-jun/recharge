package com.zpj.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author zpj
 * @since 2021-06-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="User对象", description="")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty(value = "手机号")
    private String number;

    @ApiModelProperty(value = "密码")
    private String pwd ;

    @ApiModelProperty(value = "名字")
    private String name;

    @ApiModelProperty(value = "积分")
    private Integer bount;

    @ApiModelProperty(value = "关注的人")
    private String focus;

    @ApiModelProperty(value = "关注我的人")
    private String fans;

}
