public class LocationUtil {
   public static String[] getLocation(String ip) {
      String[] returnString=new String[2];
      try {
         Map map = new HashMap();
         map.put("ip", ip);
         map.put("ak", "z8cPGX5TKKrZOYbrAlgYcnSYHFm6o5cE");
         map.put("coor", "bd09ll");
         String StringReturn = HttpRequest.sendPost("http://api.map.baidu.com/location/ip", map);
         
         JSONObject jobject=JSONObject.fromObject(StringReturn);
            logger.info(StringReturn);
            if(jobject.getInt("status")==0) {
               JSONObject point= jobject.getJSONObject("content").getJSONObject("point");
               String x=point.getString("x");
               String y=point.getString("y");
               returnString= new String[] { x, y };
            }
            
      } catch (Exception e) {
         e.printStackTrace();
      }
      return returnString;

   }

public static String sendPost(String url, Map<String, String> paramMap) {
   CloseableHttpClient httpclient = HttpClients.createDefault();
   HttpPost httpPost = new HttpPost(url);
   CloseableHttpResponse response = null;
   InputStreamReader ins = null;
   BufferedReader in = null;
   StringBuffer result = new StringBuffer();
   HttpEntity entity = null;
   try {
      // 拼接参数
      List<NameValuePair> nvps = new ArrayList<NameValuePair>();
      for (String param : paramMap.keySet()) {
         nvps.add(new BasicNameValuePair(param, paramMap.get(param)));
      }
      httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
      response = httpclient.execute(httpPost);
      if(response.getStatusLine().getStatusCode()!=200){
         //throw new Exception("请求异常");
         return null;
      }
      entity = response.getEntity();
      // 消耗掉response
      // 定义BufferedReader输入流来读取URL的响应
      ins = new InputStreamReader(entity.getContent(), "UTF-8");
      in = new BufferedReader(ins);
      String line;
      while ((line = in.readLine()) != null) {
         result.append(line);
      }

      return result.toString();
   } catch (Exception e) {
      log.error("post请求异常", e);

   } finally {
      close(httpclient, response, ins, in, entity);
   }
   return null;
}

}
