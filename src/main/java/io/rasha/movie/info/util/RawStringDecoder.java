package io.rasha.movie.info.util;

import feign.FeignException;
import feign.Response;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import java.io.IOException;
import java.lang.reflect.Type;

public class RawStringDecoder implements Decoder {

  @Override
  public Object decode(Response response, Type type)
      throws IOException, DecodeException, FeignException {
    String responseBody = new String(response.body().toString());
    // You can now access the raw response body as a String
    // ... do something with responseBody ...

    // Delegate to default decoder for model mapping
    return responseBody;
  }
}