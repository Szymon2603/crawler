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

import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.beardeddev.crawler.core.v2.exceptions.BadConfigurationException;
import pl.beardeddev.crawler.core.v2.exceptions.CoreException;
import pl.beardeddev.crawler.core.v2.model.CollectedElement;
import pl.beardeddev.crawler.core.v2.model.configuration.AttributeConfig;
import pl.beardeddev.crawler.core.v2.model.configuration.ElementConfig;
import pl.beardeddev.crawler.core.v2.model.configuration.RootElementConfig;
import pl.beardeddev.crawler.core.v2.model.configuration.SelectorConfig;
import pl.beardeddev.crawler.core.v2.model.configuration.TextNodeConfig;
import pl.beardeddev.crawler.core.v2.wrappers.URLWrapper;

/**
 *
 * @author Szymon Grzelak
 */
public class CrawlerImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(CrawlerImpl.class);
    private static final String LINK_SELECTOR = "a[href]";
    private static final String HREF_ATTR = "abs:href";

    private final DocumentProvider DOCUMENT_PROVIDER;
    private Set<URLWrapper> urls = new HashSet<>();
    private Set<URLWrapper> visitedUrls = new HashSet<>();

    public CrawlerImpl(DocumentProvider documentProvider) {
        this.DOCUMENT_PROVIDER = documentProvider;
    }

    public List<CollectedElement> run(URLWrapper startUrl, RootElementConfig rootElementConfig) throws CoreException {
        initializeUrls(startUrl);
        Set<CollectedElement> r = new HashSet<>();
        List<CollectedElement> result = new LinkedList<>();
        int maxVisist = rootElementConfig.getMaxVisists();
        while (maxVisist-- > 0 && !urls.isEmpty()) {
            URLWrapper nextUrl = urls.iterator().next();
            Document doc = DOCUMENT_PROVIDER.getDocument(nextUrl);
            processDocument(rootElementConfig, doc, result);
            updateUrls(nextUrl, doc);
        }
        return result;
    }

    private void initializeUrls(URLWrapper startUrl) {
        urls = new HashSet<>();
        visitedUrls = new HashSet<>();
        urls.add(startUrl);
    }

    private void processDocument(RootElementConfig rootElementConfig, Document doc, List<CollectedElement> result) {
        checkConfigForRootElement(rootElementConfig);
        SelectorConfig selector = rootElementConfig.getSelector();
        LOGGER.debug("Looking for root elements using selector {}", selector.getValue());
        Elements elements = doc.select(selector.getValue());
        elements.forEach(el -> {
            List<ElementConfig> elementsConfig = rootElementConfig.getElements();
            elementsConfig.forEach(conf -> {
                String value = null;
                if(conf.getAttribute() != null) {
                    AttributeConfig attrConfig = conf.getAttribute();
                    Elements found = el.select(attrConfig.getValue());
                    value = found.attr(attrConfig.getAttributeName());
                } else if(conf.getTextNode() != null) {
                    TextNodeConfig textNodeConfig = conf.getTextNode();
                    Elements found = el.select(textNodeConfig.getValue());
                    value = found.text();
                }
                CollectedElement element = new CollectedElement();
                element.addElementAttr(conf.getElementName(), new CollectedElement.Attribute(conf.getElementType(), value));
                result.add(element);
            });
        });
    }

    private void checkConfigForRootElement(RootElementConfig rootElementConfig) throws BadConfigurationException {
        if(rootElementConfig.getSelector() == null) {
            throw new BadConfigurationException("Missing selector for root element!");
        }
    }

    private void updateUrls(URLWrapper nextUrl, Document doc) {
        updateVisitedUrls(nextUrl);
        Optional<Elements> links = collectAllLinks(doc);

        links.ifPresent(v ->
            v.forEach((link) -> addUrlIfNotVisitedAndCorrect(link))
        );
    }

    private void updateVisitedUrls(URLWrapper nextUrl) {
        urls.remove(nextUrl);
        visitedUrls.add(nextUrl);
    }

    private Optional<Elements> collectAllLinks(Document doc) {
        Elements links = doc.select(LINK_SELECTOR);
        return Optional.ofNullable(links);
    }

    private void addUrlIfNotVisitedAndCorrect(org.jsoup.nodes.Element link) {
        String href = link.attr(HREF_ATTR);
        try {
            URLWrapper url = new URLWrapper(href);
            if (!visitedUrls.contains(url)) {
                urls.add(url);
            }
        } catch (MalformedURLException ex) {
            LOGGER.warn("Bad url, ignore it", ex);
        }
    }
}
