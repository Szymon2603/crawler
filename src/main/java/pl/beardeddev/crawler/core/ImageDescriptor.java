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

/**
 * Klasa zawierająca selektor do elementów, które mają zostać odnalezione na stronach HTML.
 * 
 * @author Szymon Grzelak
 */
public class ImageDescriptor {

    private final String IMAGE_SELECTOR;
    private final String NEXT_ELEMENT_SELECTOR;
    private final String COMMENTS_SELECTOR;
    private final String RATING_SELECTOR;

    public ImageDescriptor(String imageSelector, String nextElementSelector, String commentsSelector, String ratingSelector) {
        this.IMAGE_SELECTOR = imageSelector;
        this.NEXT_ELEMENT_SELECTOR = nextElementSelector;
        this.COMMENTS_SELECTOR = commentsSelector;
        this.RATING_SELECTOR = ratingSelector;
    }
    
    /**
     * Zwraca selektor elementu zawierającego obrazek.
     * 
     * @return selektor w postaci łańcucha znakowego.
     */
    public String getImageSelector() {
        return IMAGE_SELECTOR;
    }

    /**
     * Zwraca selektor linku prowadzącego do następnej strony z obrazkiem.
     * 
     * @return selektor w postaci łańcucha znakowego.
     */
    public String getNextElementSelector() {
        return NEXT_ELEMENT_SELECTOR;
    }

    /**
     * Zwraca selektor do elementu zawierającego liczbę komentarzy.
     * 
     * @return selektor w postaci łańcucha znakowego.
     */
    public String getCommentsSelector() {
        return COMMENTS_SELECTOR;
    }

    /**
     * Zwraca selektor do elementu zawierającego ocenę obrazka.
     * 
     * @return selektor w postaci łańcucha znakowego.
     */
    public String getRatingSelector() {
        return RATING_SELECTOR;
    }
    
}
