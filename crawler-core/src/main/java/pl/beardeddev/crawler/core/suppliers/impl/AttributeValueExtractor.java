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
package pl.beardeddev.crawler.core.suppliers.impl;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.beardeddev.crawler.core.exceptions.BadConfigurationException;
import pl.beardeddev.crawler.core.suppliers.ElementValueExtractor;

/**
 * Klasa pozyskująca wartość atrybutu o danej nazwie z elementu określonego za pomocą selektora CSS.
 * 
 * @author Szymon Grzelak
 */
@ToString
@EqualsAndHashCode
public final class AttributeValueExtractor implements ElementValueExtractor {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AttributeValueExtractor.class);
    
    private final String ELEMENT_QUERY;
    private final String ATTRIBUTE_NAME;

    /**
     * Metoda fabrykująca tworząca instancję {@class AttributeValueExtractor} oraz sprawdzająca jej poprawność.
     * Jeżeli chcemy uzyskać odnośnik ze ścieżką absolutną do nazwy atrybutu należy dodać przedrostek "abs:".
     * 
     * @param elementQuery selektor CSS elementu.
     * @param attributeName nazwa atrybutu dla którego ma zostać pozyskana wartość.
     * @return instancja {@class AttributeValueExtractor} z poprawną konfiguracją.
     * @throws BadConfigurationException jeżeli konfiguracja nie jest poprawna.
     */
    public static AttributeValueExtractor getInstance(String elementQuery, String attributeName) throws BadConfigurationException {
        AttributeValueExtractor instance = new AttributeValueExtractor(elementQuery, attributeName);
        if (!instance.isValid()) {
            throw BadConfigurationException.createWithDefaultMessage(instance);
        }
        return instance;
    }
    
    /**
     * Konstruktor parametrowy. Jeżeli chcemy uzyskać odnośnik ze ścieżką absolutną do nazwy atrybutu należy dodać
     * przedrostek "abs:".
     * 
     * @param elementQuery selektor CSS elementu.
     * @param attributeName nazwa atrybutu dla którego ma zostać pozyskana wartość.
     * @throws BadConfigurationException jeżeli konfiguracja jest niepoprawna
     */
    private AttributeValueExtractor(String elementQuery, String attributeName) {
        ELEMENT_QUERY = elementQuery;
        ATTRIBUTE_NAME = attributeName;
    }
    
    @Override
    public String getValue(Document document) {
        Elements elements = document.select(ELEMENT_QUERY);
        if (elements.isEmpty()) {
            return null;
        }
        String value = elements.attr(ATTRIBUTE_NAME);
        LOGGER.debug("Return value: {}", value);
        return value;
    }

    @Override
    public Boolean isValid() {
        return ELEMENT_QUERY != null && ATTRIBUTE_NAME != null;
    }
}
