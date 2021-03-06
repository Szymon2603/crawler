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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;
import pl.beardeddev.crawler.core.exceptions.BadConfigurationException;
import pl.beardeddev.crawler.core.exceptions.CoreException;
import pl.beardeddev.crawler.core.model.ParsedImage;
import pl.beardeddev.crawler.core.suppliers.ElementValueExtractor;
import pl.beardeddev.crawler.core.suppliers.ImageElementsExtractors;
import pl.beardeddev.crawler.core.suppliers.ImageElementsExtractorsBuilder;
import pl.beardeddev.crawler.core.suppliers.ImageElementsSupplier;
import pl.beardeddev.crawler.core.suppliers.impl.AttributeValueExtractor;
import pl.beardeddev.crawler.core.suppliers.impl.ImageElementsSupplierImpl;
import pl.beardeddev.crawler.core.suppliers.impl.TextValueExtractor;
import pl.beardeddev.crawler.core.wrappers.URLWrapper;

/**
 *
 * @author Szymon Grzelak
 */
public class CrawlerSpec {
    
    private URL url;
    private URLWrapper urlWrapper;
    private Crawler crawler;
    private DocumentProvider documentProvider;
    private ElementValueExtractor ratingExtractor;
    private ElementValueExtractor commentsExtractor;
    private ElementValueExtractor nextElementExtractor;
    private ElementValueExtractor imageExtractor;
    private ImageElementsExtractors extractors;
    private ImageElementsSupplier imageElementsSupplier;
    
    @Before
    public void setUp() throws BadConfigurationException {
        url = this.getClass().getClassLoader().getResource("testPage.html");
        urlWrapper = spy(new URLWrapper(url));
        documentProvider = spy(new PageProvider());
        
        String imageSelector = "img";
        String nextElementSelector = "a";
        String commentsSelector = "div#commentsNumber";
        String ratingSelector = "div[id=rateNummber]";
        
        imageExtractor = spy(AttributeValueExtractor.getInstance(imageSelector, "abs:src"));
        nextElementExtractor = spy(AttributeValueExtractor.getInstance(nextElementSelector, "abs:href"));
        commentsExtractor = spy(TextValueExtractor.getInstance(commentsSelector));
        ratingExtractor = spy(TextValueExtractor.getInstance(ratingSelector));
        
        ImageElementsExtractorsBuilder builder = new ImageElementsExtractorsBuilder();
        builder.setImageExtractor(imageExtractor)
               .setNextElementExtractor(nextElementExtractor)
               .setCommentsExtractor(commentsExtractor)
               .setRatingExtractor(ratingExtractor);
        extractors = builder.build();
        
        imageElementsSupplier = spy(new ImageElementsSupplierImpl(extractors, Locale.getDefault()));
        
        crawler = spy(new Crawler(documentProvider, imageElementsSupplier));
    }
    
    @Test(expected = CoreException.class)
    public void givenNullArgWhenGetImageThenThrowCoreException() throws CoreException, MalformedURLException {
        crawler.getImage(null);
    }
    
    @Test()
    public void givenNullArgWhenGetImagesThenListIsEmpty() {
        List<ParsedImage> result = crawler.getImages(null, 1);
        Assert.assertTrue("List should be empty!", result.isEmpty());
    }
    
    @Test()
    public void givenNoImageWhenGetImageThenReturnNull() throws CoreException, MalformedURLException {
        doReturn(null).when(imageExtractor).getValue(any());
        ParsedImage result = crawler.getImage(urlWrapper);
        Assert.assertNull("Should be null", result);
    }
    
    @Test()
    public void givenImageWhenGetImageThenImageURLIsSet() throws CoreException, MalformedURLException {
        ParsedImage result = crawler.getImage(urlWrapper);
        URL expected = new URL("https://local");
        Assert.assertTrue("URL is not equals!", expected.equals(result.getImageURL()));
    }
    
    @Test()
    public void givenNumOfCommentsWhenGetImageThenNumOfCommentsPropertyIsSet() throws CoreException, MalformedURLException {
        ParsedImage result = crawler.getImage(urlWrapper);
        Integer expected = 10;
        Integer actual = result.getNumberOfComments();
        Assert.assertTrue(String.format("Number of comments is not equals! Actual is: %d", actual), expected.equals(actual));
    }
    
