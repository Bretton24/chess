package Request;

import com.google.gson.Gson;

public class BaseReq {
  public static Gson serial = new Gson();
  public static String stringify(spark.Request req){return req.body();};
}
