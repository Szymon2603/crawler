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
/**
 * Author:  Szymon Grzelak
 * Created: 2017-05-11
 */

/* Konfiguracja wybierania atrybutów */
INSERT INTO ATTRIBUTE_VALUE_EXTRACTOR_CONFIG(ID, TYPE, ELEMENT_SELECTOR, ATTRIBUTE_NAME) VALUES (1, 'ATTRIBUTE_VALUE_EXTRACTOR', '.badge-item-img', 'abs:src');
INSERT INTO ATTRIBUTE_VALUE_EXTRACTOR_CONFIG(ID, TYPE, ELEMENT_SELECTOR, ATTRIBUTE_NAME) VALUES (2, 'ATTRIBUTE_VALUE_EXTRACTOR', 'div.badge-toolbar-pre.fixed-wrap-post-bar > div.badge-entry-toolbar-sticky.post-afterbar-a.in-post-top > div.post-nav > a.badge-fast-entry.badge-next-post-entry.next', 'abs:href');
INSERT INTO TEXT_VALUE_EXTRACTOR_CONFIG(ID, TYPE, ELEMENT_SELECTOR) VALUES (3, 'TEXT_VALUE_EXTRACTOR', 'header > p > a.comment.badge-evt > span');
INSERT INTO TEXT_VALUE_EXTRACTOR_CONFIG(ID, TYPE, ELEMENT_SELECTOR) VALUES (4, 'TEXT_VALUE_EXTRACTOR', 'span > span.badge-item-love-count');

/* Lokalizacje dla formatów liczb */
INSERT INTO NUMBER_FORMAT_LOCALE(ID, LANGUAGE_TAG, DESCRIPTION) VALUES (1, 'en-EN', 'Notacja używana w krajach anglojęzycznych takich jak UK.');

/* Pakiet konfiguracji */
INSERT INTO CONFIG_PACKAGE_MASTER(ID, LOCALE, NAME) VALUES (1, 1, '9GAG');
INSERT INTO CONFIG_PACKAGE_DETAIL(ID, DETAIL_NAME, CONFIG, PACKAGE_MASTER) VALUES(1, 'IMAGE_EXTRACTOR', 1, 1);
INSERT INTO CONFIG_PACKAGE_DETAIL(ID, DETAIL_NAME, CONFIG, PACKAGE_MASTER) VALUES(2, 'NEXT_ELEMENT_EXTRACTOR', 2, 1);
INSERT INTO CONFIG_PACKAGE_DETAIL(ID, DETAIL_NAME, CONFIG, PACKAGE_MASTER) VALUES(3, 'COMMENTS_EXTRACTOR', 3, 1);
INSERT INTO CONFIG_PACKAGE_DETAIL(ID, DETAIL_NAME, CONFIG, PACKAGE_MASTER) VALUES(4, 'RATING_EXTRACTOR', 4, 1);
