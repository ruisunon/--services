package org.testcontainers;

import org.testcontainers.utility.DockerImageName;

public interface ClickhouseTestImages {
    DockerImageName YANDEX_CLICKHOUSE_IMAGE = DockerImageName.parse("yandex/clickhouse-server:18.10.3");
    DockerImageName CLICKHOUSE_IMAGE = DockerImageName.parse("clickhouse/clickhouse-server:21.9.2-alpine");
}
