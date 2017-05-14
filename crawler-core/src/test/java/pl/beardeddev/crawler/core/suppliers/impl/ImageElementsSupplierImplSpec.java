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
package pl.beardeddev.crawler.core.suppliers.impl;

import java.util.Locale;
import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import java.net.MalformedURLException;
import static org.mockito.Mockito.spy;
import pl.beardeddev.crawler.core.exceptions.CoreException;
import pl.beardeddev.crawler.core.exceptions.ElementExtractorNotSetException;
import pl.beardeddev.crawler.core.suppliers.ElementValueExtractor;
import pl.beardeddev.crawler.core.suppliers.ImageElementsExtractors;
import pl.beardeddev.crawler.core.wrappers.URLWrapper;

/**
 *
 * @author Szymon Grzelak
 */
public class ImageElementsSupplierImplSpec {
    
    private ImageElementsSupplierImpl instance;
    private Document document;
    private ImageElementsExtractors imageElementsExtractors;
    private ElementValueExtractor elementValueExtractor;
    private final String IMAGE_URL = "http://some-fake-adress.com";
    private final String NUM = "10";
    
    @Before
    public void setUp() {
        document = mock(Document.class);
        imageElementsExtractors = spy(new ImageElementsExtractors(null, null, null, null));
        elementValueExtractor = mock(ElementValueExtractor.class);
        instance = new ImageElementsSupplierImpl(imageElementsExtractors, Locale.getDefault());
    }
    
    @Test
    public void givenImageExtractorWhenGetImageThenReturnNotNull() throws CoreException, MalformedURLException, ElementExtractorNotSetException {
        doReturn(IMAGE_URL).when(elementValueExtractor).getValue(document);
        doReturn(elementValueExtractor).when(imageElementsExtractors).getImageExtractor();
        URLWrapper result = instance.getImageURL(document);
        Assert.assertEquals(new URLWrapper(IMAGE_URL), result);
    }
    
    @Test
    public void givenMalformedURLWhenGetImageThenReturnNull() throws CoreException, MalformedURLException, ElementExtractorNotSetException {
        doReturn("").when(elementValueExtractor).getValue(document);
        doReturn(elementValueExtractor).when(imageElementsExtractors).getImageExtractor();
        URLWrapper result = instance.getImageURL(document);
        Assert.assertNull(result);
    }
    
    @Test
    public void givenNullImageExtractorWhenGetImageThenReturnNull() throws CoreException, MalformedURLException {
        URLWrapper result = instance.getImageURL(document);
        Assert.assertNull(result);
    }
    
    @Test
    public void givenCommentsExtractorWhenGetNumberOfCommentsThenReturnNotNull() throws CoreException, MalformedURLException, ElementExtractorNotSetException {
        doReturn(NUM).when(elementValueExtractor).getValue(document);
        doReturn(elementValueExtractor).when(imageElementsExtractors).getCommentsExtractor();
        Integer result = instance.getImageNumberOfComments(document);
        Assert.assertEquals(Integer.valueOf(NUM), result);
    }
    
    @Test
    public void givenBadNumberWhenGetNumberOfCommentsThenReturnNull() throws CoreException, MalformedURLException, ElementExtractorNotSetException {
        doReturn("").when(elementValueExtractor).getValue(document);
        doReturn(elementValueExtractor).when(imageElementsExtractors).getCommentsExtractor();
        Integer result = instance.getImageNumberOfComments(document);
        Assert.assertNull(result);
    }
    
    @Test
    public void givenNullCommentsExtractorWhenGetNumberOfCommentsThenReturnNull() throws CoreException, MalformedURLException {
        Integer result = instance.getImageNumberOfComments(document);
        Assert.assertNull(result);
    }
    
    @Test
    public void givenRatingExtractorWhenGetImageRatingsThenReturnNotNull() throws CoreException, MalformedURLException, ElementExtractorNotSetException {
        doReturn(NUM).when(elementValueExtractor).getValue(document);
        doReturn(elementValueExtractor).when(imageElementsExtractors).getRatingExtractor();
        Integer result = instance.getImageRating(document);
        Assert.assertEquals(Integer.valueOf(NUM), result);
    }
    
    @Test
    public void givenBadNumberWhenGetImageRatingsThenReturnNull() throws CoreException, MalformedURLException, ElementExtractorNotSetException {
        doReturn("").when(elementValueExtractor).getValue(document);
        doReturn(elementValueExtractor).when(imageElementsExtractors).getRatingExtractor();
        Integer result = instance.getImageRating(document);
        Assert.assertNull(result);
    }
    
    @Test
    public void givenNullRatingExtractorWhenGetImageRatingsThenReturnNull() throws CoreException, MalformedURLException {
        Integer result = instance.getImageRating(document);
        Assert.assertNull(result);
    }
    
    @Test
    public void givenNextImageURLExtractorWhenGetNextImageURLThenReturnNotNull() throws CoreException, MalformedURLException, ElementExtractorNotSetException {
        doReturn(IMAGE_URL).when(elementValueExtractor).getValue(document);
        doReturn(elementValueExtractor).when(imageElementsExtractors).getNextElementExtractor();
        URLWrapper result = instance.getNextImageURL(document);
        Assert.assertEquals(new URLWrapper(IMAGE_URL), result);
    }
    
    @Test
    public void givenMalformedURLWhenGetNextImageURLThenReturnNull() throws CoreException, MalformedURLException, ElementExtractorNotSetException {
        doReturn("").when(elementValueExtractor).getValue(document);
        doReturn(elementValueExtractor).when(imageElementsExtractors).getNextElementExtractor();
        URLWrapper result = instance.getNextImageURL(document);
        Assert.assertNull(result);
    }
    
    @Test
    public void givenNullNextImageURLExtractorWhenGetNextImageURLThenReturnNull() throws CoreException, MalformedURLException {
        URLWrapper result = instance.getNextImageURL(document);
        Assert.assertNull(result);
    }
    
    @Test
    public void givenBadSelectorWhenGetNextImageURLThenReturnNull() throws CoreException, MalformedURLException, ElementExtractorNotSetException {
        doReturn(null).when(elementValueExtractor).getValue(document);
        doReturn(elementValueExtractor).when(imageElementsExtractors).getNextElementExtractor();
        URLWrapper result = instance.getNextImageURL(document);
        Assert.assertNull(result);
    }
}
