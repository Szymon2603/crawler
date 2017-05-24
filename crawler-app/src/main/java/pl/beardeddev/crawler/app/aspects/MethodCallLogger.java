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
package pl.beardeddev.crawler.app.aspects;

import java.util.StringJoiner;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Aspekt do logowania wywołań metod.
 * 
 * @author Szymon Grzelak
 */
@Aspect
public class MethodCallLogger {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodCallLogger.class);
    
    @Pointcut(value = "execution(public * *(..))")
    public void anyPublicMethod() {}

    /**
     * Logowanie wywołań metod publicznych z adnotacją {@class Loggable}.
     * 
     * @param joinPoint punkt złączenia.
     * @param loggable adnotacja {@class Loggable} z metadanymi na temat dokładności logowania.
     * @return rezultat wywołania metody.
     * @throws Throwable błędy związane z wywołaniem metody jak również działaniem aspektu.
     */
    @Around(value = "anyPublicMethod() && @annotation(loggable)")
    public Object publicMethodCall(ProceedingJoinPoint joinPoint, Loggable loggable) throws Throwable {
        Signature signature = joinPoint.getSignature();
        LOGGER.debug("Call method {}", methodSignature(signature, loggable));
        logArgumentsIfNeeded(loggable, joinPoint);
        Object returnValue = joinPoint.proceed();
        logReturnValueIfNeeded(loggable, signature, returnValue);
        LOGGER.debug("End of method {} incovation", methodSignature(signature, loggable));
        return returnValue;
    }
    
    public String methodSignature(Signature signature, Loggable loggable) {
        if(loggable.shortMethodSignature()) {
            return signature.toShortString();
        } else {
            return signature.toLongString();
        }
    }

    private void logArgumentsIfNeeded(Loggable loggable, ProceedingJoinPoint joinPoint) {
        if(loggable.logArguments()) {
            StringJoiner sj = new StringJoiner(", ", "[", "]");
            for (Object arg : joinPoint.getArgs()) {
                sj.add(arg.toString());
            }
            LOGGER.debug("Arguments: {}", sj.toString());
        }
    }
    
    private void logReturnValueIfNeeded(Loggable loggable, Signature signature, Object returnValue) {
        if(loggable.logReturnValue()) {
            LOGGER.debug("Method {} return {}", signature.toLongString(), returnValue);
        }
    }
}
