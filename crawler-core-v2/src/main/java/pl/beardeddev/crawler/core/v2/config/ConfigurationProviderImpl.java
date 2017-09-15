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

import java.io.Reader;
import java.io.StringReader;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import org.slf4j.LoggerFactory;
import pl.beardeddev.crawler.core.v2.exceptions.CoreException;
import pl.beardeddev.crawler.core.v2.model.configuration.RootElementConfig;

/**
 *
 * @author Szymon Grzelak
 */
public class ConfigurationProviderImpl implements ConfigurationProvider {
    
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ConfigurationProviderImpl.class);

    @Override
    public RootElementConfig getConfigurationFromXml(String xml) throws CoreException {
        StringReader stringReader = new StringReader(xml);
        return getConfigurationFromXml(stringReader);
    }

    @Override
    public RootElementConfig getConfigurationFromXml(Reader xml) throws CoreException {
        try {
            JAXBContext context = JAXBContext.newInstance(RootElementConfig.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            JAXBElement<RootElementConfig> rootElementConfig = (JAXBElement<RootElementConfig>) unmarshaller.unmarshal(new StreamSource(xml), RootElementConfig.class);
            return rootElementConfig.getValue();
        } catch (JAXBException ex) {
            LOGGER.error("Error while parsing xml config file", ex);
            throw new CoreException("Error while parsing xml config file");
        }
    }
    
}
