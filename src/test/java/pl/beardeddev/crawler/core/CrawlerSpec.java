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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import pl.beardeddev.crawler.core.wrappers.URLWrapper;
import pl.beardeddev.crawler.domain.Image;
import pl.beardeddev.crawler.exceptions.CoreException;

/**
 *
 * @author Szymon Grzelak
 */
public class CrawlerSpec {
    
    private URL url;
    private URLWrapper urlWrapper;
    private Crawler crawler;
    private DocumentProvider documentProvider;
    private ImageDescriptor imageDescriptor;
    private String imageSelector;
    private String nextElementSelector;
    private String commentsSelector;
    private String ratingSelector;
    
    @Before
    public void setUp() {
        url = this.getClass().getClassLoader().getResource("testPage.html");
        urlWrapper = spy(new URLWrapper(url));
        documentProvider = spy(new PageProvider());
        imageSelector = "img";
        nextElementSelector = "a";
        commentsSelector = "div#commentsNumber";
        ratingSelector = "div[id=rateNummber]";
        imageDescriptor = spy(new ImageDescriptor(imageSelector, nextElementSelector, commentsSelector, ratingSelector));
        crawler = spy(new Crawler(documentProvider, imageDescriptor));
    }
    
    @Test(expected = CoreException.class)
    public void givenNullArgWhenGetImageThenThrowCoreException() throws CoreException, MalformedURLException {
        crawler.getImage(null);
    }
    
    @Test(expected = CoreException.class)
    public void givenNullArgWhenGetImagesThenThrowCoreException() throws CoreException {
        crawler.getImages(null);
    }
    
    @Test()
    public void givenNoImageWhenGetImageThenReturnNull() throws CoreException, MalformedURLException {
        doReturn("img[id=notexist]").when(imageDescriptor).getImageSelector();
        Image result = crawler.getImage(urlWrapper);
        Assert.assertNull("Should be null", result);
    }
    
    @Test()
    public void givenImageWhenGetImageThenImageURLIsSet() throws CoreException, MalformedURLException {
        Image result = crawler.getImage(urlWrapper);
        URL expected = new URL("https://local");
        Assert.assertTrue("URL is not equals!", expected.equals(result.getImageURL()));
    }
    
    @Test()
    public void givenNumOfCommentsWhenGetImageThenNumOfCommentsPropertyIsSet() throws CoreException, MalformedURLException {
        Image result = crawler.getImage(urlWrapper);
        Integer expected = 10;
        Integer actual = result.getNumberOfComments();
        Assert.assertTrue(String.format("Number of comments is not equals! Actual is: %d", actual), expected.equals(actual));
    }
    
    @Test()
    public void givenBadNumOfCommentsSelectorWhenGetImageThenValueIsNotSet() throws CoreException, MalformedURLException {
        doReturn("div#badId").when(imageDescriptor).getCommentsSelector();
        Image result = crawler.getImage(urlWrapper);
        Integer actual = result.getNumberOfComments();
        Assert.assertNull(String.format("Number of comments should be null"), actual);
    }
}
