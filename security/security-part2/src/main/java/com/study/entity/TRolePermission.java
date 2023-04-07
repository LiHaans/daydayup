package com.golaxy.entity;

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
 * @author creator
 * @since 2021-11-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="TRolePermission对象", description="")
public class TRolePermission implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "role_id", type = IdType.ID_WORKER)
    private String roleId;

    private String permissionId;


}
