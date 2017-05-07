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

import org.junit.Assert;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.doReturn;
import pl.beardeddev.crawler.core.suppliers.impl.AttributeValueExtractor;
import pl.beardeddev.crawler.core.suppliers.ElementValueExtractor;
import static org.mockito.Mockito.mock;

/**
 *
 * @author Szymon Grzelak
 */
public class AttributeValueExtractorSpec {

    private ElementValueExtractor extractor;
    private Document document;
    private Elements elements; 
    private String imageSelector;
    private String attributeName;
    
    @Before
    public void setUp() {
        imageSelector = "img";
        attributeName = "src";
        extractor = new AttributeValueExtractor(imageSelector, attributeName);
        document = mock(Document.class);
        elements = mock(Elements.class);
    }
    
    @Test
    public void whenElementAbsentThenReturnValue() {
        doReturn(elements).when(document).select(imageSelector);
        final String expected = "value";
        doReturn(expected).when(elements).attr(attributeName);
        String value = extractor.getValue(document);
        Assert.assertEquals("Expected value should be not null!", expected, value);
    }
    
    @Test
    public void whenElementsEmptyThenReturnNull() {
        doReturn(elements).when(document).select(imageSelector);
        doReturn(true).when(elements).isEmpty();
        String value = extractor.getValue(document);
        Assert.assertNull("Expected null value!", value);
    }
    
    @Test
    public void whenAttributeHaveNoValueThenReturnNull() {
        doReturn(elements).when(document).select(imageSelector);
        doReturn(null).when(elements).attr(attributeName);
        String value = extractor.getValue(document);
        Assert.assertNull("Expected null value!", value);
    }
}
