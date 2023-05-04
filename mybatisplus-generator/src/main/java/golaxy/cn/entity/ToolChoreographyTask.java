package golaxy.cn.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @author lihang
 * @since 2023-03-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ToolChoreographyTask对象", description="")
public class ToolChoreographyTask implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "任务名称")
    private String taskName;

    @ApiModelProperty(value = "任务描述")
    private String description;

    @ApiModelProperty(value = "所属机构")
    private String orgId;

    @ApiModelProperty(value = "创建人")
    private String creator;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "状态(0未开始,1运行中)")
    private Integer status;

    @ApiModelProperty(value = "dag图")
    private String taskDag;

    @ApiModelProperty(value = "更新人")
    private String modifier;

    @ApiModelProperty(value = "更新时间")
    private Date modifierTime;

    @ApiModelProperty(value = "编目id")
    private Integer catalogId;

    @ApiModelProperty(value = "是否启用")
    private Boolean enable;

    @ApiModelProperty(value = "是否删除")
    @TableLogic
    private Boolean isDeleted;


}
