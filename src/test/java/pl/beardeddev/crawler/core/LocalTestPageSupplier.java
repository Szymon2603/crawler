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
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import pl.beardeddev.crawler.core.wrappers.URLWrapper;
import pl.beardeddev.crawler.exceptions.CoreException;

/**
 * Klasa implementująca {@see ImageElementsSupplier} dla testów jednostkowych {@see Crawler}
 * 
 * @author Szymon Grzelak
 */
public class LocalTestPageSupplier implements ImageElementsSupplier {
    
    private static final Logger LOGGER = Logger.getLogger(LocalTestPageSupplier.class);
    private final ImageDescriptor IMAGE_DESCRIPTOR;
    private final String PROTOCOL;
    
    public LocalTestPageSupplier(ImageDescriptor imageDescriptor) {
        IMAGE_DESCRIPTOR = imageDescriptor;
        //Pozyskanie sciezki katalogu z lokalnymi plikami testowymi
        String file = getClass().getClassLoader().getResource("testPage.html").getPath();
        int lastIndex = file.lastIndexOf("/") + 1;
        PROTOCOL = "file:///" + file.substring(0, lastIndex);
    }

    @Override
    public URLWrapper getImageURL(Document document) throws CoreException {
        Elements elements = document.select(IMAGE_DESCRIPTOR.getImageSelector());
        if(elements.isEmpty()) {
            return null;
        }
        try {
            URL url = new URL(elements.attr("src"));
            return new URLWrapper(url);
        } catch(MalformedURLException ex) {
            LOGGER.error("Bad URL!", ex);
            throw new CoreException("Bad URL!", ex);
        }
    }

    @Override
    public Integer getImageNumberOfComments(Document document) throws CoreException {
        Elements elements = document.select(IMAGE_DESCRIPTOR.getCommentsSelector());
        String elementValue = elements.text();
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
        Elements elements = document.select(IMAGE_DESCRIPTOR.getRatingSelector());
        String elementValue = elements.text();
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
        Elements elements = document.select(IMAGE_DESCRIPTOR.getNextElementSelector());
        String elementValue = fixProtocol(elements.attr("href"));
        if(elements.isEmpty()) {
            LOGGER.warn("URL not found!");
            return null;
        }
        try {
            LOGGER.info(String.format("Next image URL is: %s", elementValue));
            URL url = new URL(elementValue);
            return new URLWrapper(url);
        } catch(MalformedURLException ex) {
            LOGGER.warn(String.format("Bad URL! [%s]", elementValue), ex);
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
