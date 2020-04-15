package com.github.jsappz.lamegame.sdk;

import com.github.jsappz.lamegame.model.JsonConverter;
import com.github.jsappz.lamegame.model.User;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.exception.ContextedRuntimeException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultServiceUnavailableRetryStrategy;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.requireNonNull;

public class LameGameClientImpl implements LameGameClient {
    private final HttpClientBuilder clientBuilder;
    private final RequestConfig config;
    private final String scheme;
    private final String host;
    private final int port;

    private LameGameClientImpl(Builder b) {
        this.scheme = requireNonNull(b.scheme, "scheme");
        this.host = requireNonNull(b.host, "host");
        Validate.isTrue(b.port > -1);
        this.port = b.port;
        this.config = RequestConfig.custom()
                .setConnectTimeout(b.timeout)
                .setConnectionRequestTimeout(b.timeout)
                .setSocketTimeout(b.timeout)
                .build();
        this.clientBuilder = HttpClientBuilder.create()
                //TODO extend ServiceUnavailableRetryStrategy errors than 503 are retryable
                .setServiceUnavailableRetryStrategy(new DefaultServiceUnavailableRetryStrategy(b.maxRetries, b.retryInterval));
    }

    @Override
    public User getUser(String username) {
        try (CloseableHttpResponse response = httpGet(buildUri(Paths.USER))) {
            return JsonConverter.readFromStream(response.getEntity().getContent(), User.class);
        } catch (IOException e) {
            throw new ContextedRuntimeException("Can't fetch user" + e, e)
                    .addContextValue("scheme", scheme)
                    .addContextValue("host", host)
                    .addContextValue("path", Paths.USER.path)
                    .addContextValue("port", port)
                    .addContextValue("username", username);
        }
    }

    @Override
    public boolean createUser(User user) {
        Map<String, String> userParams = new HashMap<>();
        userParams.put("username", user.getUsername());
        try (CloseableHttpResponse response = httpPost(buildUri(Paths.USER, userParams))) {
            return true;
        } catch (IOException e) {
            throw new ContextedRuntimeException("Can't create user " + e, e)
                    .addContextValue("scheme", scheme)
                    .addContextValue("host", host)
                    .addContextValue("path", Paths.USER.path)
                    .addContextValue("port", port)
                    .addContextValue("user", user);
        }
    }

    private CloseableHttpResponse httpGet(URI uri) {
        try (CloseableHttpClient client = clientBuilder.build()) {
            HttpGet request = new HttpGet(uri);
            request.setConfig(config);
            request.setHeader("Content-type", "application/json");
            return client.execute(request);
        } catch (IOException e) {
            throw new ContextedRuntimeException("Failed request " + e, e)
                    .addContextValue("uri", uri.toString());
        }
    }

    private CloseableHttpResponse httpPost(URI uri) {
        try (CloseableHttpClient client = clientBuilder.build()) {
            HttpPost request = new HttpPost(uri);
            request.setConfig(config);
            request.setHeader("Content-type", "application/json");
            return client.execute(request);
        } catch (IOException e) {
            throw new ContextedRuntimeException("Failed request " + e, e)
                    .addContextValue("uri", uri.toString());
        }
    }

    public URI buildUri(Paths path) {
        return buildUri(path, Collections.emptyMap());
    }

    public URI buildUri(Paths path, Map<String, String> params) {
        URIBuilder builder = new URIBuilder()
                .setScheme(scheme)
                .setHost(host)
                .setPort(port)
                .setPath(path.path);
        for (Map.Entry<String, String> param : params.entrySet()) {
            builder.setParameter(param.getKey(), param.getValue());
        }

        try {
            return builder.build();
        } catch (URISyntaxException e) {
            throw new ContextedRuntimeException("Failed to build url " + e, e)
                    .addContextValue("schema", scheme)
                    .addContextValue("host", host)
                    .addContextValue("path", path.path)
                    .addContextValue("params", params);
        }
    }

    private enum Paths {
        USER("user");

        private String path;

        Paths(String path) {
            this.path = path;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String scheme;
        private String host;
        private int port = -1;
        private int timeout = 60;
        private int maxRetries = 3;
        private int retryInterval = 3000;

        private Builder() {}

        public Builder scheme(String scheme) {
            this.scheme = scheme;
            return this;
        }

        public Builder host(String host) {
            this.host = host;
            return this;
        }

        public Builder port(int port) {
            this.port = port;
            return this;
        }

        public Builder timeout(int timeout) {
            this.timeout = timeout;
            return this;
        }

        public Builder maxRetries(int maxRetries) {
            this.maxRetries = timeout;
            return this;
        }

        public Builder retryInterval(int retryInterval) {
            this.retryInterval = retryInterval;
            return this;
        }

        public LameGameClientImpl build() {
            return new LameGameClientImpl(this);
        }
    }
}
