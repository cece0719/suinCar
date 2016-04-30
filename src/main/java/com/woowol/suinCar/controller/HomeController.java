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

import java.util.Map;

@Controller
public class HomeController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@RequestMapping(value = "/")
	public synchronized String home(@RequestParam Map<String, String> params, Model model) throws InterruptedException {
//		final GpioController gpio = GpioFactory.getInstance();
//		final GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04, "MyLED");
//		pin.setShutdownOptions(true);
//		if (StringUtil.isNotNullOrEmpty(params.get("led"))) {
//			if ("on".equals(params.get("led"))) {
//				pin.high();
//			} else {
//				pin.low();
//			}
//		}
//		gpio.shutdown();
//		gpio.unprovisionPin(pin);
//
//		logger.info("hello~!");
//		model.addAttribute("msg", pin.isHigh()?"on":"off");
		return "home";
	}

	@RequestMapping(value = "/led")
	public synchronized String led(@RequestParam Map<String, String> params) {
		final GpioController gpio = GpioFactory.getInstance();
		final GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04, "MyLED");
		pin.setShutdownOptions(true);
		if (!StringUtils.isEmpty(params.get("status")) && "on".equals(params.get("status"))) {
			pin.high();
		} else {
			pin.low();
		}
		gpio.shutdown();
		gpio.unprovisionPin(pin);
		return "home";
	}
}

