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
package pl.beardeddev.crawler.core.suppliers;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Klasa pozyskująca wartość tekstową z elementu określonego za pomocą selektora CSS.
 * 
 * @author Szymon Grzelak
 */
public class TextValueExtractor implements ElementValueExtractor {

    private static final Logger LOGGER = LoggerFactory.getLogger(TextValueExtractor.class);

    private String elementQuery;

    /**
     * Konstruktor parametrowy.
     * 
     * @param elementQuery selektro elementu z którego ma zostać pozyskana wartość tekstowa.
     */
    public TextValueExtractor(String elementQuery) {
        this.elementQuery = elementQuery;
    }
    
    @Override
    public String getValue(Document document) {
        Elements elements = document.select(elementQuery);
        String value = elements.text();
        LOGGER.debug("Return value: %s", value);
        return value;
    }
}
