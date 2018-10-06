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

import static io.github.x3mweb.advtool.Config.isNullOrEmpty;
import static java.util.Collections.sort;
import static java.util.Optional.empty;
import static org.apache.commons.io.FileUtils.listFiles;
import static org.apache.commons.lang3.SystemUtils.IS_OS_WINDOWS;
import static org.jsoup.Jsoup.parse;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**

 * @author Roman Torskyi @ Andrzej Dabrowski
 * @since 0.0.1
 */
public class EdgeDriverManager extends WebDriverManager {

    protected EdgeDriverManager() {
        driverManagerType = DriverManagerType.EDGE;
        exportParameterKey = "advtool.edgeDriverExport";
        driverVersionKey = "advtool.edgeDriverVersion";
        driverUrlKey = "advtool.edgeDriverUrl";
        driverName = "MicrosoftWebDriver";
    }

    @Override
    protected List<URL> getDrivers() throws IOException {
        listVersions = new ArrayList<>();
        List<URL> urlList = new ArrayList<>();

        URL driverUrl = config().getDriverUrl(driverUrlKey);
        log.debug("Reading {} to find out the latest version of Edge driver",
                driverUrl);

        try (InputStream in = httpClient
                .execute(httpClient.createHttpGet(driverUrl)).getEntity()
                .getContent()) {
            Document doc = parse(in, null, "");

            Elements downloadLink = doc
                    .select("ul.driver-downloads li.driver-download > a");
            Elements versionParagraph = doc.select(
                    "ul.driver-downloads li.driver-download p.driver-download__meta");

            // Due to recent changes in Edge driver page, the first three
            // paragraphs note related with the version of the binaries
            versionParagraph.remove(0);
            versionParagraph.remove(0);
            versionParagraph.remove(0);

            for (int i = 0; i < downloadLink.size(); i++) {
                String[] version = versionParagraph.get(i).text().split(" ");
                String v = version[1];
                if (!v.equalsIgnoreCase("version")) {
                    listVersions.add(v);
                    urlList.add(new URL(downloadLink.get(i).attr("href")));
                }
            }

            return urlList;
        }
    }

    @Override
    public List<String> getVersions() {
        httpClient = new HttpClient(config().getTimeout());
        try {
            getDrivers();
            sort(listVersions, new VersionComparator());
            return listVersions;
        } catch (IOException e) {
            throw new WebDriverManagerException(e);
        }
    }

    @Override
    protected List<URL> getLatest(List<URL> list, String driver) {
        log.trace("Checking the lastest version of {} with URL list {}", driver,
                list);
        List<URL> out = new ArrayList<>();
        versionToDownload = listVersions.iterator().next();
        out.add(list.iterator().next());
        log.info("Latest version of MicrosoftWebDriver is {}",
                versionToDownload);
        return out;
    }

    @Override
    protected File postDownload(File archive) {
        Collection<File> listFiles = listFiles(new File(archive.getParent()),
                new String[] { "exe" }, true);
        return listFiles.iterator().next();
    }

    @Override
    protected Optional<String> getBrowserVersion() {
        if (IS_OS_WINDOWS) {
            String browserVersionOutput = Shell.runAndWait("powershell",
                    "get-appxpackage Microsoft.MicrosoftEdge");
            if (!isNullOrEmpty(browserVersionOutput)) {
                return Optional.of(
                        Shell.getVersionFromPowerShellOutput(browserVersionOutput));
            }
        }
        return empty();
    }

}
