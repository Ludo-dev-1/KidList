package com.christmaswishlist.infrastructure.configuration;

import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

record NeonDatabaseUrl(String jdbcUrl, String username, String password) {
    static NeonDatabaseUrl parse(String databaseUrl) {
        String normalizedUrl = databaseUrl.trim();
        if (normalizedUrl.length() >= 2
                && ((normalizedUrl.startsWith("\"") && normalizedUrl.endsWith("\""))
                || (normalizedUrl.startsWith("'") && normalizedUrl.endsWith("'")))) {
            normalizedUrl = normalizedUrl.substring(1, normalizedUrl.length() - 1);
        }
        URI uri = URI.create(normalizedUrl);
        String scheme = uri.getScheme();
        if (!"postgresql".equalsIgnoreCase(scheme) && !"postgres".equalsIgnoreCase(scheme)) {
            throw new IllegalArgumentException("DATABASE_URL must use the postgres or postgresql scheme.");
        }
        if (uri.getHost() == null || uri.getRawPath() == null || uri.getRawPath().length() < 2) {
            throw new IllegalArgumentException("DATABASE_URL must include a host and database name.");
        }

        String rawUserInfo = uri.getRawUserInfo();
        int separator = rawUserInfo == null ? -1 : rawUserInfo.indexOf(':');
        if (separator < 1) {
            throw new IllegalArgumentException("DATABASE_URL must include a username and password.");
        }

        String host = uri.getHost().contains(":") ? "[" + uri.getHost() + "]" : uri.getHost();
        int port = uri.getPort() < 0 ? 5432 : uri.getPort();
        String query = uri.getRawQuery();
        if (query != null) {
            query = query.replaceAll("(?i)(^|&)channel_binding=", "$1channelBinding=");
        }
        String jdbcUrl = "jdbc:postgresql://" + host + ":" + port + uri.getRawPath()
                + (query == null ? "" : "?" + query);

        return new NeonDatabaseUrl(
                jdbcUrl,
                decode(rawUserInfo.substring(0, separator)),
                decode(rawUserInfo.substring(separator + 1)));
    }

    private static String decode(String value) {
        return URLDecoder.decode(value.replace("+", "%2B"), StandardCharsets.UTF_8);
    }
}
