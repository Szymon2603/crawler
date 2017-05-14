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
 * Klasa pozyskująca wartość tekstową z elementu określonego za pomocą selektora CSS.
 * 
 * @author Szymon Grzelak
 */
@ToString
@EqualsAndHashCode
public class TextValueExtractor implements ElementValueExtractor {

    private static final Logger LOGGER = LoggerFactory.getLogger(TextValueExtractor.class);

    private final String ELEMENT_QUERY;

    /**
     * Metoda fabrykująca tworząca instancję {@class TextValueExtractor} oraz sprawdzająca jej poprawność.
     * 
     * @param elementQuery selektor elementu z którego ma zostać pozyskana wartość tekstowa.
     * @return instancja {@class TextValueExtractor} z poprawną konfiguracją.
     * @throws BadConfigurationException jeżeli konfiguracja jest niepoprawna.
     */
    public static TextValueExtractor getInstance(String elementQuery) throws BadConfigurationException {
        TextValueExtractor instance = new TextValueExtractor(elementQuery);
        if(!instance.isValid()) {
            throw BadConfigurationException.createWithDefaultMessage(instance);
        }
        return instance;
    }
    
    /**
     * Konstruktor parametrowy.
     * 
     * @param elementQuery selektor elementu z którego ma zostać pozyskana wartość tekstowa.
     */
    private TextValueExtractor(String elementQuery) {
        ELEMENT_QUERY = elementQuery;
    }
    
    @Override
    public String getValue(Document document) {
        Elements elements = document.select(ELEMENT_QUERY);
        String value = elements.text();
        LOGGER.debug("Return value: {}", value);
        return value;
    }

    @Override
    public Boolean isValid() {
        return ELEMENT_QUERY != null;
    }
}
