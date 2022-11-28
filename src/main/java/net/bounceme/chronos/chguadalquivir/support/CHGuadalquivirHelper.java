package net.bounceme.chronos.chguadalquivir.support;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.chguadalquivir.model.Zona;

@Slf4j
public class CHGuadalquivirHelper {

	/**
	 * @param mapper
	 * @param zonasJson
	 * @return
	 */
	public List<Zona> getZonesFromJsonResource(ObjectMapper mapper, String zonasJson) {
		try (InputStream istream = getClass().getResourceAsStream("/" + zonasJson)) {
			List<Zona> zonas = mapper.readValue(istream, new TypeReference<List<Zona>>() {
			});

			return zonas;
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

	public Map<String, String> initFormData(Document document) throws Exception {
		try {
			Map<String, String> data = new HashMap<>();
	
			String eventValidation = document.select("input[name=__EVENTVALIDATION]").first().attr("value");
		    String viewState = document.select("input[name=__VIEWSTATE]").first().attr("value");
		    String viewStateGen = document.select("input[name=__VIEWSTATEGENERATOR]").first().attr("value");
		    
		    data.put("__EVENTVALIDATION", eventValidation);
		    data.put("__VIEWSTATE", viewState);
		    data.put("__VIEWSTATEGENERATOR", viewStateGen);
	
			return data;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
	}
	
	/**
	 * @param document
	 * @param idForm
	 * @param idSelect
	 * @return
	 * @throws Exception
	 */
	public String getNameFrom(Document document, String idForm, String idSelect) throws Exception {
		try {
			Element formElement = document.getElementById(idForm);
			if (!Objects.isNull(formElement)) {
				Element select = formElement.getElementById(idSelect);
				return select.attr("name");
			}
			
			return StringUtils.EMPTY;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
	}
}
