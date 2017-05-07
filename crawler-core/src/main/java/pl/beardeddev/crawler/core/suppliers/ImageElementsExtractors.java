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
package pl.beardeddev.crawler.core.suppliers;

/**
 *
 * @author Szymon Grzelak
 */
public class ImageElementsExtractors {
    
    private final ElementValueExtractor IMAGE_EXTRACTOR;
    private final ElementValueExtractor NEXT_ELEMENT_EXTRACTOR;
    private final ElementValueExtractor COMMENTS_EXTRACTOR;
    private final ElementValueExtractor RATING_EXTRACTOR;

    public ImageElementsExtractors(ElementValueExtractor imageExtractor, ElementValueExtractor nextElementExtractor, ElementValueExtractor commentsExtractor, ElementValueExtractor ratingExtractor) {
        this.IMAGE_EXTRACTOR = imageExtractor;
        this.NEXT_ELEMENT_EXTRACTOR = nextElementExtractor;
        this.COMMENTS_EXTRACTOR = commentsExtractor;
        this.RATING_EXTRACTOR = ratingExtractor;
    }

    public ElementValueExtractor getImageExtractor() {
        return IMAGE_EXTRACTOR;
    }

    public ElementValueExtractor getNextElementExtractor() {
        return NEXT_ELEMENT_EXTRACTOR;
    }

    public ElementValueExtractor getCommentsExtractor() {
        return COMMENTS_EXTRACTOR;
    }

    public ElementValueExtractor getRatingExtractor() {
        return RATING_EXTRACTOR;
    }
}
