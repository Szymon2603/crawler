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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import pl.beardeddev.crawler.core.wrappers.URLWrapper;
import pl.beardeddev.crawler.core.model.Image;
import pl.beardeddev.crawler.core.exceptions.CoreException;

/**
 * Klasa robota internetowego (pająka internetowego). Przeszukuje podane strony internetowe pod kątem elementów zwracanych
 * za pomocą {@see ImageElementsSupplier} i transformuje je na obiekty klasy {@see Image}. Dokument do przetworzenia dostarczany
 * jest za pomocą instancji klasy implementującej {@see DocumentProvider}.
 * 
 * @author Szymon Grzelak
 */
public class Crawler implements Serializable {

    private static final long serialVersionUID = 6393778875493770690L;
    private static final Logger LOGGER = Logger.getLogger(Crawler.class);
    
    private final DocumentProvider DOCUMENT_PROVIDER;
    private final ImageElementsSupplier IMAGE_ELEMENTS_SUPPLIER;
    private Document document;

    /**
     * Konstruktor parametrowy. Stworzona instancja za pomocą tego konstruktora jest gotowa do przetwarzania dokumentów.
     * Wszystkie parametry konstruktora są wymagane (nie mogą posiadać wartości null).
     * 
     * @param documentProvider obiekt dostarczający dokumenty do przetworzenia.
     * @param imageElementsSupplier obiekt dostarczajacy wymagane elementy obrazka
     */
    public Crawler(DocumentProvider documentProvider, ImageElementsSupplier imageElementsSupplier) {
        this.DOCUMENT_PROVIDER = documentProvider;
        this.IMAGE_ELEMENTS_SUPPLIER = imageElementsSupplier;
    }
    
    /**
     * Przetwarza dokument pod podanym adresem URL. Następnie próbuje uzyskać nastęnym element i powtórzyć dla niego
     * operację przetworzenia dokumentu na instancję {@see Image}. Jeżeli dokument nie będzie możliwy do uzyskania w
     * wyniku błędów, zostanie zwrócony dotychczasowy rezultat przetwazrania.
     * 
     * @param urlStartPoint początkowy adres dokumentu, nie może być null.
     * @param maxVisits maksymalna ilość stron do odwiezenia.
     * @return lista obiektów {@see Image}.
     */
    public List<Image> getImages(URLWrapper urlStartPoint, int maxVisits) {
        List<Image> result = new ArrayList<>();
        try {
            isNotNull(urlStartPoint);
            URLWrapper nextURLToVisit = urlStartPoint;
            for (int i = 0; i < maxVisits && nextURLToVisit != null; ++i) {
                Image image = getImage(nextURLToVisit);
                if (image == null) {
                    break;
                }
                result.add(image);
                nextURLToVisit = IMAGE_ELEMENTS_SUPPLIER.getNextImageURL(document);
            }
        } catch(CoreException ex) {
            LOGGER.warn("Przerwanie przetwarzania zbierania!", ex);
        }
        return result;
    }
    
    /**
     * Przetwarza dokument znajdujący się pod podanym adresem URL na instancję {@see Image}.
     * 
     * @param url adres dokumentu do przetworzenia, nie może być to wartość null.
     * @return instancja {@see Image}, null jeżeli nie udało znaleźć się adresu obrazka.
     * @throws CoreException jeżeli przekazany obiekt {@see URLWrapper} jest null oraz błędy zgłaszane przez pozostałe
     * komponenty składowe pająka.
     */
    public Image getImage(URLWrapper url) throws CoreException {
        isNotNull(url);
        return createImage(url);
    }

    private Image createImage(URLWrapper url) throws CoreException {
        LOGGER.trace(String.format("Creating image for: %s.", url.getURL().toString()));
        document = DOCUMENT_PROVIDER.getDocument(url);
        URLWrapper urlImg = IMAGE_ELEMENTS_SUPPLIER.getImageURL(document);
        if(urlImg != null) {
            Image image = new Image();
            image.setImageURL(urlImg.getURL());
            Integer numberOfComments = IMAGE_ELEMENTS_SUPPLIER.getImageNumberOfComments(document);
            image.setNumberOfComments(numberOfComments);
            Integer rating = IMAGE_ELEMENTS_SUPPLIER.getImageRatings(document);
            image.setRating(rating);
            LOGGER.trace(String.format("Return image object: %s.", image.toString()));
            return image;
        }
        LOGGER.trace("Can't find image URL, returning null reference.");
        return null;
    }
    
    private void isNotNull(URLWrapper url) throws CoreException {
        if(url == null) {
            LOGGER.error("URL is null!");
            throw new CoreException("URL can't be null!");
        }
    }
}
