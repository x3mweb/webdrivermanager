/*
 * (C) Copyright 2018 Boni Garcia (http://bonigarcia.github.io/)
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

import static java.lang.String.join;
import static java.lang.invoke.MethodHandles.lookup;
import static org.apache.commons.lang3.SystemUtils.IS_OS_LINUX;
import static org.apache.commons.lang3.SystemUtils.IS_OS_MAC;
import static org.apache.commons.lang3.SystemUtils.IS_OS_WINDOWS;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Properties;

import org.slf4j.Logger;

/**

 * @author Roman Torskyi @ Andrzej Dabrowski
 * @since 0.0.1
 */
public class Config {

    final Logger log = getLogger(lookup().lookupClass());

    static final String HOME = "~";

    ConfigKey<String> properties = new ConfigKey<>("advtool.properties",
            String.class, "webdrivermanager.properties");

    ConfigKey<String> targetPath = new ConfigKey<>("advtool.targetPath",
            String.class);
    ConfigKey<Boolean> forceCache = new ConfigKey<>("advtool.forceCache",
            Boolean.class);
    ConfigKey<Boolean> override = new ConfigKey<>("advtool.override",
            Boolean.class);
    ConfigKey<Boolean> useMirror = new ConfigKey<>("advtool.useMirror",
            Boolean.class);
    ConfigKey<Boolean> useBetaVersions = new ConfigKey<>("advtool.useBetaVersions",
            Boolean.class);
    ConfigKey<Boolean> avoidExport = new ConfigKey<>("advtool.avoidExport",
            Boolean.class);
    ConfigKey<Boolean> avoidOutputTree = new ConfigKey<>("advtool.avoidOutputTree",
            Boolean.class);
    ConfigKey<Boolean> avoidAutoVersion = new ConfigKey<>(
            "advtool.avoidAutoVersion", Boolean.class);
    ConfigKey<Integer> timeout = new ConfigKey<>("advtool.timeout", Integer.class);

    ConfigKey<String> architecture = new ConfigKey<>("advtool.architecture",
            String.class, defaultArchitecture());
    ConfigKey<String> os = new ConfigKey<>("advtool.os", String.class,
            defaultOsName());
    ConfigKey<String> proxy = new ConfigKey<>("advtool.proxy", String.class);
    ConfigKey<String> proxyUser = new ConfigKey<>("advtool.proxyUser",
            String.class);
    ConfigKey<String> proxyPass = new ConfigKey<>("advtool.proxyPass",
            String.class);
    ConfigKey<String> ignoreVersions = new ConfigKey<>("advtool.ignoreVersions",
            String.class);
    ConfigKey<String> gitHubTokenName = new ConfigKey<>("advtool.gitHubTokenName",
            String.class);
    ConfigKey<String> gitHubTokenSecret = new ConfigKey<>(
            "advtool.gitHubTokenSecret", String.class);

    ConfigKey<String> driverVersion = new ConfigKey<>(String.class);
    ConfigKey<URL> driverUrl = new ConfigKey<>(URL.class);
    ConfigKey<URL> driverMirrorUrl = new ConfigKey<>(URL.class);
    ConfigKey<String> driverExport = new ConfigKey<>(String.class);

    ConfigKey<Integer> serverPort = new ConfigKey<>("advtool.serverPort",
            Integer.class);

    private <T> T resolve(ConfigKey<T> configKey) {
        String name = configKey.getName();
        T tValue = configKey.getValue();
        Class<T> type = configKey.getType();

        return resolver(name, tValue, type);
    }

    private <T> T resolver(String name, T tValue, Class<T> type) {
        String strValue;
        strValue = System.getenv(name.toUpperCase().replace(".", "_"));
        if (strValue == null) {
            strValue = System.getProperty(name);
        }
        if (strValue == null && tValue != null) {
            return tValue;
        }
        if (strValue == null) {
            strValue = getProperty(name);
        }
        return parse(type, strValue);
    }

    @SuppressWarnings("unchecked")
    private <T> T parse(Class<T> type, String strValue) {
        T output = null;
        if (type.equals(String.class)) {
            output = (T) strValue;
        } else if (type.equals(Integer.class)) {
            output = (T) Integer.valueOf(strValue);
        } else if (type.equals(Boolean.class)) {
            output = (T) Boolean.valueOf(strValue);
        } else if (type.equals(URL.class)) {
            try {
                output = (T) new URL(strValue);
            } catch (Exception e) {
                throw new WebDriverManagerException(e);
            }
        } else {
            throw new WebDriverManagerException(
                    "Type " + type.getTypeName() + " cannot be parsed");
        }
        return output;
    }

    private String getProperty(String key) {
        String value = null;
        Properties props = new Properties();
        try {
            InputStream inputStream = Config.class
                    .getResourceAsStream("/" + getProperties());
            props.load(inputStream);
            value = props.getProperty(key);
        } catch (Exception e) {
            throw new WebDriverManagerException(e);
        } finally {
            if (value == null) {
                log.trace("Property key {} not found, using default value",
                        key);
                value = "";
            }
        }
        return value;
    }

    public void reset() {
        for (Field field : this.getClass().getDeclaredFields()) {
            if (field.getType() == ConfigKey.class) {
                try {
                    ((ConfigKey<?>) field.get(this)).reset();
                } catch (Exception e) {
                    log.warn("Exception resetting {}", field);
                }
            }
        }
    }

    private String defaultOsName() {
        String osName = System.getProperty("os.name").toLowerCase();
        if (IS_OS_WINDOWS) {
            osName = OperatingSystem.WIN.name();
        } else if (IS_OS_LINUX) {
            osName = OperatingSystem.LINUX.name();
        } else if (IS_OS_MAC) {
            osName = OperatingSystem.MAC.name();
        }
        return osName;
    }

