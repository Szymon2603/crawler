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
package pl.beardeddev.crawler.app.factory;

import java.util.Locale;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.beardeddev.crawler.app.domain.ConfigPackageDetail;
import pl.beardeddev.crawler.app.domain.ConfigPackageMaster;
import pl.beardeddev.crawler.app.domain.Configs;
import pl.beardeddev.crawler.app.domain.ExtractorConfig;
import pl.beardeddev.crawler.app.exceptions.BusinessLogicRuntimeException;
import pl.beardeddev.crawler.core.Crawler;
import pl.beardeddev.crawler.core.factory.BaseCrawlerFactory;
import pl.beardeddev.crawler.core.suppliers.ElementValueExtractor;
import pl.beardeddev.crawler.core.suppliers.ImageElementsExtractors;
import pl.beardeddev.crawler.core.suppliers.ImageElementsExtractorsBuilder;
import pl.beardeddev.crawler.core.suppliers.ImageElementsSupplier;
import pl.beardeddev.crawler.core.suppliers.impl.ImageElementsSupplierImpl;

/**
 * Prosta implementacja fabryki crawler-ów. Bazuje na dostarczonych do fabryki elementów wymaganych przez {@class Crawler}.
 * Rozszerza klasę {@class BaseCrawlerFactory}, która dostarcza domyślnej implementacji {@class PageProvider}-a
 * 
 * @author Szymon Grzelak
 */
public class CrawlerFactoryImpl extends BaseCrawlerFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(CrawlerFactoryImpl.class);
    
    private final Locale locale;
    private final ConfigPackageMaster config;

    public CrawlerFactoryImpl(Locale locale, ConfigPackageMaster config) {
        this.locale = locale;
        this.config = config;
    }
    
    @Override
    public Crawler makeCrawler() {
        LOGGER.debug("Create Crawler");
        ImageElementsSupplier imageElementsSupplier = createImageElementsSupplier();
        Crawler crawler = new Crawler(getPageProvider(), imageElementsSupplier);
        LOGGER.debug("Successful crawler creation");
        return crawler;
    }

    private ImageElementsSupplier createImageElementsSupplier() {
        LOGGER.debug("Create ImageElementsSupplier");
        ImageElementsExtractors imageElementsExtractors = createImageElementsExtractors();
        ImageElementsSupplier imageElementsSupplier = new ImageElementsSupplierImpl(imageElementsExtractors, locale);
        return imageElementsSupplier;
    }
    
    private ImageElementsExtractors createImageElementsExtractors() {
        LOGGER.debug("Create ImageElementsExtractors based on config -> {}", config);
        ImageElementsExtractorsBuilder builder = new ImageElementsExtractorsBuilder();
        Set<ConfigPackageDetail> details = config.getPackageDetails();
        builder.setImageExtractor(findElementValueExtractor(details, Configs.IMAGE_EXTRACTOR))
                .setCommentsExtractor(findElementValueExtractor(details, Configs.COMMENTS_EXTRACTOR))
                .setRatingExtractor(findElementValueExtractor(details, Configs.RATING_EXTRACTOR))
                .setNextElementExtractor(findElementValueExtractor(details, Configs.NEXT_ELEMENT_EXTRACTOR));
        ImageElementsExtractors imageElementsExtractors = builder.build();
        return imageElementsExtractors;
    }
    
    private ElementValueExtractor findElementValueExtractor(Set<ConfigPackageDetail> details, Configs configType) {
        LOGGER.debug("Looking for config type: {}", configType);
        ExtractorConfig extractorConfig = details
                .stream()
                .filter(e -> configType.equals(e.getDetailName()))
                .findFirst().get().getConfig();
        
        return getElementValueExtractor(extractorConfig, configType);
    }

    private ElementValueExtractor getElementValueExtractor(ExtractorConfig extractorConfig, Configs configType) {
        if(extractorConfig != null) {
            ElementValueExtractor elementValueExtractor = extractorConfig.getElementValueExtractor();
            LOGGER.info("Return {} for config type {}", elementValueExtractor, configType);
            return elementValueExtractor;
        } else {
            LOGGER.error("Can't find ElementValueExtractor for config: {}.", configType);
            throw new BusinessLogicRuntimeException("ElementValueExtractor is required");
        }
    }
}
