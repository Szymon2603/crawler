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
package pl.beardeddev.crawler.app.controllers;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.beardeddev.crawler.app.domain.Image;
import pl.beardeddev.crawler.app.dto.ImageDto;
import pl.beardeddev.crawler.app.repositories.ImageRepository;
import pl.beardeddev.crawler.app.utils.CollectionWrapper;

/**
 *
 * @author Szymon Grzelak
 */
@RestController
public class ImageController {

    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/images")
    public CollectionWrapper<ImageDto> getImages() {
        List<Image> images = imageRepository.findAll();
        List<ImageDto> imagesDto = images.stream().map(this::imageToDto).collect(Collectors.toList());
        return CollectionWrapper.of(imagesDto);
    }
    
    @GetMapping("/image/{id}")
    public ImageDto getImage(@PathVariable Long id) {
        Image image = imageRepository.findOne(id);
        return imageToDto(image);
    }
    
    private ImageDto imageToDto(Image image) {
        return image != null ? modelMapper.map(image, ImageDto.class) : null;
    }
}
