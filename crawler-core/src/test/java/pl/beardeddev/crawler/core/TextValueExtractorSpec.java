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

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import pl.beardeddev.crawler.core.exceptions.BadConfigurationException;
import pl.beardeddev.crawler.core.suppliers.ElementValueExtractor;
import pl.beardeddev.crawler.core.suppliers.impl.TextValueExtractor;

/**
 *
 * @author Szymon Grzelak
 */
public class TextValueExtractorSpec {
    
    private ElementValueExtractor extractor;
    private Document document;
    private Elements elements;
    private String commentsSelector;
    
    @Before
    public void setUp() throws BadConfigurationException {
        commentsSelector = "div#commentsNumber";
        extractor = TextValueExtractor.getInstance(commentsSelector);
        elements = mock(Elements.class);
        document = mock(Document.class);
        doReturn(elements).when(document).select(commentsSelector);
    }
    
    @Test(expected = BadConfigurationException.class)
    public void givenNullCommentsSelectorWhenCallGetInstanceThenThrowBadConfigurationException() throws BadConfigurationException {
        extractor = TextValueExtractor.getInstance(null);
    }
    
    @Test
    public void whenElementAbsentThenReturnValue() {
        final String expected = "value";
        doReturn(expected).when(elements).text();
        String value = extractor.getValue(document);
        Assert.assertEquals("Expected value should be not null!", expected, value);
    }
    
    @Test
    public void whenElementsEmptyThenReturnNull() {
        doReturn(true).when(elements).isEmpty();
        String value = extractor.getValue(document);
        Assert.assertNull("Expected null value!", value);
    }
    
    @Test
    public void whenAttributeHaveNoValueThenReturnNull() {
        doReturn(null).when(elements).text();
        String value = extractor.getValue(document);
        Assert.assertNull("Expected null value!", value);
    }
    
    @Test
    public void whenBadSelectorThenReturnNull() throws BadConfigurationException {
        extractor = TextValueExtractor.getInstance("");
        doReturn(elements).when(document).select(any(String.class));
        String result = extractor.getValue(document);
        Assert.assertNull(result);
    }
}
