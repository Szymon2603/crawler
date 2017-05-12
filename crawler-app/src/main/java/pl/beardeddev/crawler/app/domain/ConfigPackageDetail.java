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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Klasa encyjna będąca reprezentacją pakietu konfiguracji dla crawlera. Jest to encja ze szczegółami pakietu, przechowu
 * jąca informację o jednej wybranej konfiguracji.
 * 
 * @author Szymon Grzelak
 */
@Entity
@Table(name = "CONFIG_PACKAGE_DETAIL")
@ToString(exclude = "packageMaster")
@EqualsAndHashCode(of = "id")
public class ConfigPackageDetail implements Serializable {

    private static final long serialVersionUID = -3819581089894532412L;
    
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "DETAIL_NAME")
    @Enumerated(EnumType.STRING)
    private Configs detailName;
    
    @JoinColumn(name = "CONFIG")
    @ManyToOne(optional = false)
    private ExtractorConfig config;
    
    @JoinColumn(name = "PACKAGE_MASTER")
    @ManyToOne(optional = false)
    private ConfigPackageMaster packageMaster;

    public ConfigPackageDetail() {}

    public ConfigPackageDetail(Long id, Configs detailName, ExtractorConfig config, ConfigPackageMaster packageMaster) {
        this.id = id;
        this.detailName = detailName;
        this.config = config;
        this.packageMaster = packageMaster;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Configs getDetailName() {
        return detailName;
    }

    public void setDetailName(Configs detailName) {
        this.detailName = detailName;
    }

    public ExtractorConfig getConfig() {
        return config;
    }

    public void setConfig(ExtractorConfig config) {
        this.config = config;
    }

    public ConfigPackageMaster getPackageMaster() {
        return packageMaster;
    }

    public void setPackageMaster(ConfigPackageMaster packageMaster) {
        this.packageMaster = packageMaster;
    }
}
