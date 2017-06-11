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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.beardeddev.crawler.app.config.ApplicationConfig;
import pl.beardeddev.crawler.app.config.ConfigConstants;
import pl.beardeddev.crawler.app.domain.Image;
import pl.beardeddev.crawler.app.services.CrawlerService;
import pl.beardeddev.crawler.core.Crawler;
import pl.beardeddev.crawler.core.factory.CrawlerFactory;
import pl.beardeddev.crawler.core.model.ParsedImage;
import pl.beardeddev.crawler.core.wrappers.URLWrapper;

/**
 * Testy jednostkowe dla {@class CrawlerServiceImpl}.
 * 
 * @author Szymon Grzelak
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ServiceTestConfiguration.class })
public class CrawlerServiceImplTest {
    
    @Autowired
    private CrawlerService crawlerService;
    private CrawlerFactory crawlerFactory;
    private Crawler crawler;
    private URLWrapper urlWrapper;
    private String urlPattern = "http://some-site.com/%d";
    private int defaultMaxVisits = 10;
    
    public CrawlerServiceImplTest() {
    }
    
    @Before
    public void setUp() {
        crawlerFactory = mock(CrawlerFactory.class);
        crawler = mock(Crawler.class);
        doReturn(crawler).when(crawlerFactory).makeCrawler();
        urlWrapper = mock(URLWrapper.class);
    }
    
    @After
    public void tearDown() {
    }
    
    private List<ParsedImage> prepareImagesListMock(int howMany) throws MalformedURLException {
        List<ParsedImage> result = new ArrayList<>(howMany);
        for(int i = 0; i < howMany; ++i) {
            ParsedImage image = new ParsedImage();
            result.add(image);
            URL url = new URL(String.format(urlPattern, i));
            image.setImageURL(url);
            image.setNumberOfComments(i + 10);
            image.setRating(i + 20);
        }
        return result;
    }
    
    @Test
    public void whenInstatniedByAutowiredThanNotNull() {
        Assert.assertNotNull("Instance should be not null", crawlerService);
    }
    
    @Test
    public void whenCrawlerReturnEmptyListThenReturnEmptyList() {
        doReturn(Collections.EMPTY_LIST).when(crawler).getImages(urlWrapper, defaultMaxVisits);
        List<Image> result = crawlerService.runCrawler(crawlerFactory, urlWrapper, defaultMaxVisits);
        Assert.assertTrue("List should be empty!", result.isEmpty());
    }
    
    @Test
    public void whenCrawlerReturnImagesThenReturnNotEmptyList() throws MalformedURLException {
        final int listSize = 3;
        List<ParsedImage> imagesListMock = prepareImagesListMock(listSize);
        doReturn(imagesListMock).when(crawler).getImages(urlWrapper, defaultMaxVisits);
        List<Image> result = crawlerService.runCrawler(crawlerFactory, urlWrapper, defaultMaxVisits);
        Assert.assertFalse("List shouldn't be empty!", result.isEmpty() && result.size() == listSize);
    }
    
    @Test
    public void givenEmptyListWhenCallAssyncMethodThenReturnFutureAndEmptyList() throws InterruptedException, ExecutionException, TimeoutException {
        doReturn(Collections.EMPTY_LIST).when(crawler).getImages(urlWrapper, defaultMaxVisits);
        Future<List<Image>> result = crawlerService.runCralwerAsync(crawlerFactory, urlWrapper, defaultMaxVisits);
        Assert.assertTrue("List should be empty!", result.get(10, TimeUnit.SECONDS).isEmpty());
    }
    
    @Test
    public void givenNotEmptyListWhenCallAssyncMethodThenReturnFutureAndNotEmptyList() throws MalformedURLException, InterruptedException, ExecutionException, TimeoutException {
        final int listSize = 3;
        List<ParsedImage> imagesListMock = prepareImagesListMock(listSize);
        doReturn(imagesListMock).when(crawler).getImages(urlWrapper, defaultMaxVisits);
        Future<List<Image>> future = crawlerService.runCralwerAsync(crawlerFactory, urlWrapper, defaultMaxVisits);
        List<Image> result = future.get(10, TimeUnit.SECONDS);
        Assert.assertFalse("List shouldn't be empty!", result.isEmpty() && result.size() == listSize);
    }
}
