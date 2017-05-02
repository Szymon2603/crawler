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
package pl.beardeddev.crawler.app.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.beardeddev.crawler.app.domain.ParsedImage;
import pl.beardeddev.crawler.app.repositories.ParsedImageRepository;
import pl.beardeddev.crawler.app.services.CrawlerService;
import pl.beardeddev.crawler.core.Crawler;
import pl.beardeddev.crawler.core.factory.CrawlerFactory;
import pl.beardeddev.crawler.core.model.Image;
import pl.beardeddev.crawler.core.wrappers.URLWrapper;

/**
 * Implementacja serwisu związanego z głównymi funkcjonalnościami robota internetowego.
 * 
 * @author Szymon Grzelak
 */
@Service
@Transactional
public class CrawlerServiceImpl implements CrawlerService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CrawlerServiceImpl.class);
    
    @Autowired
    private ParsedImageRepository parsedImageRepository;
    
    @Override
    public Future<List<ParsedImage>> runCralwerAsync(CrawlerFactory crawlerFactory, URLWrapper startUrl, int maxVisits) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<List<ParsedImage>> result = executor.submit(() -> { 
            return runCrawler(crawlerFactory, startUrl, maxVisits);
        });
        return result;
    }
    
    @Override
    public List<ParsedImage> runCrawler(CrawlerFactory crawlerFactory, URLWrapper startUrl, int maxVisits) {
        Crawler crawler = crawlerFactory.makeCrawler();
        List<Image> images = crawler.getImages(startUrl, maxVisits);
        List<ParsedImage> parsedImages = parseImages(images);
        return parsedImages;
    }

    private List<ParsedImage> parseImages(List<Image> images) {
        List<ParsedImage> result = new ArrayList<>(images.size());
        Date currentDate = new Date();
        for (Image image : images) {
            ParsedImage parsedImage = new ParsedImage(image);
            parsedImage.setCreateDate(currentDate);
            result.add(parsedImage);
        }
        return result;
    }
    
    @Override
    public void saveParsedImage(List<ParsedImage> parsedImages) {
        parsedImageRepository.save(parsedImages);
    }
}