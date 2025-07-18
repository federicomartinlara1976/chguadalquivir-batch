package net.bounceme.chronos.chguadalquivir.support;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.chguadalquivir.model.Zona;
import net.bounceme.chronos.dto.chguadalquivir.CHGuadalquivirMessageDTO;
import net.bounceme.chronos.dto.chguadalquivir.MessageType;

@Component
@Scope("prototype")
@Slf4j
public class CHGuadalquivirHelper {

	private static final String VALUE = "value";

	private SimpleDateFormat dateFormat;
	
	public CHGuadalquivirHelper(SimpleDateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

	/**
	 * @param mapper
	 * @param zonasJson
	 * @return
	 */
	public List<Zona> getZonesFromJsonResource(ObjectMapper mapper, String zonasJson) {
		try (InputStream istream = getClass().getResourceAsStream("/" + zonasJson)) {
			return mapper.readValue(istream, new TypeReference<List<Zona>>() {
			});

		} catch (IOException e) {
			log.error(e.getMessage());
			return Collections.emptyList();
		}
	}

	/**
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public Document retrieveDocument(String url) throws IOException {
		log.debug("Connecting to {}...", url);
		return Jsoup.connect(url).get();
	}

	@SneakyThrows
	public Map<String, String> initFormData(Document document) {
		Map<String, String> data = new HashMap<>();

		// Datos requeridos para hacer el POST
		String eventValidation = document.select("input[name=__EVENTVALIDATION]").first().attr(VALUE);
		String viewState = document.select("input[name=__VIEWSTATE]").first().attr(VALUE);
		String viewStateGen = document.select("input[name=__VIEWSTATEGENERATOR]").first().attr(VALUE);

		data.put("__EVENTVALIDATION", eventValidation);
		data.put("__VIEWSTATE", viewState);
		data.put("__VIEWSTATEGENERATOR", viewStateGen);

		return data;
	}

	/**
	 * @param document
	 * @param idForm
	 * @param idSelect
	 * @return
	 * @throws Exception
	 */
	@SneakyThrows
	public String getNameFrom(Document document, String idForm, String idSelect) {

		Element formElement = document.getElementById(idForm);
		if (!Objects.isNull(formElement)) {
			Element select = formElement.getElementById(idSelect);
			return select.attr("name");
		}

		return StringUtils.EMPTY;
	}

	/**
	 * @param d
	 * @param numOfDays
	 * @return
	 */
	public Date subtractDays(Date date, Integer numOfDays) {
		LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

		// minus numOfDays
		localDateTime = localDateTime.minusDays(numOfDays);

		// convert LocalDateTime to date
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * @param date
	 * @return
	 */
	public String parseDate(Date date) {
		return dateFormat.format(date);
	}
	
	@SneakyThrows
	public Date fromString(String sDate) {
		return dateFormat.parse(sDate);
	}

	/**
	 * @param input
	 * @param scale
	 * @return
	 */
	public Double round(Double input, Integer scale) {
		BigDecimal bd = BigDecimal.valueOf(input).setScale(scale, RoundingMode.HALF_EVEN);
		return bd.doubleValue();
	}
	
	public <T> CHGuadalquivirMessageDTO<T> buidMessage(T data, Class<T> typeClass, MessageType messageType) {
		CHGuadalquivirMessageDTO<T> messageDTO = new CHGuadalquivirMessageDTO<>();
		messageDTO.setClassName(typeClass.getName());
		messageDTO.setMessageType(messageType);
		messageDTO.setData(data);
		
		return messageDTO;
	}
}
