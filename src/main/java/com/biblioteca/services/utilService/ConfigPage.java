package com.biblioteca.services.utilService;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class ConfigPage {
	
	public static Pageable configSort(Pageable pageable) {
		Sort sort = Sort.by(Sort.Direction.DESC,"id");
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        return pageable;
	}
}
