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

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.beardeddev.crawler.app.repositories.ConfigPackageMasterRepository;
import pl.beardeddev.crawler.app.repositories.ImageRepository;
import pl.beardeddev.crawler.app.services.CrawlerService;

/**
 * Klasa konfiguracyjna na potrzby testowania kontrolerów aplikacji. Zawiera atrapy repozytoriów oraz innych potrzebnych
 * ziaren oraz konfiguracji.
 * 
 * @author Szymon Grzelak
 */
@Configuration
@Profile(value = ControllerTestConfiguration.CONTROLLER_TEST_PROFILE)
class ControllerTestConfiguration {
    
    public static final String CONTROLLER_TEST_PROFILE = "controller-test-profile";
    
    @Autowired
    private ApplicationContext ctx;
    
    @Bean
    public ImageRepository imageRepository() {
        return Mockito.mock(ImageRepository.class);
    }
    
    @Bean
    public CrawlerService crawlerService() {
        return Mockito.mock(CrawlerService.class);
    }
    
    @Bean
    public ConfigPackageMasterRepository configPackageMasterRepository() {
        return Mockito.mock(ConfigPackageMasterRepository.class);
    }
    
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
    
    @Bean
    public MockMvc mockMvc() {
        return MockMvcBuilders.webAppContextSetup((WebApplicationContext) ctx).build();
    }
    
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
