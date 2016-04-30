package com.woowol.suinCar.controller;


import com.pi4j.io.gpio.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

	private GpioController gpio = GpioFactory.getInstance();

	private List<Map<String, Object>> controlList = new ArrayList<Map<String, Object>>(){{
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
	public synchronized String home(Model model) throws InterruptedException {
		for (Map<String, Object> one : controlList) {
			GpioPinDigitalOutput pinDigitalOutput = gpio.provisionDigitalOutputPin((Pin) one.get("pin"));
			pinDigitalOutput.setShutdownOptions(true);

			one.put("isOn", pinDigitalOutput.isHigh());

			gpio.shutdown();
			gpio.unprovisionPin(pinDigitalOutput);
		}
		model.addAttribute("controlList", controlList);
		return "home";
	}

	@RequestMapping(value = "/call")
	@ResponseBody
	public synchronized Map<String, String> call(@RequestParam Map<String, String> params) {
		int index = Integer.parseInt(params.get("index"));
		Map<String, Object> one = controlList.get(index);
		if ("onOff".equals(one.get("type"))) {
			GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin((Pin) one.get("pin"));
			pin.setShutdownOptions(true);
			if ("on".equals(params.get("status"))) {
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

