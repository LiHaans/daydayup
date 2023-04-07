package com.golaxy.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @Auther: lihang
 * @Date: 2021-07-08 10:37
 * @Description:
 */
@Entity
@Data
public class SysRole {
    @Id
    @GeneratedValue
    private Long id; // 主键.
    private String name; // 角色名称,如 admin/user
    private String description; // 角色描述,用于UI显示

    // 角色 -- 权限关系：多对多
    @JsonIgnoreProperties(value = {"roles"})
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "SysRolePermission", joinColumns = {@JoinColumn(name = "roleId")}, inverseJoinColumns = {@JoinColumn(name = "permissionId")})
    private List<SysPermission> permissions;

    // 用户 -- 角色关系：多对多
    @JsonIgnoreProperties(value = {"roles"})
    @ManyToMany
    @JoinTable(name = "SysUserRole", joinColumns = {@JoinColumn(name = "roleId")}, inverseJoinColumns = {@JoinColumn(name = "uid")})
    private List<UserInfo> userInfos;// 一个角色对应多个用户
}
