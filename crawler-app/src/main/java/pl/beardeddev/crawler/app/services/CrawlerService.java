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

import java.util.List;
import java.util.Locale;
import java.util.concurrent.Future;
import pl.beardeddev.crawler.app.domain.ConfigPackageMaster;
import pl.beardeddev.crawler.app.domain.Image;
import pl.beardeddev.crawler.core.factory.CrawlerFactory;
import pl.beardeddev.crawler.core.wrappers.URLWrapper;

/**
 * Interfejs serwisu związanego z głównymi funkcjonalnościami robota internetowego.
 * 
 * @author Szymon Grzelak
 */
public interface CrawlerService {
    
    /**
     * Uruchamia asynchronicznie robota internetowego w zadanej konfiguracji przekazanej jako parametry. Rezultatem jest
     * instancja klasy implementującej {@class Future} umożliwiająca pobranie rezultatu pracy robota jako listy po
     * zakończeniu zadania.
     * 
     * @param crawlerFactory fabryka tworząca skonfigurowanego robota internetowego.
     * @param startUrl początkowy adres.
     * @param maxVisits maksymalna liczba wizyt.
     * @return instancja {@class Future} będąca powiązaniem z wątkiem roboczym obsułguacym pracę robota.
     */
    Future<List<Image>> runCralwerAsync(CrawlerFactory crawlerFactory, URLWrapper startUrl, int maxVisits);
    
    /**
     * Uruchamia robota internetowego w zadanej konfiguracji przekazanej jako parametry. Rezultatem jest
     * lista sprasowanych obiektów przetworzonych na odpowiednią klasę domenową.
     * 
     * @param crawlerFactory fabryka tworząca skonfigurowanego robota internetowego.
     * @param startUrl początkowy adres.
     * @param maxVisits maksymalna liczba wizyt.
     * @return lista sprasowanych dokumentów.
     */
    List<Image> runCrawler(CrawlerFactory crawlerFactory, URLWrapper startUrl, int maxVisits);
    
    /**
     * Zapis listy sparsowanych dokumentów.
     * 
     * @param parsedImages lista obiektów do zapisu.
     */
    void saveParsedImage(List<Image> parsedImages);
    
}
