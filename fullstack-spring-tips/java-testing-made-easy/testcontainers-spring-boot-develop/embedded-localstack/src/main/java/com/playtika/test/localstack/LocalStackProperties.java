package com.playtika.test.localstack;

import com.playtika.test.common.properties.CommonContainerProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.testcontainers.containers.localstack.LocalStackContainer;

import java.util.Collection;
import java.util.Collections;

@Data
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties("embedded.localstack")
public class LocalStackProperties extends CommonContainerProperties {
    public static final String BEAN_NAME_EMBEDDED_LOCALSTACK = "embeddedLocalstack";
    public Collection<LocalStackContainer.Service> services = Collections.emptyList();
    public int edgePort = 4566;
    public String hostname = "localhost";
    public String hostnameExternal = "localhost";

    // https://hub.docker.com/r/localstack/localstack
    @Override
    public String getDefaultDockerImage() {
        return "localstack/localstack:0.14.0";
    }
}
