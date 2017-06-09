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
package pl.beardeddev.crawler.app.services;

import pl.beardeddev.crawler.app.domain.Image;
import pl.beardeddev.crawler.app.exceptions.BusinessEntityExistsException;

/**
 * Interfejs serwisu do zarządzania encjami {@class Image}.
 * 
 * @author Szymon Grzelak
 */
public interface ImageService {

    /**
     * Tworzy nową encję i zapisuje ją w bazie. Sprawdza wcześniej czy dana encja istnieje już w bazie.
     * 
     * @param image obiekt encyjny do utrwalenia w bazie.
     * @return instancja klasy encyjnej reprezentująca utrwaloną krotkę.
     * @throws BusinessEntityExistsException wyjątek biznesowy rzucany w przypadku gdy dana encja istnieje już w bazie.
     */
    Image createImage(Image image) throws BusinessEntityExistsException;

    /**
     * Sprawdza czy podana encja istnieje w bazie. Sprawdzenie domyślnie powinno się odbywać po właściwości id oraz imageURL.
     * 
     * @param image obiekt encyjny.
     * @return true jeżeli istnieje już taka encja w bazie, false w przeciwnym wypadku.
     */
    boolean imageExists(Image image);
    
}
