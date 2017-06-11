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
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.Assert;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import pl.beardeddev.crawler.app.config.WebConfig;
import pl.beardeddev.crawler.app.domain.Image;
import pl.beardeddev.crawler.app.exceptions.BusinessEntityExistsException;
import pl.beardeddev.crawler.app.repositories.ImageRepository;
import pl.beardeddev.crawler.app.services.ImageService;
import pl.beardeddev.crawler.app.utils.CollectionWrapper;

/**
 *
 * @author Szymon Grzelak
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { ControllerTestConfiguration.class, WebConfig.class })
@ActiveProfiles(profiles = ControllerTestConfiguration.CONTROLLER_TEST_PROFILE)
public class ImageControllerTest {
    
    @Autowired
    private ImageRepository imageRepositoryMock;
    @Autowired
    private ImageService imageService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    
    private Image image1;
    private Image image2;
    
    @Before
    public void setUp() {
        image1 = new Image(1L, "http://some-site.com/1", 10, 20, new Date());
        image2 = new Image(2L, "http://some-site.com/2", 30, 400, new Date());
    }

    @Test
    public void whenGetImagesThenGetListOfImages() throws Exception {
        final List<Image> imagesList = Arrays.asList(image1, image2);
        when(imageRepositoryMock.findAll()).thenReturn(imagesList);
        String responseValue = mockMvc
                .perform(get("/images"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn().getResponse().getContentAsString();
        CollectionWrapper imagesWrapped = new CollectionWrapper(imagesList);
        Assert.assertEquals(responseValue, objectMapper.writeValueAsString(imagesWrapped));
    }
    
    @Test
    public void whenGetImageThenReturnOneImage() throws Exception {
        when(imageRepositoryMock.findOne(1L)).thenReturn(image1);
        String responseValue = mockMvc
                .perform(get("/image/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals(responseValue, objectMapper.writeValueAsString(image1));
    }
    
    @Test
    public void givenBadIdWhenGetImageThenReturnNull() throws Exception {
        when(imageRepositoryMock.findOne(1L)).thenReturn(null);
        String responseValue = mockMvc
                .perform(get("/image/1"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals("", responseValue);
    }
    
    @Test
    public void whenCreateImageThenCallCreateImage() throws Exception {
        MockHttpServletRequestBuilder reqBuilder = post("/image")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(image1));
        mockMvc.perform(reqBuilder).andExpect(status().isCreated());
        verify(imageService, times(1)).createImage(image1);
    }
    
    @Test
    public void givenExistImageWhenCreateImageThenResponseStatusIsConflict() throws Exception {
        doThrow(BusinessEntityExistsException.class).when(imageService).createImage(image1);
        MockHttpServletRequestBuilder reqBuilder = post("/image")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(image1));
        mockMvc.perform(reqBuilder).andExpect(status().isConflict());
    }
}
