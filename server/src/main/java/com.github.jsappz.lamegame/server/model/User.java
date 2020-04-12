package com.github.jsappz.lamegame.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static java.util.Objects.requireNonNull;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonPropertyOrder(alphabetic = true)
@JsonDeserialize(builder = User.Builder.class)
public class User implements ApiObject {
    private final String username;
    private final int id;

    private User(Builder b) {
        this.id = b.id;
        this.username = requireNonNull(b.username, "username");
    }

    @JsonProperty("id")
    public int getId() {
        return id;
    }

    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    public User.Builder toBuilder() {
        return builder()
                .username(username)
                .id(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return new EqualsBuilder()
                .append(id, user.id)
                .append(username, user.username)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(username)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("username", username)
                .toString();
    }


    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {
        @JsonProperty("id")
        private int id;

        @JsonProperty("username")
        private String username;

        private Builder() {
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
