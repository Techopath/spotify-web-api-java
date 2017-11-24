package com.wrapper.spotify.methods;

import com.google.common.util.concurrent.SettableFuture;
import com.wrapper.spotify.JsonUtil;
import com.wrapper.spotify.exceptions.*;
import com.wrapper.spotify.models.NewReleases;
import net.sf.json.JSONObject;

import java.io.IOException;

public class NewReleasesRequest extends AbstractRequest {

  protected NewReleasesRequest(Builder builder) {
    super(builder);
  }

  public static Builder builder() {
    return new Builder();
  }

  public SettableFuture<NewReleases> getAsync() {
    final SettableFuture<NewReleases> newReleasesFuture = SettableFuture.create();

    try {
      newReleasesFuture.set(JsonUtil.createNewReleases(JSONObject.fromObject(getJson())));
    } catch (Exception e) {
      newReleasesFuture.setException(e);
    }

    return newReleasesFuture;
  }

  public NewReleases get() throws
          IOException,
          NoContentException,
          BadRequestException,
          UnauthorizedException,
          ForbiddenException,
          NotFoundException,
          TooManyRequestsException,
          InternalServerErrorException,
          BadGatewayException,
          ServiceUnavailableException {
    return JsonUtil.createNewReleases(JSONObject.fromObject(getJson()));
  }

  public static final class Builder extends AbstractRequest.Builder<Builder> {

    public Builder limit(int limit) {
      assert (limit > 0);
      return setParameter("limit", String.valueOf(limit));
    }

    public Builder offset(int offset) {
      assert (offset >= 0);
      return setParameter("offset", String.valueOf(offset));
    }

    public Builder country(String countryCode) {
      assert (countryCode != null);
      return setParameter("country", countryCode);
    }

    public NewReleasesRequest build() {
      setPath("/v1/browse/new-releases");
      return new NewReleasesRequest(this);
    }

  }
}