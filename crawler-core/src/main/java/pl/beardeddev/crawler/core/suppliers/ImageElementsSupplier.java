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
import pl.beardeddev.crawler.core.wrappers.URLWrapper;
import pl.beardeddev.crawler.core.exceptions.CoreException;

/**
 * Interfesj zapewniający pozyskanie odpowiednich elementów z dokumentu zdefiniowanych za pomocą deskryptora
 * 
 * @author Szymon Grzelak
 */
public interface ImageElementsSupplier {
   
    /**
     * Zwraca adres URL do obrazka zawarty w dokumencie.
     * 
     * @param document dokument, dla którego ma zostać pobrany adres obrazka.
     * @return adres URL obrazka, null jeżei nie udało się znaleźć elementu.
     * @throws CoreException błędy związane z przetwarzanie dokumentu lub próbą utworzenia adresu URL.
     */
    public URLWrapper getImageURL(Document document) throws CoreException;
    
    /**
     * Zwraca liczbę komentarzy obrazka zawartą w dokumencie.
     * 
     * @param document dokument, dla którego ma zostać pobrana liczba komentarzy.
     * @return liczba komentarzy, null jeżeli nie udało się znaleźć elementu.
     * @throws CoreException błędy związane z przetwarzanie dokumentu lub parsowaniem wyników na obiekt {@see Integer}.
     */
    public Integer getImageNumberOfComments(Document document) throws CoreException;
    
    /**
     * Zwraca ranking obrazka zawarty w dokumencie.
     * 
     * @param document dokument, dla którego ma zostać pobrany ranking.
     * @return ranking, null jeżeli nie udało się znaleźć elementu.
     * @throws CoreException błędy związane z przetwarzanie dokumentu lub parsowaniem wyników na obiekt {@see Integer}.
     */
    public Integer getImageRatings(Document document) throws CoreException;
    
    /**
     * Zwraca adres URL do następnego obrazka zawarty w dokumencie.
     * 
     * @param document dokument, dla którego ma zostać pobrany adres URL.
     * @return adres URL, null jeżeli nie udało się znaleźć elementu.
     * @throws CoreException błędy związane z przetwarzanie dokumentu lub próbą utworzenia adresu URL.
     */
    public URLWrapper getNextImageURL(Document document) throws CoreException;
}