    @Test()
    public void givenBadNumOfCommentsSelectorWhenGetImageThenValueIsNotSet() throws CoreException, MalformedURLException {
        doReturn(null).when(commentsExtractor).getValue(any());
        ParsedImage result = crawler.getImage(urlWrapper);
        Integer actual = result.getNumberOfComments();
        Assert.assertNull(String.format("Number of comments should be null"), actual);
    }
    
    @Test()
    public void givenRatingWhenGetImageThenRatingPropertyIsSet() throws CoreException, MalformedURLException {
        ParsedImage result = crawler.getImage(urlWrapper);
        Integer expected = 15;
        Integer actual = result.getRating();
        Assert.assertTrue(String.format("Ratings are not equals! Actual is: %d", actual), expected.equals(actual));
    }
    
    @Test()
    public void givenBadRatingsSelectorWhenGetImageThenValueIsNotSet() throws CoreException, MalformedURLException {
        doReturn(null).when(ratingExtractor).getValue(any());
        ParsedImage result = crawler.getImage(urlWrapper);
        Integer actual = result.getRating();
        Assert.assertNull(String.format("Ratings should be null"), actual);
    }
    
    @Test()
    public void givenCorrectURLWhenGetImagesThenListIsNotEmpty() {
        List<ParsedImage> result = crawler.getImages(urlWrapper, 1);
        Assert.assertFalse("List is empty!", result.isEmpty());
    }
    
    @Test()
    public void givenBadImageSelectorWhenGetImagesThenListIsEmpty() {
        doReturn(null).when(imageExtractor).getValue(any());
        List<ParsedImage> result = crawler.getImages(urlWrapper, 1);
        Assert.assertTrue("List is not empty!", result.isEmpty());
    }
    
    @Test()
    public void givenMaxNumOfImagesWhenGetImagesThenListHaveCorrectSize() {
        int expected = 2;
        List<ParsedImage> result = crawler.getImages(urlWrapper, expected);
        int actual = result.size();
        Assert.assertEquals("List have incorrect size!", expected, actual);
    }
    
    @Test()
    public void whenGetImagesThenListHaveDiffrentImages() {
        int expected = 2;
        List<ParsedImage> result = crawler.getImages(urlWrapper, expected);
        Set<ParsedImage> resultSet = result.stream().collect(Collectors.toCollection(() -> new HashSet()));
        int actual = resultSet.size();
        Assert.assertEquals("List have incorrect size!", expected, actual);
    }
    
    @Test()
    public void givenNoNextElementURLWhenGetImagesThenReturnResult() throws CoreException {
        doReturn(null).when(imageElementsSupplier).getNextImageURL(any(Document.class));
        int expected = 1;
        List<ParsedImage> result = crawler.getImages(urlWrapper, expected);
        Set<ParsedImage> resultSet = result.stream().collect(Collectors.toCollection(() -> new HashSet()));
        int actual = resultSet.size();
        Assert.assertEquals("List have incorrect size!", expected, actual);
    }
    
    @Test()
    public void givenCoreExceptionWhenGetImagesThenReturnResult() throws CoreException {
        doThrow(CoreException.class).when(imageElementsSupplier).getNextImageURL(any(Document.class));
        int expected = 1;
        List<ParsedImage> result = crawler.getImages(urlWrapper, expected);
        Set<ParsedImage> resultSet = result.stream().collect(Collectors.toCollection(() -> new HashSet()));
        int actual = resultSet.size();
        Assert.assertEquals("List have incorrect size!", expected, actual);
    }
    
    @Test()
    public void givenCoreExceptionWhenGetImagesThenReturnNoResult() throws CoreException {
        doThrow(CoreException.class).when(crawler).getImage(any(URLWrapper.class));
        int expected = 0;
        List<ParsedImage> result = crawler.getImages(urlWrapper, expected);
        Set<ParsedImage> resultSet = result.stream().collect(Collectors.toCollection(() -> new HashSet()));
        int actual = resultSet.size();
        Assert.assertEquals("List have incorrect size!", expected, actual);
    }
}
