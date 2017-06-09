/*
 * The MIT License
 *
 * Copyright 2017 Szymon Grzelak.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package pl.beardeddev.crawler.app.controllers;

import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import pl.beardeddev.crawler.app.utils.CollectionWrapper;

/**
 * Porada dla wszystkich kontrolerów aby przepakowywała zwracane kolekcje w obiekt {@class CollectionWrapper} w celu
 * poprawnej ich serializacji.
 * 
 * @author Szymon Grzelak
 */
@ControllerAdvice
public class CollectionWrapperControllerAdvice implements ResponseBodyAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(CollectionWrapperControllerAdvice.class);
    
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        LOGGER.debug("Return type is [{}] and converter type is [{}]", returnType.toString(), converterType.getCanonicalName());
        boolean isJacksonToHttpMessageConverter = AbstractJackson2HttpMessageConverter.class.isAssignableFrom(converterType);
        boolean isReturnTypeCollection = Collection.class.isAssignableFrom(returnType.getMethod().getReturnType());
        LOGGER.debug("Flag isJacksonToHttpMessageConverter [{}], flag isReturnTypeCollection [{}]", isJacksonToHttpMessageConverter, isReturnTypeCollection);
        return isJacksonToHttpMessageConverter && isReturnTypeCollection;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        LOGGER.debug("Wrapping body with {}", CollectionWrapper.class.getSimpleName());
        return new CollectionWrapper((Collection)body);
    }
}
