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
package pl.beardeddev.crawler.app.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Klasa encyjna przechowująca informacje na temat selektora i nazwy atrybutu elementu z którego ma zostać pobrana
 * wartość w dokumencie.
 * 
 * @author Szymon Grzelak
 */
@Entity
@Table(name = "ATTRIBUTE_VALUE_EXTRACTOR_CONFIG")
@PrimaryKeyJoinColumn(name = "ID")
@ToString
@EqualsAndHashCode(callSuper = true)
public class AttributeValueExtractorConfig extends ExtractorConfig {

    private static final long serialVersionUID = -3522944652114728696L;
    
    @Column(name = "ATTRIBUTE_NAME")
    private String attributeName;

    public AttributeValueExtractorConfig() {}

    public AttributeValueExtractorConfig(String attributeName, Long id, Extractors type, String elementSelector) {
        super(id, type, elementSelector);
        this.attributeName = attributeName;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }
}
