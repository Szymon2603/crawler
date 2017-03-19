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
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import pl.beardeddev.crawler.core.wrappers.URLWrapper;
import pl.beardeddev.crawler.core.exceptions.CoreException;

/**
 * Specyfikacja wykonywalna dla klasy {@code PagePRovider}
 * 
 * @author Szymon Grzelak
 */
public class PageProviderSpec {
    
    private PageProvider pageProvider;
    private URL url;
    private URLWrapper urlWrapper;
    private URLConnection urlConnection;
    
    @Before
    public void setUp() throws MalformedURLException, IOException {
        pageProvider = spy(new PageProvider());
        url = this.getClass().getClassLoader().getResource("testPage.html");
        urlWrapper = spy(new URLWrapper(url));
        urlConnection = mock(URLConnection.class);
    }
    
    @Test
    public void whenGetPageByLocalURLThenReturnPage() throws CoreException {
        Document result = pageProvider.getDocument(urlWrapper);
        Assert.assertNotNull("Page can't be null", result);
    }
    
    @Test
    public void whenGetEncodingIsSetThenReturnDocumentWithThatEncoding() throws CoreException, IOException {
        doReturn(urlConnection).when(urlWrapper).openConnection();
        doReturn("UTF-8").when(urlConnection).getContentEncoding();
        doReturn(url.openStream()).when(urlConnection).getInputStream();
        Document result = pageProvider.getDocument(urlWrapper);
        Assert.assertTrue("Encdocing should be UTF-8", result.charset().contains(Charset.forName("UTF-8")));
    }
    
    @Test(expected = CoreException.class)
    public void givenIOExceptionWhenGetInputStreamThenThrowCoreException() throws CoreException, IOException {
        doReturn(urlConnection).when(urlWrapper).openConnection();
        doThrow(IOException.class).when(urlConnection).getInputStream();
        pageProvider.getDocument(urlWrapper);
    }
    
    @Test(expected = CoreException.class)
    public void gicenIOExceptionWhenOpenConnectionThenThrowCoreException() throws CoreException, IOException {
        doThrow(IOException.class).when(urlWrapper).openConnection();
        pageProvider.getDocument(urlWrapper);
    }
}
