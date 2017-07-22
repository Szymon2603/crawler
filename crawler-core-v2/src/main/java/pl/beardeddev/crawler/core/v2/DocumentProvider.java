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

import org.jsoup.nodes.Document;
import pl.beardeddev.crawler.core.v2.wrappers.URLWrapper;
import pl.beardeddev.crawler.core.v2.exceptions.CoreException;

/**
 * Interfejs zapewniający sparsowane documenty HTML pobrane ze źrodła określonego adresem URL
 * 
 * @author Szymon Grzelak
 */
public interface DocumentProvider {
    
    /**
     * Pobiera dokument HTML z danego źródła URL
     * 
     * @param urlWrapper adres URL
     * @return sparsowany dokument HTML
     * @throws CoreException błędy związane z próbą pobrania dokumentu
     */
    Document getDocument(URLWrapper urlWrapper) throws CoreException;
}
