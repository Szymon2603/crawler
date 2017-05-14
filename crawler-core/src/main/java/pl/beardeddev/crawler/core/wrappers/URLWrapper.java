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
package pl.beardeddev.crawler.core.wrappers;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Klasa opakowująca klasę {@code java.net.URL} umożliwiająca wykonanie testów jednostkowych.
 * 
 * @author Szymon Grzelak
 */
@ToString
@EqualsAndHashCode
public class URLWrapper implements Serializable {

    private static final long serialVersionUID = 6024044659311363956L;

    private URL url;

    public URLWrapper() {}

    public URLWrapper(String url) throws MalformedURLException {
        this.url = new URL(url);
    }
    
    public URLWrapper(URL url) {
        this.url = url;
    }
    
    /**
     * Wywołuje odpowiednią metodę klasy {@code java.net.URL} w celu uzyskania połączenia z danym zasobem
     * 
     * @return połączenie z danym zasobem
     * @throws IOException błędy przy próbie utworzenia połączenia
     */
    public URLConnection openConnection() throws IOException {
        return url.openConnection();
    }

    public URL getURL() {
        return url;
    }

    public void setURL(URL url) {
        this.url = url;
    }
    
}
