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
 * Implementacja Budowniczego dla {@class ImageElementsExtractors}.
 * 
 * @author Szymon Grzelak
 */
public class ImageElementsExtractorsBuilder {

    private ElementValueExtractor imageExtractor;
    private ElementValueExtractor nextElementExtractor;
    private ElementValueExtractor commentsExtractor;
    private ElementValueExtractor ratingExtractor;

    public ImageElementsExtractorsBuilder() {
    }

    public ImageElementsExtractorsBuilder setImageExtractor(ElementValueExtractor imageExtractor) {
        this.imageExtractor = imageExtractor;
        return this;
    }

    public ImageElementsExtractorsBuilder setNextElementExtractor(ElementValueExtractor nextElementExtractor) {
        this.nextElementExtractor = nextElementExtractor;
        return this;
    }

    public ImageElementsExtractorsBuilder setCommentsExtractor(ElementValueExtractor commentsExtractor) {
        this.commentsExtractor = commentsExtractor;
        return this;
    }

    public ImageElementsExtractorsBuilder setRatingExtractor(ElementValueExtractor ratingExtractor) {
        this.ratingExtractor = ratingExtractor;
        return this;
    }

    public ImageElementsExtractors build() {
        return new ImageElementsExtractors(imageExtractor, nextElementExtractor, commentsExtractor, ratingExtractor);
    }
    
}