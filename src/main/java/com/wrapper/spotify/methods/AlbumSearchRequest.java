package com.wrapper.spotify.methods;

import com.google.common.util.concurrent.SettableFuture;
import com.wrapper.spotify.JsonUtil;
import com.wrapper.spotify.exceptions.*;
import com.wrapper.spotify.models.AlbumSimplified;
import com.wrapper.spotify.models.Paging;
import net.sf.json.JSONObject;

import java.io.IOException;

public class AlbumSearchRequest extends AbstractRequest {

  protected AlbumSearchRequest(Builder builder) {
    super(builder);
  }

  public static Builder builder() {
    return new Builder();
  }

  public SettableFuture<Paging<AlbumSimplified>> getAsync() {
    SettableFuture<Paging<AlbumSimplified>> searchResultFuture = SettableFuture.create();

    try {
      final JSONObject jsonObject = JSONObject.fromObject(getJson());
      searchResultFuture.set(JsonUtil.createSimpleAlbumPage(jsonObject.getJSONObject("albums")));
    } catch (Exception e) {
      searchResultFuture.setException(e);
    }

    return searchResultFuture;
  }

  public Paging<AlbumSimplified> get() throws
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
    final JSONObject jsonObject = JSONObject.fromObject(getJson());
    return JsonUtil.createSimpleAlbumPage(JSONObject.fromObject(jsonObject).getJSONObject("albums"));
  }

  public static final class Builder extends AbstractRequest.Builder<Builder> {

    public Builder query(String query) {
      assert (query != null);
      setPath("/v1/search");
      setParameter("type", "album");
      return setParameter("q", query);
    }

    public Builder market(String market) {
      assert (market != null);
      return setParameter("market", market);
    }

    public Builder limit(int limit) {
      assert (limit > 0);
      return setParameter("limit", String.valueOf(limit));
    }

    public Builder offset(int offset) {
      assert (offset >= 0);
      return setParameter("offset", String.valueOf(offset));
    }

    public AlbumSearchRequest build() {
      return new AlbumSearchRequest(this);
    }

  }

}
