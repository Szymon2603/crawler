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
package pl.beardeddev.crawler.app.factory;

import pl.beardeddev.crawler.app.domain.AttributeValueExtractorConfig;
import pl.beardeddev.crawler.app.domain.TextValueExtractorConfig;
import pl.beardeddev.crawler.core.suppliers.impl.AttributeValueExtractor;
import pl.beardeddev.crawler.core.suppliers.impl.TextValueExtractor;

/**
 * Klasa zawierająca metody fabrykujące. Służą do utworzenia instancji klasy implementującej
 * {@class ElementValueExtractor}. Implementacje tych klas znajdują sią w bibliotece core i nie posiadają
 * konstruktorów potrzebnych na użycie w module aplikacji.
 * 
 * @author Szymon Grzelak
 */
public class ExtractorFactory {
    
    /**
     * Tworzy instancję {@class AttributeValueExtractor} na podstawie konfiguracji opisanej za pomocą
     * klasy encyjnej {@class AttributeValueExtractorConfig}.
     * 
     * @param config instancja klasy konfiguracyjnej
     * @return instancja {@class AttributeValueExtractor} 
     */
    public static AttributeValueExtractor createExtractor(AttributeValueExtractorConfig config) {
        return new AttributeValueExtractor(config.getElementSelector(), config.getAttributeName());
    }
    
    /**
     * Tworzy instancję {@class TextValueExtractor} na podstawie konfiguracji opisanej za pomocą
     * klasy encyjnej {@class TextValueExtractorConfig}.
     * 
     * @param config instancja klasy konfiguracyjnej
     * @return instancja {@class TextValueExtractor} 
     */
    public static TextValueExtractor createExtractor(TextValueExtractorConfig config) {
        return new TextValueExtractor(config.getElementSelector());
    }
}
