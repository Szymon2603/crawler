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
package pl.beardeddev.crawler.app.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import pl.beardeddev.crawler.app.domain.Image;
import pl.beardeddev.crawler.app.exceptions.BusinessEntityExistsException;
import pl.beardeddev.crawler.app.repositories.ImageRepository;
import pl.beardeddev.crawler.app.services.ImageService;

/**
 * Domyślna implementacja interfejsu {@class ImageService}.
 * 
 * @author Szymon Grzelak
 */
@Service
public class ImageServiceImpl implements ImageService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageServiceImpl.class);
    
    @Autowired
    private ImageRepository imageRepository;
    
    @Override
    public Image createImage(Image image) throws BusinessEntityExistsException {
        if(imageExists(image)) {
            LOGGER.error("Entity exist [{}]", image);
            throw new BusinessEntityExistsException("Entity exist " + image.toString());
        }
        return imageRepository.saveAndFlush(image);
    }
    
    @Override
    public boolean imageExists(Image image) {
        Image probe = new Image();
        probe.setId(image.getId());
        probe.setImageURL(image.getImageURL());
        Example<Image> imageExample = Example.of(probe, ExampleMatcher.matchingAny());
        return imageRepository.exists(imageExample);
    }
}
