/*
 * (C) Copyright 2016 Boni Garcia (http://bonigarcia.github.io/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package io.github.x3mweb.advtool.test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URL;

import io.github.x3mweb.advtool.WebDriverManager;
import io.github.x3mweb.advtool.WebDriverManagerException;
import org.junit.Test;

/**

 * @author Roman Torskyi @ Andrzej Dabrowski
 * @since 0.0.1
 */
public class TaobaoTest {

    @Test
    public void testTaobao() throws Exception {
        WebDriverManager.config().setAvoidAutoVersion(true).setDriverUrl(
                new URL("http://npm.taobao.org/mirrors/chromedriver/2.33/"));
        WebDriverManager.chromedriver().useMirror().setup();

        File binary = new File(WebDriverManager.chromedriver().getBinaryPath());
        assertTrue(binary.exists());
    }

    @Test(expected = WebDriverManagerException.class)
    public void testTaobaoException() {
        WebDriverManager.edgedriver().useMirror().setup();
        File binary = new File(WebDriverManager.edgedriver().getBinaryPath());
        assertTrue(binary.exists());
    }

}
