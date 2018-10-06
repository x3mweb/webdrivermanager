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
package io.github.x3mweb.advtool;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

/**

 * @author Roman Torskyi @ Andrzej Dąbrowski
 * @since 0.0.1
 */
public class ChromeDriverManager extends WebDriverManager {

    protected ChromeDriverManager() {
        driverManagerType = DriverManagerType.CHROME;
        exportParameterKey = "advtool.chromeDriverExport";
        driverVersionKey = "advtool.chromeDriverVersion";
        driverUrlKey = "advtool.chromeDriverUrl";
        driverMirrorUrlKey = "advtool.chromeDriverMirrorUrl";
        driverName = "chromedriver";
    }

    @Override
    protected List<URL> getDrivers() throws IOException {
        URL driverUrl = config().getDriverUrl(driverUrlKey);
        List<URL> urls;
        if (isUsingTaobaoMirror()) {
            urls = getDriversFromMirror(driverUrl);
        } else {
            urls = getDriversFromXml(driverUrl);
        }
        return urls;
    }

    @Override
    protected String getCurrentVersion(URL url, String driverName) {
        if (isUsingTaobaoMirror()) {
            int i = url.getFile().lastIndexOf(SLASH);
            int j = url.getFile().substring(0, i).lastIndexOf(SLASH) + 1;
            return url.getFile().substring(j, i);
        } else {
            return super.getCurrentVersion(url, driverName);
        }
    }

    @Override
    protected Optional<String> getBrowserVersion() {
        return getDefaultBrowserVersion("PROGRAMFILES(X86)",
                "\\\\Google\\\\Chrome\\\\Application\\\\chrome.exe",
                "google-chrome",
                "/Applications/Google Chrome.app/Contents/MacOS/Google Chrome",
                "--version", driverManagerType.toString());
    }

}
