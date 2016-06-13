
import java.io.IOException;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class JSONUtil {
	public final static ObjectMapper mapper = new ObjectMapper();
	
	
 /**
  * 将json字符串转换为json对象
  * @author: 李海宁
  * @date: 2015年12月2日 下午3:12:34
  * @param jsonStr json字符串
  * @return JsonNode json对象
  * @throws JsonProcessingException
  * @throws IOException
  */
 public static JsonNode jsonStrToJsonNode(String jsonStr) throws JsonProcessingException, IOException{
	 JsonNode jsonNode = mapper.readTree(jsonStr);
	 return jsonNode;
 }
 /**
  * json字符串转map
  * @author: 李海宁
  * @date: 2015年12月3日 下午3:42:57
  * @param jsonStr json字符串
  * @return  Map《String，String》
 * @throws IOException 
 * @throws JsonMappingException 
 * @throws JsonParseException 
  */
 public static Map<String,String> jsonToMap(String jsonStr) throws JsonParseException, JsonMappingException, IOException{
	@SuppressWarnings("unchecked")
	Map<String,String> map = mapper.readValue(jsonStr, Map.class);
	 return map;
 }
 /**
  * java对象转json字符串
  * @author: 李海宁
  * @date: 2015年12月22日 下午3:26:45
  * @param obj java对象
  * @return json字符串
  * @throws Exception
  */
	public static String javaBeanToJson(Object obj) throws Exception{
		return mapper.writeValueAsString(obj);
	}
}
