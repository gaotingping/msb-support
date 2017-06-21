package com.mvw.china.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.ClassUtils;

import com.mvw.china.bus.annotation.ApiService;

public class ScannerUtils{

	private static final String RESOURCE_PATTERN = "/**/*.class";

	private static ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
	
	private static TypeFilter typeFilter = new AnnotationTypeFilter(ApiService.class, false);

	private static List<Class<?>> classSet = new ArrayList<Class<?>>();

	public static List<Class<?>> getServiceEntry(List<String> packages) throws IOException,ClassNotFoundException {
		classSet.clear();
		if (!packages.isEmpty()) {
			for (String pkg : packages) {
				String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + ClassUtils.convertClassNameToResourcePath(pkg) + RESOURCE_PATTERN;
				Resource[] resources = resourcePatternResolver.getResources(pattern);
				MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
				for (Resource resource : resources) {
					if (resource.isReadable()) {
						MetadataReader reader = readerFactory.getMetadataReader(resource);
						String className = reader.getClassMetadata().getClassName();
						if (matchesEntityTypeFilter(reader, readerFactory)) {
							classSet.add(Class.forName(className));
						}
					}
				}
			}
		}
		return classSet;
	}

	private static boolean matchesEntityTypeFilter(MetadataReader reader,
			MetadataReaderFactory readerFactory) throws IOException {
		return typeFilter.match(reader, readerFactory);
	}
}