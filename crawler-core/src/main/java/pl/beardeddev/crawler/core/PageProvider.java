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
package pl.beardeddev.crawler.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URLConnection;
import org.slf4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.LoggerFactory;
import pl.beardeddev.crawler.core.wrappers.URLWrapper;
import pl.beardeddev.crawler.core.exceptions.CoreException;

/**
 * Klasa odpowiedzialna za pobranie z danego źródła określonego adresem URL zawartości oraz próba sparsowania
 * jej za pomocą biblioteki Jsoup
 * 
 * @author Szymon Grzelak
 */
public class PageProvider implements DocumentProvider, Serializable {

    private static final long serialVersionUID = 3609424201008737736L;
    
    private final Logger LOGGER = LoggerFactory.getLogger(PageProvider.class);

    /**
     * Metoda pobierająca dokument HTML z danego źródła określonego adresem URL.
     * 
     * @param documentURL adresu URL dokumentu.
     * @return sparsowany dokument przedstawiony jako instancja klasy {@code org.jsoup.nodes.Document}.
     * @throws CoreException wyjątek zgłaszany w przypadku błędów odczytywania zasobów z podanego adresu URL.
     */
    @Override
    public Document getDocument(URLWrapper documentURL) throws CoreException {
        try {
            URLConnection connection = documentURL.openConnection();
            return parseDocument(connection);
        } catch(CoreException ex) {
            throw ex;
        } catch (IOException ex) {
            LOGGER.error("Error while trying get page", ex);
            throw new CoreException("Core exception while geting page.", ex);
        }
    }

    private Document parseDocument(URLConnection connection) throws CoreException {
        try(InputStream input = connection.getInputStream()) {
            return Jsoup.parse(input, null, connection.getURL().toString());
        } catch(IOException ex) {
            LOGGER.error("Error while trying get page", ex);
            throw new CoreException("Core exception while getting page.", ex);
        }
    }
}
