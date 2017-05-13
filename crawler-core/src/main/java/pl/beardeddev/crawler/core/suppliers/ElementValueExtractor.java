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

/**
 * Interfejs umożliwiający konfigurację pozyskiwania elementów z danego dokumentu.
 * 
 * @author Szymon Grzelak
 */
public interface ElementValueExtractor {

    /**
     * Metoda pozyskująca wartość elementu z dokumentu. Zwraca ją w postaci łańcucha znakowego. Przed wywołaniem
     * tej motody należy się upewnić, że konfiguracja jest poprawna przy pomocy metody {@link #isValid()}.
     * 
     * @param document dokument z którego ma zostać pozyskana wartość.
     * @return wartość elementu.
     */
    String getValue(Document document);
    
    /**
     * Metoda służąca do sprawdzenia czy ekstraktor posiada poprawną i pełną konfigurację. Błąd konfiguracji może zostać 
     * również zakomunikowany wyjątkiem {@class BadConfigurationException}. Poprawność ekstraktora najlepiej jest
     * sprawdzić w momencie tworzenia jego instancji.
     * Nie powinno używać się wartośći null jako komunikatu błędnej konfiguracji (chyba, że w ramach danej implementacji
     * ekstraktora będzie ona uzasadniona)
     * 
     * @return true jeżeli ekstraktor posiada poprawną konfigurację.
     */
    Boolean isValid();
}
