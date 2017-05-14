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

import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.beardeddev.crawler.core.exceptions.ElementExtractorNotSetException;

/**
 * Klasa agregujaca ekstraktory {@class ElementValueExtractor}. W przypadku, gdy którś z ekstraktorów nie został ustawiony
 * zostaje zgłoszony wyjątek {@class ElementExtractorNotSetException}.
 * 
 * @author Szymon Grzelak
 */
@ToString
public class ImageElementsExtractors {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageElementsExtractors.class);
    private static final String IMAGE_EXTRACTOR_NOT_SET = "Image extractor not set! It may cause problems.";
    private static final String NEXT_ELEMENT_EXTRACTOR_NOT_SET = "Next element extractor not set! It may cause problems.";
    private static final String COMMENTS_EXTRACTOR_NOT_SET = "Comments extractor not set! It may cause problems.";
    private static final String RATING_EXTRACTOR_NOT_SET = "Rating extractor not set! It may cause problems.";
    
    private final ElementValueExtractor IMAGE_EXTRACTOR;
    private final ElementValueExtractor NEXT_ELEMENT_EXTRACTOR;
    private final ElementValueExtractor COMMENTS_EXTRACTOR;
    private final ElementValueExtractor RATING_EXTRACTOR;

    public ImageElementsExtractors(ElementValueExtractor imageExtractor, ElementValueExtractor nextElementExtractor, ElementValueExtractor commentsExtractor, ElementValueExtractor ratingExtractor) {
        this.IMAGE_EXTRACTOR = imageExtractor;
        this.NEXT_ELEMENT_EXTRACTOR = nextElementExtractor;
        this.COMMENTS_EXTRACTOR = commentsExtractor;
        this.RATING_EXTRACTOR = ratingExtractor;
        checkConfiguration();
    }
    
    public ElementValueExtractor getImageExtractor() throws ElementExtractorNotSetException {
        if(IMAGE_EXTRACTOR == null) {
            throw new ElementExtractorNotSetException(IMAGE_EXTRACTOR_NOT_SET);
        }
        return IMAGE_EXTRACTOR;
    }

    public ElementValueExtractor getNextElementExtractor() throws ElementExtractorNotSetException {
        if(NEXT_ELEMENT_EXTRACTOR == null) {
            throw new ElementExtractorNotSetException(NEXT_ELEMENT_EXTRACTOR_NOT_SET);
        }
        return NEXT_ELEMENT_EXTRACTOR;
    }

    public ElementValueExtractor getCommentsExtractor() throws ElementExtractorNotSetException {
        if(COMMENTS_EXTRACTOR == null) {
            throw new ElementExtractorNotSetException(COMMENTS_EXTRACTOR_NOT_SET);
        }
        return COMMENTS_EXTRACTOR;
    }

    public ElementValueExtractor getRatingExtractor() throws ElementExtractorNotSetException {
        if(RATING_EXTRACTOR == null) {
            throw new ElementExtractorNotSetException(RATING_EXTRACTOR_NOT_SET);
        }
        return RATING_EXTRACTOR;
    }

    private void checkConfiguration() {
        if (IMAGE_EXTRACTOR == null) {
            LOGGER.warn(IMAGE_EXTRACTOR_NOT_SET);
        }
        if (NEXT_ELEMENT_EXTRACTOR == null) {
            LOGGER.warn(NEXT_ELEMENT_EXTRACTOR_NOT_SET);
        }
        if (COMMENTS_EXTRACTOR == null) {
            LOGGER.warn(COMMENTS_EXTRACTOR_NOT_SET);
        }
        if (RATING_EXTRACTOR == null) {
            LOGGER.warn(RATING_EXTRACTOR_NOT_SET);
        }
    }
}
