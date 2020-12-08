/*
 * Copyright 2020-2024 Limbo Team (https://github.com/limbo-world).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.limbo.utils;

import com.google.common.collect.Lists;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ContextClassLoaderLocal;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Brozen
 * @date 2019/7/4 11:09 AM
 * @email brozen@qq.com
 */
public class EnhancedBeanUtils {

    static {
        ConvertUtils.register(new DateConverter(null), Date.class);
    }


    public static <S, D> D createAndCopy(S source, Class<D> clazz) {
        try {
            D newOne = clazz.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(newOne, source);
            return newOne;
        } catch (Exception e) {
            throw new IllegalStateException("拷贝数据错误！source="+source+"class="+clazz, e);
        }
    }

    public static <S, D> List<D> createAndCopyList(List<S> source, Class<D> clazz) {
        if (CollectionUtils.isEmpty(source)) {
            return Lists.newArrayList();
        }

        List<D> dest = new ArrayList<>(source.size());
        for (S s : source) {
            dest.add(createAndCopy(s, clazz));
        }
        return dest;
    }

    public static <S, D> void copyProperties(S source, D dest) {
        try {
            BeanUtils.copyProperties(dest, source);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Bean属性拷贝失败", e);
        }
    }

    public static <S, D> void copyPropertiesIgnoreNull(S source, D dest) {
        try {
            IgnoreNullBeanUtilsBean.getInstance().copyProperties(dest, source);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Bean属性拷贝失败", e);
        }
    }

    private static class IgnoreNullBeanUtilsBean extends BeanUtilsBean {
        private final Log log = LogFactory.getLog(BeanUtils.class);

        private static final ContextClassLoaderLocal<BeanUtilsBean> BEANS_BY_CLASSLOADER = new ContextClassLoaderLocal<BeanUtilsBean>() {
            // Creates the default instance used when the context classloader is unavailable
            protected BeanUtilsBean initialValue() {
                return new IgnoreNullBeanUtilsBean();
            }
        };


        public static BeanUtilsBean getInstance() {
            return BEANS_BY_CLASSLOADER.get();
        }

        public static void setInstance(BeanUtilsBean newInstance) {
            BEANS_BY_CLASSLOADER.set(newInstance);
        }

        @Override
        public void copyProperty(Object bean, String name, Object value) throws IllegalAccessException, InvocationTargetException {
            if (value == null) {
                if (log.isTraceEnabled() || log.isDebugEnabled()) {
                    log.info("copy property ignore null value for [" + name + "]");
                }
                return;
            }
            super.copyProperty(bean, name, value);
        }

        @Override
        public void setProperty(Object bean, String name, Object value) throws IllegalAccessException, InvocationTargetException {
            if (value == null) {
                if (log.isTraceEnabled() || log.isDebugEnabled()) {
                    log.info("set property ignore null value for [" + name + "]");
                }
                return;
            }
            super.setProperty(bean, name, value);
        }
    }

}
