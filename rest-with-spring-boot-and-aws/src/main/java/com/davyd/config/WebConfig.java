package com.davyd.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.davyd.converter.YamlJackson2HttpMessageConverter;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(new YamlJackson2HttpMessageConverter());
	}

	private static final MediaType MEDIA_TYPE_APPLICATION_YML = MediaType.valueOf("application/x-yaml");

	// Via QUERY PARAM
	// http://localhost:8080/person/v1/4?mediaType=xml
//	@Override
//	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
//		configurer.favorParameter(true)//
//				.parameterName("mediaType")//
//				.ignoreAcceptHeader(true)//
//				.useRegisteredExtensionsOnly(false)//
//				.defaultContentType(MediaType.APPLICATION_JSON)//
//				.mediaType("json", MediaType.APPLICATION_JSON)//
//				.mediaType("xml", MediaType.APPLICATION_XML);
//	}

	// Via HEADER PARAM
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.favorParameter(false)//
				.ignoreAcceptHeader(false)//
				.useRegisteredExtensionsOnly(false)//
				.defaultContentType(MediaType.APPLICATION_JSON)//
				.mediaType("json", MediaType.APPLICATION_JSON)//
				.mediaType("x-yaml", MEDIA_TYPE_APPLICATION_YML)//
				.mediaType("xml", MediaType.APPLICATION_XML);
	}

}
