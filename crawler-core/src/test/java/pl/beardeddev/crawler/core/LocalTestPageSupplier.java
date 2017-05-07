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

import pl.beardeddev.crawler.core.suppliers.ImageElementsSupplier;
import java.net.MalformedURLException;
import java.net.URL;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.beardeddev.crawler.core.wrappers.URLWrapper;
import pl.beardeddev.crawler.core.exceptions.CoreException;
import pl.beardeddev.crawler.core.suppliers.ElementValueExtractor;
import pl.beardeddev.crawler.core.suppliers.ImageElementsExtractors;

/**
 * Klasa implementująca {@see ImageElementsSupplier} dla testów jednostkowych {@see Crawler}
 * 
 * @author Szymon Grzelak
 */
public class LocalTestPageSupplier implements ImageElementsSupplier {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(LocalTestPageSupplier.class);
    //private final ImageDescriptor IMAGE_DESCRIPTOR;
    private final ImageElementsExtractors IMAGE_ELEMENTS_EXTRACTORS;
    private final String PROTOCOL;
    
    public LocalTestPageSupplier(ImageElementsExtractors imageElementsExtractors) {
        IMAGE_ELEMENTS_EXTRACTORS = imageElementsExtractors;
        //Pozyskanie sciezki katalogu z lokalnymi plikami testowymi
        String file = getClass().getClassLoader().getResource("testPage.html").getPath();
        int lastIndex = file.lastIndexOf("/") + 1;
        PROTOCOL = "file:///" + file.substring(0, lastIndex);
    }

    @Override
    public URLWrapper getImageURL(Document document) throws CoreException {
        try {
            ElementValueExtractor extractor = IMAGE_ELEMENTS_EXTRACTORS.getImageExtractor();
            String urlString = extractor.getValue(document);
            LOGGER.debug("Extracted URL: %s", urlString);
            return urlString != null ? new URLWrapper(urlString) : null;
        } catch(MalformedURLException ex) {
            LOGGER.error("Bad URL!", ex);
            throw new CoreException("Bad URL!", ex);
        }
    }

    @Override
    public Integer getImageNumberOfComments(Document document) throws CoreException {
        ElementValueExtractor extractor = IMAGE_ELEMENTS_EXTRACTORS.getCommentsExtractor();
        String elementValue = extractor.getValue(document);
        try {
            Integer numberOfComments = Integer.parseInt(elementValue);
            return numberOfComments;
        } catch(NumberFormatException ex) {
            LOGGER.warn("Can't parse number of comments. Return null instead.", ex);
            return null;
        }
    }

    @Override
    public Integer getImageRatings(Document document) throws CoreException {
        ElementValueExtractor extractor = IMAGE_ELEMENTS_EXTRACTORS.getRatingExtractor();
        String elementValue = extractor.getValue(document);
        try {
            Integer rating = Integer.parseInt(elementValue);
            return rating;
        } catch (NumberFormatException ex) {
            LOGGER.warn("Can't parse ratings. Return null instead.", ex);
            return null;
        }
    }

    @Override
    public URLWrapper getNextImageURL(Document document) throws CoreException {
        ElementValueExtractor extractor = IMAGE_ELEMENTS_EXTRACTORS.getNextElementExtractor();
        String elementValue = fixProtocol(extractor.getValue(document));
        if(elementValue == null) {
            LOGGER.warn("URL not found!");
            return null;
        }
        try {
            LOGGER.info("Next image URL is: %s", elementValue);
            URL url = new URL(elementValue);
            return new URLWrapper(url);
        } catch(MalformedURLException ex) {
            LOGGER.warn("Bad URL! [%s]", elementValue, ex);
            return null;
        }
    }
    
    private String fixProtocol(String url) {
        if(url.startsWith(PROTOCOL)) {
            return url;
        } else {
            return PROTOCOL + url;
        }
    }
}
