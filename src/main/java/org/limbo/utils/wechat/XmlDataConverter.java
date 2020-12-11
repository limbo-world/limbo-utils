/*
 * Copyright 2020-2024 Limbo Team (https://github.com/limbo-world).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.limbo.utils.wechat;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.limbo.utils.wechat.cache.MetaInfoCache;
import org.limbo.utils.wechat.cache.RequestMetaInfo;
import org.limbo.utils.wechat.cache.ResponseMetaInfo;
import org.limbo.utils.wechat.request.WeChatRequest;
import org.limbo.utils.wechat.response.WeChatResponse;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.TreeMap;

/**
 * @author Brozen
 * @date 2019/10/22 2:34 PM
 */
@Slf4j
public class XmlDataConverter<REQ extends WeChatRequest<RES>, RES extends WeChatResponse> implements DataConverter<REQ, RES> {

    private Class<REQ> requestClass;

    private Class<RES> responseClass;

    private RequestMetaInfo requestMetaInfo;

    private ResponseMetaInfo responseMetaInfo;

    private ConversionService conversionService;

    public XmlDataConverter(Class<REQ> requestClass, Class<RES> responseClass) {
        this.requestClass = requestClass;
        this.responseClass = responseClass;
        if (requestClass != null) {
            this.requestMetaInfo = MetaInfoCache.parseRequest(requestClass);
        }
        if (responseClass != null) {
            this.responseMetaInfo = MetaInfoCache.parseResponse(responseClass);
        }
        conversionService = new DefaultConversionService();
    }

    public String serialize(REQ request) {
        StringBuffer xml = new StringBuffer();
        xml.append("<xml>");

        TreeMap<String, RequestMetaInfo.RequestParamInfo> paramInfos = requestMetaInfo.getParamInfo();
        paramInfos.forEach((fieldName, paramInfo) -> {
            String fieldValue = null;
            try {
                Object obj = paramInfo.getGetter().invoke(request);
                if (obj != null) {
                    fieldValue = String.valueOf(obj);
                }
            } catch (ReflectiveOperationException e) {
                log.error("反射获取Xml字段报错！", e);
            }

            if (fieldValue == null) {
                return;
            }

            if (paramInfo.getWeChatApiField().cdata()) {
                xml.append(String.format("<%s><![CDATA[%s]]</%s>", fieldName, fieldValue, fieldName));
            } else {
                xml.append(String.format("<%s>%s</%s>", fieldName, fieldValue, fieldName));
            }
        });
        xml.append("</xml>");
        return xml.toString();
    }

    public RES deserialize(String data) {
        return deserialize(data, null);
    }

    public RES deserialize(String data, RES response) {
        data = data.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");

        if (StringUtils.isBlank(data)) {
            return null;
        }

        TreeMap<String, ResponseMetaInfo.ResponseParamInfo> responseParamInfo = responseMetaInfo.getParamInfo();
        try(InputStream in = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8))) {
            if (response == null) {
                response = responseClass.newInstance();
            }

            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(in);
            Element root = doc.getRootElement();
            List children = root.getChildren();
            for (Object child : children) {
                Element ele = (Element) child;
                String tagName = ele.getName();
                ResponseMetaInfo.ResponseParamInfo paramInfo = responseParamInfo.get(tagName);
                if (paramInfo == null) {
                    continue;
                }

                String tagContent;
                List subChildren = ele.getChildren();
                if (subChildren.isEmpty()) {
                    tagContent = ele.getTextNormalize();
                } else {
                    tagContent = getChildrenText(children);
                }

                if (String.class.equals(paramInfo.getField().getType())) {
                    paramInfo.getSetter().invoke(response, tagContent);
                } else {
                    paramInfo.getSetter().invoke(response, conversionService.convert(tagContent, paramInfo.getField().getType()));
                }
            }

            return response;
        } catch (JDOMException e) {
            throw new IllegalStateException("解析Response XML 失败！" + data, e);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("解析Response XML 到Response类失败！" + data, e);
        } catch (IOException e) {
            throw new IllegalStateException("不可能！解析XML流错误！", e);
        }
    }


    /**
     * 获取子结点的xml
     */
    private String getChildrenText(List children) {
        StringBuilder sb = new StringBuilder();
        if (!children.isEmpty()) {
            for (Object aChildren : children) {
                Element e = (Element) aChildren;
                String name = e.getName();
                String value = e.getTextNormalize();
                List list = e.getChildren();
                sb.append("<").append(name).append(">");
                if (!list.isEmpty()) {
                    sb.append(getChildrenText(list));
                }
                sb.append(value);
                sb.append("</").append(name).append(">");
            }
        }

        return sb.toString();
    }
}
