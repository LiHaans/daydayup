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
@ApiModel(value="ToolManagerInfo对象", description="")
public class ToolManagerInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "工具名称")
    private String name;

    @ApiModelProperty(value = "工具描述")
    private String description;

    @ApiModelProperty(value = "来源数据id")
    private Integer srcSourceId;

    @ApiModelProperty(value = "目标数据源id")
    private Integer destSourceId;

    @ApiModelProperty(value = "机构id")
    private String orgId;

    @ApiModelProperty(value = "编目id")
    private Integer catalogId;

    @ApiModelProperty(value = "状态(1审核中，2已通过，3已驳回)")
    private Integer status;

    @ApiModelProperty(value = "启用状态(0启用，1禁用)")
    private Integer enable;

    @ApiModelProperty(value = "输入表")
    private String srcTables;

    @ApiModelProperty(value = "输出表")
    private String destTables;

    @ApiModelProperty(value = "文件地址")
    private String filePath;

    @ApiModelProperty(value = "logo地址")
    private String logo;

    @ApiModelProperty(value = "创建人")
    private String creator;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "更新人")
    private String modifier;

    @ApiModelProperty(value = "更新时间")
    private Date modifierTime;

    @ApiModelProperty(value = "版本")
    private Integer version;

    @ApiModelProperty(value = "是否删除")
    @TableLogic
    private Boolean isDeleted;


}
