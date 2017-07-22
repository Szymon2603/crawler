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

import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import org.jsoup.nodes.Document;
import pl.beardeddev.crawler.core.v2.exceptions.CoreException;
import pl.beardeddev.crawler.core.v2.extractors.AttributeValueExtractor;
import pl.beardeddev.crawler.core.v2.extractors.ExtractorFactory;
import pl.beardeddev.crawler.core.v2.model.Configuration;
import pl.beardeddev.crawler.core.v2.model.Configuration.ConfigurationElement;
import pl.beardeddev.crawler.core.v2.model.Element;
import pl.beardeddev.crawler.core.v2.wrappers.URLWrapper;

/**
 *
 * @author Szymon Grzelak
 */
public class CrawlerImpl {
    
    private final DocumentProvider DOCUMENT_PROVIDER;
    private final ExtractorFactory EXTRACTOR_FACTORY = null;
    //TODO: Przenieść do konfiguracji
    private int maxVisist = 10;

    public CrawlerImpl(DocumentProvider documentProvider) {
        this.DOCUMENT_PROVIDER = documentProvider;
    }
    
    public List<Element> run(URLWrapper startUrl, Configuration configuration) throws CoreException {
        List<Element> result = new LinkedList<>();
        
        URLWrapper nextUrl = startUrl;
        while(maxVisist-- > 0) {
            Document doc = DOCUMENT_PROVIDER.getDocument(nextUrl);
            for(Entry<String, ConfigurationElement> entry : configuration.getSelectorsMap().entrySet()) {
                ConfigurationElement config = entry.getValue();
                AttributeValueExtractor extractor = EXTRACTOR_FACTORY.getExtractor(config.getType());
                String attrValue = extractor.getValue(doc, config.getSelector());
                String attrName = entry.getKey();
                Element element = new Element();
                element.addElementAttr(attrName, new Element.Attribute(config.getType(), attrValue));
                result.add(element);
            }
        }
        return result;
    }
}
