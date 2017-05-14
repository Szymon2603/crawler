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

import java.net.MalformedURLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.ToString;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.beardeddev.crawler.core.exceptions.CoreException;
import pl.beardeddev.crawler.core.exceptions.ElementExtractorNotSetException;
import pl.beardeddev.crawler.core.suppliers.ElementValueExtractor;
import pl.beardeddev.crawler.core.suppliers.ImageElementsExtractors;
import pl.beardeddev.crawler.core.suppliers.ImageElementsSupplier;
import pl.beardeddev.crawler.core.wrappers.URLWrapper;

/**
 * Domyślna implementacja interfejsu {@class ImageElementsSupplier}. Do poprawnego działania potrzeuje informacji
 * na temat protokołu jaki jest używany przez dany serwis w celu utworzenia poprawnych adresów URL do zasobów takich
 * jak adres obrazka lub adres następnego elementu.
 * 
 * @author Szymon Grzelak
 */
@ToString
public class ImageElementsSupplierImpl implements ImageElementsSupplier {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageElementsSupplierImpl.class);
    private final ImageElementsExtractors IMAGE_ELEMENTS_EXTRACTORS;
    private final NumberFormat NUMBER_FORMAT;

    public ImageElementsSupplierImpl(ImageElementsExtractors imageElementsExtractors, Locale locale) {
        IMAGE_ELEMENTS_EXTRACTORS = imageElementsExtractors;
        NUMBER_FORMAT = NumberFormat.getIntegerInstance(locale);
    }
    
    @Override
    public URLWrapper getImageURL(Document document) throws CoreException {
        try {
            String stringValue = getStringValue(IMAGE_ELEMENTS_EXTRACTORS::getImageExtractor, document);
            LOGGER.debug("Extracted URL: {}", stringValue);
            return new URLWrapper(stringValue);
        } catch(MalformedURLException ex) {
            LOGGER.warn("Bad URL!", ex);
        } catch (NoSuchElementException ex) {
            LOGGER.warn("Can't find image url. Return null instead", ex);
        } catch (ElementExtractorNotSetException ex) {
            LOGGER.warn("Image extractor is not set, return null instead.", ex);
        }
        return null;
    }

    @Override
    public Integer getImageNumberOfComments(Document document) throws CoreException {
        try {
            String stringValue = getStringValue(IMAGE_ELEMENTS_EXTRACTORS::getCommentsExtractor, document);
            Integer numberOfComments = NUMBER_FORMAT.parse(stringValue).intValue();
            return numberOfComments;
        } catch(ParseException ex) {
            LOGGER.warn("Can't parse number of comments. Return null instead.", ex);
        } catch(NoSuchElementException ex) {
            LOGGER.info("Can't find number of comments. Return null instead");
        } catch (ElementExtractorNotSetException ex) {
            LOGGER.warn("Number of comments extractor is not set, return null instead.", ex);
        }
        return null;
    }

    @Override
    public Integer getImageRatings(Document document) throws CoreException {
        try {
            String stringValue = getStringValue(IMAGE_ELEMENTS_EXTRACTORS::getRatingExtractor, document);
            Integer rating = NUMBER_FORMAT.parse(stringValue).intValue();
            return rating;
        } catch (ParseException ex) {
            LOGGER.warn("Can't parse ratings. Return null instead.", ex);
        } catch(NoSuchElementException ex) {
            LOGGER.info("Can't find ratings. Return null instead"); 
        } catch (ElementExtractorNotSetException ex) {
            LOGGER.warn("Ratings extractor is not set, return null instead.", ex);
        }
        return null;
    }

    @Override
    public URLWrapper getNextImageURL(Document document) throws CoreException {
        try {
            String stringValue = getStringValue(IMAGE_ELEMENTS_EXTRACTORS::getNextElementExtractor, document);
            LOGGER.info("Extracted next image URL is: {}", stringValue);
            return new URLWrapper(stringValue);
        } catch(MalformedURLException ex) {
            LOGGER.warn("Bad URL!", ex);
        } catch(NoSuchElementException ex) {
            LOGGER.warn("URL not found!");
        } catch (ElementExtractorNotSetException ex) {
            LOGGER.warn("Next image URL extractor is not set, return null instead.", ex);
        }
        return null;
    }
    
    private String getStringValue(ElementValueExtractorSupplier supplier, Document document) throws ElementExtractorNotSetException {
        ElementValueExtractor extractor = supplier.get();
        Optional<String> elementValue = Optional.ofNullable(extractor.getValue(document));
        String stringValue = elementValue.get();
        return stringValue;
    }
    
    @FunctionalInterface
    private interface ElementValueExtractorSupplier {
        ElementValueExtractor get() throws ElementExtractorNotSetException;
    }
}
