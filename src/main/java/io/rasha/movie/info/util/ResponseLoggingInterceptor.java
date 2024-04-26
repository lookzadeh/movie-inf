package io.rasha.movie.info.util;

import feign.RequestInterceptor;
import feign.Response;
import feign.ResponseMapper;
import java.lang.reflect.Type;

public class ResponseLoggingInterceptor implements ResponseMapper {

  @Override
  public Response map(Response response, Type type) {
    String body = response.body().toString();
    return response;
  }
}
