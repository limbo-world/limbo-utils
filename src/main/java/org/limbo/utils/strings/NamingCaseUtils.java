package org.limbo.utils.strings;

import lombok.experimental.UtilityClass;

/**
 * 变量命名格式工具
 *
 * @author Brozen
 * @since 1.0.3
 */
@UtilityClass
public class NamingCaseUtils {



    /**
     * 驼峰命名转下划线命名，变量名中，遇到大写字母则在前面插入下划线，并将大写字母转小写。
     * @param name 驼峰格式的变量名
     * @return 下划线格式的变量名
     */
    public static String camelToUnderline(String name) {
        char[] chars = name.toCharArray();
        StringBuilder newName = new StringBuilder();

        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];

            if (c >= 'A' && c <= 'Z') {
                // 如首字母大写，不在前面插入下划线
                if (i != 0) {
                    newName.append('_');
                }

                newName.append((char) (c + 32));
            } else {
                newName.append(c);
            }
        }

        return newName.toString();
    }


    /**
     * 下划线命名转驼峰命名，变量名中遇到下划线则将下划线去掉，并将下划线后面的第一个字母转大写。
     * @param name 下划线格式的变量名
     * @return 驼峰格式的变量名
     */
    public static String underlineToCamel(String name) {
        char[] chars = name.toCharArray();
        StringBuilder newName = new StringBuilder();

        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];

            // 首字母是下划线，则不处理后续字母为大写
            if (c == '_' && (i != 0) && (c = chars[++i]) >= 'a' && c <= 'z') {
                newName.append(((char) (c - 32)));
            } else if (c != '_') {
                newName.append(c);
            }
        }

        return newName.toString();
    }


}
