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

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.beardeddev.crawler.core.suppliers.ElementValueExtractor;

/**
 * Klasa abstrakcyjna dla konfiguracji sposobu pozyskiwania elementów zawartych w dokumencie.
 * 
 * @author Szymon Grzelak
 */
@Entity
@Table(name = "EXTRACTOR_CONFIG")
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@ToString
@EqualsAndHashCode(of = "id")
public abstract class ExtractorConfig implements Serializable {

    private static final long serialVersionUID = 6853121706485850783L;
    
    @Id
    @GeneratedValue
    @Column(name = "ID") 
    protected Long id;
    
    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    protected Extractors type;
    
    @Column(name = "ELEMENT_SELECTOR")
    protected String elementSelector;

    public ExtractorConfig() {}
    
    public ExtractorConfig(Long id, Extractors type, String elementSelector) {
        this.id = id;
        this.type = type;
        this.elementSelector = elementSelector;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Extractors getType() {
        return type;
    }

    public void setType(Extractors type) {
        this.type = type;
    }
    
    public String getElementSelector() {
        return elementSelector;
    }

    public void setElementSelector(String elementSelector) {
        this.elementSelector = elementSelector;
    }
    
    public ElementValueExtractor getElementValueExtractor() {
        return type.getElementValueExtractor(this);
    }
}
