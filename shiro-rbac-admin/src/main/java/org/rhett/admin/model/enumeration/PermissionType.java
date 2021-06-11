package org.rhett.admin.model.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PermissionType {
    MENU(1, "菜单"),
    BUTTON(2, "按钮");

    private final int code;
    private final String name;
}
