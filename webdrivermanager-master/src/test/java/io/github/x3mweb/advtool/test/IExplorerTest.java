/*
 * (C) Copyright 2015 Boni Garcia (http://bonigarcia.github.io/)
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

import static java.lang.invoke.MethodHandles.lookup;
import static org.junit.Assert.assertTrue;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.File;

import io.github.x3mweb.advtool.OperatingSystem;
import io.github.x3mweb.advtool.WebDriverManager;
import org.junit.Test;
import org.slf4j.Logger;

/**

 * @author Roman Torskyi @ Andrzej Dabrowski
 * @since 0.0.1
 */
public class IExplorerTest {

    final Logger log = getLogger(lookup().lookupClass());

    @Test
    public void testIExplorerLatest() {
        WebDriverManager.iedriver().operatingSystem(OperatingSystem.WIN).setup();
        assertIEDriverBinary();
    }

    @Test
    public void testIExplorerVersion() {
        WebDriverManager.iedriver().operatingSystem(OperatingSystem.WIN).version("3.11").setup();
        assertIEDriverBinary();
    }

    private void assertIEDriverBinary() {
        File binary = new File(WebDriverManager.iedriver().getBinaryPath());
        log.debug("Binary path for IEDriverServer {}", binary);
        assertTrue(binary.exists());
    }

}
