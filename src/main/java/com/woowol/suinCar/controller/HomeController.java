package com.woowol.suinCar.controller;


import com.pi4j.io.gpio.*;
import com.pi4j.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	private List<Map<String, Object>> list= new ArrayList<Map<String, Object>>(){{
		add(new HashMap<String, Object>(){{
			put("name", "LED1");
			put("type", "onOff");
			put("pin", RaspiPin.GPIO_04);
		}});
		add(new HashMap<String, Object>(){{
			put("name", "LED2");
			put("type", "onOff");
			put("pin", RaspiPin.GPIO_01);
		}});
	}};

	@RequestMapping(value = "/")
	public synchronized String home(@RequestParam Map<String, String> params, Model model) throws InterruptedException {
		for (Map<String, Object> one : list) {
			Pin pin = (Pin) one.get("pin");
			GpioController gpio = GpioFactory.getInstance();
			GpioPinDigitalOutput pinDigitalOutput = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04, "MyLED");
			pinDigitalOutput.setShutdownOptions(true);

			one.put("isOn", pinDigitalOutput.isHigh());

			gpio.shutdown();
			gpio.unprovisionPin(pinDigitalOutput);
//			one.put("isOn", true);
		}
		model.addAttribute("list", list);
		return "home";
	}

	@RequestMapping(value = "/call")
	@ResponseBody
	public synchronized Map<String, String> call(@RequestParam Map<String, String> params) {
		int index = Integer.parseInt(params.get("index"));
		Map<String, Object> one = list.get(index);
		if ("onOff".equals(one.get("type"))) {
			GpioController gpio = GpioFactory.getInstance();
			GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin((Pin) one.get("pin"), (String) one.get("name"));
			pin.setShutdownOptions(true);
			if (!StringUtils.isEmpty(params.get("status")) && "on".equals(params.get("status"))) {
				pin.high();
			} else {
				pin.low();
			}
			gpio.shutdown();
			gpio.unprovisionPin(pin);
		}
		return new HashMap<String, String>(){{
			put("rtn", "success");
		}};
	}
}