    private String defaultArchitecture() {
        return "X" + System.getProperty("sun.arch.data.model");
    }

    public static boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }

    public boolean isExecutable(File file) {
        return resolve(os).equalsIgnoreCase("win")
                ? file.getName().toLowerCase().endsWith(".exe")
                : file.canExecute();
    }

    // Getters and setters

    public String getProperties() {
        return resolve(properties);
    }

    public Config setProperties(String properties) {
        this.properties.setValue(properties);
        return this;
    }

    public String getTargetPath() {
        String resolved = resolve(targetPath);
        String path = null;

        if (resolved != null) {
            path = resolved;
            if (path.contains(HOME)) {
                path = path.replace(HOME, System.getProperty("user.home"));
            }
            if (path.equals(".")) {
                path = Paths.get("").toAbsolutePath().toString();
            }
        }
        return path;
    }

    public Config setTargetPath(String value) {
        this.targetPath.setValue(value);
        return this;
    }

    public boolean isForceCache() {
        return resolve(forceCache);
    }

    public Config setForceCache(boolean value) {
        this.forceCache.setValue(value);
        return this;
    }

    public boolean isOverride() {
        return resolve(override);
    }

    public Config setOverride(boolean value) {
        this.override.setValue(value);
        return this;
    }

    public boolean isUseMirror() {
        return resolve(useMirror);
    }

    public Config setUseMirror(boolean value) {
        this.useMirror.setValue(value);
        return this;
    }

    public boolean isUseBetaVersions() {
        return resolve(useBetaVersions);
    }

    public Config setUseBetaVersions(boolean value) {
        this.useBetaVersions.setValue(value);
        return this;
    }

    public boolean isAvoidExport() {
        return resolve(avoidExport);
    }

    public Config setAvoidExport(boolean value) {
        this.avoidExport.setValue(value);
        return this;
    }

    public boolean isAvoidOutputTree() {
        return resolve(avoidOutputTree);
    }

    public Config setAvoidOutputTree(boolean value) {
        this.avoidOutputTree.setValue(value);
        return this;
    }

    public boolean isAvoidAutoVersion() {
        return resolve(avoidAutoVersion);
    }

    public Config setAvoidAutoVersion(boolean value) {
        this.avoidAutoVersion.setValue(value);
        return this;
    }

    public int getTimeout() {
        return resolve(timeout);
    }

    public Config setTimeout(int value) {
        this.timeout.setValue(value);
        return this;
    }

    public Architecture getArchitecture() {
        return Architecture.valueOf(resolve(architecture));
    }

    public Config setArchitecture(Architecture value) {
        this.architecture.setValue(value.name());
        return this;
    }

    public String getOs() {
        return resolve(os);
    }

    public Config setOs(String value) {
        this.os.setValue(value);
        return this;
    }

    public String getProxy() {
        return resolve(proxy);
    }

    public Config setProxy(String value) {
        this.proxy.setValue(value);
        return this;
    }

    public String getProxyUser() {
        return resolve(proxyUser);
    }

    public Config setProxyUser(String value) {
        this.proxyUser.setValue(value);
        return this;
    }

    public String getProxyPass() {
        return resolve(proxyPass);
    }

    public Config setProxyPass(String value) {
        this.proxyPass.setValue(value);
        return this;
    }

    public String[] getIgnoreVersions() {
        String ignored = resolve(ignoreVersions);
        String[] out = {};
        if (!isNullOrEmpty(ignored)) {
            out = ignored.split(",");
        }
        return out;
    }

    public Config setIgnoreVersions(String... value) {
        this.ignoreVersions.setValue(join(",", value));
        return this;
    }

    public String getGitHubTokenName() {
        return resolve(gitHubTokenName);
    }

    public Config setGitHubTokenName(String value) {
        this.gitHubTokenName.setValue(value);
        return this;
    }

    public String getGitHubTokenSecret() {
        return resolve(gitHubTokenSecret);
    }

    public Config setGitHubTokenSecret(String value) {
        this.gitHubTokenSecret.setValue(value);
        return this;
    }

    public String getDriverVersion(String name) {
        return resolver(name, driverVersion.getValue(),
                driverVersion.getType());
    }

    public Config setDriverVersion(String value) {
        this.driverVersion.setValue(value);
        return this;
    }

    public URL getDriverUrl(String name) {
        return resolver(name, driverUrl.getValue(), driverUrl.getType());
    }

    public Config setDriverUrl(URL value) {
        this.driverUrl.setValue(value);
        return this;
    }

    public URL getDriverMirrorUrl(String name) {
        return resolver(name, driverMirrorUrl.getValue(),
                driverMirrorUrl.getType());
    }

    public Config setDriverMirrorUrl(URL value) {
        this.driverMirrorUrl.setValue(value);
        return this;
    }

    public String getDriverExport(String name) {
        return resolver(name, driverExport.getValue(), driverExport.getType());
    }

    public Config setDriverExport(String value) {
        this.driverExport.setValue(value);
        return this;
    }

    public Boolean getUseMirror(String driverMirrorUrlKey) {
        if (isNullOrEmpty(driverMirrorUrlKey)) {
            throw new WebDriverManagerException("Mirror URL not available");
        }
        return resolve(useMirror);
    }

    public int getServerPort() {
        return resolve(serverPort);
    }

    public Config setServerPort(int value) {
        this.serverPort.setValue(value);
        return this;
    }
}
