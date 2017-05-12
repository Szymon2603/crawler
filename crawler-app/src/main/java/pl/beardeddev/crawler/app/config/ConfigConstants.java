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
package pl.beardeddev.crawler.app.config;

/**
 * Klucze i stałe wydzielone z plików konfiguracyjnych w celu uniknięcia używania literałów.
 * 
 * @author Szymon Grzelak
 */
public class ConfigConstants {
    
    // Pakiety komponentów
    public static final String SERVICES_PACKAGE = "pl.beardeddev.crawler.app.services";
    public static final String REPOSITORIES_PACKAGE = "pl.beardeddev.crawler.app.repositories";
    public static final String CONTROLLERS_PACKAGE = "pl.beardeddev.crawler.app.controllers";
    
    // Profile
    public static final String DEVELOPMENT_PROFILE = "development";
    public static final String TEST_PROFILE = "test";
    
    // Pliki z właściwościami
    public static final String APP_DEV_PROPERTIES_FILE = "classpath:app-dev.properties";
    
    // Pliki do inicjalizacji danych
    public static final String DEVELOPMENT_INIT_DATA_FILE = "db/init-data-dev.sql";
    
    // Właściwości na potrzeby konfiguracji dostępu do danych
    public static final String DATA_SOURCE_DATABASE_NAME = "dataSource.databaseName";
    public static final String DATA_SOURCE_PASSWORD = "dataSource.password";
    public static final String DATA_SOURCE_USER = "dataSource.user";
    public static final String DATA_SOURCE_SERVER_NAME = "dataSource.serverName";
    public static final String DATA_SOURCE_URL = "dataSource.url";
    public static final String DATA_SOURCE_PORT = "dataSource.port";
    public static final String DOMAIN_CLASS_PACKAGE = "pl.beardeddev.crawler.app.domain";
    public static final String MYSQL_DIALECT_CLASS = "org.hibernate.dialect.MySQL57Dialect";
    public static final String H2_DIALECT_CLASS = "org.hibernate.dialect.H2Dialect";
    public static final String JPA_VENDOR_ADAPTER_GENERATE_DDL = "jpaVendorAdapter.setGenerateDdl";
    public static final String JPA_VENDOR_ADAPTER_SHOW_SQL = "jpaVendorAdapter.showSql";
}
