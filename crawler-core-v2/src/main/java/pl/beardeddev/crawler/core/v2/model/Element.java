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
package pl.beardeddev.crawler.core.v2.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Szymon Grzelak
 */
public class Element {

    private final Map<String, Attribute> ATTRIBUTES;

    public Element() {
        this.ATTRIBUTES = new HashMap<>();
    }

    public Element(Map<String, Attribute> result) {
        this.ATTRIBUTES = result;
    }

    public Attribute getElementAttr(String name) {
        return ATTRIBUTES.get(name);
    }

    public Optional<Attribute> addElementAttr(String name, Attribute attribute) {
        return Optional.ofNullable(ATTRIBUTES.put(name, attribute));
    }

    @Getter
    @Setter
    public static class Attribute {

        private Type type;
        private String value;

        public Attribute(Type type, String value) {
            this.type = type;
            this.value = value;
        }
    }
}
