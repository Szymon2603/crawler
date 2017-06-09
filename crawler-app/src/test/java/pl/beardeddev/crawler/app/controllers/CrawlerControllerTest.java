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
import java.net.MalformedURLException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import pl.beardeddev.crawler.app.config.WebConfig;
import pl.beardeddev.crawler.app.domain.ConfigPackageMaster;
import pl.beardeddev.crawler.app.domain.Image;
import pl.beardeddev.crawler.app.repositories.ConfigPackageMasterRepository;
import pl.beardeddev.crawler.app.repositories.ImageRepository;
import pl.beardeddev.crawler.app.services.CrawlerService;
import pl.beardeddev.crawler.app.utils.CollectionWrapper;
import pl.beardeddev.crawler.core.wrappers.URLWrapper;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 *
 * @author Szymon Grzelak
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { ControllerTestConfiguration.class, WebConfig.class })
@ActiveProfiles(profiles = ControllerTestConfiguration.CONTROLLER_TEST_PROFILE)
public class CrawlerControllerTest {
    
    @Autowired
    private CrawlerService crawlerServiceMock;
    @Autowired
    private ConfigPackageMasterRepository configPackageMasterRepositoryMock;
    @Autowired
    private ImageRepository imageRepositoryMock;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private List<Image> imagesList;
    private CollectionWrapper imagesListWrapped;
    private ConfigPackageMaster config;
    private String startUrl;
    private int maxVisitsDefault;
    
    
    @Before
    public void setUp() throws MalformedURLException {
        imagesList = spy(LongStream
                .rangeClosed(1, 5)
                .mapToObj((id) -> new Image(id, "some-site/" + id, Long.valueOf(id).intValue(), Long.valueOf(id).intValue(), new Date()))
                .collect(Collectors.toList()));
        imagesListWrapped = new CollectionWrapper(imagesList);
        config = mock(ConfigPackageMaster.class);
        startUrl = "http://some-site.com";
        maxVisitsDefault = 10;
    }
    
    @Test
    public void whenGetImagesThenGetListOfImages() throws Exception {
        when(crawlerServiceMock.runCrawler(eq(config), any(URLWrapper.class), eq(maxVisitsDefault))).thenReturn(imagesList);
        when(configPackageMasterRepositoryMock.findOne(any(Long.class))).thenReturn(config);
        String responseValue = mockMvc
                .perform(get("/runCrawler")
                        .param("startUrl", startUrl)
                        .param("maxVisits", "10")
                        .param("configId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(objectMapper.writeValueAsString(imagesListWrapped), responseValue);
    }
    
    @Test
    public void whenNoMaxVisitsAndGetImagesThenGetListOfImages() throws Exception {
        when(crawlerServiceMock.runCrawler(eq(config), any(URLWrapper.class), eq(maxVisitsDefault))).thenReturn(imagesList);
        when(configPackageMasterRepositoryMock.findOne(any(Long.class))).thenReturn(config);
        String responseValue = mockMvc
                .perform(get("/runCrawler")
                        .param("startUrl", startUrl)
                        .param("configId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(objectMapper.writeValueAsString(imagesListWrapped), responseValue);
    }
    
    @Test
    public void whenGetImageThenReturnListOfImagesAndSaveThem() throws Exception {
        when(crawlerServiceMock.runCrawler(eq(config), any(URLWrapper.class), eq(maxVisitsDefault))).thenReturn(imagesList);
        when(configPackageMasterRepositoryMock.findOne(any(Long.class))).thenReturn(config);
        String responseValue = mockMvc
                .perform(get("/runAndSaveCrawler")
                        .param("startUrl", startUrl)
                        .param("maxVisits", "10")
                        .param("configId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(objectMapper.writeValueAsString(imagesListWrapped), responseValue);
        reset(imagesList);
        verify(imageRepositoryMock, times(1)).save(imagesList);
    }
    
    @Test
    public void whenNoMaxVisitsAndGetImageThenReturnListOfImagesAndSaveThem() throws Exception {
        when(crawlerServiceMock.runCrawler(eq(config), any(URLWrapper.class), eq(maxVisitsDefault))).thenReturn(imagesList);
        when(configPackageMasterRepositoryMock.findOne(any(Long.class))).thenReturn(config);
        String responseValue = mockMvc
                .perform(get("/runAndSaveCrawler")
                        .param("startUrl", startUrl)
                        .param("configId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(objectMapper.writeValueAsString(imagesListWrapped), responseValue);
        reset(imagesList);
        verify(imageRepositoryMock, times(1)).save(imagesList);
    }
    
}
