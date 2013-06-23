package ua.dp.ardas.radiator.resr.client;

import org.codehaus.jackson.map.ObjectMapper;

public class RadiatorObjectMapper extends ObjectMapper {

	public RadiatorObjectMapper() {
		super();
//		configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
//		setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"));
//
//		SimpleModule module = new SimpleModule("CusomModuleForMediaType", new Version(1, 0, 0, null));
//		module.addSerializer(new JsonMediaEnumSerializer());
//		module.addSerializer(new JsonStatusObjectiveSerializer());
//		registerModule(module);
		
//		configure(SerializationConfig.Feature.WRAP_ROOT_VALUE, false);
//		configure(DeserializationConfig.Feature.USE_ANNOTATIONS, true);
//		configure(DeserializationConfig.Feature.AUTO_DETECT_FIELDS, true);
//		configure(DeserializationConfig.Feature.UNWRAP_ROOT_VALUE, true);
	}
	
//
//	public static void main(String[] args) {
//
//	    ObjectMapper mapper = new RadiatorObjectMapper();
//	    ObjectWriter writer = mapper.writer().withRootName("wrapper");
//	    String json = writer.writeValueAsString(new Bean());
//	    assertEquals("{\"wrapper\":{\"a\":3}}", json);
//
//	    ObjectReader reader = mapper.reader(Bean.class).withRootName("wrapper");
//	    Bean bean = reader.readValue(json);
//	    assertNotNull(bean);
//
//	    // also: verify that we can override SerializationFeature as well:
//	    ObjectMapper wrapping = rootMapper();
//	    json = wrapping.writer().withRootName("something").writeValueAsString(new Bean());
//	    assertEquals("{\"something\":{\"a\":3}}", json);
//	    json = wrapping.writer().withRootName("").writeValueAsString(new Bean());
//	    assertEquals("{\"a\":3}", json);
//
//	    bean = wrapping.reader(Bean.class).withRootName("").readValue(json);
//	    assertNotNull(bean);
//	}
}