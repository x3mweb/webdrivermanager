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
package io.github.x3mweb.advtool;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;


/**

 * @author Roman Torskyi @ Andrzej Dabrowski
 * @since 0.0.1
 */
public class FirefoxDriverManager extends WebDriverManager {

    protected FirefoxDriverManager() {
        driverManagerType = DriverManagerType.FIREFOX;
        exportParameterKey = "advtool.geckoDriverExport";
        driverVersionKey = "advtool.geckoDriverVersion";
        driverUrlKey = "advtool.geckoDriverUrl";
        driverMirrorUrlKey = "advtool.geckoDriverMirrorUrl";
        driverName = "geckodriver";
    }

    @Override
    protected List<URL> getDrivers() throws IOException {
        return getDriversFromGitHub();
    }

    @Override
    protected String getCurrentVersion(URL url, String driverName) {
        String currentVersion = url.getFile().substring(
                url.getFile().indexOf('-') + 1, url.getFile().lastIndexOf('-'));
        if (currentVersion.startsWith("v")) {
            currentVersion = currentVersion.substring(1);
        }
        return currentVersion;
    }

    @Override
    protected String preDownload(String target, String version) {
        int iSeparator = target.indexOf(version) - 1;
        int iDash = target.lastIndexOf(version) + version.length();
        int iPoint = target.lastIndexOf(".zip");
        int iPointTazGz = target.lastIndexOf(".tar.gz");
        int iPointGz = target.lastIndexOf(".gz");

        if (iPointTazGz != -1) {
            iPoint = iPointTazGz;
        } else if (iPointGz != -1) {
            iPoint = iPointGz;
        }

        target = target.substring(0, iSeparator + 1)
                + target.substring(iDash + 1, iPoint).toLowerCase()
                + target.substring(iSeparator);
        return target;
    }

    @Override
    protected Optional<String> getBrowserVersion() {
        return getDefaultBrowserVersion("PROGRAMFILES(X86)",
                "\\\\Mozilla Firefox\\\\firefox.exe", "firefox",
                "/Applications/Firefox.app/Contents/MacOS/firefox", "-v",
                driverManagerType.toString());
    }

}
