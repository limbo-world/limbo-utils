package org.limbo.utils.test.strings;

import org.junit.Test;
import org.limbo.utils.strings.NamingCaseUtils;

/**
 * @author Brozen
 * @since 2022-01-05
 */
public class NamingCaseUtilsTest {

    @Test
    public void test() {

        System.out.println('A' - 'a');

        System.out.println(NamingCaseUtils.camelToUnderline("namingCaseUtilsTest"));
        System.out.println(NamingCaseUtils.camelToUnderline("namingCaseUtilsTestAC"));

        System.out.println(NamingCaseUtils.underlineToCamel("plan_@info_id"));
        System.out.println(NamingCaseUtils.underlineToCamel("_plan_info_id"));
        System.out.println(NamingCaseUtils.underlineToCamel("a_b_c"));

    }


}
