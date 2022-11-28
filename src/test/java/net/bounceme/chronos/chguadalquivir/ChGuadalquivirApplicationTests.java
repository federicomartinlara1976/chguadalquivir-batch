package net.bounceme.chronos.chguadalquivir;

import java.util.Map;

import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.chguadalquivir.model.Embalse;
import net.bounceme.chronos.chguadalquivir.reader.DailyRegisterItemReader;
import net.bounceme.chronos.chguadalquivir.support.CHGuadalquivirHelper;

@SpringBootTest
@Slf4j
class ChGuadalquivirApplicationTests {
	
	@Value("${application.url}")
	private String url;

	@Autowired
	private ApplicationContext ctx;
	
	@Test
	void contextLoads() {
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testObtainRecords() {
		DailyRegisterItemReader<Embalse> reader = ctx.getBean("dailyRegisterItemReader", DailyRegisterItemReader.class);
		Assert.assertNotNull(reader);
	}
	
	@Test
	public void testHelper() {
		try {
			CHGuadalquivirHelper helper = new CHGuadalquivirHelper();
			Document document = helper.retrieveDocument(url);
			Map<String, String> postData = helper.initFormData(document);
			
			Assert.assertNotNull(postData);
			Assert.assertNotEquals(0, postData.size());
			
			String name = helper.getNameFrom(document, "form2", "DDBzona");
			log.debug(name);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

}
