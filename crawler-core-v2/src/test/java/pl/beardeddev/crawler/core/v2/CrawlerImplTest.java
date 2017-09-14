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
package pl.beardeddev.crawler.core.v2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import pl.beardeddev.crawler.core.v2.config.ConfigurationProviderImpl;
import pl.beardeddev.crawler.core.v2.model.CollectedElement;
import pl.beardeddev.crawler.core.v2.model.CollectedElement.Attribute;
import pl.beardeddev.crawler.core.v2.model.configuration.RootElementConfig;
import pl.beardeddev.crawler.core.v2.model.configuration.Type;
import pl.beardeddev.crawler.core.v2.wrappers.URLWrapper;

/**
 *
 * @author Szymon Grzelak
 */
public class CrawlerImplTest {
    
    private CrawlerImpl instance;
    private URLWrapper firstUrl;
    private DocumentProvider documentProvider;
    private Document document;
    private Elements elements;
    private Element element;
    private RootElementConfig configuration;
    
    @Before
    public void setUp() {
        firstUrl = mock(URLWrapper.class);
        documentProvider = mock(DocumentProvider.class);
        document = mock(Document.class);
        doReturn(document).when(documentProvider).getDocument(firstUrl);
        
        elements = mock(Elements.class);
        element = mock(Element.class);
        
        configuration = mock(RootElementConfig.class);
        
        instance = new CrawlerImpl(documentProvider);
    }
    
    public void initializeEnvToUseFiles() throws FileNotFoundException {
        PageProvider pageProvider = new PageProvider();
        firstUrl = new URLWrapper(getClass().getClassLoader().getResource("testPage.html"));
        instance = new CrawlerImpl(pageProvider);
        
        ConfigurationProviderImpl confProvider = new ConfigurationProviderImpl();
        Reader reader = new FileReader(new File(getClass().getClassLoader().getResource("testPageConfig.xml").getFile()));
        configuration = confProvider.getConfigurationFromXml(reader);
    }

    @Test
    public void whenMaxVisitsLessOrEqualZeroThenReturnEmptyList() {
        doReturn(0).when(configuration).getMaxVisists();
        List<CollectedElement> result = instance.run(firstUrl, configuration);
        Assert.assertTrue(result.isEmpty());
    }
    
    @Test
    public void whenGoodConfigThenReturnNotEmptyList() throws Exception {
        initializeEnvToUseFiles();
        List<CollectedElement> result = instance.run(firstUrl, configuration);
        
        Map<String, Attribute> attrs1 = new HashMap<>();
        attrs1.put("image", new Attribute(Type.STRING, "https://local"));
        attrs1.put("number of comments", new Attribute(Type.INTEGER, "10"));
        attrs1.put("rating", new Attribute(Type.INTEGER, "15"));
        
        CollectedElement first = new CollectedElement(attrs1);
        
        Map<String, Attribute> attrs2 = new HashMap<>();
        attrs2.put("image", new Attribute(Type.STRING, "https://local"));
        attrs2.put("number of comments", new Attribute(Type.INTEGER, "100"));
        attrs2.put("rating", new Attribute(Type.INTEGER, "150,00"));
        
        CollectedElement second = new CollectedElement(attrs2);
        Assert.assertEquals(first, result.get(0));
        Assert.assertEquals(second, result.get(1));
    }
}
