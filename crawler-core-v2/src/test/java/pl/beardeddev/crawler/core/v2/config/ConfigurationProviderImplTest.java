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
package pl.beardeddev.crawler.core.v2.config;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.transform.stream.StreamSource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.beardeddev.crawler.core.v2.exceptions.CoreException;
import pl.beardeddev.crawler.core.v2.model.configuration.RootElementConfig;

/**
 *
 * @author Szymon Grzelak
 */
public class ConfigurationProviderImplTest {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationProviderImplTest.class);
    
    private ConfigurationProviderImpl instance;
    private File xmlConfig;
    private String xmlConfigStr;
    private RootElementConfig rootElementConfig;
    
    @Before
    public void setUp() throws IOException, JAXBException {
        instance = spy(new ConfigurationProviderImpl());
        initializeRealFile();
    }
    
    private void initializeRealFile() throws IOException, JAXBException {
        xmlConfig = new File(getClass().getClassLoader().getResource("test-config.xml").getFile());
        xmlConfigStr = Files.readAllLines(Paths.get(xmlConfig.toURI()))
                .stream()
                .reduce("", (actual, next) -> actual += next);
        
        LOGGER.info("XML Config as string: {}", xmlConfigStr);
        
        rootElementConfig = (RootElementConfig) JAXBContext
                .newInstance(RootElementConfig.class)
                .createUnmarshaller()
                .unmarshal(new StreamSource(xmlConfig), RootElementConfig.class)
                .getValue();
    }
    
    @Test
    public void whenPassCorrectConfigFileThenReturnResult() throws IOException, JAXBException {
        initializeRealFile();
        RootElementConfig result = instance.getConfigurationFromXml(xmlConfigStr);
        Assert.assertEquals(rootElementConfig, result);
    }
    
    @Test
    public void whenCallWithStringParamThenCallMethodWithReader() {
        instance.getConfigurationFromXml(xmlConfigStr);
        verify(instance, times(1)).getConfigurationFromXml(any(Reader.class));
    }
    
    @Test(expected = CoreException.class)
    public void whenBadXmlThanThrowCoreException() {
        instance.getConfigurationFromXml("");
    }
}
