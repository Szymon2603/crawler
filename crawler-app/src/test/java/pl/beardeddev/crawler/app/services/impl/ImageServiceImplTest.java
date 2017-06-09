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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.beardeddev.crawler.app.domain.Image;
import pl.beardeddev.crawler.app.repositories.ImageRepository;
import pl.beardeddev.crawler.app.services.ImageService;
import org.springframework.data.domain.ExampleMatcher;
import static org.mockito.Mockito.verify;
import pl.beardeddev.crawler.app.exceptions.BusinessEntityExistsException;

/**
 * Testy jednostkowe dla {@class ImageServiceImpl}.
 * 
 * @author Szymon Grzelak
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ServiceTestConfiguration.class })
public class ImageServiceImplTest {
    
    @Autowired
    private ImageService imageService;
    @Autowired
    private ImageRepository imageRepository;
    private Image image;
    private String imageURL = "http://some-site.com";
    private Example<Image> imageExample;
    
    @Before
    public void setUp() {
        image = new Image();
        image.setId(1L);
        image.setImageURL(imageURL);
        imageExample = Example.of(image, ExampleMatcher.matchingAny());
        reset(imageRepository);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void whenImageExistsThenCallExistsMethod() {
        imageService.imageExists(image);
        verify(imageRepository, times(1)).exists(imageExample);
    }
    
    @Test(expected = BusinessEntityExistsException.class)
    public void whenCreateImageAndExistThenThrowBusinessEntityExistsException() throws BusinessEntityExistsException {
        doReturn(true).when(imageRepository).exists(imageExample);
        imageService.createImage(image);
    }
    
    @Test
    public void whenCreateImageThenCallSaveAndFlush() throws BusinessEntityExistsException {
        doReturn(false).when(imageRepository).exists(imageExample);
        imageService.createImage(image);
        verify(imageRepository, times(1)).saveAndFlush(image);
    }
}
