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
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Klasa encyjna będąca reprezentacją pakietu konfiguracji dla crawlera. Jest to encja z ogólnymi informacjami pakietu.
 * 
 * @author Szymon Grzelak
 */
@Entity
@Table(name = "CONFIG_PACKAGE_MASTER")
@ToString
@EqualsAndHashCode(of = "id")
public class ConfigPackageMaster implements Serializable {

    private static final long serialVersionUID = 5012196746521016730L;
    
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "NAME")
    private String name;
    
    @JoinColumn(name = "LOCALE")
    @ManyToOne
    private NumberFormatLocale numberFormatLocale;
    
    @OneToMany(mappedBy = "packageMaster", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE }, fetch = FetchType.EAGER)
    private Set<ConfigPackageDetail> packageDetails;

    public ConfigPackageMaster() {}

    public ConfigPackageMaster(Long id, String name, NumberFormatLocale numberFormatLocale, Set<ConfigPackageDetail> packageDetails) {
        this.id = id;
        this.name = name;
        this.numberFormatLocale = numberFormatLocale;
        this.packageDetails = packageDetails;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NumberFormatLocale getNumberFormatLocale() {
        return numberFormatLocale;
    }

    public void setNumberFormatLocale(NumberFormatLocale numberFormatLocale) {
        this.numberFormatLocale = numberFormatLocale;
    }
    
    public Set<ConfigPackageDetail> getPackageDetails() {
        return packageDetails;
    }

    public void setPackageDetails(Set<ConfigPackageDetail> packageDetails) {
        this.packageDetails = packageDetails;
    }
}
