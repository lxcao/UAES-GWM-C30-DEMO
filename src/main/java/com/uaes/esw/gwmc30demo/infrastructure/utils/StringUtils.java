/*
 * Copyright (c) 2004- 2018 UAES-ESW
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.uaes.esw.gwmc30demo.infrastructure.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import static com.uaes.esw.gwmc30demo.constant.CommonConstants.AND_SYMBOL;
import static com.uaes.esw.gwmc30demo.constant.CommonConstants.CHARACTOERSET_UTF8;
import static com.uaes.esw.gwmc30demo.constant.CommonConstants.EQUAL_SYMBOL;

public interface StringUtils {
    // 对Map内所有value作utf8编码，拼接返回结果
    static String toQueryString(Map<?, ?> data)
            throws UnsupportedEncodingException {
        StringBuffer queryString = new StringBuffer();
        for (Map.Entry<?, ?> pair : data.entrySet()) {
            queryString.append(pair.getKey() + EQUAL_SYMBOL);
            queryString.append(URLEncoder.encode((String) pair.getValue(),
                    CHARACTOERSET_UTF8) + AND_SYMBOL);
        }
        if (queryString.length() > 0) {
            queryString.deleteCharAt(queryString.length() - 1);
        }
        return queryString.toString();
    }
}
