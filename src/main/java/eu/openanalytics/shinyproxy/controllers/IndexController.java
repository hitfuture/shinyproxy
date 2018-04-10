/**
 * ShinyProxy
 *
 * Copyright (C) 2016-2018 Open Analytics
 *
 * ===========================================================================
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Apache License as published by
 * The Apache Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Apache License for more details.
 *
 * You should have received a copy of the Apache License
 * along with this program.  If not, see <http://www.apache.org/licenses/>
 */
package eu.openanalytics.shinyproxy.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import eu.openanalytics.shinyproxy.services.AppService.ShinyApp;
 
@Controller
public class IndexController extends BaseController {
	
	@RequestMapping("/")
    String index(ModelMap map, HttpServletRequest request) {
		prepareMap(map, request);
		
		List<ShinyApp> apps = userService.getAccessibleApps(SecurityContextHolder.getContext().getAuthentication());
		map.put("apps", apps.toArray());

		Map<ShinyApp, String> appLogos = new HashMap<>();
		map.put("appLogos", appLogos);
		
		boolean displayAppLogos = false;
		boolean categoryMenu = false; /*Add the ability to create a side menu that has a menu of categories and apps */
		for (ShinyApp app: apps) {
			if (app.getLogoUrl() != null) {
				displayAppLogos = true;
				appLogos.put(app, resolveImageURI(app.getLogoUrl()));
			}
		}
		map.put("displayAppLogos", displayAppLogos);

		return "index";
    }
}