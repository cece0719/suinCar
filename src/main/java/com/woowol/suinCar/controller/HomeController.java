package com.woowol.suinCar.controller;


import com.pi4j.io.gpio.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@RequestMapping(value = "/")
	public synchronized String home(Model model) throws InterruptedException {
		final GpioController gpio = GpioFactory.getInstance();
		final GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04, "MyLED");
		pin.setShutdownOptions(true);
		Thread.sleep(1000);
		pin.high();
		Thread.sleep(1000);
		pin.toggle();
		Thread.sleep(1000);
		pin.toggle();
		Thread.sleep(1000);
		pin.toggle();
		gpio.shutdown();
		gpio.unprovisionPin(pin);

		logger.info("hello~!");
		model.addAttribute("msg", "helloWorld");
		return "home";
	}
}

