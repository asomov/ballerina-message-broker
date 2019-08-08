/*
 * Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */
package io.ballerina.messaging.broker.client.utils;

import io.ballerina.messaging.broker.client.resources.Configuration;
import org.snakeyaml.beans.v1.api.BeanDump;
import org.snakeyaml.beans.v1.api.BeanLoad;
import org.snakeyaml.beans.v1.api.ClassDefinition;
import org.snakeyaml.engine.v2.api.DumpSettings;
import org.snakeyaml.engine.v2.api.LoadSettings;
import org.snakeyaml.engine.v2.api.YamlOutputStreamWriter;
import org.snakeyaml.engine.v2.common.FlowStyle;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static io.ballerina.messaging.broker.client.utils.Constants.SYSTEM_PARAM_CLI_CLIENT_CONFIG_FILE;

/**
 * Container class for common static methods of Broker CLI Client.
 */
public class Utils {

    /**
     * Create {@link BrokerClientException} instance including the error message.
     *
     * @param errorMsg    error message.
     * @param rootCommand root command used in the script.
     * @return new {@link BrokerClientException} instance with error message.
     */
    public static BrokerClientException createUsageException(String errorMsg, String rootCommand) {
        BrokerClientException brokerClientException = new BrokerClientException();
        brokerClientException.addMessage(rootCommand + ": " + errorMsg);
        brokerClientException.addMessage("Run '" + rootCommand + " --help' for usage.");
        return brokerClientException;
    }

    private static Configuration readConfigurationFile() {
        BeanLoad<Configuration> beanLoad = new BeanLoad<>(LoadSettings.builder().build(),
                new ClassDefinition(Configuration.class));

        try (InputStream in = new FileInputStream(getConfigFilePath())) {
            Configuration configuration = beanLoad.loadBeanFromInputStream(in);
            // validate the configuration
            if (!Configuration.validateConfiguration(configuration)) {
                BrokerClientException exception = new BrokerClientException();
                exception.addMessage("Error in the CLI client configuration");
                exception.addMessage("Please re-initialize using 'init' command");
                throw exception;
            }
            return configuration;
        } catch (IOException e) {
            BrokerClientException brokerClientException = new BrokerClientException();
            brokerClientException.addMessage("error when reading the configuration file. " + e.getMessage());
            throw brokerClientException;
        }
    }

    /**
     * Read CLI Client configuration file and binds its information into a {@link Configuration} instance.
     * If the password needs to be overridden it should be passed into the method, otherwise pass null.
     *
     * @param password updated password that should be set into the Configuration instance. Pass null to use existing.
     * @return generated {@link Configuration} instance.
     */
    public static Configuration getConfiguration(String password) {
        Configuration configuration = readConfigurationFile();
        // order-ride the password
        if (Objects.nonNull(password)) {
            configuration.setPassword(password);
        }

        if (Objects.isNull(configuration.getPassword())) {
            BrokerClientException exception = new BrokerClientException();
            exception.addMessage("User password is not provided.");
            exception.addMessage(
                    "Setup the password at global level using 'init' command or provide password when executing each "
                            + "command using (--password|-p) flag");
            throw exception;
        }
        return configuration;
    }

    /**
     * Create the CLI configuration information file.
     *
     * @param configuration instance containing the Configuration information.
     */
    public static void createConfigurationFile(Configuration configuration) {
        DumpSettings settings = DumpSettings.builder()
                .setMultiLineFlow(true)
                .setDefaultFlowStyle(FlowStyle.BLOCK)
                .build();
        BeanDump dumper = new BeanDump(settings);

        // dump to the file
        try (StreamToFileWriter writer = new StreamToFileWriter(new FileOutputStream(getConfigFilePath()),
                StandardCharsets.UTF_8)) {
            dumper.dump(configuration, writer);
        } catch (IOException e) {
            BrokerClientException brokerClientException = new BrokerClientException();
            brokerClientException.addMessage("error when creating the configuration file. " + e.getMessage());
            throw brokerClientException;
        }
    }

    private static String getConfigFilePath() {
        String path = System.getProperty(SYSTEM_PARAM_CLI_CLIENT_CONFIG_FILE);
        if (path != null && !path.trim().isEmpty()) {
            return path;
        }
        return Constants.DEFAULT_CONFIG_FILE_PATH;
    }
}

/**
 * Implement Writer which does not throw IOExceptions.
 */
class StreamToFileWriter extends YamlOutputStreamWriter {
    public StreamToFileWriter(OutputStream out, Charset cs) {
        super(out, cs);
    }
    @Override
    public void processIOException(IOException e) {
        BrokerClientException brokerClientException = new BrokerClientException();
        brokerClientException.addMessage("error when creating the configuration file. " + e.getMessage());
        throw brokerClientException;
    }
}
