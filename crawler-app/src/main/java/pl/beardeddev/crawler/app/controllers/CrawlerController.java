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

import java.net.MalformedURLException;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.beardeddev.crawler.app.domain.ConfigPackageMaster;
import pl.beardeddev.crawler.app.domain.Image;
import pl.beardeddev.crawler.app.dto.ImageDto;
import pl.beardeddev.crawler.app.repositories.ConfigPackageMasterRepository;
import pl.beardeddev.crawler.app.repositories.ImageRepository;
import pl.beardeddev.crawler.app.services.CrawlerService;
import pl.beardeddev.crawler.core.wrappers.URLWrapper;

/**
 *
 * @author Szymon Grzelak
 */
@RestController
public class CrawlerController {
    
    @Autowired
    private CrawlerService crawlerService;
    @Autowired
    private ConfigPackageMasterRepository configPackageMasterRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private ModelMapper modelMapper;
    
    @GetMapping("/runCrawler")
    public List<ImageDto> runCrawler(@RequestParam String startUrl, @RequestParam(defaultValue = "10") int maxVisits,
                                     @RequestParam Long configId) throws MalformedURLException {
        ConfigPackageMaster configPackage = configPackageMasterRepository.findOne(configId);
        URLWrapper startUrlWrapper = new URLWrapper(startUrl);
        List<Image> images = crawlerService.runCrawler(configPackage, startUrlWrapper, maxVisits);
        return images.stream().map(this::imageToDto).collect(Collectors.toList());
    }
    
    @GetMapping("/runAndSaveCrawler")
    public List<ImageDto> runAndSaveCrawler(@RequestParam String startUrl, @RequestParam(defaultValue = "10") int maxVisits,
                                            @RequestParam Long configId) throws MalformedURLException {
        ConfigPackageMaster configPackage = configPackageMasterRepository.findOne(configId);
        URLWrapper startUrlWrapper = new URLWrapper(startUrl);
        List<Image> images = crawlerService.runCrawler(configPackage, startUrlWrapper, maxVisits);
        imageRepository.save(images);
        return images.stream().map(this::imageToDto).collect(Collectors.toList());
    }
    
    private ImageDto imageToDto(Image image) {
        return image != null ? modelMapper.map(image, ImageDto.class) : null;
    }
}